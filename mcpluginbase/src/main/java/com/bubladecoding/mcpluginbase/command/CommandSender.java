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

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

class CommandSender implements com.bubladecoding.mcpluginbase.command.interfaces.CommandSender {

    private org.bukkit.command.CommandSender base;

    public CommandSender(org.bukkit.command.CommandSender base) {
        this.base = base;
    }

    public org.bukkit.command.CommandSender getBase() {
        return base;
    }

    /**
     * Get the sender as a {@link Player}.
     *
     * @return The sender as a {@link Player} or null if sender is not a {@link Player}
     */
    public Player getPlayer() {
        if (isPlayer()) {
            return (Player) base;
        }

        return null;
    }

    /**
     * Get the sender as a {@link ConsoleCommandSender}.
     *
     * @return The sender as a {@link ConsoleCommandSender} or null if sender is not a {@link ConsoleCommandSender}
     */
    public ConsoleCommandSender getConsole () {
        if (isConsole()) {
            return (ConsoleCommandSender) base;
        }

        return null;
    }

    /**
     * Get the sender as a {@link BlockCommandSender}.
     *
     * @return The sender as a {@link BlockCommandSender} or null if sender is not a {@link BlockCommandSender}
     */
    public BlockCommandSender getBlockCommandSender() {
        if (isCommandBlock()) {
            return (BlockCommandSender) base;
        }

        return null;
    }

    /**
     * Get the sender as a {@link CommandMinecart}.
     *
     * @return The sender as a {@link CommandMinecart} or null if sender is not a {@link CommandMinecart}
     */
    public CommandMinecart getCommandMinecartSender() {
        if (isCommandBlockCart()) {
            return (CommandMinecart) base;
        }

        return null;
    }

    /**
     * Check if the sender is a {@link Player}.
     *
     * @return true if it is a {@link Player}.
     */
    public boolean isPlayer() {
        return base instanceof Player;
    }

    /**
     * Check if the sender is the {@link ConsoleCommandSender}.
     *
     * @return true if it is the {@link ConsoleCommandSender}.
     */
    public boolean isConsole() {
        return base instanceof ConsoleCommandSender;
    }

    /**
     * Check if the sender is a {@link BlockCommandSender}.
     *
     * @return true if it is a  {@link BlockCommandSender}.
     */
    public boolean isCommandBlock() {
        return base instanceof BlockCommandSender;
    }


    /**
     * Check if the sender is a {@link CommandMinecart}.
     *
     * @return true if it is a {@link CommandMinecart}.
     */
    public boolean isCommandBlockCart() {
        return base instanceof CommandMinecart;
    }

    /**
     * Send a message that automatically changes '&x' to the corresponding color.
     * @param message The message you want to send.
     */
    @Override
    public void sendMessage(@NotNull String message) {
        base.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Send a list of messages that automatically changes '&x' to the corresponding color.
     * @param messages The messages you want to send.
     */
    @Override
    public void sendMessage(@NotNull String[] messages) {
        base.sendMessage(Arrays.stream(messages).map(m -> ChatColor.translateAlternateColorCodes('&', m)).toArray(String[]::new));
    }

    /**
     * Send a message that automatically changes '&x' to the corresponding color.
     * @param message The message you want to send.
     */
    public void sendMessage(char altColorChar, @NotNull String message) {
        base.sendMessage(ChatColor.translateAlternateColorCodes(altColorChar, message));
    }

    /**
     * Send a list of messages that automatically changes '&x' to the corresponding color.
     * @param messages The messages you want to send.
     */
    public void sendMessage(char altColorChar, @NotNull String[] messages) {
        messages = Arrays.stream(messages).map(m -> ChatColor.translateAlternateColorCodes(altColorChar, m)).toArray(String[]::new);
        base.sendMessage(messages);
    }

    @NotNull
    @Override
    public Server getServer() {
        return base.getServer();
    }

    @NotNull
    @Override
    public String getName() {
        return base.getName();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return base.spigot();
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return base.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return base.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return base.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return base.hasPermission(perm);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return base.addAttachment(plugin, name, value);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return base.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return base.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return base.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
        base.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        base.recalculatePermissions();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return base.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return base.isOp();
    }

    @Override
    public void setOp(boolean value) {
        base.setOp(value);
    }


}
