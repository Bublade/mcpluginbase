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
package com.bubladecoding.mcpluginbase.command.tabcompleter;

import com.bubladecoding.mcpluginbase.command.interfaces.ArgumentTabCompleter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialTabCompleter implements ArgumentTabCompleter {

    @Override
    public List<String> complete(String arg) {
        return Arrays.stream(Material.values())
                .filter(material -> include(material, arg))
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private boolean include(Material material, String arg) {
        String name = material.name();
        if (name.startsWith(arg.toUpperCase())) {
            return true;
        }

        String strippedName = material.name().replaceAll("_", "");
        if (strippedName.startsWith(arg) || strippedName.contains(arg)) {
            return true;
        }

        String[] args = arg.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        String value = String.join("_", args).toUpperCase();

        return name.startsWith(value) || name.contains(value);
    }
}
