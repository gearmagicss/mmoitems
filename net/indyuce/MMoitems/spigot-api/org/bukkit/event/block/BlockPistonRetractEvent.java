// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.event.HandlerList;

public class BlockPistonRetractEvent extends BlockPistonEvent
{
    private static final HandlerList handlers;
    private List<Block> blocks;
    
    static {
        handlers = new HandlerList();
    }
    
    public BlockPistonRetractEvent(final Block block, final List<Block> blocks, final BlockFace direction) {
        super(block, direction);
        this.blocks = blocks;
    }
    
    @Deprecated
    public Location getRetractLocation() {
        return this.getBlock().getRelative(this.getDirection(), 2).getLocation();
    }
    
    public List<Block> getBlocks() {
        return this.blocks;
    }
    
    @Override
    public HandlerList getHandlers() {
        return BlockPistonRetractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockPistonRetractEvent.handlers;
    }
}
