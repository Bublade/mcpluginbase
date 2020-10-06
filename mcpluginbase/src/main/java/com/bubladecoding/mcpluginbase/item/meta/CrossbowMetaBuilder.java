package com.bubladecoding.mcpluginbase.item.meta;
/*
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

import com.bubladecoding.mcpluginbase.item.ItemBuilder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * MetaBuilder class for editing {@link CrossbowMeta}.
 */
public class CrossbowMetaBuilder extends BaseMetaBuilder<CrossbowMeta, CrossbowMetaBuilder> {

    public CrossbowMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, CrossbowMeta.class, CrossbowMetaBuilder.class);
    }

    /**
     * Sets the projectiles charged on this item.
     *
     * Removes all projectiles when given null.
     *
     * @param projectiles the projectiles to set
     * @throws IllegalArgumentException if one of the projectiles is not an
     * arrow or firework rocket
     */
    public CrossbowMetaBuilder setChargedProjectiles(@Nullable List<ItemStack> projectiles) {
        return editMeta(meta -> meta.setChargedProjectiles(projectiles));
    }

    /**
     * Adds a charged projectile to this item.
     *
     * @param item projectile
     * @throws IllegalArgumentException if the projectile is not an arrow or
     * firework rocket
     */
    public CrossbowMetaBuilder addChargedProjectile(@NotNull ItemStack item) {
        return editMeta(meta -> meta.addChargedProjectile(item));
    }
}
