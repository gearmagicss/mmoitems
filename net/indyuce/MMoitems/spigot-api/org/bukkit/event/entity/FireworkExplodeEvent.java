// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class FireworkExplodeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public FireworkExplodeEvent(final Firework what) {
        super(what);
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public Firework getEntity() {
        return (Firework)super.getEntity();
    }
    
    @Override
    public HandlerList getHandlers() {
        return FireworkExplodeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return FireworkExplodeEvent.handlers;
    }
}
