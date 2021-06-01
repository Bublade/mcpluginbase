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

import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CommandClassParser {

    private final CommandMethodParser commandMethodParser;

    public CommandClassParser(CommandMethodParser commandMethodParser) {
        this.commandMethodParser = commandMethodParser;
    }

    public <T> CommandClass parseCommandClass(Class<T> commandClass) {
        Method[] methods = Arrays.stream(commandClass.getMethods())
                .filter(method -> method.isAnnotationPresent(CommandExecutor.class))
                .toArray(Method[]::new);

        CommandMethod[] commandMethods = new CommandMethod[methods.length];
        for (int i = 0, methodsLength = methods.length; i < methodsLength; i++) {
            Method method = methods[i];
            commandMethods[i] = commandMethodParser.parseCommandMethod(method);
        }

        return new CommandClass(commandMethods);
    }

}
