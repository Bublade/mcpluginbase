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

import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

/**
 * MetaBuilder class for editing {@link TropicalFishBucketMeta}.
 */
public class TropicalFishBucketMetaBuilder extends BaseMetaBuilder<TropicalFishBucketMeta, TropicalFishBucketMetaBuilder> {

    public TropicalFishBucketMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, TropicalFishBucketMeta.class, TropicalFishBucketMetaBuilder.class);
    }

    /**
     * Sets the color of the fish's pattern.
     * <p>
     * Setting this when hasVariant() returns <code>false</code> will initialize
     * all other values to unspecified defaults.
     *
     * @param color pattern color
     */
    public TropicalFishBucketMetaBuilder setPatternColor(@NotNull DyeColor color) {
        return editMeta(meta -> meta.setPatternColor(color));
    }

    /**
     * Sets the color of the fish's body.
     * <p>
     * Setting this when hasVariant() returns <code>false</code> will initialize
     * all other values to unspecified defaults.
     *
     * @param color body color
     */
    public TropicalFishBucketMetaBuilder setBodyColor(@NotNull DyeColor color) {
        return editMeta(meta -> meta.setBodyColor(color));
    }

    /**
     * Sets the fish's pattern.
     * <p>
     * Setting this when hasVariant() returns <code>false</code> will initialize
     * all other values to unspecified defaults.
     *
     * @param pattern new pattern
     */
    public TropicalFishBucketMetaBuilder setPattern(@NotNull TropicalFish.Pattern pattern) {
        return editMeta(meta -> meta.setPattern(pattern));
    }
}
