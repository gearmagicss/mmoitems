// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;

public class ProjectileHitEvent extends EntityEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public ProjectileHitEvent(final Projectile projectile) {
        super(projectile);
    }
    
    @Override
    public Projectile getEntity() {
        return (Projectile)this.entity;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ProjectileHitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ProjectileHitEvent.handlers;
    }
}
