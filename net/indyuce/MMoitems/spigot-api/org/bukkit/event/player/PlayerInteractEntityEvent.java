// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerInteractEntityEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected Entity clickedEntity;
    boolean cancelled;
    private EquipmentSlot hand;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerInteractEntityEvent(final Player who, final Entity clickedEntity) {
        this(who, clickedEntity, EquipmentSlot.HAND);
    }
    
    public PlayerInteractEntityEvent(final Player who, final Entity clickedEntity, final EquipmentSlot hand) {
        super(who);
        this.cancelled = false;
        this.clickedEntity = clickedEntity;
        this.hand = hand;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Entity getRightClicked() {
        return this.clickedEntity;
    }
    
    public EquipmentSlot getHand() {
        return this.hand;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerInteractEntityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerInteractEntityEvent.handlers;
    }
}
