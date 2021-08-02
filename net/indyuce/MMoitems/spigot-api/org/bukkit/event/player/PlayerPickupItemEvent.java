// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Item;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerPickupItemEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Item item;
    private boolean cancel;
    private final int remaining;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerPickupItemEvent(final Player player, final Item item, final int remaining) {
        super(player);
        this.cancel = false;
        this.item = item;
        this.remaining = remaining;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getRemaining() {
        return this.remaining;
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
        return PlayerPickupItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerPickupItemEvent.handlers;
    }
}
