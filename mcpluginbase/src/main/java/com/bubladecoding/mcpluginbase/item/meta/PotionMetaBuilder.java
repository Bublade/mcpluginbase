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

import org.bukkit.Color;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * MetaBuilder class for editing {@link PotionMeta}.
 */
public class PotionMetaBuilder extends BaseMetaBuilder<PotionMeta, PotionMetaBuilder> {

    protected PotionMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, PotionMeta.class, PotionMetaBuilder.class);
    }

    /**
     * Sets the underlying potion data
     *
     * @param data PotionData to set the base potion state to
     */
    public PotionMetaBuilder setBasePotionData(@NotNull PotionData data) {
        return editMeta(meta -> meta.setBasePotionData(data));
    }

    /**
     * Adds a custom potion effect to this potion.
     *
     * @param effect the potion effect to add
     * @param overwrite true if any existing effect of the same type should be
     * overwritten
     */
    public PotionMetaBuilder addCustomEffect(@NotNull PotionEffect effect, boolean overwrite) {
        return editMeta(meta -> meta.addCustomEffect(effect, overwrite));
    }

    /**
     * Removes a custom potion effect from this potion.
     *
     * @param type the potion effect type to remove
     */
    public PotionMetaBuilder removeCustomEffect(@NotNull PotionEffectType type) {
        return editMeta(meta -> meta.removeCustomEffect(type));
    }

    /**
     * Moves a potion effect to the top of the potion effect list.
     * <p>
     * This causes the client to display the potion effect in the potion's name.
     *
     * @param type the potion effect type to move
     * @deprecated use {@link #setBasePotionData(org.bukkit.potion.PotionData)}
     */
    @Deprecated
    public PotionMetaBuilder setMainEffect(@NotNull PotionEffectType type) {
        return editMeta(meta -> meta.setMainEffect(type));
    }

    /**
     * Sets the potion color. A custom potion color will alter the display of
     * the potion in an inventory slot.
     *
     * @param color the color to set
     */
    public PotionMetaBuilder setColor(@Nullable Color color) {
        return editMeta(meta -> meta.setColor(color));
    }
}
