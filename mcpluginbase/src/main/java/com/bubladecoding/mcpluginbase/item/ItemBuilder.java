package com.bubladecoding.mcpluginbase.item;
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

import com.bubladecoding.mcpluginbase.item.meta.ItemMetaBuilder;
import com.bubladecoding.mcpluginbase.item.meta.MetaBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.material.MaterialData;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An {@link ItemStack} builder, for creating new {@link ItemStack}s and editing them.
 */
public class ItemBuilder {

    private final ItemStack stack;

    /**
     * Constructor for an all clean {@link ItemStack}.
     * The default {@link Material} is {@link Material#AIR}
     */
    public ItemBuilder() {
        this(Material.AIR);
    }

    /**
     * Constructor for a new {@link ItemStack} based on the given {@link Material}.
     * @param material The {@link Material} type of the ItemStack
     */
    public ItemBuilder(Material material) {
        this.stack = new ItemStack(material);
    }

    /**
     * Constructor for editing an existing {@link ItemStack}.
     * @param stack The {@link ItemStack}
     */
    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Adds the specified {@link Enchantment} to this item stack.
     * <p>
     * If this item stack already contained the given enchantment (at any
     * level), it will be replaced.
     *
     * @param enchantment Enchantment to add
     * @param level       Level of the enchantment
     * @throws IllegalArgumentException if enchantment null, or enchantment is
     *                                  not applicable
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        stack.addEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds the specified enchantments to this item stack.
     * <p>
     * This method is the same as calling {@link
     * #addEnchantment(org.bukkit.enchantments.Enchantment, int)} for each
     * element of the map.
     *
     * @param enchantments Enchantments to add
     * @throws IllegalArgumentException if the specified enchantments is null
     * @throws IllegalArgumentException if any specific enchantment or level
     *                                  is null. <b>Warning</b>: Some enchantments may be added before this
     *                                  exception is thrown.
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        stack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Adds the specified {@link Enchantment} to this item stack.
     * <p>
     * If this item stack already contained the given enchantment (at any
     * level), it will be replaced.
     * <p>
     * This method is unsafe and will ignore level restrictions or item type.
     * Use at your own discretion.
     *
     * @param enchantment Enchantment to add
     * @param level       Level of the enchantment
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        stack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds the specified enchantments to this item stack in an unsafe manner.
     * <p>
     * This method is the same as calling {@link
     * #addUnsafeEnchantment(org.bukkit.enchantments.Enchantment, int)} for
     * each element of the map.
     *
     * @param enchantments Enchantments to add
     */
    public ItemBuilder addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        stack.addUnsafeEnchantments(enchantments);
        return this;
    }

    /**
     * Sets the amount of items in this stack
     *
     * @param amount New amount of items in this stack
     */
    public ItemBuilder setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    /**
     * Sets the type of this item
     * <p>
     * Note that in doing so you will reset the MaterialData for this stack.
     * <p>
     * <b>IMPORTANT: An <i>Item</i>Stack is only designed to contain
     * <i>items</i>. Do not use this class to encapsulate Materials for which
     * {@link Material#isItem()} returns false.</b>
     *
     * @param type New type to set the items in this stack to
     */
    public ItemBuilder setType(Material type) {
        stack.setType(type);
        return this;
    }

    /**
     * Sets the damage, if the item meta is not {@link Damageable} nothing will happen.
     *
     * @param damage item damage
     */
    public ItemBuilder setDamage(int damage) {
        ItemMeta meta = stack.getItemMeta();
        if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(damage);
            stack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Sets the repair penalty, if the item meta is not {@link Repairable} nothing will happen.
     *
     * @param cost repair penalty
     */
    public ItemBuilder setRepairCost(int cost) {
        ItemMeta meta = stack.getItemMeta();
        if (meta instanceof Repairable) {
            ((Repairable) meta).setRepairCost(cost);
            stack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Sets the MaterialData for this stack of items
     *
     * @param data New MaterialData for this item
     */
    @Deprecated
    public ItemBuilder setData(MaterialData data) {
        stack.setData(data);
        return this;
    }

    /**
     * Sets the durability of this item
     *
     * @param durability Durability of this item
     * @deprecated durability is now part of ItemMeta. To avoid confusion and
     * misuse, {@link #editMeta(Class)}, {@link #editItemMeta(Class, Consumer)},
     * and {@link Damageable#setDamage(int)} should be used instead. This is because
     * any call to this method will be overwritten by subsequent setting of
     * ItemMeta which was created before this call.
     */
    @Deprecated
    public ItemBuilder setDurability(short durability) {
        stack.setDurability(durability);
        return this;
    }

    /**
     * Set the ItemMeta of the ItemStack.
     *
     * @param itemMeta new ItemMeta, or null to indicate meta data be cleared.
     * @return True if successfully applied ItemMeta, see {@link
     * ItemFactory#isApplicable(ItemMeta, ItemStack)}
     * @throws IllegalArgumentException if the item meta was not created by
     *                                  the {@link ItemFactory}
     */
    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.stack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Method for editing the item meta.
     *
     * @param meta {@link Consumer} for editing the meta.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder editItemMeta(Consumer<ItemMeta> meta) {
        ItemMeta itemMeta = this.stack.getItemMeta();
        meta.accept(itemMeta);
        this.stack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Method for editing the item meta with a specific meta class.
     *
     * @param metaClass The {@link ItemMeta} class or an extended variant.
     * @param meta      {@link Consumer} for editing the meta.
     * @param <T>       Type of the {@link ItemMeta} that is going to be edited.
     * @return The {@link ItemBuilder} instance.
     */
    public <T extends ItemMeta> ItemBuilder editItemMeta(Class<T> metaClass, Consumer<T> meta) {
        if (metaClass.isInstance(this.stack.getItemMeta())) {
            T itemMeta = metaClass.cast(this.stack.getItemMeta());
            meta.accept(itemMeta);
            this.stack.setItemMeta(itemMeta);
        }

        return this;
    }

    public ItemMetaBuilder editMeta() {
        return new ItemMetaBuilder(this);
    }

    /**
     * Edit the {@link ItemMeta} with a custom {@link MetaBuilder}.
     *
     * @param metaBuilderClass {@link Class<T>} of the custom {@link MetaBuilder}.
     * @param <T> Type of the {@link MetaBuilder}.
     * @return The {@link MetaBuilder} to chain edit the {@link ItemMeta}, 
     * {@link MetaBuilder} can be closed using {@link MetaBuilder#buildMeta()}
     */
    public <T extends MetaBuilder<?, ?>> T editMeta(Class<T> metaBuilderClass) {
        try {
            return metaBuilderClass.getConstructor(ItemBuilder.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Build the item Builder with as result the {@link ItemStack}.
     * @return The {@link ItemStack} made with this builder.
     */
    public ItemStack build() {
        return stack;
    }

}
