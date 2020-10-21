/*
 * Copyright (c) 2020 bublade
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

import com.bubladecoding.mcpluginbase.command.argument.ArgumentParser;
import com.bubladecoding.mcpluginbase.command.annotations.Argument;
import com.bubladecoding.mcpluginbase.command.annotations.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.interfaces.ArgumentTabCompleter;
import net.minecraft.server.v1_16_R2.CommandMe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandBase implements TabExecutor {

    private String name;
    private CommandMethod main;
    private CommandMethod[] subCommands;
    private String[] subCommandNames;

    public CommandBase() {
        this.name = getClass().getSimpleName().replace("Command", "").toLowerCase();
        readCommands();
    }

    public CommandBase(String name) {
        this.name = name;
        readCommands();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> originalArgs = new ArrayList<>(Arrays.asList(args));
        CommandMethod method = getCommandMethod(originalArgs);
        ArgumentTabCompleter completer = ArgumentParser.getTabCompleter(originalArgs, method);
        if (completer == null || originalArgs.size() == 0) {
            return Collections.emptyList();
        }

        return completer.complete(originalArgs.get(0));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> originalArgs = new ArrayList<>(Arrays.asList(args));
        CommandMethod method = getCommandMethod(originalArgs);

        Object[] arguments = ArgumentParser.parse(originalArgs, method);
        Object[] baseParameters = new Object[]{
                new com.bubladecoding.mcpluginbase.command.CommandSender(sender),
                command,
                originalArgs.toArray(new String[0])
        };
        Object[] parameters = new Object[baseParameters.length + arguments.length];
        System.arraycopy(baseParameters, 0, parameters, 0, baseParameters.length);
        System.arraycopy(arguments, 0, parameters, 3, arguments.length);
        return method.call(this, parameters);
    }

    private CommandMethod getCommandMethod(List<String> args) {
        CommandMethod method = main;
        if (args.size() > 0) {
            for (int i = 0; i < subCommandNames.length; i++) {
                if (subCommandNames[i].equalsIgnoreCase(args.get(0))) {
                    method = subCommands[i];
                    args.remove(0);
                    break;
                }
            }
        }

        return method;
    }

    private void readCommands() {
        Method[] methods = Arrays.stream(getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(CommandExecutor.class)).toArray(Method[]::new);

        Method main = Arrays.stream(methods).filter(method -> !method.getAnnotation(CommandExecutor.class).subCommand()).findFirst().orElse(null);
        if (main == null) {
            try {
                main = getClass().getDeclaredMethod("helpCommand", com.bubladecoding.mcpluginbase.command.interfaces.CommandSender.class, Command.class, String[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (main != null) {
            this.main = new CommandMethod(main);
        } else {
            try {
                throw new Exception("Something went wrong while setting up the command '" + getClass().getSimpleName() + "'" +
                        ", main command executor was not specified and could not bind default (command help)");
            } catch (Exception e) {
                handleException(e);
            }
        }

        Method[] subs = Arrays.stream(methods).filter(method -> method.getAnnotation(CommandExecutor.class).subCommand()).toArray(Method[]::new);
        this.subCommands = new CommandMethod[subs.length];
        this.subCommandNames = new String[subs.length];
        for (int i = 0; i < subs.length; i++) {
            this.subCommands[i] = new CommandMethod(subs[i]);

            String commandExecutorName = subs[i].getAnnotation(CommandExecutor.class).name();
            this.subCommandNames[i] = !commandExecutorName.equals("") ? commandExecutorName : subs[i].getName();
        }
    }

    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    public boolean helpCommand(com.bubladecoding.mcpluginbase.command.interfaces.CommandSender sender, Command command, String[] args) {
        // todo well this...
        return true;
    }

    public List<String> getCommands() {
        List<Method> executors = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(CommandExecutor.class))
                .collect(Collectors.toList());

        List<String> lst = new ArrayList<>();

        for (Method method : executors) {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getAnnotation(CommandExecutor.class).name());
            for (Parameter p : Arrays.stream(method.getParameters())
                    .filter(parameter -> parameter.isAnnotationPresent(Argument.class))
                    .collect(Collectors.toList())) {
                Argument argument = p.getAnnotation(Argument.class);
                String required = "<%s>";
                String optional = "[%s]";
                String array = "[%s, %s, ...]";

                String argumentString = String.format(argument.optional() ? optional : required, argument.name());
                if (argument.multiple()) {
                    String[] counted = new String[2];
                    for (int i = 0; i < 2; i++) {
                        counted[i] = String.format(argument.optional() ? optional : required, argument.name() + (i + 1));
                    }
                    argumentString = String.format(array, (Object[]) counted);
                }
                sb.append(" ").append(argumentString);
            }
            lst.add(sb.toString());
        }

        return lst;
    }
}
