// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public abstract class PlayerDataEvent extends PlayerEvent implements Cancellable
{
    private final PlayerData playerData;
    private boolean cancelled;
    
    public PlayerDataEvent(final PlayerData playerData) {
        super(playerData.getPlayer());
        this.playerData = playerData;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
