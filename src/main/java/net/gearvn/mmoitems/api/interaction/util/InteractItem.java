// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.EquipmentSlot;

public class InteractItem
{
    private final EquipmentSlot slot;
    private final ItemStack item;
    
    public InteractItem(final Player player, final Material material) {
        this.slot = (this.hasItem(player.getInventory().getItemInMainHand(), material) ? EquipmentSlot.HAND : (this.hasItem(player.getInventory().getItemInOffHand(), material) ? EquipmentSlot.OFF_HAND : null));
        this.item = ((this.slot == EquipmentSlot.HAND) ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand());
    }
    
    @Deprecated
    public InteractItem(final Player player, final String s) {
        this.slot = (this.hasItem(player.getInventory().getItemInMainHand(), s) ? EquipmentSlot.HAND : (this.hasItem(player.getInventory().getItemInOffHand(), s) ? EquipmentSlot.OFF_HAND : null));
        this.item = ((this.slot == EquipmentSlot.HAND) ? player.getInventory().getItemInMainHand() : player.getInventory().getItemInOffHand());
    }
    
    public boolean hasItem() {
        return this.slot != null;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    private boolean hasItem(final ItemStack itemStack, final Material material) {
        return itemStack != null && itemStack.getType() == material;
    }
    
    private boolean hasItem(final ItemStack itemStack, final String suffix) {
        return itemStack != null && itemStack.getType().name().endsWith(suffix);
    }
}
