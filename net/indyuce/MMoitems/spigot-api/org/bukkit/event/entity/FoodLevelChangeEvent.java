// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class FoodLevelChangeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private int level;
    
    static {
        handlers = new HandlerList();
    }
    
    public FoodLevelChangeEvent(final HumanEntity what, final int level) {
        super(what);
        this.cancel = false;
        this.level = level;
    }
    
    @Override
    public HumanEntity getEntity() {
        return (HumanEntity)this.entity;
    }
    
    public int getFoodLevel() {
        return this.level;
    }
    
    public void setFoodLevel(int level) {
        if (level > 20) {
            level = 20;
        }
        else if (level < 0) {
            level = 0;
        }
        this.level = level;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return FoodLevelChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return FoodLevelChangeEvent.handlers;
    }
}
