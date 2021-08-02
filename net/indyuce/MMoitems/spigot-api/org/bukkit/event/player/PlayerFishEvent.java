// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerFishEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity entity;
    private boolean cancel;
    private int exp;
    private final State state;
    private final Fish hookEntity;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public PlayerFishEvent(final Player player, final Entity entity, final State state) {
        this(player, entity, null, state);
    }
    
    public PlayerFishEvent(final Player player, final Entity entity, final Fish hookEntity, final State state) {
        super(player);
        this.cancel = false;
        this.entity = entity;
        this.hookEntity = hookEntity;
        this.state = state;
    }
    
    public Entity getCaught() {
        return this.entity;
    }
    
    public Fish getHook() {
        return this.hookEntity;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public int getExpToDrop() {
        return this.exp;
    }
    
    public void setExpToDrop(final int amount) {
        this.exp = amount;
    }
    
    public State getState() {
        return this.state;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerFishEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerFishEvent.handlers;
    }
    
    public enum State
    {
        FISHING("FISHING", 0), 
        CAUGHT_FISH("CAUGHT_FISH", 1), 
        CAUGHT_ENTITY("CAUGHT_ENTITY", 2), 
        IN_GROUND("IN_GROUND", 3), 
        FAILED_ATTEMPT("FAILED_ATTEMPT", 4), 
        BITE("BITE", 5);
        
        private State(final String name, final int ordinal) {
        }
    }
}
