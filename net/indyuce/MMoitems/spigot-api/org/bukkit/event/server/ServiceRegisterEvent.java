// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.event.HandlerList;

public class ServiceRegisterEvent extends ServiceEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public ServiceRegisterEvent(final RegisteredServiceProvider<?> registeredProvider) {
        super(registeredProvider);
    }
    
    @Override
    public HandlerList getHandlers() {
        return ServiceRegisterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServiceRegisterEvent.handlers;
    }
}
