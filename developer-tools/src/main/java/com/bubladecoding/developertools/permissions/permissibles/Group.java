package com.bubladecoding.developertools.permissions.permissibles;
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

import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Group implements IGroup {

    private final UUID uuid;
    private final String name;

    public Group(String name) {
        this(UUID.randomUUID(), name);
    }

    public Group(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addGroup(IGroup group) {
    }

    @Override
    public void setGroups(IGroup group) {

    }

    @Override
    public void removeGroup(IGroup group) {

    }

    @Override
    public void setGroups(List<IGroup> groups) {

    }

    @Override
    public List<IGroup> getGroups() {
        return null;
    }

    @Override
    public Map<String, Boolean> getPermissions() {
        return new HashMap<>();
    }

    @Override
    public void setPermission(@NotNull String name) {

    }

    @Override
    public void setPermission(@NotNull Permission permission) {

    }

    @Override
    public void setPermission(@NotNull String name, boolean value) {

    }

    @Override
    public void setPermission(@NotNull Permission permission, boolean value) {

    }

    @Override
    public void unsetPermission(@NotNull String name) {

    }

    @Override
    public void unsetPermission(@NotNull Permission permission) {

    }

    @Override
    public String getPermissibleType() {
        return "group";
    }

}
