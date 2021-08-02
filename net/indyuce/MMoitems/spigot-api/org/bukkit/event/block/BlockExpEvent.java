// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockExpEvent extends BlockEvent
{
    private static final HandlerList handlers;
    private int exp;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockExpEvent(final Block block, final int exp) {
        super(block);
        this.exp = exp;
    }
    
    public int getExpToDrop() {
        return this.exp;
    }
    
    public void setExpToDrop(final int exp) {
        this.exp = exp;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockExpEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockExpEvent.handlers;
    }
}
