// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockSpreadEvent extends BlockFormEvent
{
    private static final HandlerList handlers;
    private final Block source;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockSpreadEvent(final Block block, final Block source, final BlockState newState) {
        super(block, newState);
        this.source = source;
    }
    
    public Block getSource() {
        return this.source;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockSpreadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockSpreadEvent.handlers;
    }
}
