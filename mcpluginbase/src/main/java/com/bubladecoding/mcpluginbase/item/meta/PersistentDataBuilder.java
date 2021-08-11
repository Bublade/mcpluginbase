/*
 * Copyright (c) 2021 bublade
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
package com.bubladecoding.mcpluginbase.item.meta;

import com.bubladecoding.mcpluginbase.item.ItemBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PersistentDataBuilder {

    private final ItemBuilder itemBuilder;
    private final Plugin plugin;

    public PersistentDataBuilder(ItemBuilder itemBuilder, Plugin plugin) {
        this.itemBuilder = itemBuilder;
        this.plugin = plugin;
    }
    /*
     *  The primitive one value types.
     */
    public PersistentDataBuilder setByte(String key, Byte value) {
        return editPersistentData(key, PersistentDataType.BYTE, value);
    }
    public PersistentDataBuilder setShort(String key, Short value) {
        return editPersistentData(key, PersistentDataType.SHORT, value);
    }
    public PersistentDataBuilder setInteger(String key, Integer value) {
        return editPersistentData(key, PersistentDataType.INTEGER, value);
    }
    public PersistentDataBuilder setLong(String key, Long value) {
        return editPersistentData(key, PersistentDataType.LONG, value);
    }
    public PersistentDataBuilder setFloat(String key, Float value) {
        return editPersistentData(key, PersistentDataType.FLOAT, value);
    }
    public PersistentDataBuilder setDouble(String key, Double value) {
        return editPersistentData(key, PersistentDataType.DOUBLE, value);
    }

    /*
     * String.
     */
    public PersistentDataBuilder setString(String key, String value) {
        return editPersistentData(key, PersistentDataType.STRING, value);
    }

    /*
     * Primitive Arrays.
     */
    public PersistentDataBuilder setByteArray(String key, byte[] value) {
        return editPersistentData(key, PersistentDataType.BYTE_ARRAY, value);
    }
    public PersistentDataBuilder setIntArray(String key, int[] value) {
        return editPersistentData(key, PersistentDataType.INTEGER_ARRAY, value);
    }
    public PersistentDataBuilder setLongArray(String key, long[] value) {
        return editPersistentData(key, PersistentDataType.LONG_ARRAY, value);
    }

    /*
     *Complex Arrays.
     */
    public PersistentDataBuilder setPersistentDataContainerArray(String key, PersistentDataContainer[] value) {
        return editPersistentData(key, PersistentDataType.TAG_CONTAINER_ARRAY, value);
    }

    /*
     * Nested PersistentDataContainer.
     */
    public PersistentDataBuilder setPersistentDataContainer(String key, PersistentDataContainer value) {
        return editPersistentData(key, PersistentDataType.TAG_CONTAINER, value);
    }

    public ItemBuilder editItem() {
        return itemBuilder;
    }

    protected <T, Z> PersistentDataBuilder editPersistentData(@NotNull String key, @NotNull PersistentDataType<T, Z> type, @NotNull Z value) {
        itemBuilder.editItemMeta(meta -> {
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), type, value);
        });
        return this;
    }
}
