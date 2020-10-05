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

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

/**
 * MetaBuilder class for editing {@link EnchantmentStorageMeta}.
 */
public class EnchantmentStorageMetaBuilder extends BaseMetaBuilder<EnchantmentStorageMeta, EnchantmentStorageMetaBuilder> {

    protected EnchantmentStorageMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, EnchantmentStorageMeta.class, EnchantmentStorageMetaBuilder.class);
    }

    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param enchantment            Enchantment to store
     * @param level                  Level for the enchantment
     * @param ignoreLevelRestriction this indicates the enchantment should be
     *                               applied, ignoring the level limit
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     * @throws IllegalArgumentException if enchantment is null
     */
    public EnchantmentStorageMetaBuilder addStoredEnchant(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        return editMeta(meta -> meta.addStoredEnchant(enchantment, level, ignoreLevelRestriction));
    }

    /**
     * Remove the specified stored enchantment from this item meta.
     *
     * @param enchantment Enchantment to remove
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     * @throws IllegalArgumentException if enchantment is null
     */
    public EnchantmentStorageMetaBuilder removeStoredEnchant(@NotNull Enchantment enchantment) throws IllegalArgumentException {
        return editMeta(meta -> meta.removeStoredEnchant(enchantment));
    }
}
