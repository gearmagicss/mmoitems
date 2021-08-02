// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class RemoteServerCommandEvent extends ServerCommandEvent
{
    private static final HandlerList handlers;
    
    static {
        handlers = new HandlerList();
    }
    
    public RemoteServerCommandEvent(final CommandSender sender, final String command) {
        super(sender, command);
    }
    
    @Override
    public HandlerList getHandlers() {
        return RemoteServerCommandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return RemoteServerCommandEvent.handlers;
    }
}
