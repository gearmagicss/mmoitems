// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerItemConsumeEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean isCancelled;
    private ItemStack item;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerItemConsumeEvent(final Player player, final ItemStack item) {
        super(player);
        this.isCancelled = false;
        this.item = item;
    }
    
    public ItemStack getItem() {
        return this.item.clone();
    }
    
    public void setItem(final ItemStack item) {
        if (item == null) {
            this.item = new ItemStack(Material.AIR);
        }
        else {
            this.item = item;
        }
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerItemConsumeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerItemConsumeEvent.handlers;
    }
}
