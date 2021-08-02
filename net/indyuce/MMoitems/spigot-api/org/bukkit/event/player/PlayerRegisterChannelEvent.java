// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;

public class PlayerRegisterChannelEvent extends PlayerChannelEvent
{
    public PlayerRegisterChannelEvent(final Player player, final String channel) {
        super(player, channel);
    }
}
