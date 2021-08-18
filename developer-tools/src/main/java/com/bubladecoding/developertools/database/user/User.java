package com.bubladecoding.developertools.database.user;
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

import com.bubladecoding.developertools.DeveloperToolsPlugin;
import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import com.bubladecoding.developertools.permissions.interfaces.IUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class User implements IUser {

    private final DeveloperToolsPlugin plugin;
    private PermissionAttachment permissionAttachment;
    private Player playerBase;
    private Long id;
    private String note;

    public User(DeveloperToolsPlugin plugin, Player playerBase) {
        this.plugin = plugin;
        this.playerBase = playerBase;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void addGroup(IGroup group) {

    }

    @Override
    public void setGroups(IGroup group) {

    }

    @Override
    public void removeGroup(IGroup group) {

    }

    @Override
    public void setGroups(List<IGroup> groups) {

    }

    @Override
    public List<IGroup> getGroups() {
        return null;
    }

    @Override
    public Map<String, Boolean> getPermissions() {
        return getPlayerPluginAttachment().getPermissions();
    }

    @Override
    public void setPermission(@NotNull String name) {
        getPlayerPluginAttachment().setPermission(name, true);
    }

    @Override
    public void setPermission(@NotNull Permission permission) {
        getPlayerPluginAttachment().setPermission(permission, true);
    }

    @Override
    public void setPermission(@NotNull String name, boolean value) {
        getPlayerPluginAttachment().setPermission(name, value);
    }

    @Override
    public void setPermission(@NotNull Permission permission, boolean value) {
        getPlayerPluginAttachment().setPermission(permission, value);

    }

    @Override
    public void unsetPermission(@NotNull String name) {
        getPlayerPluginAttachment().unsetPermission(name);
    }

    @Override
    public void unsetPermission(@NotNull Permission permission) {
        getPlayerPluginAttachment().unsetPermission(permission);
    }

    @Override
    public String getPermissibleType() {
        return "user";
    }

    protected PermissionAttachment getPlayerPluginAttachment() {
        return getPlayerPluginAttachment(this.plugin);
    }

    protected PermissionAttachment getPlayerPluginAttachment(Plugin plugin) {
        if (permissionAttachment != null) {
            return permissionAttachment;
        }

        PermissionAttachmentInfo info = this.playerBase.getEffectivePermissions().stream()
                .filter(pai -> pai.getAttachment() != null)
                .filter(pai -> pai.getAttachment().getPlugin().equals(plugin))
                .findFirst().orElse(null);

        permissionAttachment = info != null ? info.getAttachment() : playerBase.addAttachment(plugin);
        return permissionAttachment;
    }

    @Override
    public UUID getUniqueId() {
        return playerBase.getUniqueId();
    }

    @Override
    public void loadPlayer(UUID uuid) {
        this.playerBase = Bukkit.getPlayer(uuid);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }
}
