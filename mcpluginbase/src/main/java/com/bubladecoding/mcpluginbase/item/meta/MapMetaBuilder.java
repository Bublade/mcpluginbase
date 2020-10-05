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
import org.bukkit.UndefinedNullability;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Nullable;

/**
 * MetaBuilder class for editing {@link MapMeta}.
 */
public class MapMetaBuilder extends BaseMetaBuilder<MapMeta, MapMetaBuilder> {

    protected MapMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, MapMeta.class, MapMetaBuilder.class);
    }

    /**
     * Sets the map ID. This is used to determine what map is displayed.
     *
     * @param id the map id to set
     * @deprecated These methods are poor API: They rely on the caller to pass
     * in an only an integer property, and have poorly defined implementation
     * behavior if that integer is not a valid map (the current implementation
     * for example will generate a new map with a different ID). The xxxMapView
     * family of methods should be used instead.
     * @see MapMeta#setMapView(org.bukkit.map.MapView)
     */
    @Deprecated
    public MapMetaBuilder setMapId(int id) {
        return editMeta(meta -> meta.setMapId(id));
    }

    /**
     * Sets the associated map. This is used to determine what map is displayed.
     *
     * <p>
     * The implementation <b>may</b> allow null to clear the associated map, but
     * this is not required and is liable to generate a new (undefined) map when
     * the item is first used.
     *
     * @param map the map to set
     */
    public MapMetaBuilder setMapView(@UndefinedNullability("implementation defined") MapView map) {
        return editMeta(meta -> meta.setMapView(map));
    }

    /**
     * Sets if this map is scaling or not.
     *
     * @param value true to scale
     */
    public MapMetaBuilder setScaling(boolean value) {
        return editMeta(meta -> meta.setScaling(value));
    }

    /**
     * Sets the location name. A custom map color will alter the display of the
     * map in an inventory slot.
     *
     * @param name the name to set
     */
    public MapMetaBuilder setLocationName(@Nullable String name) {
        return editMeta(meta -> meta.setLocationName(name));
    }

    /**
     * Sets the map color. A custom map color will alter the display of the map
     * in an inventory slot.
     *
     * @param color the color to set
     */
    public MapMetaBuilder setColor(@Nullable Color color) {
        return editMeta(meta -> meta.setColor(color));
    }
}
