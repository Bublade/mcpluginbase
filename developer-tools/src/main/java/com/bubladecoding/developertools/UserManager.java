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
package com.bubladecoding.developertools;

import com.bubladecoding.developertools.database.user.UsersTable;
import com.bubladecoding.developertools.managers.IUserManager;
import com.bubladecoding.developertools.permissions.interfaces.IUser;
import com.bubladecoding.developertools.database.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager implements IUserManager {

    private final DeveloperToolsPlugin plugin;
    private final UsersTable usersTable;
    private final Map<UUID, IUser> users;

    public UserManager(DeveloperToolsPlugin plugin, UsersTable usersTable) {
        this.plugin = plugin;
        this.usersTable = usersTable;
        this.users = new HashMap<>();
    }

    @Nullable
    @Override
    public IUser getUser(@NotNull String name) {
        Player player = Bukkit.getPlayerExact(name);
        if (player == null) {
            return null;
        }

        return getUser(player);
    }

    @Nullable
    @Override
    public IUser getUser(@NotNull UUID uuid) {
        IUser user = this.users.get(uuid);
        if (user == null) {
            return getNewUserByPlayer(plugin.getServer().getPlayer(uuid));
        }

        return user;
    }

    @Nullable
    @Override
    public IUser getUser(@NotNull Player player) {
        IUser user = this.users.get(player.getUniqueId());
        if (user == null) {
            return getNewUserByPlayer(player);
        }
        return user;
    }

    @Override
    public void updateUser(@NotNull UUID uuid, @NotNull IUser user) {
        this.users.put(uuid, user);
        usersTable.save(user);
    }

    @Nullable
    @Override
    public IUser unloadUser(@NotNull UUID uuid) {
        return this.users.remove(uuid);
    }

    private IUser getNewUserByPlayer(Player player) {
        if (player == null) {
            return null;
        }

        IUser user = usersTable.loadWhere("uuid = ?", player.getUniqueId().toString());

        if (user == null) {
            user = new User(plugin, player);
            usersTable.save(user);
        }

        this.users.put(user.getUniqueId(), user);

        return user;
    }
}
