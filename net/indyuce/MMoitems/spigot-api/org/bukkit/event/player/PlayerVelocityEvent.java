// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerVelocityEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Vector velocity;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerVelocityEvent(final Player player, final Vector velocity) {
        super(player);
        this.cancel = false;
        this.velocity = velocity;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Vector getVelocity() {
        return this.velocity;
    }
    
    public void setVelocity(final Vector velocity) {
        this.velocity = velocity;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerVelocityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerVelocityEvent.handlers;
    }
}
