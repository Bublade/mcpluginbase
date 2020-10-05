package com.bubladecoding.mcpluginbase.command.argument;
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

import com.bubladecoding.mcpluginbase.parsing.Parser;
import com.bubladecoding.mcpluginbase.parsing.parses.*;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for easily parsing arguments.
 */
public class Arguments {

    private String[] originalArgs;

    /**
     * Constructor.
     *
     * @param originalArgs The arguments given.
     */
    public Arguments(String[] originalArgs) {
        this.originalArgs = originalArgs;
    }

    /**
     * Get the {@link Boolean} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Boolean getBoolean(int position) {
        return getValue(position, BooleanParser.class);
    }

    /**
     * Get the {@link Boolean} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public Boolean getBoolean(int position, Boolean fallback) {
        return getValue(position, BooleanParser.class, fallback);
    }

    /**
     * Get the {@link Double} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Double getDouble(int position) {
        return getValue(position, DoubleParser.class);
    }

    /**
     * Get the {@link Double} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public Double getDouble(int position, Double fallback) {
        return getValue(position, DoubleParser.class, fallback);
    }

    /**
     * Get the {@link Float} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Float getFloat(int position) {
        return getValue(position, FloatParser.class);
    }

    /**
     * Get the {@link Float} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public Float getFloat(int position, Float fallback) {
        return getValue(position, FloatParser.class, fallback);
    }

    /**
     * Get the {@link Integer} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Integer getInteger(int position) {
        return getValue(position, IntParser.class);
    }

    /**
     * Get the {@link Integer} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public Integer getInteger(int position, Integer fallback) {
        return getValue(position, IntParser.class, fallback);
    }

    /**
     * Get the {@link Material} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Material getMaterial(int position) {
        return getValue(position, MaterialParser.class);
    }

    /**
     * Get the {@link Material} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public Material getMaterial(int position, Material fallback) {
        return getValue(position, MaterialParser.class, fallback);
    }

    /**
     * Get the {@link OfflinePlayer} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public OfflinePlayer getOfflinePlayer(int position) {
        return getValue(position, OfflinePlayerParser.class);
    }

    /**
     * Get the {@link OfflinePlayer} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    public OfflinePlayer getOfflinePlayer(int position, OfflinePlayer fallback) {
        return getValue(position, OfflinePlayerParser.class, fallback);
    }

    /**
     * Get the {@link Player} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    @Nullable
    public Player getPlayer(int position) {
        return getValue(position, PlayerParser.class);
    }

    /**
     * Get the {@link Player} argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @return The parsed argument.
     */
    @NotNull
    public Player getPlayer(int position, Player fallback) {
        return getValue(position, PlayerParser.class, fallback);
    }

    /**
     * Get the original argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @return The parsed argument.
     */
    public String get(int position) {
        return originalArgs[position - 1];
    }

    /**
     * Get the original argument at the given position (NOT 0 index BASED).
     *
     * @param position Position to get the argument from.
     * @param fallback Fallback if position is not available.
     * @return The parsed argument.
     */
    @NotNull
    public String get(int position, String fallback) {
        String value = null;
        if (positionAvailable(position)) {
            value = originalArgs[position - 1];
        }
        return value != null ? value : fallback;
    }

    /**
     * Get the value of the argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument (NOT 0 index BASED).
     * @param parser   The parser to use when parsing the argument.
     * @param <V>      Type of the value.
     * @param <T>      Type of the {@link Parser} that returns a {@link V}
     * @return The parsed value, or null.
     */
    @Nullable
    public <V, T extends Parser<V>> V getValue(int position, Class<T> parser) {
        if (!positionAvailable(position)) {
            throw new ArgumentNotFoundException(position);
        }

        try {
            return parser.newInstance().parse(get(position));
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Get the value of the argument at the given position (NOT 0 index BASED).
     *
     * @param position Position of the argument (NOT 0 index BASED).
     * @param parser   The parser to use when parsing the argument.
     * @param fallback Fallback if value is {@literal null}.
     * @param <V>      Type of the value.
     * @param <T>      Type of the {@link Parser} that returns a {@link V}.
     * @return The parsed value, or the fallback if value is {@literal null}.
     */
    public <V, T extends Parser<V>> V getValue(int position, Class<T> parser, V fallback) {
        if (!positionAvailable(position)) {
            throw new ArgumentNotFoundException(position);
        }

        try {
            V value = parser.newInstance().parse(get(position));
            return value != null ? value : fallback;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Size of the original array.
     *
     * @return The size of the original array.
     */
    public int size() {
        return originalArgs.length;
    }

    public boolean positionAvailable(int position) {
        return originalArgs.length >= position;
    }

    /**
     * The original arguments in array form.
     * @return Array of the original arg.
     */
    public String[] getOriginalArgs() {
        return originalArgs;
    }

}
