// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.server;

import org.bukkit.util.CachedServerIcon;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.net.InetAddress;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;

public class ServerListPingEvent extends ServerEvent implements Iterable<Player>
{
    private static final int MAGIC_PLAYER_COUNT = Integer.MIN_VALUE;
    private static final HandlerList handlers;
    private final InetAddress address;
    private String motd;
    private final int numPlayers;
    private int maxPlayers;
    
    static {
        handlers = new HandlerList();
    }
    
    public ServerListPingEvent(final InetAddress address, final String motd, final int numPlayers, final int maxPlayers) {
        Validate.isTrue(numPlayers >= 0, "Cannot have negative number of players online", numPlayers);
        this.address = address;
        this.motd = motd;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
    }
    
    protected ServerListPingEvent(final InetAddress address, final String motd, final int maxPlayers) {
        this.numPlayers = Integer.MIN_VALUE;
        this.address = address;
        this.motd = motd;
        this.maxPlayers = maxPlayers;
    }
    
    public InetAddress getAddress() {
        return this.address;
    }
    
    public String getMotd() {
        return this.motd;
    }
    
    public void setMotd(final String motd) {
        this.motd = motd;
    }
    
    public int getNumPlayers() {
        int numPlayers = this.numPlayers;
        if (numPlayers == Integer.MIN_VALUE) {
            numPlayers = 0;
            for (final Player player : this) {
                ++numPlayers;
            }
        }
        return numPlayers;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public void setMaxPlayers(final int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    public void setServerIcon(final CachedServerIcon icon) throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public HandlerList getHandlers() {
        return ServerListPingEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServerListPingEvent.handlers;
    }
    
    @Override
    public Iterator<Player> iterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
