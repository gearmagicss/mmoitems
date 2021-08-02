// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockGrowEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final BlockState newState;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockGrowEvent(final Block block, final BlockState newState) {
        super(block);
        this.cancelled = false;
        this.newState = newState;
    }
    
    public BlockState getNewState() {
        return this.newState;
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
        return BlockGrowEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockGrowEvent.handlers;
    }
}
