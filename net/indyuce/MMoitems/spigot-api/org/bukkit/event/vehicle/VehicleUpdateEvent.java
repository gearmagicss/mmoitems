// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.HandlerList;

public class VehicleUpdateEvent extends VehicleEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public VehicleUpdateEvent(final Vehicle vehicle) {
        super(vehicle);
    }
    
    @Override
    public HandlerList getHandlers() {
        return VehicleUpdateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleUpdateEvent.handlers;
    }
}
