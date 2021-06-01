package com.bubladecoding.developertools.permissions.interfaces;
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

import com.bubladecoding.developertools.permissions.GroupParser;
import com.bubladecoding.developertools.permissions.UserParser;
import com.bubladecoding.mcpluginbase.command.annotation.HelpFormat;
import com.bubladecoding.mcpluginbase.command.annotation.Selector;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@Selector(name="group", parameterType = IGroup.class)
@Selector(name="user", parameterType = IUser.class)
@HelpFormat("@[selectors] {name}")
public interface IPermissible {

    String getName();

    void addGroup(IGroup group);
    void setGroups(IGroup group);
    void removeGroup(IGroup group);
    void setGroups(List<IGroup> groups);

    Map<String, Boolean> getPermissions();

    void setPermission(@NotNull String name);
    void setPermission(@NotNull Permission permission);
    void setPermission(@NotNull String name, boolean value);
    void setPermission(@NotNull Permission permission, boolean value);

    void unsetPermission(@NotNull String name);
    void unsetPermission(@NotNull Permission permission);
}
