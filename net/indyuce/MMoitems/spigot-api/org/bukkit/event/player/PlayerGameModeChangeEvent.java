// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerGameModeChangeEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final GameMode newGameMode;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerGameModeChangeEvent(final Player player, final GameMode newGameMode) {
        super(player);
        this.newGameMode = newGameMode;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public GameMode getNewGameMode() {
        return this.newGameMode;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerGameModeChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerGameModeChangeEvent.handlers;
    }
}
