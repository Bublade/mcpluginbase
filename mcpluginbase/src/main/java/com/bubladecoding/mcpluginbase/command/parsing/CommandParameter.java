package com.bubladecoding.mcpluginbase.command.parsing;
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

import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;

import java.util.List;
import java.util.concurrent.Callable;

public class CommandParameter<T> {

    private final String name;
    private final boolean optional;
    private final T defaultValue;
    private final ParameterParser<T> parameterParser;
    private final T[] options;
    private final Callable<T[]> callableOptions;

    public CommandParameter(String name, ParameterParser<T> parameterParser) {
        this(name, parameterParser, false, null, null, null);
    }

    public CommandParameter(String name, ParameterParser<T> parameterParser, boolean optional, T[] options) {
        this(name, parameterParser, optional, null, options, null);
    }

    public CommandParameter(String name, ParameterParser<T> parameterParser, boolean optional, Callable<T[]> callableOptions) {
        this(name, parameterParser, optional, null, null, callableOptions);
    }

    public CommandParameter(String name, ParameterParser<T> parameterParser, T defaultValue) {
        this(name, parameterParser, true, defaultValue, null, null);
    }

    public CommandParameter(String name, ParameterParser<T> parameterParser, boolean optional, T defaultValue, T[] options, Callable<T[]> callableOptions) {
        this.name = name;
        this.parameterParser = parameterParser;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.options = options;
        this.callableOptions = callableOptions;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    @SuppressWarnings("unchecked")
    public T[] getOptions() {
        try {
            return options != null ? options : callableOptions.call();
        } catch (Exception e) {
            return (T[]) new Object[0];
        }
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public ParameterParser<T> getParameterParser() {
        return parameterParser;
    }

    public T getValue(List<String> args) {
        T value = parameterParser.parse(args);
        return value != null ? value : defaultValue;
    }
}
