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

import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.ICommandManager;
import com.bubladecoding.mcpluginbase.command.MainCommandExecutor;
import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;
import com.bubladecoding.mcpluginbase.command.parser.PlayerParameterParser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

class CommandManager implements ICommandManager {

    private final Map<Class<?>, ParameterParser<?>> parsers;
    private final Map<Class<?>, Object> options;
    private final PluginBase pluginBase;
    private final MainCommandExecutor commandExecutor;

    public CommandManager(PluginBase pluginBase) {
        this.pluginBase = pluginBase;

        this.parsers = new HashMap<>();
        this.options = new HashMap<>();
        this.commandExecutor = new MainCommandExecutor(pluginBase);

        registerParser(Player.class, PlayerParameterParser.class);
    }

    public <P extends PluginBase, T extends CommandBase<P>> void registerCommands(T commandBase) {
        this.commandExecutor.addCommands(commandBase);
    }

    public <P extends PluginBase, T extends CommandBase<P>> void registerCommands(Class<T> commandClass) {
        T commandBase = pluginBase.createInjectedClass(commandClass);
        this.commandExecutor.addCommands(commandBase);
    }

    @Override
    public <T, S extends ParameterParser<T>> void registerParser(Class<T> target, Class<S> parserClass) {
        parsers.put(target, pluginBase.createInjectedClass(parserClass));
    }

    @Override
    public void registerOption(Class<?> optionClass){
        Object option = pluginBase.createInjectedClass(optionClass);
        options.put(optionClass, option);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> ParameterParser<T> unregisterParser(Class<T> target) {
        return (ParameterParser<T>) parsers.remove(target);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> ParameterParser<T> getParser(Class<T> target) {
        return (ParameterParser<T>) parsers.get(target);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getOption(Class<T> target) {
        return (T) options.get(target);
    }
}
