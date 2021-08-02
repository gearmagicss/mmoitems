// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PlayerLeashEntityEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity leashHolder;
    private final Entity entity;
    private boolean cancelled;
    private final Player player;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerLeashEntityEvent(final Entity what, final Entity leashHolder, final Player leasher) {
        this.cancelled = false;
        this.leashHolder = leashHolder;
        this.entity = what;
        this.player = leasher;
    }
    
    public Entity getLeashHolder() {
        return this.leashHolder;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public final Player getPlayer() {
        return this.player;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerLeashEntityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerLeashEntityEvent.handlers;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
}
