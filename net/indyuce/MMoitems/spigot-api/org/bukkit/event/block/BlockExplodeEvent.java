// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockExplodeEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final List<Block> blocks;
    private float yield;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockExplodeEvent(final Block what, final List<Block> blocks, final float yield) {
        super(what);
        this.blocks = blocks;
        this.yield = yield;
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
    
    public List<Block> blockList() {
        return this.blocks;
    }
    
    public float getYield() {
        return this.yield;
    }
    
    public void setYield(final float yield) {
        this.yield = yield;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockExplodeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockExplodeEvent.handlers;
    }
}
