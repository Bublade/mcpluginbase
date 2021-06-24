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

import com.google.common.collect.Multimap;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * MetaBuilder class for editing {@link ItemMeta}.
 */
public abstract class BaseMetaBuilder<T extends ItemMeta, M extends MetaBuilder<T, M>> extends MetaBuilder<T, M> {

    public BaseMetaBuilder(ItemBuilder itemBuilder, Class<T> metaClass, Class<M> builderClass) {
        super(itemBuilder, metaClass, builderClass);
    }

    /**
     * Sets the display name.
     *
     * @param name the name to set
     */
    public M setDisplayName(@Nullable String name) {
        return editMeta(meta -> meta.setDisplayName(name));
    }

    /**
     * Sets the localized name.
     *
     * @param name the name to set
     */
    public M setLocalizedName(@Nullable String name) {
        return editMeta(meta -> meta.setLocalizedName(name));
    }

    /**
     * Sets the lore for this item.
     * Removes lore when given null.
     *
     * @param lore the lore that will be set
     */
    public M setLore(@Nullable List<String> lore) {
        return editMeta(meta -> meta.setLore(lore));
    }

    /**
     * Sets the lore for this item.
     * Removes lore when given null.
     *
     * @param lore the lore that will be set
     */
    public M setLore(@Nullable String... lore) {
        return editMeta(meta -> meta.setLore(Arrays.asList(lore)));
    }

    /**
     * Sets the custom model data.
     * <p>
     * CustomModelData is an integer that may be associated client side with a
     * custom item model.
     *
     * @param data the data to set, or null to clear
     */
    public M setCustomModelData(@Nullable Integer data) {
        return editMeta(meta -> meta.setCustomModelData(data));
    }

    /**
     * Adds the specified enchantment to this item meta.
     *
     * @param enchantment Enchantment to add
     * @param level Level for the enchantment
     * @param ignoreLevelRestriction this indicates the enchantment should be
     *     applied, ignoring the level limit
     */
    public M addEnchantment(@NotNull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        return editMeta(meta -> meta.addEnchant(enchantment, level, ignoreLevelRestriction));
    }

    /**
     * Removes the specified enchantment from this item meta.
     *
     * @param enchantment Enchantment to remove
     */
    public M removeEnchantment(@NotNull Enchantment enchantment) {
        return editMeta(meta -> meta.removeEnchant(enchantment));
    }

    /**
     * Set itemflags which should be ignored when rendering a ItemStack in the Client. This Method does silently ignore double set itemFlags.
     *
     * @param itemFlags The hideflags which shouldn't be rendered
     */
    public M addItemFlags(@NotNull ItemFlag... itemFlags) {
        return editMeta(meta -> meta.addItemFlags(itemFlags));
    }

    /**
     * Remove specific set of itemFlags. This tells the Client it should render it again. This Method does silently ignore double removed itemFlags.
     *
     * @param itemFlags Hideflags which should be removed
     */
    public M removeItemFlags(@NotNull ItemFlag... itemFlags) {
        return editMeta(meta -> meta.removeItemFlags(itemFlags));
    }

    /**
     * Sets the unbreakable tag. An unbreakable item will not lose durability.
     *
     * @param unbreakable true if set unbreakable
     */
    public M setUnbreakable(boolean unbreakable) {
        return editMeta(meta -> meta.setUnbreakable(unbreakable));
    }

    /**
     * Add an Attribute and it's Modifier.
     * AttributeModifiers can now support {@link EquipmentSlot}s.
     * If not set, the {@link AttributeModifier} will be active in ALL slots.
     * <br>
     * Two {@link AttributeModifier}s that have the same {@link java.util.UUID}
     * cannot exist on the same Attribute.
     *
     * @param attribute the {@link Attribute} to modify
     * @param modifier the {@link AttributeModifier} specifying the modification
     *
     * @throws NullPointerException if Attribute is null
     * @throws NullPointerException if AttributeModifier is null
     * @throws IllegalArgumentException if AttributeModifier already exists
     */
    public M addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return editMeta(meta -> meta.addAttributeModifier(attribute, modifier));
    }

    /**
     * Set all {@link Attribute}s and their {@link AttributeModifier}s.
     * To clear all currently set Attributes and AttributeModifiers use
     * null or an empty Multimap.
     * If not null nor empty, this will filter all entries that are not-null
     * and add them to the ItemStack.
     *
     * @param attributeModifiers the new Multimap containing the Attributes
     *                           and their AttributeModifiers
     */
    public M setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers) {
        return editMeta(meta -> meta.setAttributeModifiers(attributeModifiers));
    }

    /**
     * Remove all {@link AttributeModifier}s associated with the given
     * {@link Attribute}.
     * This will return false if nothing was removed.
     *
     * @param attribute attribute to remove
     * @throws NullPointerException if Attribute is null
     */
    public M removeAttributeModifier(@NotNull Attribute attribute) {
        return editMeta(meta -> meta.removeAttributeModifier(attribute));
    }

    /**
     * Remove all {@link Attribute}s and {@link AttributeModifier}s for a
     * given {@link EquipmentSlot}.<br>
     * If the given {@link EquipmentSlot} is null, this will remove all
     * {@link AttributeModifier}s that do not have an EquipmentSlot set.
     *
     * @param slot the {@link EquipmentSlot} to clear all Attributes and
     *             their modifiers for
     */
    public M removeAttributeModifier(@NotNull EquipmentSlot slot) {
        return editMeta(meta -> meta.removeAttributeModifier(slot));
    }

    /**
     * Remove a specific {@link Attribute} and {@link AttributeModifier}.
     * AttributeModifiers are matched according to their {@link java.util.UUID}.
     *
     * @param attribute the {@link Attribute} to remove
     * @param modifier the {@link AttributeModifier} to remove
     *
     * @throws NullPointerException if the Attribute is null
     * @throws NullPointerException if the AttributeModifier is null
     *
     * @see AttributeModifier#getUniqueId()
     */
    public M removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        return editMeta(meta -> meta.removeAttributeModifier(attribute, modifier));
    }

    public M editPersistentData(Consumer<? super PersistentDataContainer> persistentDataConsumer) {
        return editMeta(meta -> persistentDataConsumer.accept(meta.getPersistentDataContainer()));
    }

    public <Y, Z> M setPersistentData(@NotNull NamespacedKey key, @NotNull PersistentDataType<Y, Z> type, @NotNull Z value) {
        return editMeta(meta -> meta.getPersistentDataContainer().set(key, type, value));
    }

    public <Y, Z> M removePersistentData(@NotNull NamespacedKey key) {
        return editMeta(meta -> meta.getPersistentDataContainer().remove(key));
    }
}
