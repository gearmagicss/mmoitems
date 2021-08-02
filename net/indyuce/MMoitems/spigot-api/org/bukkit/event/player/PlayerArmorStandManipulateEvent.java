// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;

public class PlayerArmorStandManipulateEvent extends PlayerInteractEntityEvent
{
    private static final HandlerList handlers;
    private final ItemStack playerItem;
    private final ItemStack armorStandItem;
    private final EquipmentSlot slot;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerArmorStandManipulateEvent(final Player who, final ArmorStand clickedEntity, final ItemStack playerItem, final ItemStack armorStandItem, final EquipmentSlot slot) {
        super(who, clickedEntity);
        this.playerItem = playerItem;
        this.armorStandItem = armorStandItem;
        this.slot = slot;
    }
    
    public ItemStack getPlayerItem() {
        return this.playerItem;
    }
    
    public ItemStack getArmorStandItem() {
        return this.armorStandItem;
    }
    
    public EquipmentSlot getSlot() {
        return this.slot;
    }
    
    @Override
    public ArmorStand getRightClicked() {
        return (ArmorStand)this.clickedEntity;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PlayerArmorStandManipulateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerArmorStandManipulateEvent.handlers;
    }
}
