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

import com.bubladecoding.mcpluginbase.command.annotation.Permission;

import java.lang.reflect.Method;

public class CommandMethod {

    private Object parent;
    private final Method method;

    private final String name;
    private final CommandArgument<?>[] arguments;
    private final String description;
    private final String[] permissions;
    private final Permission.ListType listType;

    private final String[] aliases;

    public CommandMethod(Method method, String name, CommandArgument<?>[] arguments, String description, String[] permissions, Permission.ListType permissionsListType, String[] aliases) {
        this.method = method;
        this.name = name;
        this.arguments = arguments;
        this.description = description;
        this.permissions = permissions;
        this.listType = permissionsListType;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public CommandArgument<?>[] getArguments() {
        return arguments;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public String[] getAliases() {
        return aliases;
    }

    public Method getMethod() {
        return method;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }
}
