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
package com.bubladecoding.developertools.permissions.actions;

import com.bubladecoding.developertools.permissions.PermissionValueType;
import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import com.bubladecoding.developertools.permissions.interfaces.IPermissible;
import com.bubladecoding.developertools.permissions.interfaces.IPermissibleAction;
import com.bubladecoding.developertools.permissions.interfaces.IUser;
import com.bubladecoding.mcpluginbase.parsing.parses.IntParser;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoAction implements IPermissibleAction {

    @Override
    public String getActionName() {
        return "info";
    }

    @Override
    public boolean apply(CommandSender sender, String[] values, PermissionValueType type, IPermissible permissible) {
        String permissibleTN = "";
        if (permissible instanceof IUser user) {
            permissibleTN = "user " + user.getName();
        }

        if (permissible instanceof IGroup group){
            permissibleTN = "group " + group.getName();
        }

        Integer page = 1;
        if (values.length > 0) {
            IntParser parser = new IntParser();
            page = parser.parse(values[0], 1);
        }

        String info = "";
        ArrayList<String> infoList = new ArrayList<>();

        if (type == PermissionValueType.PERMISSION) {
            Map<String, Boolean> permissions = permissible.getPermissions();
            info = "Listed " + permissions.size() + " permissions for " + permissibleTN;
            infoList.addAll(permissions.entrySet()
                    .stream()
                    .map(permission -> (permission.getValue() ? ChatColor.GREEN : ChatColor.RED) + permission.getKey())
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        if (type == PermissionValueType.GROUP) {
            List<IGroup> groups = permissible.getGroups();
            info = "Listed " + groups.size() + " groups for " + permissibleTN;
            infoList.addAll(groups.stream()
                    .map(group -> ChatColor.GREEN + " - " + group.getName())
                    .collect(Collectors.toCollection(ArrayList::new)));
        }

        sender.sendMessage(info + " page " + page + "/" + ((int)Math.ceil(infoList.size() / 10d)));
        sender.sendMessage(infoList.stream().skip((page - 1) * 10L).limit(10).toArray(String[]::new));
        return false;
    }
}
