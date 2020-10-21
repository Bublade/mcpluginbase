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
import com.bubladecoding.mcpluginbase.command.interfaces.ArgumentTabCompleter;
import com.bubladecoding.mcpluginbase.parsing.Parser;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

public class CommandArgument implements Argument {

    private final Argument argument;
    private Parser<?> parser;
    private ArgumentTabCompleter completer;

    public CommandArgument(Argument argument) {
        this.argument = argument;
        this.parser = null;
        try {
            this.parser = argument.parser().newInstance();
            this.completer = argument.tabCompleter().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object parse(String arg) {
        return parser.parse(arg);
    }

    public ArgumentTabCompleter getTabCompleter() {
        return completer;
    }

    @Override
    public String name() {
        return argument.name();
    }

    @Override
    public String[] aliases() {
        return argument.aliases();
    }

    @Override
    public boolean optional() {
        return argument.optional();
    }

    @Override
    public boolean multiple() {
        return argument.optional();
    }

    @Override
    public Class<? extends Parser<?>> parser() {
        return argument.parser();
    }

    @Override
    public Class<? extends ArgumentTabCompleter> tabCompleter() {
        return argument.tabCompleter();
    }

    @Override
    @Nullable
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
