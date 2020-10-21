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
package com.bubladecoding.mcpluginbase.command;

import com.bubladecoding.mcpluginbase.command.annotations.Argument;
import com.bubladecoding.mcpluginbase.command.annotations.Option;

import java.lang.reflect.Parameter;

public class CommandParameter {

    private final Parameter parameter;
    private final CommandArgument argument;
    private final CommandOption option;

    public CommandParameter(Parameter parameter) {
        this.parameter = parameter;
        argument = asArgument();
        option = asOption();
    }

    public boolean isArgument() {
        return argument != null;
    }

    public boolean isOption() {
        return option != null;
    }

    public CommandArgument getArgument() {
        return argument;
    }

    public CommandOption getOption() {
        return option;
    }

    private CommandOption asOption() {
        if (parameter.isAnnotationPresent(Option.class)) {
            return new CommandOption(parameter.getAnnotation(Option.class));
        }
        return null;
    }

    private CommandArgument asArgument() {
        if (parameter.isAnnotationPresent(Argument.class)) {
            return new CommandArgument(parameter.getAnnotation(Argument.class));
        }

        return null;
    }
}
