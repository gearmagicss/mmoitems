// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityToggleGlideEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final boolean isGliding;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityToggleGlideEvent(final LivingEntity who, final boolean isGliding) {
        super(who);
        this.cancel = false;
        this.isGliding = isGliding;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public boolean isGliding() {
        return this.isGliding;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityToggleGlideEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityToggleGlideEvent.handlers;
    }
}
