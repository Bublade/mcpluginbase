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
import com.bubladecoding.mcpluginbase.command.exceptions.ArgumentException;
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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class MainCommandExecutor<T extends PluginBase> implements CommandExecutor {

    private final HashMap<String, CommandMethod> commands;
    private final T pluginBase;
    private final HashMap<Class<?>, Parser<?>> baseParsers = new HashMap<>();

    public MainCommandExecutor(T pluginBase) {
        this.pluginBase = pluginBase;
        this.commands = new HashMap<>();

        registerParser(Boolean.class, new BooleanParser());
        registerParser(Double.class, new DoubleParser());
        registerParser(Float.class, new FloatParser());
        registerParser(Integer.class, new IntParser());
        registerParser(String.class, new StringParser());
        registerParser(Long.class, new LongParser());

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
        if (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h")) {
            return handleHelp(sender, command, method, arguments, lstArgs);
        }

        return handleCommand(sender, command, method, arguments, lstArgs);
    }

    private boolean handleHelp(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull CommandMethod method,
            @NotNull CommandArgument<?>[] arguments,
            @NotNull List<String> args) {
        ArrayList<String> helpCommands = new ArrayList<>();
        helpCommands.add(method.getName());

        for (CommandArgument<?> argument : arguments) {
            if (argument.hasHelpFormat()) {
                appendMultipleHelpRules(helpCommands, Collections.singletonList(argument.getHelpFormat()));
                continue;
            }

            if (argument.hasSelectors()) {
                appendMultipleHelpRules(helpCommands, argument.getSelectors());
                continue;
            }

            if (argument.hasOptions()) {
                appendMultipleHelpRules(helpCommands, argument.getOptions());
                continue;
            }

            if (argument.isAutoInject()) {
                if (!argument.hasName()) {
                    continue;
                }

                String helpFormat = String.format(argument.isOptional() ? "[<%s>]" : "<%s>", argument.getName());
                appendMultipleHelpRules(helpCommands, Collections.singletonList(helpFormat));
                continue;
            }

            String helpFormat = argument.isOptional() ? "[<%s>]" : "<%s>";

            if (argument.getType().isEnum()) {
                List<String> constants = Arrays.stream(argument.getType().getEnumConstants()).map(Object::toString).collect(Collectors.toList());
                helpFormat = String.format(helpFormat, String.join("|", constants));
            } else {
                helpFormat = String.format(helpFormat, argument.getName());
            }

            appendMultipleHelpRules(helpCommands, Collections.singletonList(helpFormat));
        }

        Collections.sort(helpCommands);
        sender.sendMessage(helpCommands.toArray(new String[0]));

        return true;
    }

    private void appendMultipleHelpRules(ArrayList<String> helpCommands, Option[] options) {
        appendMultipleHelpRules(helpCommands, Arrays.stream(options).map(Option::name).collect(Collectors.toList()));
    }

    private void appendMultipleHelpRules(ArrayList<String> helpCommands, Selector[] selectors) {
        appendMultipleHelpRules(helpCommands, Arrays.stream(selectors)
                .map(s -> s.selectorName() + " <" + (s.valueName().isEmpty() ? s.selectorName() : s.valueName()) + ">")
                .collect(Collectors.toList()));
    }

    private void appendMultipleHelpRules(ArrayList<String> helpCommands, List<String> toAppend) {
        ArrayList<String> helpCommandsCopy = new ArrayList<>(helpCommands);
        helpCommands.clear();

        for (String append : toAppend) {
            for (String helpLine : helpCommandsCopy) {
                helpCommands.add(helpLine + " " + append);
            }
        }
    }

    private boolean handleCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull CommandMethod method,
            @NotNull CommandArgument<?>[] arguments,
            @NotNull ArrayList<String> args) {
        try {
            Object[] arrArguments = new Object[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                CommandArgument<?> argument = arguments[i];
                arrArguments[i] = getArgumentValue(sender, command, argument, args);
            }

            Object result = method.getMethod().invoke(method.getParent(), arrArguments);
            if (result instanceof Boolean) {
                return (Boolean) result;
            }

            return true;
        } catch (ArgumentException e) {
            sender.sendMessage(String.format("Not enough arguments, argument '%s' is missing!", e.getName()));
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Object getArgumentValue(
            @NotNull CommandSender sender,
            @NotNull Command command,
            CommandArgument<?> argument,
            ArrayList<String> args
    ) throws ArgumentException {
        if (argument.isAutoInject()) {
            if (argument.getType().equals(CommandSender.class)) {
                return sender;
            }

            if (argument.getType().equals(Command.class)) {
                return command;
            }

            if (argument.getType().isArray() && argument.getType().getComponentType().equals(String.class)) {
                String[] arrArgs = args.toArray(new String[0]);
                args.clear();
                return arrArgs;
            }

            // todo get injection from command class?
            return null;
        }

        if (args.size() == 0) {
            if (!argument.isOptional()) {
                // todo send not enough arguments message.
                throw new ArgumentException("A required argument is not given.", argument.getName(), argument.getType());
            }

            return null;
        }

        if (argument.getOptions().length > 0) {
            return getOptionValue(argument, args);
        }

        if (argument.getSelectors().length > 0) {
            return getSelectorValue(argument, command, sender, args);
        }

        if (argument.getType().isEnum()) {
            List<Object> constants = Arrays.stream(argument.getType().getEnumConstants()).collect(Collectors.toList());

            final String value = args.remove(0);
            return constants.stream().filter(c -> c.toString().equalsIgnoreCase(value)).findFirst().orElse(null);
        }

        ParameterParser<?> paramParser = argument.getParameterParser();
        if (paramParser != null) {
            return paramParser.parse(command, sender, args);
        } else if (baseParsers.containsKey(argument.getType())) {
            String value = args.remove(0);
            return baseParsers.get(argument.getType()).parse(value);
        }

        return null;
    }

    @Nullable
    private Object getSelectorValue(
            CommandArgument<?> argument,
            @NotNull Command command,
            @NotNull CommandSender sender,
            ArrayList<String> args) {
        Selector[] selectors = argument.getSelectors();
        for (Selector selector : selectors) {
            if (args.get(0).equals(selector.selectorName())) {
                args.remove(0);
                int argsCount = args.size();
                ParameterParser<?> parser = pluginBase.getCommandManager().getParser(selector.parameterType());
                Object value = null;
                if (parser != null) {
                    value = parser.parse(command, sender, args);
                }

                if (argsCount == args.size()) {
                    args.remove(0);
                }
                return value;
            }
        }

        return null;
    }

    @Nullable
    private Object getOptionValue(CommandArgument<?> argument, ArrayList<String> args) {
        Option[] options = argument.getOptions();
        for (Option option : options) {
            if (args.get(0).equals(option.name())) {
                args.remove(0);
                return pluginBase.getCommandManager().getOption(option.optionClass());
            }
        }

        return null;
    }


    private <A> void registerParser(Class<A> type, Parser<A> parser) {
        this.baseParsers.put(type, parser);
    }

    private void registerOptions(List<Class<?>> options) {
        for (Class<?> option : options) {
            pluginBase.getCommandManager().registerOption(option);
        }
    }

}
