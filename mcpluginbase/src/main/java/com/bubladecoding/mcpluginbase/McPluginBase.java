package com.bubladecoding.mcpluginbase;
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

import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class McPluginBase extends JavaPlugin {

    /**
     * Register a command.
     * @param name Name of the command in the plugin.yml.
     * @param executor The executor.
     */
    protected void registerCommandExecutor(String name, CommandExecutor executor) {
        PluginCommand command = getCommand(name);
        if (command == null || executor == null) {
            return;
        }

        command.setExecutor(executor);
    }
    /**
     * Register a command tab completer.
     * @param name Name of the command in the plugin.yml.
     * @param executor The completer.
     */
    protected void registerTabCompleter(String name, TabCompleter executor) {
        PluginCommand command = getCommand(name);
        if (command == null || executor == null) {
            return;
        }

        command.setTabCompleter(executor);
    }
    /**
     * Register a command with tab executor.
     * @param name Name of the command in the plugin.yml.
     * @param executor The executor.
     */
    protected void registerTabExecutor(String name, TabExecutor executor) {
        PluginCommand command = getCommand(name);
        if (command == null || executor == null) {
            return;
        }

        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }
}