// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class HorseJumpEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private float power;
    
    static {
        handlers = new HandlerList();
    }
    
    public HorseJumpEvent(final Horse horse, final float power) {
        super(horse);
        this.power = power;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Deprecated
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    @Override
    public Horse getEntity() {
        return (Horse)this.entity;
    }
    
    public float getPower() {
        return this.power;
    }
    
    @Deprecated
    public void setPower(final float power) {
        this.power = power;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HorseJumpEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return HorseJumpEvent.handlers;
    }
}
