// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;

public class PlayerPickupArrowEvent extends PlayerPickupItemEvent
{
    private final Arrow arrow;
    
    public PlayerPickupArrowEvent(final Player player, final Item item, final Arrow arrow) {
        super(player, item, 0);
        this.arrow = arrow;
    }
    
    public Arrow getArrow() {
        return this.arrow;
    }
}
