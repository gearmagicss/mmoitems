// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerItemHeldEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final int previous;
    private final int current;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerItemHeldEvent(final Player player, final int previous, final int current) {
        super(player);
        this.cancel = false;
        this.previous = previous;
        this.current = current;
    }
    
    public int getPreviousSlot() {
        return this.previous;
    }
    
    public int getNewSlot() {
        return this.current;
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
        return PlayerItemHeldEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerItemHeldEvent.handlers;
    }
}
