// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EnderDragonChangePhaseEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final EnderDragon.Phase currentPhase;
    private EnderDragon.Phase newPhase;
    
    static {
        handlers = new HandlerList();
    }
    
    public EnderDragonChangePhaseEvent(final EnderDragon enderDragon, final EnderDragon.Phase currentPhase, final EnderDragon.Phase newPhase) {
        super(enderDragon);
        this.currentPhase = currentPhase;
        this.setNewPhase(newPhase);
    }
    
    @Override
    public EnderDragon getEntity() {
        return (EnderDragon)this.entity;
    }
    
    public EnderDragon.Phase getCurrentPhase() {
        return this.currentPhase;
    }
    
    public EnderDragon.Phase getNewPhase() {
        return this.newPhase;
    }
    
    public void setNewPhase(final EnderDragon.Phase newPhase) {
        Validate.notNull(newPhase, "New dragon phase cannot be null");
        this.newPhase = newPhase;
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
    public HandlerList getHandlers() {
        return EnderDragonChangePhaseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EnderDragonChangePhaseEvent.handlers;
    }
}
