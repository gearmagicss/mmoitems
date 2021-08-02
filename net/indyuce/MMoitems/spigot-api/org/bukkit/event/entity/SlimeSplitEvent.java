// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class SlimeSplitEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private int count;
    
    static {
        handlers = new HandlerList();
    }
    
    public SlimeSplitEvent(final Slime slime, final int count) {
        super(slime);
        this.cancel = false;
        this.count = count;
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
    public Slime getEntity() {
        return (Slime)this.entity;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    @Override
    public HandlerList getHandlers() {
        return SlimeSplitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return SlimeSplitEvent.handlers;
    }
}
