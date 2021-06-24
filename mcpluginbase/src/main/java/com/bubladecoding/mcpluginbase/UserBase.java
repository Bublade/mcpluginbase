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
package com.bubladecoding.mcpluginbase;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.*;

public class UserBase implements Player {

    protected final Player playerBase;

    public UserBase(Player playerBase) {
        this.playerBase = playerBase;
    }

    /**
     * Gets the player's estimated ping in milliseconds.
     * <p>
     * In Vanilla this value represents the average of the response time to the
     * last four application layer ping packets sent. This value does not
     * represent the network round trip time and as such may have less
     * granularity and be impacted by other sources. For these reasons it
     * <b>should not</b> be used for anti-cheat purposes. Its recommended use is
     * only as a <b>qualitative</b> indicator of connection quality (Vanilla
     * uses it for this purpose in the tab list).
     *
     * @return player ping
     */
    @Override
    public int getPing() {
        return playerBase.getPing();
    }

    /**
     * Send block damage. This fakes block break progress for a user at a
     * certain location. This will not actually change the block's break
     * progress in any way.
     *
     * @param loc      the location of the damaged block
     * @param progress the progress from 0.0 - 1.0 where 0 is no damage and
     */
    @Override
    public void sendBlockDamage(@NotNull Location loc, float progress) {
        playerBase.sendBlockDamage(loc, progress);
    }

    /**
     * Get the regeneration rate (1 health per x ticks) of
     * the HumanEntity when they have saturation and
     * their food level is {@literal >=} 20. Default is 10.
     *
     * @return the regeneration rate
     */
    @Override
    public int getSaturatedRegenRate() {
        return playerBase.getSaturatedRegenRate();
    }

    /**
     * Set the regeneration rate (1 health per x ticks) of
     * the HumanEntity when they have saturation and
     * their food level is {@literal >=} 20. Default is 10.
     * Not affected if the world's difficulty is peaceful.
     *
     * @param ticks the amount of ticks to gain 1 health.
     */
    @Override
    public void setSaturatedRegenRate(int ticks) {
        playerBase.setSaturatedRegenRate(ticks);
    }

    /**
     * Get the regeneration rate (1 health per x ticks) of
     * the HumanEntity when they have no saturation and
     * their food level is {@literal >=} 18. Default is 80.
     *
     * @return the regeneration rate
     */
    @Override
    public int getUnsaturatedRegenRate() {
        return playerBase.getUnsaturatedRegenRate();
    }

    /**
     * Get the regeneration rate (1 health per x ticks) of
     * the HumanEntity when they have no saturation and
     * their food level is {@literal >=} 18. Default is 80.
     * Not affected if the world's difficulty is peaceful.
     *
     * @param ticks the amount of ticks to gain 1 health.
     */
    @Override
    public void setUnsaturatedRegenRate(int ticks) {
        playerBase.setUnsaturatedRegenRate(ticks);
    }

    /**
     * Get the starvation rate (1 health per x ticks) of
     * the HumanEntity. Default is 80.
     *
     * @return the starvation rate
     */
    @Override
    public int getStarvationRate() {
        return playerBase.getStarvationRate();
    }

    /**
     * Get the starvation rate (1 health per x ticks) of
     * the HumanEntity. Default is 80.
     *
     * @param ticks the amount of ticks to lose 1 health
     */
    @Override
    public void setStarvationRate(int ticks) {
        playerBase.setStarvationRate(ticks);
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.playerBase.getDisplayName();
    }

    @Override
    public void setDisplayName(@Nullable String name) {
        this.playerBase.setDisplayName(name);
    }

    @Override
    public @NotNull String getPlayerListName() {
        return this.playerBase.getPlayerListName();
    }

    @Override
    public void setPlayerListName(@Nullable String name) {
        this.playerBase.setPlayerListName(name);
    }

    @Override
    public @Nullable String getPlayerListHeader() {
        return this.playerBase.getPlayerListHeader();
    }

    @Override
    public @Nullable String getPlayerListFooter() {
        return this.playerBase.getPlayerListFooter();
    }

    @Override
    public void setPlayerListHeader(@Nullable String header) {
        this.playerBase.setPlayerListHeader(header);
    }

    @Override
    public void setPlayerListFooter(@Nullable String footer) {
        this.playerBase.setPlayerListFooter(footer);
    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable String header, @Nullable String footer) {
        this.playerBase.setPlayerListHeaderFooter(header, footer);
    }

    @Override
    public void setCompassTarget(@NotNull Location loc) {
        this.playerBase.setCompassTarget(loc);
    }

    @Override
    public @NotNull Location getCompassTarget() {
        return this.playerBase.getCompassTarget();
    }

    @Override
    public @Nullable InetSocketAddress getAddress() {
        return this.playerBase.getAddress();
    }

    @Override
    public boolean isConversing() {
        return this.playerBase.isConversing();
    }

    @Override
    public void acceptConversationInput(@NotNull String input) {
        this.playerBase.acceptConversationInput(input);
    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        return this.playerBase.beginConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        this.playerBase.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent details) {
        this.playerBase.abandonConversation(conversation, details);
    }

    @Override
    public void sendRawMessage(@NotNull String message) {
        this.playerBase.sendRawMessage(message);
    }

    @Override
    public void kickPlayer(@Nullable String message) {
        this.playerBase.kickPlayer(message);
    }

    @Override
    public void chat(@NotNull String msg) {
        this.playerBase.chat(msg);
    }

    @Override
    public boolean performCommand(@NotNull String command) {
        return this.playerBase.performCommand(command);
    }

    @Override
    public @NotNull Location getLocation() {
        return this.playerBase.getLocation();
    }

    @Override
    public @Nullable Location getLocation(@Nullable Location loc) {
        return this.playerBase.getLocation(loc);
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        this.playerBase.setVelocity(velocity);
    }

    @Override
    public @NotNull Vector getVelocity() {
        return this.playerBase.getVelocity();
    }

    @Override
    public double getHeight() {
        return this.playerBase.getHeight();
    }

    @Override
    public double getWidth() {
        return this.playerBase.getWidth();
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return this.playerBase.getBoundingBox();
    }

    @Deprecated
    @Override
    public boolean isOnGround() {
        return this.playerBase.isOnGround();
    }

    @Override
    public @NotNull World getWorld() {
        return this.playerBase.getWorld();
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        this.playerBase.setRotation(yaw, pitch);
    }

    @Override
    public boolean teleport(@NotNull Location location) {
        return this.playerBase.teleport(location);
    }

    @Override
    public boolean teleport(@NotNull Location location, PlayerTeleportEvent.@NotNull TeleportCause cause) {
        return this.playerBase.teleport(location, cause);
    }

    @Override
    public boolean teleport(@NotNull Entity destination) {
        return this.playerBase.teleport(destination);
    }

    @Override
    public boolean teleport(@NotNull Entity destination, PlayerTeleportEvent.@NotNull TeleportCause cause) {
        return this.playerBase.teleport(destination, cause);
    }

    @Override
    public @NotNull List<Entity> getNearbyEntities(double x, double y, double z) {
        return this.playerBase.getNearbyEntities(x, y, z);
    }

    @Override
    public int getEntityId() {
        return this.playerBase.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return this.playerBase.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return this.playerBase.getMaxFireTicks();
    }

    @Override
    public void setFireTicks(int ticks) {
        this.playerBase.setFireTicks(ticks);
    }

    @Override
    public void remove() {
        this.playerBase.remove();
    }

    @Override
    public boolean isDead() {
        return this.playerBase.isDead();
    }

    @Override
    public boolean isValid() {
        return this.playerBase.isValid();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.playerBase.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull String[] messages) {
        this.playerBase.sendMessage(messages);
    }

    @Override
    public @NotNull Server getServer() {
        return this.playerBase.getServer();
    }

    @Override
    public boolean isPersistent() {
        return this.playerBase.isPersistent();
    }

    @Override
    public void setPersistent(boolean persistent) {
        this.playerBase.setPersistent(persistent);
    }

    @Deprecated
    @Override
    public @Nullable Entity getPassenger() {
        return this.playerBase.getPassenger();
    }

    @Deprecated
    @Override
    public boolean setPassenger(@NotNull Entity passenger) {
        return this.playerBase.setPassenger(passenger);
    }

    @Override
    public @NotNull List<Entity> getPassengers() {
        return this.playerBase.getPassengers();
    }

    @Override
    public boolean addPassenger(@NotNull Entity passenger) {
        return this.playerBase.addPassenger(passenger);
    }

    @Override
    public boolean removePassenger(@NotNull Entity passenger) {
        return this.playerBase.removePassenger(passenger);
    }

    @Override
    public boolean isEmpty() {
        return this.playerBase.isEmpty();
    }

    @Override
    public boolean eject() {
        return this.playerBase.eject();
    }

    @Override
    public float getFallDistance() {
        return this.playerBase.getFallDistance();
    }

    @Override
    public void setFallDistance(float distance) {
        this.playerBase.setFallDistance(distance);
    }

    @Override
    public void setLastDamageCause(@Nullable EntityDamageEvent event) {
        this.playerBase.setLastDamageCause(event);
    }

    @Override
    public @Nullable EntityDamageEvent getLastDamageCause() {
        return this.playerBase.getLastDamageCause();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.playerBase.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return this.playerBase.getTicksLived();
    }

    @Override
    public void setTicksLived(int value) {
        this.playerBase.setTicksLived(value);
    }

    @Override
    public void playEffect(@NotNull EntityEffect type) {
        this.playerBase.playEffect(type);
    }

    @Override
    public @NotNull EntityType getType() {
        return this.playerBase.getType();
    }

    @Override
    public boolean isInsideVehicle() {
        return this.playerBase.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return this.playerBase.leaveVehicle();
    }

    @Override
    public @Nullable Entity getVehicle() {
        return this.playerBase.getVehicle();
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        this.playerBase.setCustomNameVisible(flag);
    }

    @Override
    public boolean isCustomNameVisible() {
        return this.playerBase.isCustomNameVisible();
    }

    @Override
    public void setGlowing(boolean flag) {
        this.playerBase.setGlowing(flag);
    }

    @Override
    public boolean isGlowing() {
        return this.playerBase.isGlowing();
    }

    @Override
    public void setInvulnerable(boolean flag) {
        this.playerBase.setInvulnerable(flag);
    }

    @Override
    public boolean isInvulnerable() {
        return this.playerBase.isInvulnerable();
    }

    @Override
    public boolean isSilent() {
        return this.playerBase.isSilent();
    }

    @Override
    public void setSilent(boolean flag) {
        this.playerBase.setSilent(flag);
    }

    @Override
    public boolean hasGravity() {
        return this.playerBase.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        this.playerBase.setGravity(gravity);
    }

    @Override
    public int getPortalCooldown() {
        return this.playerBase.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        this.playerBase.setPortalCooldown(cooldown);
    }

    @Override
    public @NotNull Set<String> getScoreboardTags() {
        return this.playerBase.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@NotNull String tag) {
        return this.playerBase.addScoreboardTag(tag);
    }

    @Override
    public boolean removeScoreboardTag(@NotNull String tag) {
        return this.playerBase.removeScoreboardTag(tag);
    }

    @Override
    public @NotNull PistonMoveReaction getPistonMoveReaction() {
        return this.playerBase.getPistonMoveReaction();
    }

    @Override
    public @NotNull BlockFace getFacing() {
        return this.playerBase.getFacing();
    }

    @Override
    public @NotNull Pose getPose() {
        return this.playerBase.getPose();
    }

    @Override
    public boolean isSneaking() {
        return this.playerBase.isSneaking();
    }

    @Override
    public void setSneaking(boolean sneak) {
        this.playerBase.setSneaking(sneak);
    }

    @Override
    public boolean isSprinting() {
        return this.playerBase.isSprinting();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.playerBase.setSprinting(sprinting);
    }

    @Override
    public void saveData() {
        this.playerBase.saveData();
    }

    @Override
    public void loadData() {
        this.playerBase.loadData();
    }

    @Override
    public void setSleepingIgnored(boolean isSleeping) {
        this.playerBase.setSleepingIgnored(isSleeping);
    }

    @Override
    public boolean isSleepingIgnored() {
        return this.playerBase.isSleepingIgnored();
    }

    @Override
    public boolean isOnline() {
        return this.playerBase.isOnline();
    }

    @Override
    public boolean isBanned() {
        return this.playerBase.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return this.playerBase.isWhitelisted();
    }

    @Override
    public void setWhitelisted(boolean value) {
        this.playerBase.setWhitelisted(value);
    }

    @Override
    public @Nullable Player getPlayer() {
        return this.playerBase.getPlayer();
    }

    @Override
    public long getFirstPlayed() {
        return this.playerBase.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return this.playerBase.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return this.playerBase.hasPlayedBefore();
    }

    @Override
    public @Nullable Location getBedSpawnLocation() {
        return this.playerBase.getBedSpawnLocation();
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        this.playerBase.decrementStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, int amount) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, int amount) throws IllegalArgumentException {
        this.playerBase.decrementStatistic(statistic, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, int newValue) throws IllegalArgumentException {
        this.playerBase.setStatistic(statistic, newValue);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic) throws IllegalArgumentException {
        return this.playerBase.getStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic, material);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        this.playerBase.decrementStatistic(statistic, material);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull Material material) throws IllegalArgumentException {
        return this.playerBase.getStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic, material, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull Material material, int amount) throws IllegalArgumentException {
        this.playerBase.decrementStatistic(statistic, material, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull Material material, int newValue) throws IllegalArgumentException {
        this.playerBase.setStatistic(statistic, material, newValue);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic, entityType);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        this.playerBase.decrementStatistic(statistic, entityType);
    }

    @Override
    public int getStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType) throws IllegalArgumentException {
        return this.playerBase.getStatistic(statistic, entityType);
    }

    @Override
    public void incrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount) throws IllegalArgumentException {
        this.playerBase.incrementStatistic(statistic, entityType, amount);
    }

    @Override
    public void decrementStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int amount) {
        this.playerBase.decrementStatistic(statistic, entityType, amount);
    }

    @Override
    public void setStatistic(@NotNull Statistic statistic, @NotNull EntityType entityType, int newValue) {
        this.playerBase.setStatistic(statistic, entityType, newValue);
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location) {
        this.playerBase.setBedSpawnLocation(location);
    }

    @Override
    public void setBedSpawnLocation(@Nullable Location location, boolean force) {
        this.playerBase.setBedSpawnLocation(location, force);
    }

    @Deprecated
    @Override
    public void playNote(@NotNull Location loc, byte instrument, byte note) {
        this.playerBase.playNote(loc, instrument, note);
    }

    @Override
    public void playNote(@NotNull Location loc, @NotNull Instrument instrument, @NotNull Note note) {
        this.playerBase.playNote(loc, instrument, note);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, float volume, float pitch) {
        this.playerBase.playSound(location, sound, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String sound, float volume, float pitch) {
        this.playerBase.playSound(location, sound, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch) {
        this.playerBase.playSound(location, sound, category, volume, pitch);
    }

    @Override
    public void playSound(@NotNull Location location, @NotNull String sound, @NotNull SoundCategory category, float volume, float pitch) {
        this.playerBase.playSound(location, sound, category, volume, pitch);
    }

    @Override
    public void stopSound(@NotNull Sound sound) {
        this.playerBase.stopSound(sound);
    }

    @Override
    public void stopSound(@NotNull String sound) {
        this.playerBase.stopSound(sound);
    }

    @Override
    public void stopSound(@NotNull Sound sound, @Nullable SoundCategory category) {
        this.playerBase.stopSound(sound, category);
    }

    @Override
    public void stopSound(@NotNull String sound, @Nullable SoundCategory category) {
        this.playerBase.stopSound(sound, category);
    }

    @Deprecated
    @Override
    public void playEffect(@NotNull Location loc, @NotNull Effect effect, int data) {
        this.playerBase.playEffect(loc, effect, data);
    }

    @Deprecated
    @Override
    public <T> void playEffect(@NotNull Location loc, @NotNull Effect effect, @Nullable T data) {
        this.playerBase.playEffect(loc, effect, data);
    }

    @Deprecated
    @Override
    public void sendBlockChange(@NotNull Location loc, @NotNull Material material, byte data) {
        this.playerBase.sendBlockChange(loc, material, data);
    }

    @Override
    public void sendBlockChange(@NotNull Location loc, @NotNull BlockData block) {
        this.playerBase.sendBlockChange(loc, block);
    }

    @Deprecated
    @Override
    public boolean sendChunkChange(@NotNull Location loc, int sx, int sy, int sz, @NotNull byte[] data) {
        return this.playerBase.sendChunkChange(loc, sx, sy, sz, data);
    }

    @Override
    public void sendSignChange(@NotNull Location loc, @Nullable String[] lines) throws IllegalArgumentException {
        this.playerBase.sendSignChange(loc, lines);
    }

    @Override
    public void sendSignChange(@NotNull Location loc, @Nullable String[] lines, @NotNull DyeColor dyeColor) throws IllegalArgumentException {
        this.playerBase.sendSignChange(loc, lines, dyeColor);
    }

    @Override
    public void sendMap(@NotNull MapView map) {
        this.playerBase.sendMap(map);
    }

    @Override
    public void updateInventory() {
        this.playerBase.updateInventory();
    }

    @Override
    public void setPlayerTime(long time, boolean relative) {
        this.playerBase.setPlayerTime(time, relative);
    }

    @Override
    public long getPlayerTime() {
        return this.playerBase.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return this.playerBase.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return this.playerBase.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {
        this.playerBase.resetPlayerTime();
    }

    @Override
    public void setPlayerWeather(@NotNull WeatherType type) {
        this.playerBase.setPlayerWeather(type);
    }

    @Override
    public @Nullable WeatherType getPlayerWeather() {
        return this.playerBase.getPlayerWeather();
    }

    @Override
    public void resetPlayerWeather() {
        this.playerBase.resetPlayerWeather();
    }

    @Override
    public void giveExp(int amount) {
        this.playerBase.giveExp(amount);
    }

    @Override
    public void giveExpLevels(int amount) {
        this.playerBase.giveExpLevels(amount);
    }

    @Override
    public float getExp() {
        return this.playerBase.getExp();
    }

    @Override
    public void setExp(float exp) {
        this.playerBase.setExp(exp);
    }

    @Override
    public int getLevel() {
        return this.playerBase.getLevel();
    }

    @Override
    public void setLevel(int level) {
        this.playerBase.setLevel(level);
    }

    @Override
    public int getTotalExperience() {
        return this.playerBase.getTotalExperience();
    }

    @Override
    public void setTotalExperience(int exp) {
        this.playerBase.setTotalExperience(exp);
    }

    @Override
    public void sendExperienceChange(float progress) {
        this.playerBase.sendExperienceChange(progress);
    }

    @Override
    public void sendExperienceChange(float progress, int level) {
        this.playerBase.sendExperienceChange(progress, level);
    }

    @Override
    public float getExhaustion() {
        return this.playerBase.getExhaustion();
    }

    @Override
    public void setExhaustion(float value) {
        this.playerBase.setExhaustion(value);
    }

    @Override
    public float getSaturation() {
        return this.playerBase.getSaturation();
    }

    @Override
    public void setSaturation(float value) {
        this.playerBase.setSaturation(value);
    }

    @Override
    public int getFoodLevel() {
        return this.playerBase.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int value) {
        this.playerBase.setFoodLevel(value);
    }

    @Override
    public boolean getAllowFlight() {
        return this.playerBase.getAllowFlight();
    }

    @Override
    public void setAllowFlight(boolean flight) {
        this.playerBase.setAllowFlight(flight);
    }

    @Deprecated
    @Override
    public void hidePlayer(@NotNull Player player) {
        this.playerBase.hidePlayer(player);
    }

    @Override
    public void hidePlayer(@NotNull Plugin plugin, @NotNull Player player) {
        this.playerBase.hidePlayer(plugin, player);
    }

    @Deprecated
    @Override
    public void showPlayer(@NotNull Player player) {
        this.playerBase.showPlayer(player);
    }

    @Override
    public void showPlayer(@NotNull Plugin plugin, @NotNull Player player) {
        this.playerBase.showPlayer(plugin, player);
    }

    @Override
    public boolean canSee(@NotNull Player player) {
        return this.playerBase.canSee(player);
    }

    @Override
    public boolean isFlying() {
        return this.playerBase.isFlying();
    }

    @Override
    public void setFlying(boolean value) {
        this.playerBase.setFlying(value);
    }

    @Override
    public void setFlySpeed(float value) throws IllegalArgumentException {
        this.playerBase.setFlySpeed(value);
    }

    @Override
    public void setWalkSpeed(float value) throws IllegalArgumentException {
        this.playerBase.setWalkSpeed(value);
    }

    @Override
    public float getFlySpeed() {
        return this.playerBase.getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return this.playerBase.getWalkSpeed();
    }

    @Deprecated
    @Override
    public void setTexturePack(@NotNull String url) {
        this.playerBase.setTexturePack(url);
    }

    @Override
    public void setResourcePack(@NotNull String url) {
        this.playerBase.setResourcePack(url);
    }

    @Override
    public void setResourcePack(@NotNull String url, @NotNull byte[] hash) {
        this.playerBase.setResourcePack(url, hash);
    }

    @Override
    public @NotNull Scoreboard getScoreboard() {
        return this.playerBase.getScoreboard();
    }

    @Override
    public void setScoreboard(@NotNull Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {
        this.playerBase.setScoreboard(scoreboard);
    }

    @Override
    public boolean isHealthScaled() {
        return this.playerBase.isHealthScaled();
    }

    @Override
    public void setHealthScaled(boolean scale) {
        this.playerBase.setHealthScaled(scale);
    }

    @Override
    public void setHealthScale(double scale) throws IllegalArgumentException {
        this.playerBase.setHealthScale(scale);
    }

    @Override
    public double getHealthScale() {
        return this.playerBase.getHealthScale();
    }

    @Override
    public @Nullable Entity getSpectatorTarget() {
        return this.playerBase.getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(@Nullable Entity entity) {
        this.playerBase.setSpectatorTarget(entity);
    }

    @Deprecated
    @Override
    public void sendTitle(@Nullable String title, @Nullable String subtitle) {
        this.playerBase.sendTitle(title, subtitle);
    }

    @Override
    public void sendTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        this.playerBase.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void resetTitle() {
        this.playerBase.resetTitle();
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count) {
        this.playerBase.spawnParticle(particle, location, count);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count) {
        this.playerBase.spawnParticle(particle, x, y, z, count);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, @Nullable T data) {
        this.playerBase.spawnParticle(particle, location, count, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, @Nullable T data) {
        this.playerBase.spawnParticle(particle, x, y, z, count, data);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ) {
        this.playerBase.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        this.playerBase.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        this.playerBase.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        this.playerBase.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, data);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        this.playerBase.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra);
    }

    @Override
    public void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        this.playerBase.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, @NotNull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        this.playerBase.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, data);
    }

    @Override
    public <T> void spawnParticle(@NotNull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        this.playerBase.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
    }

    @Override
    public @NotNull AdvancementProgress getAdvancementProgress(@NotNull Advancement advancement) {
        return this.playerBase.getAdvancementProgress(advancement);
    }

    @Override
    public int getClientViewDistance() {
        return this.playerBase.getClientViewDistance();
    }

    @Override
    public @NotNull String getLocale() {
        return this.playerBase.getLocale();
    }

    @Override
    public void updateCommands() {
        this.playerBase.updateCommands();
    }

    @Override
    public void openBook(@NotNull ItemStack book) {
        this.playerBase.openBook(book);
    }

    @Override
    public @NotNull Spigot spigot() {
        return this.playerBase.spigot();
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return this.playerBase.serialize();
    }

    @Override
    public @NotNull String getName() {
        return this.playerBase.getName();
    }

    @Override
    public @NotNull PlayerInventory getInventory() {
        return this.playerBase.getInventory();
    }

    @Override
    public @NotNull Inventory getEnderChest() {
        return this.playerBase.getEnderChest();
    }

    @Override
    public @NotNull MainHand getMainHand() {
        return this.playerBase.getMainHand();
    }

    @Override
    public boolean setWindowProperty(InventoryView.@NotNull Property prop, int value) {
        return this.playerBase.setWindowProperty(prop, value);
    }

    @Override
    public @NotNull InventoryView getOpenInventory() {
        return this.playerBase.getOpenInventory();
    }

    @Override
    public @Nullable InventoryView openInventory(@NotNull Inventory inventory) {
        return this.playerBase.openInventory(inventory);
    }

    @Override
    public @Nullable InventoryView openWorkbench(@Nullable Location location, boolean force) {
        return this.playerBase.openWorkbench(location, force);
    }

    @Override
    public @Nullable InventoryView openEnchanting(@Nullable Location location, boolean force) {
        return this.playerBase.openEnchanting(location, force);
    }

    @Override
    public void openInventory(@NotNull InventoryView inventory) {
        this.playerBase.openInventory(inventory);
    }

    @Override
    public @Nullable InventoryView openMerchant(@NotNull Villager trader, boolean force) {
        return this.playerBase.openMerchant(trader, force);
    }

    @Override
    public @Nullable InventoryView openMerchant(@NotNull Merchant merchant, boolean force) {
        return this.playerBase.openMerchant(merchant, force);
    }

    @Override
    public void closeInventory() {
        this.playerBase.closeInventory();
    }

    @Deprecated
    @Override
    public @NotNull ItemStack getItemInHand() {
        return this.playerBase.getItemInHand();
    }

    @Deprecated
    @Override
    public void setItemInHand(@Nullable ItemStack item) {
        this.playerBase.setItemInHand(item);
    }

    @Override
    public @NotNull ItemStack getItemOnCursor() {
        return this.playerBase.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(@Nullable ItemStack item) {
        this.playerBase.setItemOnCursor(item);
    }

    @Override
    public boolean hasCooldown(@NotNull Material material) {
        return this.playerBase.hasCooldown(material);
    }

    @Override
    public int getCooldown(@NotNull Material material) {
        return this.playerBase.getCooldown(material);
    }

    @Override
    public void setCooldown(@NotNull Material material, int ticks) {
        this.playerBase.setCooldown(material, ticks);
    }

    @Override
    public int getSleepTicks() {
        return this.playerBase.getSleepTicks();
    }

    @Override
    public boolean sleep(@NotNull Location location, boolean force) {
        return this.playerBase.sleep(location, force);
    }

    @Override
    public void wakeup(boolean setSpawnLocation) {
        this.playerBase.wakeup(setSpawnLocation);
    }

    @Override
    public @NotNull Location getBedLocation() {
        return this.playerBase.getBedLocation();
    }

    @Override
    public @NotNull GameMode getGameMode() {
        return this.playerBase.getGameMode();
    }

    @Override
    public void setGameMode(@NotNull GameMode mode) {
        this.playerBase.setGameMode(mode);
    }

    @Override
    public boolean isBlocking() {
        return this.playerBase.isBlocking();
    }

    @Override
    public boolean isHandRaised() {
        return this.playerBase.isHandRaised();
    }

    @Override
    public int getExpToLevel() {
        return this.playerBase.getExpToLevel();
    }

    @Override
    public float getAttackCooldown() {
        return this.playerBase.getAttackCooldown();
    }

    @Override
    public boolean discoverRecipe(@NotNull NamespacedKey recipe) {
        return this.playerBase.discoverRecipe(recipe);
    }

    @Override
    public int discoverRecipes(@NotNull Collection<NamespacedKey> recipes) {
        return this.playerBase.discoverRecipes(recipes);
    }

    @Override
    public boolean undiscoverRecipe(@NotNull NamespacedKey recipe) {
        return this.playerBase.undiscoverRecipe(recipe);
    }

    @Override
    public int undiscoverRecipes(@NotNull Collection<NamespacedKey> recipes) {
        return this.playerBase.undiscoverRecipes(recipes);
    }

    @Override
    public boolean hasDiscoveredRecipe(@NotNull NamespacedKey recipe) {
        return this.playerBase.hasDiscoveredRecipe(recipe);
    }

    @Override
    public @NotNull Set<NamespacedKey> getDiscoveredRecipes() {
        return this.playerBase.getDiscoveredRecipes();
    }

    @Deprecated
    @Override
    public @Nullable Entity getShoulderEntityLeft() {
        return this.playerBase.getShoulderEntityLeft();
    }

    @Deprecated
    @Override
    public void setShoulderEntityLeft(@Nullable Entity entity) {
        this.playerBase.setShoulderEntityLeft(entity);
    }

    @Deprecated
    @Override
    public @Nullable Entity getShoulderEntityRight() {
        return this.playerBase.getShoulderEntityRight();
    }

    @Deprecated
    @Override
    public void setShoulderEntityRight(@Nullable Entity entity) {
        this.playerBase.setShoulderEntityRight(entity);
    }

    @Override
    public boolean dropItem(boolean dropAll) {
        return this.playerBase.dropItem(dropAll);
    }

    @Override
    public double getEyeHeight() {
        return this.playerBase.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean ignorePose) {
        return this.playerBase.getEyeHeight(ignorePose);
    }

    @Override
    public @NotNull Location getEyeLocation() {
        return this.playerBase.getEyeLocation();
    }

    @Override
    public @NotNull List<Block> getLineOfSight(@Nullable Set<Material> transparent, int maxDistance) {
        return this.playerBase.getLineOfSight(transparent, maxDistance);
    }

    @Override
    public @NotNull Block getTargetBlock(@Nullable Set<Material> transparent, int maxDistance) {
        return this.playerBase.getTargetBlock(transparent, maxDistance);
    }

    @Override
    public @NotNull List<Block> getLastTwoTargetBlocks(@Nullable Set<Material> transparent, int maxDistance) {
        return this.playerBase.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @Override
    public @Nullable Block getTargetBlockExact(int maxDistance) {
        return this.playerBase.getTargetBlockExact(maxDistance);
    }

    @Override
    public @Nullable Block getTargetBlockExact(int maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
        return this.playerBase.getTargetBlockExact(maxDistance, fluidCollisionMode);
    }

    @Override
    public @Nullable RayTraceResult rayTraceBlocks(double maxDistance) {
        return this.playerBase.rayTraceBlocks(maxDistance);
    }

    @Override
    public @Nullable RayTraceResult rayTraceBlocks(double maxDistance, @NotNull FluidCollisionMode fluidCollisionMode) {
        return this.playerBase.rayTraceBlocks(maxDistance, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return this.playerBase.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int ticks) {
        this.playerBase.setRemainingAir(ticks);
    }

    @Override
    public int getMaximumAir() {
        return this.playerBase.getMaximumAir();
    }

    @Override
    public void setMaximumAir(int ticks) {
        this.playerBase.setMaximumAir(ticks);
    }

    @Override
    public int getArrowCooldown() {
        return this.playerBase.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(int ticks) {
        this.playerBase.setArrowCooldown(ticks);
    }

    @Override
    public int getArrowsInBody() {
        return this.playerBase.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(int count) {
        this.playerBase.setArrowsInBody(count);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return this.playerBase.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int ticks) {
        this.playerBase.setMaximumNoDamageTicks(ticks);
    }

    @Override
    public double getLastDamage() {
        return this.playerBase.getLastDamage();
    }

    @Override
    public void setLastDamage(double damage) {
        this.playerBase.setLastDamage(damage);
    }

    @Override
    public int getNoDamageTicks() {
        return this.playerBase.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int ticks) {
        this.playerBase.setNoDamageTicks(ticks);
    }

    @Override
    public @Nullable Player getKiller() {
        return this.playerBase.getKiller();
    }

    @Override
    public boolean addPotionEffect(@NotNull PotionEffect effect) {
        return this.playerBase.addPotionEffect(effect);
    }

    @Deprecated
    @Override
    public boolean addPotionEffect(@NotNull PotionEffect effect, boolean force) {
        return this.playerBase.addPotionEffect(effect, force);
    }

    @Override
    public boolean addPotionEffects(@NotNull Collection<PotionEffect> effects) {
        return this.playerBase.addPotionEffects(effects);
    }

    @Override
    public boolean hasPotionEffect(@NotNull PotionEffectType type) {
        return this.playerBase.hasPotionEffect(type);
    }

    @Override
    public @Nullable PotionEffect getPotionEffect(@NotNull PotionEffectType type) {
        return this.playerBase.getPotionEffect(type);
    }

    @Override
    public void removePotionEffect(@NotNull PotionEffectType type) {
        this.playerBase.removePotionEffect(type);
    }

    @Override
    public @NotNull Collection<PotionEffect> getActivePotionEffects() {
        return this.playerBase.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@NotNull Entity other) {
        return this.playerBase.hasLineOfSight(other);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return this.playerBase.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean remove) {
        this.playerBase.setRemoveWhenFarAway(remove);
    }

    @Override
    public @Nullable EntityEquipment getEquipment() {
        return this.playerBase.getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean pickup) {
        this.playerBase.setCanPickupItems(pickup);
    }

    @Override
    public boolean getCanPickupItems() {
        return this.playerBase.getCanPickupItems();
    }

    @Override
    public boolean isLeashed() {
        return this.playerBase.isLeashed();
    }

    @Override
    public @NotNull Entity getLeashHolder() throws IllegalStateException {
        return this.playerBase.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(@Nullable Entity holder) {
        return this.playerBase.setLeashHolder(holder);
    }

    @Override
    public boolean isGliding() {
        return this.playerBase.isGliding();
    }

    @Override
    public void setGliding(boolean gliding) {
        this.playerBase.setGliding(gliding);
    }

    @Override
    public boolean isSwimming() {
        return this.playerBase.isSwimming();
    }

    @Override
    public void setSwimming(boolean swimming) {
        this.playerBase.setSwimming(swimming);
    }

    @Override
    public boolean isRiptiding() {
        return this.playerBase.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return this.playerBase.isSleeping();
    }

    @Override
    public void setAI(boolean ai) {
        this.playerBase.setAI(ai);
    }

    @Override
    public boolean hasAI() {
        return this.playerBase.hasAI();
    }

    @Override
    public void attack(@NotNull Entity target) {
        this.playerBase.attack(target);
    }

    @Override
    public void swingMainHand() {
        this.playerBase.swingMainHand();
    }

    @Override
    public void swingOffHand() {
        this.playerBase.swingOffHand();
    }

    @Override
    public void setCollidable(boolean collidable) {
        this.playerBase.setCollidable(collidable);
    }

    @Override
    public boolean isCollidable() {
        return this.playerBase.isCollidable();
    }

    @Override
    public @NotNull Set<UUID> getCollidableExemptions() {
        return this.playerBase.getCollidableExemptions();
    }

    @Override
    public <T> @Nullable T getMemory(@NotNull MemoryKey<T> memoryKey) {
        return this.playerBase.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@NotNull MemoryKey<T> memoryKey, @Nullable T memoryValue) {
        this.playerBase.setMemory(memoryKey, memoryValue);
    }

    @Override
    public @NotNull EntityCategory getCategory() {
        return this.playerBase.getCategory();
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.playerBase.setInvisible(invisible);
    }

    @Override
    public boolean isInvisible() {
        return this.playerBase.isInvisible();
    }

    @Override
    public @Nullable AttributeInstance getAttribute(@NotNull Attribute attribute) {
        return this.playerBase.getAttribute(attribute);
    }

    @Override
    public void damage(double amount) {
        this.playerBase.damage(amount);
    }

    @Override
    public void damage(double amount, @Nullable Entity source) {
        this.playerBase.damage(amount, source);
    }

    @Override
    public double getHealth() {
        return this.playerBase.getHealth();
    }

    @Override
    public void setHealth(double health) {
        this.playerBase.setHealth(health);
    }

    @Override
    public double getAbsorptionAmount() {
        return this.playerBase.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(double amount) {
        this.playerBase.setAbsorptionAmount(amount);
    }

    @Deprecated
    @Override
    public double getMaxHealth() {
        return this.playerBase.getMaxHealth();
    }

    @Deprecated
    @Override
    public void setMaxHealth(double health) {
        this.playerBase.setMaxHealth(health);
    }

    @Deprecated
    @Override
    public void resetMaxHealth() {
        this.playerBase.resetMaxHealth();
    }

    @Override
    public @Nullable String getCustomName() {
        return this.playerBase.getCustomName();
    }

    @Override
    public void setCustomName(@Nullable String name) {
        this.playerBase.setCustomName(name);
    }

    @Override
    public void setMetadata(@NotNull String metadataKey, @NotNull MetadataValue newMetadataValue) {
        this.playerBase.setMetadata(metadataKey, newMetadataValue);
    }

    @Override
    public @NotNull List<MetadataValue> getMetadata(@NotNull String metadataKey) {
        return this.playerBase.getMetadata(metadataKey);
    }

    @Override
    public boolean hasMetadata(@NotNull String metadataKey) {
        return this.playerBase.hasMetadata(metadataKey);
    }

    @Override
    public void removeMetadata(@NotNull String metadataKey, @NotNull Plugin owningPlugin) {
        this.playerBase.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return this.playerBase.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return this.playerBase.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return this.playerBase.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return this.playerBase.hasPermission(perm);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return this.playerBase.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return this.playerBase.addAttachment(plugin);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return this.playerBase.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return this.playerBase.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
        this.playerBase.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        this.playerBase.recalculatePermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.playerBase.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return this.playerBase.isOp();
    }

    @Override
    public void setOp(boolean value) {
        this.playerBase.setOp(value);
    }

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return this.playerBase.getPersistentDataContainer();
    }

    @Override
    public void sendPluginMessage(@NotNull Plugin source, @NotNull String channel, @NotNull byte[] message) {
        this.playerBase.sendPluginMessage(source, channel, message);
    }

    @Override
    public @NotNull Set<String> getListeningPluginChannels() {
        return this.playerBase.getListeningPluginChannels();
    }

    @Override
    public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile) {
        return this.playerBase.launchProjectile(projectile);
    }

    @Override
    public <T extends Projectile> @NotNull T launchProjectile(@NotNull Class<? extends T> projectile, @Nullable Vector velocity) {
        return this.playerBase.launchProjectile(projectile, velocity);
    }

    @Override
    public boolean isInWater() {
        return this.playerBase.isInWater();
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        this.playerBase.sendMessage(uuid, s);
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String[] strings) {
        this.playerBase.sendMessage(uuid, strings);
    }

    @Override
    public void sendRawMessage(@Nullable UUID uuid, @NotNull String s) {
        this.playerBase.sendMessage(uuid, s);
    }
}
