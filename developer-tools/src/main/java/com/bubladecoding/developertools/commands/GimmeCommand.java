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
import com.bubladecoding.mcpluginbase.command.annotations.Argument;
import com.bubladecoding.mcpluginbase.command.annotations.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import com.bubladecoding.mcpluginbase.command.tabcompleter.MaterialTabCompleter;
import com.bubladecoding.mcpluginbase.command.tabcompleter.PlayerTabCompleter;
import com.bubladecoding.mcpluginbase.parsing.parses.IntParser;
import com.bubladecoding.mcpluginbase.parsing.parses.MaterialParser;
import com.bubladecoding.mcpluginbase.parsing.parses.PlayerParser;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GimmeCommand extends CommandBase {

    @CommandExecutor
    public boolean gimme(
            CommandSender sender,
            Command command,
            String[] args,
            @Argument(name = "material", parser = MaterialParser.class, tabCompleter = MaterialTabCompleter.class) Material material,
            @Argument(name = "amount", parser = IntParser.class, optional = true) Integer amount,
            @Argument(name = "to", parser = PlayerParser.class, optional = true, tabCompleter = PlayerTabCompleter.class) Player target
    ) {
        sender.getPlayer().updateCommands();
        if (target == null && sender.isPlayer()) {
            target = sender.getPlayer();
        }

        if (material == null) {
            material = Material.DIRT;
        }

        if (target == null) {
            return false;
        }

        if (amount == null) {
            amount = 1;
        }

        target.getInventory().addItem(new ItemStack(material, amount));
        return false;
    }
}
