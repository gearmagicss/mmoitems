// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class InventoryOpenEvent extends InventoryEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public InventoryOpenEvent(final InventoryView transaction) {
        super(transaction);
        this.cancelled = false;
    }
    
    public final HumanEntity getPlayer() {
        return this.transaction.getPlayer();
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
        return InventoryOpenEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryOpenEvent.handlers;
    }
}
