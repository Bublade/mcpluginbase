package com.bubladecoding.developertools;
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

import com.bubladecoding.developertools.commands.FlyCommand;
import com.bubladecoding.developertools.commands.test.*;
import com.bubladecoding.developertools.events.MobEvents;
import com.bubladecoding.developertools.managers.IGroupManager;
import com.bubladecoding.developertools.managers.IUserManager;
import com.bubladecoding.developertools.permissions.GroupParser;
import com.bubladecoding.developertools.permissions.UserParser;
import com.bubladecoding.developertools.permissions.interfaces.IGroup;
import com.bubladecoding.developertools.permissions.interfaces.IUser;
import com.bubladecoding.mcpluginbase.McPluginBase;
import org.bukkit.plugin.ServicePriority;

public final class DeveloperTools extends McPluginBase implements DeveloperToolsPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MobEvents(), this);

        getServer().getServicesManager().register(IUserManager.class, new UserManager(this), this, ServicePriority.High);
        getServer().getServicesManager().register(IGroupManager.class, new GroupManager(this), this, ServicePriority.High);

        getCommandManager().registerParser(IUser.class, UserParser.class);
        getCommandManager().registerParser(IGroup.class, GroupParser.class);

        getCommandManager().registerParser(ISelectorOneTest.class, SelectorOneTestParser.class);
        getCommandManager().registerParser(ISelectorTwoTest.class, SelectorTwoTestParser.class);

        getCommandManager().registerCommands(FlyCommand.class);
        getCommandManager().registerCommands(TestCommand.class);
    }

    @Override
    public void onDisable() {
//        saveResource();
        // Plugin shutdown logic
    }
}
