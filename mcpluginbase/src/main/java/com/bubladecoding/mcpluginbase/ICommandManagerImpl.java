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
import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;
import com.bubladecoding.mcpluginbase.command.parser.PlayerParameterParser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class ICommandManagerImpl implements ICommandManager {

    private final Map<Class<?>, ParameterParser<?>> parsers;
    private final PluginBase pluginBase;

    public ICommandManagerImpl(PluginBase pluginBase) {
        this.pluginBase = pluginBase;

        parsers = new HashMap<>();
        registerParser(Player.class, PlayerParameterParser.class);
    }

    @Override
    public <T, S extends ParameterParser<T>> void registerParser(Class<T> target, Class<S> parserClazz) {
        try {
            Constructor<S> constructor = getConstructor(parserClazz);
            Object[] params = getParamServices(constructor);
            parsers.put(target, constructor.newInstance(params));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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
    private <T> Constructor<T> getConstructor(Class<T> clazz) {
        return (Constructor<T>) Arrays.stream(clazz.getConstructors()).findFirst().orElse(null);
    }

    private Object[] getParamServices(Constructor<?> constructor) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] paramObjects = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            paramObjects[i] = this.pluginBase.getService(paramTypes[i]);
            if (paramObjects[i] == null) {
                if (pluginBase.getClass().isAssignableFrom(paramTypes[i])) {
                    paramObjects[i] = pluginBase;
                }
            }
        }

        return paramObjects;
    }
}
