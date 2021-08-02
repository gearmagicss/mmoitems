// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import java.util.Set;
import org.bukkit.Particle;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.WeatherType;
import org.bukkit.Statistic;
import org.bukkit.Achievement;
import org.bukkit.map.MapView;
import org.bukkit.Material;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.Note;
import org.bukkit.Instrument;
import java.net.InetSocketAddress;
import org.bukkit.Location;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;

public interface Player extends HumanEntity, Conversable, CommandSender, OfflinePlayer, PluginMessageRecipient
{
    String getDisplayName();
    
    void setDisplayName(final String p0);
    
    String getPlayerListName();
    
    void setPlayerListName(final String p0);
    
    void setCompassTarget(final Location p0);
    
    Location getCompassTarget();
    
    InetSocketAddress getAddress();
    
    void sendRawMessage(final String p0);
    
    void kickPlayer(final String p0);
    
    void chat(final String p0);
    
    boolean performCommand(final String p0);
    
    boolean isSneaking();
    
    void setSneaking(final boolean p0);
    
    boolean isSprinting();
    
    void setSprinting(final boolean p0);
    
    void saveData();
    
    void loadData();
    
    void setSleepingIgnored(final boolean p0);
    
    boolean isSleepingIgnored();
    
    @Deprecated
    void playNote(final Location p0, final byte p1, final byte p2);
    
    void playNote(final Location p0, final Instrument p1, final Note p2);
    
    void playSound(final Location p0, final Sound p1, final float p2, final float p3);
    
    void playSound(final Location p0, final String p1, final float p2, final float p3);
    
    void stopSound(final Sound p0);
    
    void stopSound(final String p0);
    
    @Deprecated
    void playEffect(final Location p0, final Effect p1, final int p2);
    
     <T> void playEffect(final Location p0, final Effect p1, final T p2);
    
    @Deprecated
    void sendBlockChange(final Location p0, final Material p1, final byte p2);
    
    @Deprecated
    boolean sendChunkChange(final Location p0, final int p1, final int p2, final int p3, final byte[] p4);
    
    @Deprecated
    void sendBlockChange(final Location p0, final int p1, final byte p2);
    
    void sendSignChange(final Location p0, final String[] p1) throws IllegalArgumentException;
    
    void sendMap(final MapView p0);
    
    void updateInventory();
    
    void awardAchievement(final Achievement p0);
    
    void removeAchievement(final Achievement p0);
    
    boolean hasAchievement(final Achievement p0);
    
    void incrementStatistic(final Statistic p0) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0) throws IllegalArgumentException;
    
    void incrementStatistic(final Statistic p0, final int p1) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0, final int p1) throws IllegalArgumentException;
    
    void setStatistic(final Statistic p0, final int p1) throws IllegalArgumentException;
    
    int getStatistic(final Statistic p0) throws IllegalArgumentException;
    
    void incrementStatistic(final Statistic p0, final Material p1) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0, final Material p1) throws IllegalArgumentException;
    
    int getStatistic(final Statistic p0, final Material p1) throws IllegalArgumentException;
    
    void incrementStatistic(final Statistic p0, final Material p1, final int p2) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0, final Material p1, final int p2) throws IllegalArgumentException;
    
    void setStatistic(final Statistic p0, final Material p1, final int p2) throws IllegalArgumentException;
    
    void incrementStatistic(final Statistic p0, final EntityType p1) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0, final EntityType p1) throws IllegalArgumentException;
    
    int getStatistic(final Statistic p0, final EntityType p1) throws IllegalArgumentException;
    
    void incrementStatistic(final Statistic p0, final EntityType p1, final int p2) throws IllegalArgumentException;
    
    void decrementStatistic(final Statistic p0, final EntityType p1, final int p2);
    
    void setStatistic(final Statistic p0, final EntityType p1, final int p2);
    
    void setPlayerTime(final long p0, final boolean p1);
    
    long getPlayerTime();
    
    long getPlayerTimeOffset();
    
    boolean isPlayerTimeRelative();
    
    void resetPlayerTime();
    
    void setPlayerWeather(final WeatherType p0);
    
    WeatherType getPlayerWeather();
    
    void resetPlayerWeather();
    
    void giveExp(final int p0);
    
    void giveExpLevels(final int p0);
    
    float getExp();
    
    void setExp(final float p0);
    
    int getLevel();
    
    void setLevel(final int p0);
    
    int getTotalExperience();
    
    void setTotalExperience(final int p0);
    
    float getExhaustion();
    
    void setExhaustion(final float p0);
    
    float getSaturation();
    
    void setSaturation(final float p0);
    
    int getFoodLevel();
    
    void setFoodLevel(final int p0);
    
    Location getBedSpawnLocation();
    
    void setBedSpawnLocation(final Location p0);
    
    void setBedSpawnLocation(final Location p0, final boolean p1);
    
    boolean getAllowFlight();
    
    void setAllowFlight(final boolean p0);
    
    void hidePlayer(final Player p0);
    
    void showPlayer(final Player p0);
    
    boolean canSee(final Player p0);
    
    @Deprecated
    boolean isOnGround();
    
    boolean isFlying();
    
    void setFlying(final boolean p0);
    
    void setFlySpeed(final float p0) throws IllegalArgumentException;
    
    void setWalkSpeed(final float p0) throws IllegalArgumentException;
    
    float getFlySpeed();
    
    float getWalkSpeed();
    
    @Deprecated
    void setTexturePack(final String p0);
    
    void setResourcePack(final String p0);
    
    Scoreboard getScoreboard();
    
    void setScoreboard(final Scoreboard p0) throws IllegalArgumentException, IllegalStateException;
    
    boolean isHealthScaled();
    
    void setHealthScaled(final boolean p0);
    
    void setHealthScale(final double p0) throws IllegalArgumentException;
    
    double getHealthScale();
    
    Entity getSpectatorTarget();
    
    void setSpectatorTarget(final Entity p0);
    
    @Deprecated
    void sendTitle(final String p0, final String p1);
    
    @Deprecated
    void resetTitle();
    
    void spawnParticle(final Particle p0, final Location p1, final int p2);
    
    void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4);
    
     <T> void spawnParticle(final Particle p0, final Location p1, final int p2, final T p3);
    
     <T> void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4, final T p5);
    
    void spawnParticle(final Particle p0, final Location p1, final int p2, final double p3, final double p4, final double p5);
    
    void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4, final double p5, final double p6, final double p7);
    
     <T> void spawnParticle(final Particle p0, final Location p1, final int p2, final double p3, final double p4, final double p5, final T p6);
    
     <T> void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4, final double p5, final double p6, final double p7, final T p8);
    
    void spawnParticle(final Particle p0, final Location p1, final int p2, final double p3, final double p4, final double p5, final double p6);
    
    void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4, final double p5, final double p6, final double p7, final double p8);
    
     <T> void spawnParticle(final Particle p0, final Location p1, final int p2, final double p3, final double p4, final double p5, final double p6, final T p7);
    
     <T> void spawnParticle(final Particle p0, final double p1, final double p2, final double p3, final int p4, final double p5, final double p6, final double p7, final double p8, final T p9);
    
    Spigot spigot();
    
    public static class Spigot extends Entity.Spigot
    {
        public InetSocketAddress getRawAddress() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void playEffect(final Location location, final Effect effect, final int id, final int data, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int particleCount, final int radius) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Deprecated
        public boolean getCollidesWithEntities() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Deprecated
        public void setCollidesWithEntities(final boolean collides) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void respawn() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public String getLocale() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public Set<Player> getHiddenPlayers() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void sendMessage(final BaseComponent component) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void sendMessage(final BaseComponent... components) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void sendMessage(final ChatMessageType position, final BaseComponent component) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void sendMessage(final ChatMessageType position, final BaseComponent... components) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
