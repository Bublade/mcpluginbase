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
package com.bubladecoding.mcpluginbase.parsing.parses;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialParserTest {

    @Test
    void parse_STONE_SWORD_STONE_SWORD() {
        MaterialParser parser = new MaterialParser();
        assertEquals(Material.STONE_SWORD, parser.parse("STONE_SWORD"));
    }
    @Test
    void parse_stone_sword_STONE_SWORD() {
        MaterialParser parser = new MaterialParser();
        assertEquals(Material.STONE_SWORD, parser.parse("stone_sword"));
    }
    @Test
    void parse_stoneSword_STONE_SWORD() {
        MaterialParser parser = new MaterialParser();
        assertEquals(Material.STONE_SWORD, parser.parse("stoneSword"));
    }
    @Test
    void parse_StoneSword_STONE_SWORD() {
        MaterialParser parser = new MaterialParser();
        assertEquals(Material.STONE_SWORD, parser.parse("StoneSword"));
    }
    @Test
    void parse_stonesword_STONE_SWORD() {
        MaterialParser parser = new MaterialParser();
        assertEquals(Material.STONE_SWORD, parser.parse("stonesword"));
    }
}
