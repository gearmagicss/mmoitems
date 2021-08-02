// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import java.util.UUID;
import java.net.InetAddress;
import org.bukkit.event.HandlerList;
import org.bukkit.Warning;
import org.bukkit.event.Event;

@Deprecated
@Warning(reason = "This event causes a login thread to synchronize with the main thread")
public class PlayerPreLoginEvent extends Event
{
    private static final HandlerList handlers;
    private Result result;
    private String message;
    private final String name;
    private final InetAddress ipAddress;
    private final UUID uniqueId;
    
    static {
        handlers = new HandlerList();
    }
    
    @Deprecated
    public PlayerPreLoginEvent(final String name, final InetAddress ipAddress) {
        this(name, ipAddress, null);
    }
    
    public PlayerPreLoginEvent(final String name, final InetAddress ipAddress, final UUID uniqueId) {
        this.result = Result.ALLOWED;
        this.message = "";
        this.name = name;
        this.ipAddress = ipAddress;
        this.uniqueId = uniqueId;
    }
    
    public Result getResult() {
        return this.result;
    }
    
    public void setResult(final Result result) {
        this.result = result;
    }
    
    public String getKickMessage() {
        return this.message;
    }
    
    public void setKickMessage(final String message) {
        this.message = message;
    }
    
    public void allow() {
        this.result = Result.ALLOWED;
        this.message = "";
    }
    
    public void disallow(final Result result, final String message) {
        this.result = result;
        this.message = message;
    }
    
    public String getName() {
        return this.name;
    }
    
    public InetAddress getAddress() {
        return this.ipAddress;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerPreLoginEvent.handlers;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerPreLoginEvent.handlers;
    }
    
    public enum Result
    {
        ALLOWED("ALLOWED", 0), 
        KICK_FULL("KICK_FULL", 1), 
        KICK_BANNED("KICK_BANNED", 2), 
        KICK_WHITELIST("KICK_WHITELIST", 3), 
        KICK_OTHER("KICK_OTHER", 4);
        
        private Result(final String name, final int ordinal) {
        }
    }
}
