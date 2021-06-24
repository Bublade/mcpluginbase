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
import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;
import com.bubladecoding.mcpluginbase.command.annotation.Description;
import com.bubladecoding.mcpluginbase.command.annotation.Permission;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Locale;

public class CommandMethodParser {

    private final PluginBase pluginBase;
    private final CommandArgumentParser argumentParser;

    public CommandMethodParser(PluginBase pluginBase, CommandArgumentParser argumentParser) {
        this.pluginBase = pluginBase;
        this.argumentParser = argumentParser;
    }

    public CommandMethod parseCommandMethod(Method method) {
        CommandExecutor commandExecutorAnnotation = method.getAnnotation(CommandExecutor.class);
        Description descriptionAnnotation = method.getAnnotation(Description.class);
        Permission permissionAnnotation = method.getAnnotation(Permission.class);

        CommandArgument<?>[] commandArguments = new CommandArgument[method.getParameters().length];
        Parameter[] parameters = method.getParameters();
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            commandArguments[i] = argumentParser.parseArgument(parameter);
        }

        return new CommandMethod(
                method,
                commandExecutorAnnotation != null
                        ? commandExecutorAnnotation.value()
                        : method.getName().replace("on", "").toLowerCase(Locale.ROOT),
                commandArguments,
                descriptionAnnotation != null ? descriptionAnnotation.value() : null,
                permissionAnnotation != null ? permissionAnnotation.value() : null,
                permissionAnnotation != null ? permissionAnnotation.listType() : Permission.ListType.OR,
                new String[0]
        );
    }

}
