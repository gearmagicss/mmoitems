// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerKickEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private String leaveMessage;
    private String kickReason;
    private Boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerKickEvent(final Player playerKicked, final String kickReason, final String leaveMessage) {
        super(playerKicked);
        this.kickReason = kickReason;
        this.leaveMessage = leaveMessage;
        this.cancel = false;
    }
    
    public String getReason() {
        return this.kickReason;
    }
    
    public String getLeaveMessage() {
        return this.leaveMessage;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public void setReason(final String kickReason) {
        this.kickReason = kickReason;
    }
    
    public void setLeaveMessage(final String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerKickEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerKickEvent.handlers;
    }
}
