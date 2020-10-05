package com.bubladecoding.developertools;

import com.bubladecoding.developertools.commands.GimmeCommand;
import com.bubladecoding.developertools.commands.test.TestCommand;
import com.bubladecoding.developertools.events.MobEvents;
import com.bubladecoding.mcpluginbase.McPluginBase;

public final class DeveloperTools extends McPluginBase {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerTabExecutor("test", new TestCommand());
        registerTabExecutor("gimme", new GimmeCommand());
        getServer().getPluginManager().registerEvents(new MobEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
