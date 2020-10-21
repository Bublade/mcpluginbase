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
package com.bubladecoding.mcpluginbase.command.argument;

import com.bubladecoding.mcpluginbase.command.CommandArgument;
import com.bubladecoding.mcpluginbase.command.CommandMethod;
import com.bubladecoding.mcpluginbase.command.CommandOption;
import com.bubladecoding.mcpluginbase.command.CommandParameter;
import com.bubladecoding.mcpluginbase.command.interfaces.ArgumentTabCompleter;

import java.util.List;

public class ArgumentParser {

    public static ArgumentTabCompleter getTabCompleter(List<String> args, CommandMethod commandMethod) {
        CommandParameter[] parameters = commandMethod.getParameters();
        Object[] arguments = new Object[parameters.length];
        if (args.size() == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            if (args.size() == 0) {
                return null;
            }

            CommandParameter parameter = parameters[i];
            CommandArgument argument = parameter.getArgument();
            CommandOption option = parameter.getOption();
            if (argument != null) {
                String arg = args.get(0);
                arguments[i] = argument.parse(arg);
            } else if (option != null) {
                String arg = args.get(0);
                arguments[i] = null;
            }

            if (arguments[i] == null) {
                return argument != null ? argument.getTabCompleter() : null;
            }
            args.remove(0);
        }

        return null;
    }

    public static Object[] parse(List<String> args, CommandMethod commandMethod) {
        CommandParameter[] parameters = commandMethod.getParameters();
        Object[] arguments = new Object[parameters.length];
        if (args.size() == 0) {
            return arguments;
        }

        for (int i = 0; i < parameters.length; i++) {
            if (args.size() == 0) {
                break;
            }

            CommandParameter parameter = parameters[i];
            CommandArgument argument = parameter.getArgument();
            CommandOption option = parameter.getOption();
            if (argument != null) {
                String arg = args.remove(0);
                arguments[i] = argument.parse(arg);
            } else if (option != null) {
                String arg = args.remove(0);
                arguments[i] = null;
            }
        }

        return arguments;
    }
}
