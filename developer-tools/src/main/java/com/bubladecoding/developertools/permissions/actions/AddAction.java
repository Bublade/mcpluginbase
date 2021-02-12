package com.bubladecoding.developertools.permissions.actions;
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

import com.bubladecoding.developertools.DeveloperToolsPlugin;
import com.bubladecoding.developertools.managers.IGroupManager;
import com.bubladecoding.developertools.permissions.PermissionValueType;
import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import com.bubladecoding.developertools.permissions.interfaces.IPermissible;
import com.bubladecoding.developertools.permissions.interfaces.IPermissibleAction;

public class AddAction implements IPermissibleAction {

    private final DeveloperToolsPlugin plugin;

    public AddAction(DeveloperToolsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getActionName() {
        return null;
    }

    @Override
    public boolean apply(String[] values, PermissionValueType type, IPermissible permissible) {
        if (type == PermissionValueType.PERMISSION) {
            for (String permission : values) {
                permissible.setPermission(permission, true);
            }
            return true;
        }

        if (type == PermissionValueType.GROUP) {
            for (String groupName : values) {
                IGroup group = plugin.getService(IGroupManager.class).getGroup(groupName);
                if (group != null) {
                    permissible.addGroup(group);
                }
            }
            return true;
        }

        return false;
    }
}
