// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerSwapHandItemsEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private ItemStack mainHandItem;
    private ItemStack offHandItem;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerSwapHandItemsEvent(final Player player, final ItemStack mainHandItem, final ItemStack offHandItem) {
        super(player);
        this.mainHandItem = mainHandItem;
        this.offHandItem = offHandItem;
    }
    
    public ItemStack getMainHandItem() {
        return this.mainHandItem;
    }
    
    public void setMainHandItem(final ItemStack mainHandItem) {
        this.mainHandItem = mainHandItem;
    }
    
    public ItemStack getOffHandItem() {
        return this.offHandItem;
    }
    
    public void setOffHandItem(final ItemStack offHandItem) {
        this.offHandItem = offHandItem;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerSwapHandItemsEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerSwapHandItemsEvent.handlers;
    }
}
