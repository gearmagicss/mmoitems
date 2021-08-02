// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityTargetEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Entity target;
    private final TargetReason reason;
    
    static {
        handlers = new HandlerList();
    }
    
    public EntityTargetEvent(final Entity entity, final Entity target, final TargetReason reason) {
        super(entity);
        this.cancel = false;
        this.target = target;
        this.reason = reason;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public TargetReason getReason() {
        return this.reason;
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    public void setTarget(final Entity target) {
        this.target = target;
    }
    
    @Override
    public HandlerList getHandlers() {
        return EntityTargetEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityTargetEvent.handlers;
    }
    
    public enum TargetReason
    {
        TARGET_DIED("TARGET_DIED", 0), 
        CLOSEST_PLAYER("CLOSEST_PLAYER", 1), 
        TARGET_ATTACKED_ENTITY("TARGET_ATTACKED_ENTITY", 2), 
        PIG_ZOMBIE_TARGET("PIG_ZOMBIE_TARGET", 3), 
        FORGOT_TARGET("FORGOT_TARGET", 4), 
        TARGET_ATTACKED_OWNER("TARGET_ATTACKED_OWNER", 5), 
        OWNER_ATTACKED_TARGET("OWNER_ATTACKED_TARGET", 6), 
        RANDOM_TARGET("RANDOM_TARGET", 7), 
        DEFEND_VILLAGE("DEFEND_VILLAGE", 8), 
        TARGET_ATTACKED_NEARBY_ENTITY("TARGET_ATTACKED_NEARBY_ENTITY", 9), 
        REINFORCEMENT_TARGET("REINFORCEMENT_TARGET", 10), 
        COLLISION("COLLISION", 11), 
        CUSTOM("CUSTOM", 12), 
        CLOSEST_ENTITY("CLOSEST_ENTITY", 13), 
        UNKNOWN("UNKNOWN", 14);
        
        private TargetReason(final String name, final int ordinal) {
        }
    }
}
