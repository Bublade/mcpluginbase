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

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

/**
 * MetaBuilder class for editing {@link FireworkMeta}.
 */
public class FireworkMetaBuilder extends BaseMetaBuilder<FireworkMeta, FireworkMetaBuilder> {

    protected FireworkMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, FireworkMeta.class, FireworkMetaBuilder.class);
    }

    /**
     * Add another effect to this firework.
     *
     * @param effect The firework effect to add
     * @throws IllegalArgumentException If effect is null
     */
    public FireworkMetaBuilder addEffect(@NotNull FireworkEffect effect) throws IllegalArgumentException {
        return editMeta(meta -> meta.addEffect(effect));
    }

    /**
     * Add several effects to this firework.
     *
     * @param effects The firework effects to add
     * @throws IllegalArgumentException If effects is null
     * @throws IllegalArgumentException If any effect is null (may be thrown
     *     after changes have occurred)
     */
    public FireworkMetaBuilder addEffects(@NotNull FireworkEffect... effects) throws IllegalArgumentException {
        return editMeta(meta -> meta.addEffects(effects));
    }

    /**
     * Add several firework effects to this firework.
     *
     * @param effects An iterable object whose iterator yields the desired
     *     firework effects
     * @throws IllegalArgumentException If effects is null
     * @throws IllegalArgumentException If any effect is null (may be thrown
     *     after changes have occurred)
     */
    public FireworkMetaBuilder addEffects(@NotNull Iterable<FireworkEffect> effects) throws IllegalArgumentException {
        return editMeta(meta -> meta.addEffects(effects));
    }

    /**
     * Remove an effect from this firework.
     *
     * @param index The index of the effect to remove
     * @throws IndexOutOfBoundsException If index {@literal < 0 or index >} {@link
     *     FireworkMeta#getEffectsSize()}
     */
    public FireworkMetaBuilder removeEffect(int index) throws IndexOutOfBoundsException {
        return editMeta(meta -> meta.removeEffect(index));
    }

    /**
     * Sets the approximate power of the firework. Each level of power is half
     * a second of flight time.
     *
     * @param power the power of the firework, from 0-128
     * @throws IllegalArgumentException if {@literal height<0 or height>128}
     */
    public FireworkMetaBuilder setPower(int power) throws IllegalArgumentException {
        return editMeta(meta -> meta.setPower(power));
    }
}
