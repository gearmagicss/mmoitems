// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.HandlerList;

public class PluginDisableEvent extends PluginEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public PluginDisableEvent(final Plugin plugin) {
        super(plugin);
    }
    
    @Override
    public HandlerList getHandlers() {
        return PluginDisableEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PluginDisableEvent.handlers;
    }
}
