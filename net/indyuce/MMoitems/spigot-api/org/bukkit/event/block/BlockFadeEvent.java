// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockFadeEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final BlockState newState;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockFadeEvent(final Block block, final BlockState newState) {
        super(block);
        this.newState = newState;
        this.cancelled = false;
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
        return BlockFadeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockFadeEvent.handlers;
    }
}
