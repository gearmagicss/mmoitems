// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.HandlerList;

public class VehicleCreateEvent extends VehicleEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public VehicleCreateEvent(final Vehicle vehicle) {
        super(vehicle);
    }
    
    @Override
    public HandlerList getHandlers() {
        return VehicleCreateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleCreateEvent.handlers;
    }
}
