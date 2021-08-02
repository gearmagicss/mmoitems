// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.MainHand;
import org.bukkit.event.HandlerList;

public class PlayerChangedMainHandEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final MainHand mainHand;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerChangedMainHandEvent(final Player who, final MainHand mainHand) {
        super(who);
        this.mainHand = mainHand;
    }
    
    public MainHand getMainHand() {
        return this.mainHand;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerChangedMainHandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerChangedMainHandEvent.handlers;
    }
}
