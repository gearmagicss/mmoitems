// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class TabCompleteEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private final CommandSender sender;
    private final String buffer;
    private List<String> completions;
    private boolean cancelled;
    
    static {
        handlers = new HandlerList();
    }
    
    public TabCompleteEvent(final CommandSender sender, final String buffer, final List<String> completions) {
        Validate.notNull(sender, "sender");
        Validate.notNull(buffer, "buffer");
        Validate.notNull(completions, "completions");
        this.sender = sender;
        this.buffer = buffer;
        this.completions = completions;
    }
    
    public CommandSender getSender() {
        return this.sender;
    }
    
    public String getBuffer() {
        return this.buffer;
    }
    
    public List<String> getCompletions() {
        return this.completions;
    }
    
    public void setCompletions(final List<String> completions) {
        Validate.notNull(completions);
        this.completions = completions;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public HandlerList getHandlers() {
        return TabCompleteEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return TabCompleteEvent.handlers;
    }
}
