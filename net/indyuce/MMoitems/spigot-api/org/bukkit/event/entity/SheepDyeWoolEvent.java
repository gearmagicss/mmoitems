// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.DyeColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class SheepDyeWoolEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private DyeColor color;
    
    static {
        handlers = new HandlerList();
    }
    
    public SheepDyeWoolEvent(final Sheep sheep, final DyeColor color) {
        super(sheep);
        this.cancel = false;
        this.color = color;
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
    public Sheep getEntity() {
        return (Sheep)this.entity;
    }
    
    public DyeColor getColor() {
        return this.color;
    }
    
    public void setColor(final DyeColor color) {
        this.color = color;
    }
    
    @Override
    public HandlerList getHandlers() {
        return SheepDyeWoolEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return SheepDyeWoolEvent.handlers;
    }
}
