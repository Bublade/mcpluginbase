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

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

/**
 * MetaBuilder class for editing {@link SkullMeta}.
 */
public class SkullMetaBuilder extends BaseMetaBuilder<SkullMeta, SkullMetaBuilder> {

    public SkullMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, SkullMeta.class, SkullMetaBuilder.class);
    }

    /**
     * Sets the owner of the skull.
     *
     * @param owner the new owner of the skull
     * @deprecated see {@link #setOwningPlayer(org.bukkit.OfflinePlayer)}.
     */
    @Deprecated
    public SkullMetaBuilder setOwner(@Nullable String owner) {
        return editMeta(meta -> meta.setOwner(owner));
    }

    /**
     * Sets the owner of the skull.
     * <p>
     * Plugins should check that hasOwner() returns true before calling this
     * plugin.
     *
     * @param owner the new owner of the skull
     */
    public SkullMetaBuilder setOwningPlayer(@Nullable OfflinePlayer owner) {
        return editMeta(meta -> meta.setOwningPlayer(owner));
    }
}
