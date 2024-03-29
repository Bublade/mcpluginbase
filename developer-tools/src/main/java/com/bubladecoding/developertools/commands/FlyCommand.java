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
package com.bubladecoding.developertools.commands;

import com.bubladecoding.developertools.DeveloperToolsPlugin;
import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.annotation.AutoInject;
import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends CommandBase<DeveloperToolsPlugin> {

    public FlyCommand(DeveloperToolsPlugin plugin) {
        super(plugin);
    }

    @CommandExecutor( value = "fly", parent = "player")
    public void onFly(@AutoInject CommandSender sender, @Optional Boolean state, @Optional Player target) {
        if (target == null) {
            sender.sendMessage("target null");
            if (sender instanceof Player) {
                target = (Player) sender;
                sender.sendMessage("sender is target");
            } else {
                sender.sendMessage("no target");
                return;
            }
        }

        if (state == null) {
            sender.sendMessage("state is null");
            state = !target.getAllowFlight();
        }

        target.setAllowFlight(state);
    }
}
