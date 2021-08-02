// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityUnleashEvent;

public class PlayerUnleashEntityEvent extends EntityUnleashEvent implements Cancellable
{
    private final Player player;
    private boolean cancelled;
    
    public PlayerUnleashEntityEvent(final Entity entity, final Player player) {
        super(entity, UnleashReason.PLAYER_UNLEASH);
        this.cancelled = false;
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
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
