// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import com.google.common.base.Preconditions;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class CauldronLevelChangeEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Entity entity;
    private final ChangeReason reason;
    private final int oldLevel;
    private int newLevel;
    
    static {
        handlers = new HandlerList();
    }
    
    public CauldronLevelChangeEvent(final Block block, final Entity entity, final ChangeReason reason, final int oldLevel, final int newLevel) {
        super(block);
        this.entity = entity;
        this.reason = reason;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public ChangeReason getReason() {
        return this.reason;
    }
    
    public int getOldLevel() {
        return this.oldLevel;
    }
    
    public int getNewLevel() {
        return this.newLevel;
    }
    
    public void setNewLevel(final int newLevel) {
        Preconditions.checkArgument(newLevel >= 0 && newLevel <= 3, "Cauldron level out of bounds 0 <= %s <= 3", newLevel);
        this.newLevel = newLevel;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public HandlerList getHandlers() {
        return CauldronLevelChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CauldronLevelChangeEvent.handlers;
    }
    
    public enum ChangeReason
    {
        BUCKET_FILL("BUCKET_FILL", 0), 
        BUCKET_EMPTY("BUCKET_EMPTY", 1), 
        BOTTLE_FILL("BOTTLE_FILL", 2), 
        BOTTLE_EMPTY("BOTTLE_EMPTY", 3), 
        BANNER_WASH("BANNER_WASH", 4), 
        ARMOR_WASH("ARMOR_WASH", 5), 
        EXTINGUISH("EXTINGUISH", 6), 
        EVAPORATE("EVAPORATE", 7), 
        UNKNOWN("UNKNOWN", 8);
        
        private ChangeReason(final String name, final int ordinal) {
        }
    }
}
