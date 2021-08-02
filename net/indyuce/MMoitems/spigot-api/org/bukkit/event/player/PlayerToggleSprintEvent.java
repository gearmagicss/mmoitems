// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final boolean isSprinting;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerToggleSprintEvent(final Player player, final boolean isSprinting) {
        super(player);
        this.cancel = false;
        this.isSprinting = isSprinting;
    }
    
    public boolean isSprinting() {
        return this.isSprinting;
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
        return PlayerToggleSprintEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerToggleSprintEvent.handlers;
    }
}
