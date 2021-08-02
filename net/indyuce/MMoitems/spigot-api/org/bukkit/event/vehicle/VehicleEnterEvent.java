// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleEnterEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Entity entered;
    
    static {
        handlers = new HandlerList();
    }
    
    public VehicleEnterEvent(final Vehicle vehicle, final Entity entered) {
        super(vehicle);
        this.entered = entered;
    }
    
    public Entity getEntered() {
        return this.entered;
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
        return VehicleEnterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleEnterEvent.handlers;
    }
}
