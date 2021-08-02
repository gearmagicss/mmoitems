// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerTeleportEvent extends PlayerMoveEvent
{
    private static final HandlerList handlers;
    private TeleportCause cause;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerTeleportEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
        this.cause = TeleportCause.UNKNOWN;
    }
    
    public PlayerTeleportEvent(final Player player, final Location from, final Location to, final TeleportCause cause) {
        this(player, from, to);
        this.cause = cause;
    }
    
    public TeleportCause getCause() {
        return this.cause;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerTeleportEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerTeleportEvent.handlers;
    }
    
    public enum TeleportCause
    {
        ENDER_PEARL("ENDER_PEARL", 0), 
        COMMAND("COMMAND", 1), 
        PLUGIN("PLUGIN", 2), 
        NETHER_PORTAL("NETHER_PORTAL", 3), 
        END_PORTAL("END_PORTAL", 4), 
        SPECTATE("SPECTATE", 5), 
        END_GATEWAY("END_GATEWAY", 6), 
        CHORUS_FRUIT("CHORUS_FRUIT", 7), 
        UNKNOWN("UNKNOWN", 8);
        
        private TeleportCause(final String name, final int ordinal) {
        }
    }
}
