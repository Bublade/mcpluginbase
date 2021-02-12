package com.bubladecoding.developertools.permissions;
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
import com.bubladecoding.developertools.permissions.interfaces.IPermissible;
import com.bubladecoding.developertools.permissions.interfaces.IPermissibleAction;
import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

public class PermissionCommand extends CommandBase<DeveloperToolsPlugin> {

    private static final String onSuccess = "Successfully {action} the {type} {value} to {permissible}";
    private static final String onFail = "Failed to {action} the {type} {value} to {permissible}";

    public PermissionCommand(DeveloperToolsPlugin plugin) {
        super(plugin);
    }

    // perms ([user] bublade) [add] [group] "admin owner moderator"

    @CommandExecutor
    public boolean onPermissions(CommandSender sender, IPermissible permissible, IPermissibleAction action, PermissionValueType type, String[] args) {
        boolean result = action.apply(args, type, permissible);
        String msg = (result ? onSuccess : onFail)
                .replaceAll(Pattern.quote("{action}"), action.getActionName())
                .replaceAll(Pattern.quote("{type}"), type.getDisplayName())
                .replaceAll(Pattern.quote("{value}"), String.join(", ", args))
                .replaceAll(Pattern.quote("{permissible}"), String.join(", ", permissible.getName()));
        sender.sendMessage(msg);
        return true;
    }

}
