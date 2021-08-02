// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerToggleFlightEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final boolean isFlying;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerToggleFlightEvent(final Player player, final boolean isFlying) {
        super(player);
        this.cancel = false;
        this.isFlying = isFlying;
    }
    
    public boolean isFlying() {
        return this.isFlying;
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
        return PlayerToggleFlightEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerToggleFlightEvent.handlers;
    }
}
