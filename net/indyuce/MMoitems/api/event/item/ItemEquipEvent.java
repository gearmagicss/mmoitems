// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class ItemEquipEvent extends PlayerDataEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final ItemStack item;
    
    public ItemEquipEvent(final Player player, final ItemStack item) {
        super(PlayerData.get((OfflinePlayer)player));
        this.item = item;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public HandlerList getHandlers() {
        return ItemEquipEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ItemEquipEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
