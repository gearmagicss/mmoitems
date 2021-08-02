// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.weather;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ThunderChangeEvent extends WeatherEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final boolean to;
    
    static {
        handlers = new HandlerList();
    }
    
    public ThunderChangeEvent(final World world, final boolean to) {
        super(world);
        this.to = to;
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public boolean toThunderState() {
        return this.to;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ThunderChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ThunderChangeEvent.handlers;
    }
}
