// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class InventoryPickupItemEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Inventory inventory;
    private final Item item;
    
    static {
        handlers = new HandlerList();
    }
    
    public InventoryPickupItemEvent(final Inventory inventory, final Item item) {
        this.inventory = inventory;
        this.item = item;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public Item getItem() {
        return this.item;
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
        return InventoryPickupItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryPickupItemEvent.handlers;
    }
}
