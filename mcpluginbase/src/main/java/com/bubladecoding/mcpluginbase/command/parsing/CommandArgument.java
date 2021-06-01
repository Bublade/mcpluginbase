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
package com.bubladecoding.mcpluginbase.command.parsing;

import com.bubladecoding.mcpluginbase.command.annotation.Option;
import com.bubladecoding.mcpluginbase.command.annotation.Selector;
import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CommandArgument<T> {

    private final String name;
    private final boolean autoInject;
    private final Class<T> clazz;

    private final boolean optional;
    private final T defaultValue;
    private final String description;
    private final String helpFormat;

    private final ParameterParser<T> parameterParser;
    private final Selector[] selectors;
    private final Option[] options;

    /**
     *
     * @param name
     * @param clazz
     * @param autoInject
     */
    public CommandArgument(String name, Class<T> clazz, boolean autoInject) {
        this.name = name;
        this.clazz = clazz;
        this.autoInject = autoInject;

        this.optional = false;
        this.description = null;
        this.helpFormat = null;
        this.parameterParser = null;
        this.selectors = new Selector[0];
        this.options = new Option[0];

        this.defaultValue = null;
    }

    /**
     *
     * @param name
     * @param clazz
     * @param optional
     * @param defaultValue
     * @param description
     * @param helpFormat
     * @param parameterParser
     * @param selectors
     * @param options
     */
    public CommandArgument(String name, Class<T> clazz, boolean optional, String defaultValue, String description, String helpFormat, ParameterParser<T> parameterParser, Selector[] selectors, Option[] options) {
        this.name = name;
        this.clazz = clazz;
        this.autoInject = false;

        this.optional = optional;
        this.description = description;
        this.helpFormat = helpFormat;
        this.parameterParser = parameterParser;
        this.selectors = selectors != null ? selectors : new Selector[0];
        this.options = options != null ? options : new Option[0];

        if (defaultValue != null) {
            ArrayList<String> val = new ArrayList<>(Arrays.asList(defaultValue.split(Pattern.quote(defaultValue))));
            this.defaultValue = parameterParser != null ? parameterParser.parse(val) : null;
        } else {
            this.defaultValue = null;
        }
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     *
     * @return
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getHelpFormat() {
        return helpFormat;
    }

    /**
     *
     * @return
     */
    public ParameterParser<T> getParameterParser() {
        return parameterParser;
    }

    /**
     *
     * @param args
     * @return
     */
    public T getValue(List<String> args) {
        return parameterParser.parse(args);
    }

    /**
     * Get the possible selectors for this argument.
     * @return the possible selectors for this argument.
     */
    public Selector[] getSelectors() {
        return selectors;
    }

    /**
     * Get the possible options for this argument.
     * @return the possible options for this argument.
     */
    public Option[] getOptions() {
        return options;
    }

    /**
     * Get of the argument is marked as auto-inject.
     * @return of the argument is marked as auto-inject.
     */
    public boolean isAutoInject() {
        return autoInject;
    }

    /**
     * Get the original type of the argument.
     * @return Type of the original argument.
     */
    public Class<T> getType() {
        return this.clazz;
    }
}
