// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

public class BrewEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private BrewerInventory contents;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public BrewEvent(final Block brewer, final BrewerInventory contents) {
        super(brewer);
        this.contents = contents;
    }
    
    public BrewerInventory getContents() {
        return this.contents;
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
        return BrewEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BrewEvent.handlers;
    }
}
