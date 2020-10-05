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
package com.bubladecoding.developertools.commands.test;

import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.argument.Arguments;
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestCommand extends CommandBase {

    public TestCommand() {
        addSubCommand("sub1", this::onSubCommand1);
        addSubCommandTabCompleter("sub1", this::onSubCommand1Completer);
        addSubCommand("sub2", new SubCommand2());
    }

    private List<String> onSubCommand1Completer(CommandSender sender, Command command, String s, Arguments arguments) {
        return Collections.emptyList();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull Arguments arguments) {
        final List<String> completions = new ArrayList<>();
        return StringUtil.copyPartialMatches(arguments.get(1, ""), Arrays.asList("sub1", "sub2"), completions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull Arguments arguments) {
        sender.sendMessage("&aHI! this is the main command!");
        return true;
    }

    boolean onSubCommand1(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull Arguments arguments) {
        sender.sendMessage("&aHI! this is the first sub command!");
        return true;
    }
}
