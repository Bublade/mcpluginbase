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
package com.bubladecoding.developertools.commands;

import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.argument.Arguments;
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class GimmeCommand extends CommandBase {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull Arguments arguments) {
        if (arguments.size() == 0) {
            return Arrays.asList("1", "2", "4", "8", "16", "32", "64");
        }

        String lastArg = arguments.get(arguments.size());
        if (arguments.getInteger(1) != null) {
            final List<String> suggestions = Arrays.asList("1", "2", "4", "8", "16", "32", "64");
            final List<String> completions = new ArrayList<>();
            return StringUtil.copyPartialMatches(lastArg, suggestions, completions);
        }

        final List<String> suggestions = Arrays.stream(Material.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.toList());
        final List<String> completions = new ArrayList<>();
        return StringUtil.copyPartialMatches(lastArg, suggestions, completions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull Arguments arguments) {
        if (!sender.isPlayer() || !sender.hasPermission("devtools.gimme")) {
            sender.sendMessage("&cNO!");
            return true;
        }

        if (arguments.size() == 0) {
            return false;
        }

        Material material = null;
        Integer amount = null;

        if (arguments.size() == 1) {
            material = arguments.getMaterial(1);
        }

        if (arguments.size() == 2) {
            amount = arguments.getInteger(1);
            material = arguments.getMaterial(2);
        }

        if (material == null && amount == null) {
            amount = arguments.getInteger(2);
            material = arguments.getMaterial(1);
        }

        if (material == null) {
            return false;
        }

        if (amount == null) {
            sender.getPlayer().getInventory().addItem(new ItemStack(material));
            return true;
        }

        if (amount > material.getMaxStackSize()) {
            int stacks = (int) Math.floor((double) amount / (double) material.getMaxStackSize());
            int rest = amount % material.getMaxStackSize();
            for (int i = 0; i < stacks; i++) {
                sender.getPlayer().getInventory().addItem(new ItemStack(material, material.getMaxStackSize()));
            }
            sender.getPlayer().getInventory().addItem(new ItemStack(material, rest));
            return true;
        }

        sender.getPlayer().getInventory().addItem(new ItemStack(material, amount));
        return true;
    }

}
