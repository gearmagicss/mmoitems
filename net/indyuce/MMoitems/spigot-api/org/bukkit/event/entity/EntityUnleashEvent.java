// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;

public class EntityUnleashEvent extends EntityEvent
{
    private static final HandlerList handlers;
    private final UnleashReason reason;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityUnleashEvent(final Entity entity, final UnleashReason reason) {
        super(entity);
        this.reason = reason;
    }
    
    public UnleashReason getReason() {
        return this.reason;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityUnleashEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityUnleashEvent.handlers;
    }
    
    public enum UnleashReason
    {
        HOLDER_GONE("HOLDER_GONE", 0), 
        PLAYER_UNLEASH("PLAYER_UNLEASH", 1), 
        DISTANCE("DISTANCE", 2), 
        UNKNOWN("UNKNOWN", 3);
        
        private UnleashReason(final String name, final int ordinal) {
        }
    }
}
