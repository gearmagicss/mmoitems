// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.HandlerList;

public class InventoryCloseEvent extends InventoryEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public InventoryCloseEvent(final InventoryView transaction) {
        super(transaction);
    }
    
    public final HumanEntity getPlayer() {
        return this.transaction.getPlayer();
    }
    
    @Override
    public HandlerList getHandlers() {
        return InventoryCloseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryCloseEvent.handlers;
    }
}
