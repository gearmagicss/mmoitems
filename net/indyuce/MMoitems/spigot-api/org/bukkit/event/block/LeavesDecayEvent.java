// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class LeavesDecayEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public LeavesDecayEvent(final Block block) {
        super(block);
        this.cancel = false;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return LeavesDecayEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return LeavesDecayEvent.handlers;
    }
}
