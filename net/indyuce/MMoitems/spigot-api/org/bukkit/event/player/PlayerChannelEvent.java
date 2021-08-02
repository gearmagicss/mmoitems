// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public abstract class PlayerChannelEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final String channel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerChannelEvent(final Player player, final String channel) {
        super(player);
        this.channel = channel;
    }
    
    public final String getChannel() {
        return this.channel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerChannelEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChannelEvent.handlers;
    }
}
