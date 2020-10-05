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
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * MetaBuilder class for editing {@link BannerMeta}.
 */
public class BannerMetaBuilder extends BaseMetaBuilder<BannerMeta, BannerMetaBuilder> {

    protected BannerMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, BannerMeta.class, BannerMetaBuilder.class);
    }

    /**
     * Sets the base color for this banner
     *
     * @param color the base color
     * @deprecated banner color is now stored as the data value, not meta.
     */
    @Deprecated
    public BannerMetaBuilder setBaseColor(@Nullable DyeColor color) {
        return editMeta(meta -> meta.setBaseColor(color));
    }

    /**
     * Sets the patterns used on this banner
     *
     * @param patterns the new list of patterns
     */
    public BannerMetaBuilder setPatterns(@NotNull List<Pattern> patterns) {
        return editMeta(meta -> meta.setPatterns(patterns));
    }

    /**
     * Adds a new pattern on top of the existing
     * patterns
     *
     * @param pattern the new pattern to add
     */
    public BannerMetaBuilder addPattern(@NotNull Pattern pattern) {
        return editMeta(meta -> meta.addPattern(pattern));
    }

    /**
     * Removes the pattern at the specified index
     *
     * @param index the index
     * @throws IndexOutOfBoundsException when index is not in [0, numberOfPatterns()) range
     */
    public BannerMetaBuilder removePattern(int index) {
        return editMeta(meta -> meta.removePattern(index));
    }

    /**
     * Sets the pattern at the specified index
     *
     * @param index   the index
     * @param pattern the new pattern
     * @throws IndexOutOfBoundsException when index is not in [0, numberOfPatterns()) range
     */
    public BannerMetaBuilder setPattern(int index, @NotNull Pattern pattern) {
        return editMeta(meta -> meta.setPattern(index, pattern));
    }
}
