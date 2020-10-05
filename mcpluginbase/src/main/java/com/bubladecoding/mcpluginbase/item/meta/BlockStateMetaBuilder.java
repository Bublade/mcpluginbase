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

import org.bukkit.block.BlockState;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

/**
 * MetaBuilder class for editing {@link BlockStateMeta}.
 */
public class BlockStateMetaBuilder extends BaseMetaBuilder<BlockStateMeta, BlockStateMetaBuilder> {

    protected BlockStateMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, BlockStateMeta.class, BlockStateMetaBuilder.class);
    }

    /**
     * Attaches a copy of the passed block state to the item.
     *
     * @param blockState the block state to attach to the block.
     * @throws IllegalArgumentException if the blockState is null
     *         or invalid for this item.
     */
    public BlockStateMetaBuilder setBlockState(@NotNull BlockState blockState) {
        return editMeta(meta -> meta.setBlockState(blockState));
    }
}
