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

import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.Nullable;

/**
 * MetaBuilder class for editing {@link CompassMeta}.
 */
public class CompassMetaBuilder extends BaseMetaBuilder<CompassMeta, CompassMetaBuilder> {

    protected CompassMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, CompassMeta.class, CompassMetaBuilder.class);
    }

    /**
     * Sets the location this lodestone compass will point to.
     *
     * @param lodestone new location or null to clear
     */
    public CompassMetaBuilder setLodestone(@Nullable Location lodestone) {
        return editMeta(meta -> meta.setLodestone(lodestone));
    }

    /**
     * Sets if this compass is tracking a specific lodestone.
     *
     * If true the compass will only work if there is a lodestone at the tracked
     * location.
     *
     * @param tracked new tracked status
     */
    public CompassMetaBuilder setLodestoneTracked(boolean tracked) {
        return editMeta(meta -> meta.setLodestoneTracked(tracked));
    }
}
