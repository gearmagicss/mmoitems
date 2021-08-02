// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.event.HandlerList;

public class ServiceUnregisterEvent extends ServiceEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public ServiceUnregisterEvent(final RegisteredServiceProvider<?> serviceProvider) {
        super(serviceProvider);
    }
    
    @Override
    public HandlerList getHandlers() {
        return ServiceUnregisterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServiceUnregisterEvent.handlers;
    }
}
