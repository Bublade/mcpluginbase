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
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import com.bubladecoding.mcpluginbase.command.annotations.Argument;
import com.bubladecoding.mcpluginbase.command.annotations.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.tabcompleter.PlayerTabCompleter;
import com.bubladecoding.mcpluginbase.parsing.parses.PlayerParser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class FlyCommand extends CommandBase {

    @CommandExecutor
    public boolean command(
            CommandSender sender,
            Command command,
            String[] args,
            @Argument(name = "player", parser = PlayerParser.class, tabCompleter = PlayerTabCompleter.class) Player target
    ) {
        if (!sender.isPlayer() && target == null) {
            sender.sendMessage("Player not found");
        }

        if (target != null) {
            if (togglePlayerFlight(target)) {
                sender.sendMessage("Flight for " + target.getName() + " has been set to allow.");
            } else {
                sender.sendMessage("Flight for " + target.getName() + " has been set to deny.");
            }
            return true;
        }

        if (sender.isPlayer()) {
            togglePlayerFlight(sender.getPlayer());
        }

        return true;
    }

    @CommandExecutor(subCommand = true)
    public boolean fancy(
            CommandSender sender,
            Command command,
            String[] args,
            @Argument(name = "player", parser = PlayerParser.class) Player target
    ){

        if (!sender.isPlayer() && target == null) {
            sender.sendMessage("&cPlayer not found");
        }

        if (target != null) {
            if (togglePlayerFlight(target)) {
                sender.sendMessage("&aFlight for " + target.getName() + " has been set to allow.");
            } else {
                sender.sendMessage("&cFlight for " + target.getName() + " has been set to deny.");
            }
            return true;
        }

        if (sender.isPlayer()) {
            togglePlayerFlightFancy(sender.getPlayer());
        }

        return true;
    }
    public boolean togglePlayerFlightFancy(Player player) {
        player.setAllowFlight(!player.getAllowFlight());
        String color = (player.getAllowFlight() ? ChatColor.GREEN : ChatColor.RED).toString();
        player.sendMessage(color + "Your flight mode has been set to: " + (player.getAllowFlight() ? "allow" : "deny") + ".");
        return player.getAllowFlight();
    }

    public boolean togglePlayerFlight(Player player) {
        player.setAllowFlight(!player.getAllowFlight());
        player.sendMessage("Your flight mode has been set to: " + (player.getAllowFlight() ? "allow" : "deny") + ".");
        return player.getAllowFlight();
    }

}
