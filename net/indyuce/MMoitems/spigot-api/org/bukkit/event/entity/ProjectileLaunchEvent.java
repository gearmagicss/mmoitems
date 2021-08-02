// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ProjectileLaunchEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public ProjectileLaunchEvent(final Entity what) {
        super(what);
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
    public Projectile getEntity() {
        return (Projectile)this.entity;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ProjectileLaunchEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ProjectileLaunchEvent.handlers;
    }
}
