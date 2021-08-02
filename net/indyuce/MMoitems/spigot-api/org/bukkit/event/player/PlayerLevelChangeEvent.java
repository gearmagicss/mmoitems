// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerLevelChangeEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final int oldLevel;
    private final int newLevel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerLevelChangeEvent(final Player player, final int oldLevel, final int newLevel) {
        super(player);
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }
    
    public int getOldLevel() {
        return this.oldLevel;
    }
    
    public int getNewLevel() {
        return this.newLevel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerLevelChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerLevelChangeEvent.handlers;
    }
}
