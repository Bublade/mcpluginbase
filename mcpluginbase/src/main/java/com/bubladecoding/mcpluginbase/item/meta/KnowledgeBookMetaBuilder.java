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

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * MetaBuilder class for editing {@link KnowledgeBookMeta}.
 */
public class KnowledgeBookMetaBuilder extends BaseMetaBuilder<KnowledgeBookMeta, KnowledgeBookMetaBuilder> {

    protected KnowledgeBookMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, KnowledgeBookMeta.class, KnowledgeBookMetaBuilder.class);
    }

    /**
     * Clears the existing book recipes, and sets the book to use the provided
     * recipes.
     *
     * @param recipes A list of recipes to set the book to use
     */
    public KnowledgeBookMetaBuilder setRecipes(@NotNull List<NamespacedKey> recipes) {
        return editMeta(meta -> meta.setRecipes(recipes));
    }

    /**
     * Adds new recipe to the end of the book.
     *
     * @param recipes A list of recipe keys
     */
    public KnowledgeBookMetaBuilder addRecipe(@NotNull NamespacedKey... recipes) {
        return editMeta(meta -> meta.addRecipe(recipes));
    }
}
