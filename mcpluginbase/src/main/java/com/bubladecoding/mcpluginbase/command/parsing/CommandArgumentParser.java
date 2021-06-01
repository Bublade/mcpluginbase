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

import com.bubladecoding.mcpluginbase.PluginBase;
import com.bubladecoding.mcpluginbase.command.annotation.*;
import com.bubladecoding.mcpluginbase.command.parser.ParameterParser;
import com.bubladecoding.mcpluginbase.utilities.AnnotationFinder;

import java.lang.reflect.Parameter;

public class CommandArgumentParser {

    private final PluginBase pluginBase;

    public CommandArgumentParser(PluginBase pluginBase) {
        this.pluginBase = pluginBase;
    }

    public CommandArgument<?> parseArgument(Parameter parameter) {
        if (parameter.isAnnotationPresent(AutoInject.class)) {
            return new CommandArgument<>(
                    parameter.getName(),
                    parameter.getType(),
                    true/*,
                    pluginBase.getCommandManager().getParser(parameter.getType())*/
            );
        }

        Argument argumentAnnotation = AnnotationFinder.findAnnotation(parameter, Argument.class);
        Name nameAnnotation = AnnotationFinder.findAnnotation(parameter, Name.class);
        Optional optionalAnnotation = AnnotationFinder.findAnnotation(parameter, Optional.class);
        Default defaultAnnotation = AnnotationFinder.findAnnotation(parameter, Default.class);
        Description descriptionAnnotation = AnnotationFinder.findAnnotation(parameter, Description.class);
        HelpFormat helpFormatAnnotation = AnnotationFinder.findAnnotation(parameter, HelpFormat.class);
        Options optionsAnnotation = AnnotationFinder.findAnnotation(parameter, Options.class);
        Selectors selectorsAnnotation = AnnotationFinder.findAnnotation(parameter, Selectors.class);

        ParameterParser<?> parser = pluginBase.getCommandManager().getParser(parameter.getType());
        String name = nameAnnotation != null ? nameAnnotation.value() : parameter.getType().getSimpleName();
        boolean optional = optionalAnnotation != null && optionalAnnotation.value();
        String description = descriptionAnnotation != null ? descriptionAnnotation.value() : "";
        String helpFormat = helpFormatAnnotation != null ? helpFormatAnnotation.value() : "";

        System.out.println("registered [" + name + "] as " + (optional ? "optional" : "required"));

        return new CommandArgument(
                name,
                parameter.getType(),
                optional,
                defaultAnnotation != null ? defaultAnnotation.value() : null,
                description,
                helpFormat,
                parser,
                selectorsAnnotation != null ? selectorsAnnotation.value() : new Selector[0],
                optionsAnnotation != null ? optionsAnnotation.value() : new Option[0]
        );
    }
    /*
    private <T> CommandArgument<T> createCommandArgument(String name, Class<T> clazz, boolean optional, String defaultValue, String description, String helpFormat, ParameterParser<T> parameterParser, Selector[] selectors, Option[] options) {
        return new CommandArgument<T>(name, clazz, optional, defaultValue, description, helpFormat, parameterParser, selectors, options);
    }*/

}
