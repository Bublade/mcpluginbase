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
package com.bubladecoding.developertools.teststuff;

import com.bubladecoding.developertools.DeveloperToolsPlugin;
import com.bubladecoding.developertools.database.user.User;
import com.bubladecoding.developertools.database.user.UsersTable;
import com.bubladecoding.developertools.managers.IUserManager;
import com.bubladecoding.developertools.permissions.interfaces.IUser;
import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.annotation.AutoInject;
import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.annotation.Option;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UserCommand extends CommandBase<DeveloperToolsPlugin> {

    private final UsersTable usersTable;

    public UserCommand(DeveloperToolsPlugin plugin, UsersTable usersTable) {
        super(plugin);
        this.usersTable = usersTable;
    }

    @CommandExecutor("user")
    public void onUser(@AutoInject CommandSender sender, IUserAction userAction, @AutoInject String[] args) {
        userAction.execute(sender, this.usersTable, plugin, args);
    }

    @Option(name = "load", optionClass = LoadUser.class)
    @Option(name = "save", optionClass = SaveUser.class)
    public interface IUserAction {
        void execute(CommandSender sender, UsersTable usersTable, DeveloperToolsPlugin plugin, String[] args);
    }

    public static class LoadUser implements IUserAction {

        @Override
        public void execute(CommandSender sender, UsersTable usersTable, DeveloperToolsPlugin plugin, String[] args) {
            if (sender instanceof Player player) {
                IUserManager manager = plugin.getService(IUserManager.class);
                IUser user = manager.getUser(player.getUniqueId());
                if (user != null) {
                    sender.sendMessage("User note: " + user.getNote() + " uuid: " + user.getUniqueId());
                }
            }
        }
    }

    public static class SaveUser implements IUserAction {

        @Override
        public void execute(CommandSender sender, UsersTable usersTable, DeveloperToolsPlugin plugin, String[] args) {
            if (sender instanceof Player player) {
                IUserManager manager = plugin.getService(IUserManager.class);
                IUser user = manager.getUser(player.getUniqueId());
                if (user != null) {
                    sender.sendMessage("User id " + user.getId() + " uuid " + user.getUniqueId());
                    user.setNote(String.join(" ", args));
                    manager.updateUser(user.getUniqueId(), user);
                }
            }
        }
    }
}
