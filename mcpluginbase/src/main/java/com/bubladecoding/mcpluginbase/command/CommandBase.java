package com.bubladecoding.mcpluginbase.command;
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

import com.bubladecoding.mcpluginbase.command.argument.Arguments;
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import com.bubladecoding.mcpluginbase.command.interfaces.SubCommand;
import com.bubladecoding.mcpluginbase.command.interfaces.SubTabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CommandBase implements TabExecutor, SubCommand, SubTabCompleter {

    public Map<String, SubCommand> subCommands;
    public Map<String, SubTabCompleter> subTabCompleters;

    public CommandBase() {
        subCommands = new HashMap<>();
        subTabCompleters = new HashMap<>();
    }

    @Override
    public boolean onCommand(
            @NotNull org.bukkit.command.CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        CommandSender cmdSender = new com.bubladecoding.mcpluginbase.command.CommandSender(sender);
        Arguments arguments;
        SubCommand sc = null;

        if (args.length > 0) {
            sc = subCommands.get(args[0].toLowerCase());
        }

        if (sc != null) {
            String[] newArgs = new String[0];
            if (args.length > 1) {
                newArgs = Arrays.copyOfRange(args, 1, args.length);
            }

            arguments = new Arguments(newArgs);
            if (sc instanceof CommandBase) {
                return ((CommandBase) sc).onCommand(sender, command, label, arguments.getOriginalArgs());
            }
            return sc.onCommand(cmdSender, command, label, arguments);
        }

        arguments = new Arguments(args);
        return onCommand(cmdSender, command, label, arguments);
    }

    @Override
    public List<String> onTabComplete(
            @NotNull org.bukkit.command.CommandSender sender,
            @NotNull Command command,
            @NotNull String alias,
            @NotNull String[] args
    ) {
        CommandSender cmdSender = new com.bubladecoding.mcpluginbase.command.CommandSender(sender);
        Arguments arguments;
        SubTabCompleter sc = null;

        if (args.length > 0) {
            sc = subTabCompleters.get(args[0].toLowerCase());
        }

        if (sc != null) {
            String[] newArgs = new String[0];
            if (args.length > 1) {
                newArgs = Arrays.copyOfRange(args, 1, args.length);
            }

            arguments = new Arguments(newArgs);
            if (sc instanceof CommandBase) {
                return ((CommandBase) sc).onTabComplete(sender, command, alias, arguments.getOriginalArgs());
            }
            return sc.onTabComplete(cmdSender, command, alias, arguments);
        }

        arguments = new Arguments(args);
        return onTabComplete(cmdSender, command, alias, arguments);
    }

    public void addSubCommand(String name, SubCommand subCommand) {
        this.subCommands.put(name.toLowerCase(), subCommand);
        if (subCommand instanceof SubTabCompleter) {
            addSubCommandTabCompleter(name, (SubTabCompleter) subCommand);
        }
    }

    public void addSubCommandTabCompleter(String name, SubTabCompleter subTabCompleter) {
        this.subTabCompleters.put(name.toLowerCase(), subTabCompleter);
    }

    public abstract List<String> onTabComplete(@NotNull CommandSender sender,
                                               @NotNull Command command,
                                               @NotNull String alias,
                                               @NotNull Arguments arguments);

    public abstract boolean onCommand(@NotNull CommandSender sender,
                                      @NotNull Command command,
                                      @NotNull String label,
                                      @NotNull Arguments arguments);
}
