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
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * MetaBuilder class for editing {@link BookMeta}.
 */
public class BookMetaBuilder extends BaseMetaBuilder<BookMeta, BookMetaBuilder> {

    protected BookMetaBuilder(ItemBuilder itemBuilder) {
        super(itemBuilder, BookMeta.class, BookMetaBuilder.class);
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page.
     *
     * @param pages A list of strings, each being a page
     */
    public BookMetaBuilder addPage(String... pages) {
        return editMeta(meta -> meta.addPage(pages));
    }

    /**
     * Sets the author of the book. Removes author when given null.
     *
     * @param author the author to set
     */
    public BookMetaBuilder setAuthor(String author) {
        return editMeta(meta -> meta.setAuthor(author));
    }

    /**
     * Sets the generation of the book. Removes generation when given null.
     *
     * @param generation the generation to set
     */
    public BookMetaBuilder setGeneration(BookMeta.Generation generation) {
        return editMeta(meta -> meta.setGeneration(generation));
    }

    /**
     * Sets the specified page in the book. Pages of the book must be
     * contiguous.
     * <p>
     * The data can be up to 256 characters in length, additional characters
     * are truncated.
     *
     * @param page the page number to set
     * @param data the data to set for that page
     */
    public BookMetaBuilder setPage(int page, String data) {
        return editMeta(meta -> meta.setPage(page, data));
    }

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.
     *
     * @param pages A list of pages to set the book to use
     */
    public BookMetaBuilder setPages(List<String> pages) {
        return editMeta(meta -> meta.setPages(pages));
    }

    /**
     * Clears the existing book pages, and sets the book to use the provided
     * pages. Maximum 50 pages with 256 characters per page.
     *
     * @param pages A list of strings, each being a page
     */
    public BookMetaBuilder setPages(String... pages) {
        return editMeta(meta -> meta.setPages(pages));
    }

    /**
     * Sets the title of the book.
     * <p>
     * Limited to 16 characters. Removes title when given null.
     *
     * @param title the title to set
     */
    public BookMetaBuilder setTitle(String title) {
        return editMeta(meta -> meta.setTitle(title));
    }
}
