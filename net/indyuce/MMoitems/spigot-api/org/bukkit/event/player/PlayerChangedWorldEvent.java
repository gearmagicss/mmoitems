// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class PlayerChangedWorldEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final World from;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerChangedWorldEvent(final Player player, final World from) {
        super(player);
        this.from = from;
    }
    
    public World getFrom() {
        return this.from;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerChangedWorldEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChangedWorldEvent.handlers;
    }
}
