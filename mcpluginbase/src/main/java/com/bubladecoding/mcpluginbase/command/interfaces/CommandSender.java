package com.bubladecoding.mcpluginbase.command.interfaces;
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

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;

public interface CommandSender extends org.bukkit.command.CommandSender {

    /**
     * Get the original {@link org.bukkit.command.CommandSender} from bukkit.
     *
     * @return The original {@link org.bukkit.command.CommandSender} from bukkit.
     */
    org.bukkit.command.CommandSender getBase();

    /**
     * Get the sender as a {@link Player}.
     *
     * @return The sender as a {@link Player} or null if sender is not a {@link Player}
     */
    Player getPlayer();

    /**
     * Get the sender as a {@link ConsoleCommandSender}.
     *
     * @return The sender as a {@link ConsoleCommandSender} or null if sender is not a {@link ConsoleCommandSender}
     */
    ConsoleCommandSender getConsole();

    /**
     * Get the sender as a {@link BlockCommandSender}.
     *
     * @return The sender as a {@link BlockCommandSender} or null if sender is not a {@link BlockCommandSender}
     */
    BlockCommandSender getBlockCommandSender();

    /**
     * Get the sender as a {@link CommandMinecart}.
     *
     * @return The sender as a {@link CommandMinecart} or null if sender is not a {@link CommandMinecart}
     */
    CommandMinecart getCommandMinecartSender();

    /**
     * Check if the sender is a {@link Player}.
     *
     * @return true if it is a {@link Player}.
     */
    boolean isPlayer();

    /**
     * Check if the sender is the {@link ConsoleCommandSender}.
     *
     * @return true if it is the {@link ConsoleCommandSender}.
     */
    boolean isConsole();

    /**
     * Check if the sender is a {@link BlockCommandSender}.
     *
     * @return true if it is a  {@link BlockCommandSender}.
     */
    boolean isCommandBlock();


    /**
     * Check if the sender is a {@link CommandMinecart}.
     *
     * @return true if it is a {@link CommandMinecart}.
     */
    boolean isCommandBlockCart();
}
