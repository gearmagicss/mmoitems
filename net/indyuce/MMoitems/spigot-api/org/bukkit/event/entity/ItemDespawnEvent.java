// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ItemDespawnEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final Location location;
    
    static {
        handlers = new HandlerList();
    }
    
    public ItemDespawnEvent(final Item despawnee, final Location loc) {
        super(despawnee);
        this.location = loc;
    }
    
    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    @Override
    public Item getEntity() {
        return (Item)this.entity;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ItemDespawnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ItemDespawnEvent.handlers;
    }
}
