// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BarColor;
import org.bukkit.generator.ChunkGenerator;
import java.awt.image.BufferedImage;
import org.bukkit.util.CachedServerIcon;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.help.HelpMap;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.command.ConsoleCommandSender;
import java.util.Map;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import com.avaje.ebean.config.ServerConfig;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import java.util.logging.Logger;
import org.bukkit.map.MapView;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.plugin.PluginManager;
import java.util.UUID;
import java.util.List;
import java.io.File;
import java.util.Set;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

public interface Server extends PluginMessageRecipient
{
    public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
    public static final String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";
    
    String getName();
    
    String getVersion();
    
    String getBukkitVersion();
    
    @Deprecated
    Player[] _INVALID_getOnlinePlayers();
    
    Collection<? extends Player> getOnlinePlayers();
    
    int getMaxPlayers();
    
    int getPort();
    
    int getViewDistance();
    
    String getIp();
    
    String getServerName();
    
    String getServerId();
    
    String getWorldType();
    
    boolean getGenerateStructures();
    
    boolean getAllowEnd();
    
    boolean getAllowNether();
    
    boolean hasWhitelist();
    
    void setWhitelist(final boolean p0);
    
    Set<OfflinePlayer> getWhitelistedPlayers();
    
    void reloadWhitelist();
    
    int broadcastMessage(final String p0);
    
    String getUpdateFolder();
    
    File getUpdateFolderFile();
    
    long getConnectionThrottle();
    
    int getTicksPerAnimalSpawns();
    
    int getTicksPerMonsterSpawns();
    
    Player getPlayer(final String p0);
    
    Player getPlayerExact(final String p0);
    
    List<Player> matchPlayer(final String p0);
    
    Player getPlayer(final UUID p0);
    
    PluginManager getPluginManager();
    
    BukkitScheduler getScheduler();
    
    ServicesManager getServicesManager();
    
    List<World> getWorlds();
    
    World createWorld(final WorldCreator p0);
    
    boolean unloadWorld(final String p0, final boolean p1);
    
    boolean unloadWorld(final World p0, final boolean p1);
    
    World getWorld(final String p0);
    
    World getWorld(final UUID p0);
    
    @Deprecated
    MapView getMap(final short p0);
    
    MapView createMap(final World p0);
    
    void reload();
    
    Logger getLogger();
    
    PluginCommand getPluginCommand(final String p0);
    
    void savePlayers();
    
    boolean dispatchCommand(final CommandSender p0, final String p1) throws CommandException;
    
    void configureDbConfig(final ServerConfig p0);
    
    boolean addRecipe(final Recipe p0);
    
    List<Recipe> getRecipesFor(final ItemStack p0);
    
    Iterator<Recipe> recipeIterator();
    
    void clearRecipes();
    
    void resetRecipes();
    
    Map<String, String[]> getCommandAliases();
    
    int getSpawnRadius();
    
    void setSpawnRadius(final int p0);
    
    boolean getOnlineMode();
    
    boolean getAllowFlight();
    
    boolean isHardcore();
    
    @Deprecated
    boolean useExactLoginLocation();
    
    void shutdown();
    
    int broadcast(final String p0, final String p1);
    
    @Deprecated
    OfflinePlayer getOfflinePlayer(final String p0);
    
    OfflinePlayer getOfflinePlayer(final UUID p0);
    
    Set<String> getIPBans();
    
    void banIP(final String p0);
    
    void unbanIP(final String p0);
    
    Set<OfflinePlayer> getBannedPlayers();
    
    BanList getBanList(final BanList.Type p0);
    
    Set<OfflinePlayer> getOperators();
    
    GameMode getDefaultGameMode();
    
    void setDefaultGameMode(final GameMode p0);
    
    ConsoleCommandSender getConsoleSender();
    
    File getWorldContainer();
    
    OfflinePlayer[] getOfflinePlayers();
    
    Messenger getMessenger();
    
    HelpMap getHelpMap();
    
    Inventory createInventory(final InventoryHolder p0, final InventoryType p1);
    
    Inventory createInventory(final InventoryHolder p0, final InventoryType p1, final String p2);
    
    Inventory createInventory(final InventoryHolder p0, final int p1) throws IllegalArgumentException;
    
    Inventory createInventory(final InventoryHolder p0, final int p1, final String p2) throws IllegalArgumentException;
    
    int getMonsterSpawnLimit();
    
    int getAnimalSpawnLimit();
    
    int getWaterAnimalSpawnLimit();
    
    int getAmbientSpawnLimit();
    
    boolean isPrimaryThread();
    
    String getMotd();
    
    String getShutdownMessage();
    
    Warning.WarningState getWarningState();
    
    ItemFactory getItemFactory();
    
    ScoreboardManager getScoreboardManager();
    
    CachedServerIcon getServerIcon();
    
    CachedServerIcon loadServerIcon(final File p0) throws IllegalArgumentException, Exception;
    
    CachedServerIcon loadServerIcon(final BufferedImage p0) throws IllegalArgumentException, Exception;
    
    void setIdleTimeout(final int p0);
    
    int getIdleTimeout();
    
    ChunkGenerator.ChunkData createChunkData(final World p0);
    
    BossBar createBossBar(final String p0, final BarColor p1, final BarStyle p2, final BarFlag... p3);
    
    @Deprecated
    UnsafeValues getUnsafe();
    
    Spigot spigot();
    
    public static class Spigot
    {
        public YamlConfiguration getConfig() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void broadcast(final BaseComponent component) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void broadcast(final BaseComponent... components) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void restart() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
