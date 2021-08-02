// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class CreeperPowerEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final PowerCause cause;
    private LightningStrike bolt;
    
    static {
        handlers = new HandlerList();
    }
    
    public CreeperPowerEvent(final Creeper creeper, final LightningStrike bolt, final PowerCause cause) {
        this(creeper, cause);
        this.bolt = bolt;
    }
    
    public CreeperPowerEvent(final Creeper creeper, final PowerCause cause) {
        super(creeper);
        this.cause = cause;
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    @Override
    public Creeper getEntity() {
        return (Creeper)this.entity;
    }
    
    public LightningStrike getLightning() {
        return this.bolt;
    }
    
    public PowerCause getCause() {
        return this.cause;
    }
    
    @Override
    public HandlerList getHandlers() {
        return CreeperPowerEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CreeperPowerEvent.handlers;
    }
    
    public enum PowerCause
    {
        LIGHTNING("LIGHTNING", 0), 
        SET_ON("SET_ON", 1), 
        SET_OFF("SET_OFF", 2);
        
        private PowerCause(final String name, final int ordinal) {
        }
    }
}
