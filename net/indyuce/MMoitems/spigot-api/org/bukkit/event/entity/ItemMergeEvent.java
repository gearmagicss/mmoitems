// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ItemMergeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Item target;
    
    static {
        handlers = new HandlerList();
    }
    
    public ItemMergeEvent(final Item item, final Item target) {
        super(item);
        this.target = target;
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
    public Item getEntity() {
        return (Item)this.entity;
    }
    
    public Item getTarget() {
        return this.target;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ItemMergeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ItemMergeEvent.handlers;
    }
}
