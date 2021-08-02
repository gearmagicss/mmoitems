// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class SheepRegrowWoolEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public SheepRegrowWoolEvent(final Sheep sheep) {
        super(sheep);
        this.cancel = false;
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
    
    @Override
    public HandlerList getHandlers() {
        return SheepRegrowWoolEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return SheepRegrowWoolEvent.handlers;
    }
}
