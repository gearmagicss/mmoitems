// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockRedstoneEvent extends BlockEvent
{
    private static final HandlerList handlers;
    private final int oldCurrent;
    private int newCurrent;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockRedstoneEvent(final Block block, final int oldCurrent, final int newCurrent) {
        super(block);
        this.oldCurrent = oldCurrent;
        this.newCurrent = newCurrent;
    }
    
    public int getOldCurrent() {
        return this.oldCurrent;
    }
    
    public int getNewCurrent() {
        return this.newCurrent;
    }
    
    public void setNewCurrent(final int newCurrent) {
        this.newCurrent = newCurrent;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockRedstoneEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockRedstoneEvent.handlers;
    }
}
