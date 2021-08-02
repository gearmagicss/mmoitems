// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleExitEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final LivingEntity exited;
    
    static {
        handlers = new HandlerList();
    }
    
    public VehicleExitEvent(final Vehicle vehicle, final LivingEntity exited) {
        super(vehicle);
        this.exited = exited;
    }
    
    public LivingEntity getExited() {
        return this.exited;
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
    public HandlerList getHandlers() {
        return VehicleExitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleExitEvent.handlers;
    }
}
