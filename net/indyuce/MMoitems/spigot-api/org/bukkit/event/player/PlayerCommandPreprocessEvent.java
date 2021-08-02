// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.apache.commons.lang.Validate;
import java.util.HashSet;
import org.bukkit.entity.Player;
import java.util.Set;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerCommandPreprocessEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private String message;
    private String format;
    private final Set<Player> recipients;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerCommandPreprocessEvent(final Player player, final String message) {
        super(player);
        this.cancel = false;
        this.format = "<%1$s> %2$s";
        this.recipients = new HashSet<Player>(player.getServer().getOnlinePlayers());
        this.message = message;
    }
    
    public PlayerCommandPreprocessEvent(final Player player, final String message, final Set<Player> recipients) {
        super(player);
        this.cancel = false;
        this.format = "<%1$s> %2$s";
        this.recipients = recipients;
        this.message = message;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String command) throws IllegalArgumentException {
        Validate.notNull(command, "Command cannot be null");
        Validate.notEmpty(command, "Command cannot be empty");
        this.message = command;
    }
    
    public void setPlayer(final Player player) throws IllegalArgumentException {
        Validate.notNull(player, "Player cannot be null");
        this.player = player;
    }
    
    @Deprecated
    public String getFormat() {
        return this.format;
    }
    
    @Deprecated
    public void setFormat(final String format) {
        try {
            String.format(format, this.player, this.message);
        }
        catch (RuntimeException ex) {
            ex.fillInStackTrace();
            throw ex;
        }
        this.format = format;
    }
    
    @Deprecated
    public Set<Player> getRecipients() {
        return this.recipients;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerCommandPreprocessEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerCommandPreprocessEvent.handlers;
    }
}
