// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerResourcePackStatusEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final Status status;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerResourcePackStatusEvent(final Player who, final Status resourcePackStatus) {
        super(who);
        this.status = resourcePackStatus;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerResourcePackStatusEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerResourcePackStatusEvent.handlers;
    }
    
    public enum Status
    {
        SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0), 
        DECLINED("DECLINED", 1), 
        FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2), 
        ACCEPTED("ACCEPTED", 3);
        
        private Status(final String name, final int ordinal) {
        }
    }
}
