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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Constructor mainly for auto injected parameters.
     *
     * @param name Name of the argument.
     * @param clazz The class type of the argument.
     * @param autoInject Whether the argument will be auto injected (should be {@code true}).
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
     * Constructor to define fully defined argument.
     *
     * @param name Name of the argument.
     * @param clazz The class type of the argument.
     * @param optional Whether the argument is optional.
     * @param selectors Selectors to select a different parser based on pre-defined argument.
     * @param options Selectors to select a different parser based on previous fixed command argument.
     */
    public CommandArgument(String name, Class<T> clazz, boolean optional, Selector[] selectors, Option[] options) {
        this.name = name;
        this.clazz = clazz;
        this.autoInject = false;

        this.optional = optional;
        this.selectors = selectors != null ? selectors : new Selector[0];
        this.options = options != null ? options : new Option[0];

        this.description = null;
        this.helpFormat = null;
        this.parameterParser = null;
        this.defaultValue = null;
    }

    /**
     * Constructor to define fully defined argument.
     *
     * @param name Name of the argument.
     * @param clazz The class type of the argument.
     * @param optional Whether the argument is optional.
     * @param defaultValue The default value of the argument parsed by the {@param parameterParser}.
     * @param description The description for this argument.
     * @param helpFormat Help format for this argument for overriding the built in help system.
     * @param parameterParser The parameter parsed used to parse this argument (selectors, and options get priority however (unless default value is parsed!)).
     * @param selectors Selectors to select a different parser based on pre-defined argument.
     * @param options Selectors to select a different parser based on previous fixed command argument.
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
     * Constructor to define fully defined argument.
     *
     * @param name Name of the argument.
     * @param clazz The class type of the argument.
     * @param optional Whether the argument is optional.
     * @param defaultValue The default value of the argument parsed by the {@param parameterParser}.
     * @param description The description for this argument.
     * @param helpFormat Help format for this argument for overriding the built in help system.
     * @param parameterParser The parameter parsed used to parse this argument (selectors, and options get priority however (unless default value is parsed!)).
     * @param selectors Selectors to select a different parser based on pre-defined argument.
     * @param options Selectors to select a different parser based on previous fixed command argument.
     * @param autoInject Whether the argument will be auto injected (should be {@code true}).
     */
    public CommandArgument(String name, Class<T> clazz, boolean optional, String defaultValue, String description, String helpFormat, ParameterParser<T> parameterParser, Selector[] selectors, Option[] options, boolean autoInject) {
        this.name = name;
        this.clazz = clazz;
        this.autoInject = autoInject;

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
     * Get the name of the argument.
     *
     * @return The name of the argument.
     */
    public String getName() {
        return name != null ? name : getType().getSimpleName();
    }

    /**
     * Get if the argument is optional.
     *
     * @return Whether the argument is optional.
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Gets the default value of the argument.
     *
     * @return The default value of the argument.
     */
    @Nullable
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Gets the description of the argument.
     *
     * @return The description of the argument.
     */
    @Nullable
    public String getDescription() {
        return description;
    }

    /**
     * Gets the help format of the argument.
     *
     * @return The help format of the argumen.
     */
    @Nullable
    public String getHelpFormat() {
        return helpFormat;
    }

    /**
     *
     * @return
     */
    @Nullable
    public ParameterParser<T> getParameterParser() {
        return parameterParser;
    }

    /**
     *
     * @param args
     * @return
     */
    @Nullable
    public T getValue(List<String> args) {
        if (parameterParser != null) {
            return parameterParser.parse(args);
        }
        return null;
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
     * Checks whether there are selectors.
     *
     * @return Whether the are selectors.
     */
    public boolean hasSelectors() {
        return this.selectors != null && this.selectors.length > 0;
    }

    /**
     * Checks whether there are options.
     *
     * @return Whether the are options.
     */
    public boolean hasOptions() {
        return this.options != null && this.options.length > 0;
    }

    public boolean hasHelpFormat() {
        return this.helpFormat != null && !this.helpFormat.trim().equals("");
    }

    /**
     * Checks whether the argument has a name.
     * This is false in case of the fallback (simpleName) of the parameter type.
     *
     * @return Whether the argument has a name.
     */
    public boolean hasName() {
        return this.name != null && !this.name.trim().equals("");
    }

    /**
     * Get the original type of the argument.
     * @return Type of the original argument.
     */
    public Class<T> getType() {
        return this.clazz;
    }

}
