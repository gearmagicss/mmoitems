// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.hanging;

import org.bukkit.entity.Hanging;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class HangingBreakEvent extends HangingEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final RemoveCause cause;
    
    static {
        handlers = new HandlerList();
    }
    
    public HangingBreakEvent(final Hanging hanging, final RemoveCause cause) {
        super(hanging);
        this.cause = cause;
    }
    
    public RemoveCause getCause() {
        return this.cause;
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
        return HangingBreakEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return HangingBreakEvent.handlers;
    }
    
    public enum RemoveCause
    {
        ENTITY("ENTITY", 0), 
        EXPLOSION("EXPLOSION", 1), 
        OBSTRUCTION("OBSTRUCTION", 2), 
        PHYSICS("PHYSICS", 3), 
        DEFAULT("DEFAULT", 4);
        
        private RemoveCause(final String name, final int ordinal) {
        }
    }
}
