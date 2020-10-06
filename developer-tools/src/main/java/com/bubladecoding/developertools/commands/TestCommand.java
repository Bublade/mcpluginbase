package com.bubladecoding.developertools.commands;/*
 * Copyright (c) 2020 bublade
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

import com.bubladecoding.mcpluginbase.command.CommandBase;
import com.bubladecoding.mcpluginbase.command.argument.Arguments;
import com.bubladecoding.mcpluginbase.command.interfaces.CommandSender;
import com.bubladecoding.mcpluginbase.item.ItemBuilder;
import com.bubladecoding.mcpluginbase.item.meta.ItemMetaBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestCommand extends CommandBase {

    public TestCommand() {
        super();
        addSubCommand("item", this::onItem);
    }

    private boolean onItem(CommandSender commandSender, Command command, String s, Arguments arguments) {
        Player player = commandSender.getPlayer();
        if (player == null) {
            return false;
        }

        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHERITE_SWORD);
        ItemMetaBuilder itemMetaBuilder = itemBuilder.editMeta(ItemMetaBuilder.class);

        if (itemMetaBuilder == null) {
            throw new NullPointerException("JOW WUT THE FUCK?");
        }

        itemBuilder = itemMetaBuilder.addAttributeModifier(
                        Attribute.GENERIC_ATTACK_DAMAGE,
                        new AttributeModifier("OMEGA DAMAGE", 100, AttributeModifier.Operation.ADD_NUMBER)
        ).buildMeta();

        player.getInventory().addItem(itemBuilder.build());


        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull Arguments arguments) {
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull Arguments arguments) {
        return false;
    }
}
