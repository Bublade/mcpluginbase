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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class MetaBuilder<T extends ItemMeta, V extends MetaBuilder<T, V>> {

    protected final ItemBuilder itemBuilder;
    protected final T itemMeta;

    private final Class<V> builderClass;

    protected MetaBuilder(ItemBuilder itemBuilder, Class<T> metaClass, Class<V> builderClass) {
        this.builderClass = builderClass;
        this.itemBuilder = itemBuilder;
        if (!metaClass.isInstance(itemBuilder.getStack().getItemMeta())) {
            String itemMetaType = Objects.requireNonNull(itemBuilder.getStack().getItemMeta()).getClass().getSimpleName();
            throw new ClassCastException(
                    "MetaBuilder expected an item with the ItemMeta type '" + metaClass.getSimpleName() + "' " +
                            "but got an Item with MetaType '" + itemMetaType + "' instead."
            );
        }

        this.itemMeta = metaClass.cast(itemBuilder.getStack().getItemMeta());
    }


    public ItemBuilder buildMeta() {
        itemBuilder.getStack().setItemMeta(this.itemMeta);
        return itemBuilder;
    }

    public ItemStack build() {
        itemBuilder.getStack().setItemMeta(this.itemMeta);
        return itemBuilder.getStack();
    }

    protected V editMeta(Consumer<T> meta) {
        meta.accept(this.itemMeta);
        return builderClass.cast(this);
    }
}
