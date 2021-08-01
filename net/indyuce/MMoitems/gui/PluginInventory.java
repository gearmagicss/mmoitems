// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.inventory.InventoryHolder;

public abstract class PluginInventory implements InventoryHolder
{
    protected final PlayerData playerData;
    protected final Player player;
    protected int page;
    
    public PluginInventory(@NotNull final Player player) {
        this(PlayerData.get((OfflinePlayer)player));
    }
    
    public PluginInventory(final PlayerData playerData) {
        this.page = 1;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
    }
    
    public int getPage() {
        return this.page;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public abstract Inventory getInventory();
    
    public abstract void whenClicked(final InventoryClickEvent p0);
    
    public void open() {
        if (Bukkit.isPrimaryThread()) {
            this.player.openInventory(this.getInventory());
        }
        else {
            Bukkit.getScheduler().runTask((Plugin)MMOItems.plugin, () -> this.player.openInventory(this.getInventory()));
        }
    }
}
