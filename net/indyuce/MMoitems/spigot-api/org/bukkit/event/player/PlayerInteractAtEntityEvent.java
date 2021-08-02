// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.event.HandlerList;

public class PlayerInteractAtEntityEvent extends PlayerInteractEntityEvent
{
    private static final HandlerList handlers;
    private final Vector position;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerInteractAtEntityEvent(final Player who, final Entity clickedEntity, final Vector position) {
        this(who, clickedEntity, position, EquipmentSlot.HAND);
    }
    
    public PlayerInteractAtEntityEvent(final Player who, final Entity clickedEntity, final Vector position, final EquipmentSlot hand) {
        super(who, clickedEntity, hand);
        this.position = position;
    }
    
    public Vector getClickedPosition() {
        return this.position.clone();
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerInteractAtEntityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerInteractAtEntityEvent.handlers;
    }
}
