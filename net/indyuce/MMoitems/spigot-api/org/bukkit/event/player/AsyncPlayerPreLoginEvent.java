// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import java.util.UUID;
import java.net.InetAddress;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class AsyncPlayerPreLoginEvent extends Event
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
    public AsyncPlayerPreLoginEvent(final String name, final InetAddress ipAddress) {
        this(name, ipAddress, null);
    }
    
    public AsyncPlayerPreLoginEvent(final String name, final InetAddress ipAddress, final UUID uniqueId) {
        super(true);
        this.result = Result.ALLOWED;
        this.message = "";
        this.name = name;
        this.ipAddress = ipAddress;
        this.uniqueId = uniqueId;
    }
    
    public Result getLoginResult() {
        return this.result;
    }
    
    @Deprecated
    public PlayerPreLoginEvent.Result getResult() {
        return (this.result == null) ? null : this.result.old();
    }
    
    public void setLoginResult(final Result result) {
        this.result = result;
    }
    
    @Deprecated
    public void setResult(final PlayerPreLoginEvent.Result result) {
        this.result = ((result == null) ? null : Result.valueOf(result.name()));
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
    
    @Deprecated
    public void disallow(final PlayerPreLoginEvent.Result result, final String message) {
        this.result = ((result == null) ? null : Result.valueOf(result.name()));
        this.message = message;
    }
    
    public String getName() {
        return this.name;
    }
    
    public InetAddress getAddress() {
        return this.ipAddress;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    @Override
    public HandlerList getHandlers() {
        return AsyncPlayerPreLoginEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return AsyncPlayerPreLoginEvent.handlers;
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
        
        @Deprecated
        private PlayerPreLoginEvent.Result old() {
            return PlayerPreLoginEvent.Result.valueOf(this.name());
        }
    }
}
