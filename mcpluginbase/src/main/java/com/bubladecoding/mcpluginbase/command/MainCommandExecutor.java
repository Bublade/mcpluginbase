/*
 * Copyright (c) 2021 bublade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bubladecoding.mcpluginbase.command;

import com.bubladecoding.mcpluginbase.PluginBase;
import com.bubladecoding.mcpluginbase.command.annotation.Option;
import com.bubladecoding.mcpluginbase.command.annotation.Selector;
import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;
import com.bubladecoding.mcpluginbase.command.parsing.*;
import com.bubladecoding.mcpluginbase.parsing.Parser;
import com.bubladecoding.mcpluginbase.parsing.parses.*;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainCommandExecutor implements CommandExecutor {

    private final HashMap<String, CommandMethod> commands;
    private final PluginBase pluginBase;
    private final HashMap<Class<?>, Parser<?>> baseParsers = new HashMap<>();

    public MainCommandExecutor(PluginBase pluginBase) {
        this.pluginBase = pluginBase;
        this.commands = new HashMap<>();

        registerParser(Boolean.class, new BooleanParser());
        registerParser(Double.class, new DoubleParser());
        registerParser(Float.class, new FloatParser());
        registerParser(Integer.class, new IntParser());
        registerParser(String.class, new StringParser());

        registerParser(Material.class, new MaterialParser());
        registerParser(OfflinePlayer.class, new OfflinePlayerParser());
        registerParser(Player.class, new PlayerParser());
    }

    public void addCommands(CommandBase<?> commandBase) {
        CommandClassParser ccp = new CommandClassParser(new CommandMethodParser(pluginBase, new CommandArgumentParser(pluginBase)));
        CommandClass cc = ccp.parseCommandClass(commandBase.getClass());

        Object parent = pluginBase.createInjectedClass(commandBase.getClass());
        CommandMethod[] cms = cc.commandMethods;
        for (CommandMethod cm : cms) {
            PluginCommand pc = pluginBase.getServer().getPluginCommand(cm.getName());
            if (pc != null) {
                pc.setExecutor(this);
                cm.setParent(parent);
                commands.put(cm.getName(), cm);
            }

            List<Class<?>> optionsCollection = Arrays.stream(cm.getArguments())
                    .flatMap(ca -> Arrays.stream(ca.getOptions()))
                    .map(Option::optionClass)
                    .distinct()
                    .collect(Collectors.toList());

            registerOptions(optionsCollection);

        }
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandMethod method = commands.get(command.getName());
        if (method == null) {
            return false;
        }

        ArrayList<String> lstArgs = new ArrayList<>(Arrays.asList(args));

        CommandArgument<?>[] arguments = method.getArguments();
        Object[] arrArguments = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            CommandArgument<?> argument = arguments[i];
            if (argument.isAutoInject()) {
                if (argument.getType().equals(CommandSender.class)) {
                    arrArguments[i] = sender;
                }

                if (argument.getType().equals(Command.class)) {
                    arrArguments[i] = command;
                }

                if (argument.getType().isArray() && argument.getType().getComponentType().equals(String.class)) {
                    arrArguments[i] = lstArgs.toArray(new String[0]);
                    lstArgs.clear();
                    // injected final args
                    break;
                }

                continue;
            }

            if (lstArgs.size() == 0) {
                if (!argument.isOptional()) {
                    // todo send not enough arguments message.
                    return false;
                }

                arrArguments[i] = null;
                continue;
            }

            if (argument.getOptions().length > 0) {
                Option[] options = argument.getOptions();
                boolean isSet = false;
                for (Option option : options) {
                    if (lstArgs.get(0).equals(option.name())) {
                        lstArgs.remove(0);
                        arrArguments[i] = pluginBase.getCommandManager().getOption(option.optionClass());
                        isSet = true;
                    }
                }

                if (isSet) {
                    continue;
                }
            }

            if (argument.getSelectors().length > 0) {
                Selector[] selectors = argument.getSelectors();
                boolean isSet = false;
                for (Selector selector : selectors) {
                    if (lstArgs.get(0).equals(selector.name())) {
                        lstArgs.remove(0);
                        int argsCount = lstArgs.size();
                        ParameterParser<?> parser = pluginBase.getCommandManager().getParser(selector.parameterType());
                        if (parser == null) {
                            if (argument.isOptional()) {
                                arrArguments[i] = null;
                                isSet = true;
                                continue;
                            }
                        } else {
                            arrArguments[i] = parser.parse(lstArgs);
                            isSet = true;
                        }

                        if (argsCount == lstArgs.size()) {
                            lstArgs.remove(0);
                        }
                    }
                }

                if (isSet) {
                    continue;
                }
            }

            ParameterParser<?> paramParser = argument.getParameterParser();
            if (paramParser != null) {
                arrArguments[i] = paramParser.parse(command, sender, lstArgs);
            } else if (baseParsers.containsKey(argument.getType())) {
                String value = lstArgs.remove(0);
                arrArguments[i] = baseParsers.get(argument.getType()).parse(value);
            }
        }

        try {
            method.getMethod().invoke(method.getParent(), arrArguments);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    private Object handleArgument(CommandArgument<?> argument, ArrayList<String> args) {

    }


    private <T> void registerParser(Class<T> type, Parser<T> parser) {
        this.baseParsers.put(type, parser);
    }

    private void registerOptions(List<Class<?>> options) {
        for (Class<?> option : options) {
            pluginBase.getCommandManager().registerOption(option);
        }
    }

}
