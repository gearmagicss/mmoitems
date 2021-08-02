// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerMoveEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Location from;
    private Location to;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerMoveEvent(final Player player, final Location from, final Location to) {
        super(player);
        this.cancel = false;
        this.from = from;
        this.to = to;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Location getFrom() {
        return this.from;
    }
    
    public void setFrom(final Location from) {
        this.validateLocation(from);
        this.from = from;
    }
    
    public Location getTo() {
        return this.to;
    }
    
    public void setTo(final Location to) {
        this.validateLocation(to);
        this.to = to;
    }
    
    private void validateLocation(final Location loc) {
        Preconditions.checkArgument(loc != null, (Object)"Cannot use null location!");
        Preconditions.checkArgument(loc.getWorld() != null, (Object)"Cannot use null location with null world!");
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerMoveEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerMoveEvent.handlers;
    }
}
