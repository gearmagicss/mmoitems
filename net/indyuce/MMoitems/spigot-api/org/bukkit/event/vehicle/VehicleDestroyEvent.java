// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleDestroyEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity attacker;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public VehicleDestroyEvent(final Vehicle vehicle, final Entity attacker) {
        super(vehicle);
        this.attacker = attacker;
    }
    
    public Entity getAttacker() {
        return this.attacker;
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
        return VehicleDestroyEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleDestroyEvent.handlers;
    }
}
