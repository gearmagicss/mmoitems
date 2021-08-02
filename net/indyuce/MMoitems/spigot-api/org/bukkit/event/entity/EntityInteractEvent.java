// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityInteractEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected Block block;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityInteractEvent(final Entity entity, final Block block) {
        super(entity);
        this.block = block;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityInteractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityInteractEvent.handlers;
    }
}
