// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ServerCommandEvent extends ServerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private String command;
    private final CommandSender sender;
    private boolean cancel;
    
    static {
        handlers = new HandlerList();
    }
    
    public ServerCommandEvent(final CommandSender sender, final String command) {
        this.cancel = false;
        this.command = command;
        this.sender = sender;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(final String message) {
        this.command = message;
    }
    
    public CommandSender getSender() {
        return this.sender;
    }
    
    @Override
    public HandlerList getHandlers() {
        return ServerCommandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServerCommandEvent.handlers;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
