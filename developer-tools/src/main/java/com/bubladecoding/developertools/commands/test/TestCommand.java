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
package com.bubladecoding.developertools.commands.test;

import com.bubladecoding.developertools.DeveloperToolsPlugin;
import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.annotation.AutoInject;
import com.bubladecoding.mcpluginbase.command.annotation.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand extends CommandBase<DeveloperToolsPlugin> {
    public TestCommand(DeveloperToolsPlugin plugin) {
        super(plugin);
    }

    @CommandExecutor("test")
    public void onTest(@AutoInject CommandSender sender, IOptionTest option, ISelectorTest selector, @AutoInject String[] restArgs) {
        sender.sendMessage("SELECTOR: " + selector.getSomething());
        sender.sendMessage("OPTION: " + option.getOptionName());
        sender.sendMessage("rest args: " + String.join(", ", restArgs));
    }

}
