// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerToggleSneakEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final boolean isSneaking;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerToggleSneakEvent(final Player player, final boolean isSneaking) {
        super(player);
        this.cancel = false;
        this.isSneaking = isSneaking;
    }
    
    public boolean isSneaking() {
        return this.isSneaking;
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
        return PlayerToggleSneakEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerToggleSneakEvent.handlers;
    }
}
