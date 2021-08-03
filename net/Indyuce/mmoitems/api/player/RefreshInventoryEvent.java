// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.player.inventory.EquippedPlayerItem;
import java.util.List;
import org.bukkit.event.Event;

public class RefreshInventoryEvent extends Event
{
    @NotNull
    final List<EquippedPlayerItem> itemsToEquip;
    @NotNull
    final Player player;
    @NotNull
    final PlayerData playerData;
    @NotNull
    static final HandlerList handlers;
    
    @NotNull
    public List<EquippedPlayerItem> getItemsToEquip() {
        return this.itemsToEquip;
    }
    
    @NotNull
    public Player getPlayer() {
        return this.player;
    }
    
    @NotNull
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public RefreshInventoryEvent(@NotNull final List<EquippedPlayerItem> itemsToEquip, @NotNull final Player player, @NotNull final PlayerData playerData) {
        this.itemsToEquip = itemsToEquip;
        this.player = player;
        this.playerData = playerData;
    }
    
    @NotNull
    public HandlerList getHandlers() {
        return RefreshInventoryEvent.handlers;
    }
    
    @NotNull
    public static HandlerList getHandlerList() {
        return RefreshInventoryEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
