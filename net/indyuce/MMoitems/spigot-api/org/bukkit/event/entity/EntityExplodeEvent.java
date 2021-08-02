// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityExplodeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final Location location;
    private final List<Block> blocks;
    private float yield;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityExplodeEvent(final Entity what, final Location location, final List<Block> blocks, final float yield) {
        super(what);
        this.location = location;
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
    
    public Location getLocation() {
        return this.location;
    }
    
    public float getYield() {
        return this.yield;
    }
    
    public void setYield(final float yield) {
        this.yield = yield;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityExplodeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityExplodeEvent.handlers;
    }
}
