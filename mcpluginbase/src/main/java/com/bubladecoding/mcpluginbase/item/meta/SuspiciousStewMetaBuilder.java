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

import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

/**
 * MetaBuilder class for editing {@link SuspiciousStewMeta}.
 */
public class SuspiciousStewMetaBuilder extends BaseMetaBuilder<SuspiciousStewMeta, SuspiciousStewMetaBuilder> {

    public SuspiciousStewMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, SuspiciousStewMeta.class, SuspiciousStewMetaBuilder.class);
    }

    /**
     * Adds a custom potion effect to this suspicious stew.
     *
     * @param effect the potion effect to add
     * @param overwrite true if any existing effect of the same type should be
     * overwritten
     */
    public SuspiciousStewMetaBuilder addCustomEffect(@NotNull PotionEffect effect, boolean overwrite) {
        return editMeta(meta -> meta.addCustomEffect(effect, overwrite));
    }

    /**
     * Removes a custom potion effect from this suspicious stew.
     *
     * @param type the potion effect type to remove
     */
    public SuspiciousStewMetaBuilder removeCustomEffect(@NotNull PotionEffectType type) {
        return editMeta(meta -> meta.removeCustomEffect(type));
    }

    /**
     * Removes all custom potion effects from this suspicious stew.
     */
    public SuspiciousStewMetaBuilder clearCustomEffects() {
        return editMeta(SuspiciousStewMeta::clearCustomEffects);
    }

}
