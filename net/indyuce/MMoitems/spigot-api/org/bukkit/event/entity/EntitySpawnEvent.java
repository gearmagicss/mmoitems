// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntitySpawnEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntitySpawnEvent(final Entity spawnee) {
        super(spawnee);
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public Location getLocation() {
        return this.getEntity().getLocation();
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntitySpawnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntitySpawnEvent.handlers;
    }
}
