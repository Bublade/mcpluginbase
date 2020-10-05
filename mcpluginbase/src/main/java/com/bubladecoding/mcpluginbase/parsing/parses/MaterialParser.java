package com.bubladecoding.mcpluginbase.parsing.parses;
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
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class MaterialParser implements Parser<Material> {

    @Nullable
    @Override
    public Material parse(String arg) {
        Material material = Material.getMaterial(arg.toUpperCase());

        if (material == null && !arg.contains("_")) {
            material = Arrays.stream(Material.values())
                    .filter(m -> m.name().replaceAll("_", "").toUpperCase().equals(arg.toUpperCase()))
                    .findFirst().orElse(null);
        }

        if (material == null) {
            // https://stackoverflow.com/a/7594052/5599948
            String[] args = arg.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
            String value = String.join("_", args).toUpperCase();
            material = Material.getMaterial(value);

            if (material == null && value.endsWith("S")) {
                material = Material.getMaterial(value.substring(0, value.length() - 1));
            }
        }



        return material;
    }

}
