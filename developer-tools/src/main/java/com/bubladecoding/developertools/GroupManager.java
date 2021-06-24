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
package com.bubladecoding.developertools;

import com.bubladecoding.developertools.managers.IGroupManager;
import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import com.bubladecoding.developertools.permissions.permissibles.Group;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GroupManager implements IGroupManager {

    private final DeveloperToolsPlugin plugin;
    private final Map<UUID, IGroup> groups;
    private final Map<String, UUID> groupNames;

    public GroupManager(DeveloperToolsPlugin plugin) {
        this.plugin = plugin;
        this.groups = new HashMap<>();
        this.groupNames = new HashMap<>();
    }

    @Nullable
    @Override
    public IGroup getGroup(@NotNull String name) {
        UUID uuid = this.groupNames.get(name.toLowerCase());
        return this.groups.get(uuid);
    }

    @Nullable
    @Override
    public IGroup getGroup(@NotNull UUID uuid) {
        return this.groups.get(uuid);
    }

    @Nullable
    @Override
    public IGroup createGroup(@NotNull String name) {
        if (this.groupNames.containsKey(name.toLowerCase())) {
           return null;
        }

        IGroup group = new Group(name.toLowerCase());
        this.groups.put(group.getUuid(), group);
        this.groupNames.put(name, group.getUuid());

        return group;
    }
}
