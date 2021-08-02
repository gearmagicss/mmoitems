// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.Achievement;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerAchievementAwardedEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Achievement achievement;
    private boolean isCancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerAchievementAwardedEvent(final Player player, final Achievement achievement) {
        super(player);
        this.isCancelled = false;
        this.achievement = achievement;
    }
    
    public Achievement getAchievement() {
        return this.achievement;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerAchievementAwardedEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerAchievementAwardedEvent.handlers;
    }
}
