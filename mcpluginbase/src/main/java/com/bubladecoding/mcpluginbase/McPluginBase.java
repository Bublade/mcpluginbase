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

import com.bubladecoding.mcpluginbase.command.ICommandManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class McPluginBase extends JavaPlugin implements PluginBase {

    private final ICommandManager commandManager;

    public McPluginBase() {
        commandManager = new CommandManager(this);
    }

    @Override
    public ICommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Register a command.
     *
     * @param name     Name of the command in the plugin.yml.
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
     *
     * @param name     Name of the command in the plugin.yml.
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
     *
     * @param name     Name of the command in the plugin.yml.
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

    @Override
    @Nullable
    public <T> T getService(@NotNull Class<T> tClass) {
        return getServer().getServicesManager().load(tClass);
    }

    @Override
    public <T> T createInjectedClass(Class<T> tClass) {
        try {
            Constructor<T> constructor = getConstructor(tClass);
            if (constructor != null) {
                Object[] params = getParamServices(constructor);
                return constructor.newInstance(params);
            } else {
                return tClass.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Constructor<T> getConstructor(Class<T> clazz) {
        return (Constructor<T>) Arrays.stream(clazz.getConstructors()).findFirst().orElse(null);
    }

    private Object[] getParamServices(Constructor<?> constructor) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] paramObjects = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            paramObjects[i] = this.getService(paramTypes[i]);
            if (paramObjects[i] == null && paramTypes[i].isAssignableFrom(this.getClass())) {
                paramObjects[i] = this;
            }
        }

        return paramObjects;
    }
}
