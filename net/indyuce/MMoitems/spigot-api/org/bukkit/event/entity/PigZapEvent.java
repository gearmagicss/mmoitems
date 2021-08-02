// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PigZapEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final PigZombie pigzombie;
    private final LightningStrike bolt;
    
    static {
        handlers = new HandlerList();
    }
    
    public PigZapEvent(final Pig pig, final LightningStrike bolt, final PigZombie pigzombie) {
        super(pig);
        this.bolt = bolt;
        this.pigzombie = pigzombie;
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
    public Pig getEntity() {
        return (Pig)this.entity;
    }
    
    public LightningStrike getLightning() {
        return this.bolt;
    }
    
    public PigZombie getPigZombie() {
        return this.pigzombie;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PigZapEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PigZapEvent.handlers;
    }
}
