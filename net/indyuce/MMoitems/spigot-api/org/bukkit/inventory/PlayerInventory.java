// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import org.bukkit.entity.HumanEntity;

public interface PlayerInventory extends Inventory
{
    ItemStack[] getArmorContents();
    
    ItemStack[] getExtraContents();
    
    ItemStack getHelmet();
    
    ItemStack getChestplate();
    
    ItemStack getLeggings();
    
    ItemStack getBoots();
    
    void setItem(final int p0, final ItemStack p1);
    
    void setArmorContents(final ItemStack[] p0);
    
    void setExtraContents(final ItemStack[] p0);
    
    void setHelmet(final ItemStack p0);
    
    void setChestplate(final ItemStack p0);
    
    void setLeggings(final ItemStack p0);
    
    void setBoots(final ItemStack p0);
    
    ItemStack getItemInMainHand();
    
    void setItemInMainHand(final ItemStack p0);
    
    ItemStack getItemInOffHand();
    
    void setItemInOffHand(final ItemStack p0);
    
    @Deprecated
    ItemStack getItemInHand();
    
    @Deprecated
    void setItemInHand(final ItemStack p0);
    
    int getHeldItemSlot();
    
    void setHeldItemSlot(final int p0);
    
    @Deprecated
    int clear(final int p0, final int p1);
    
    HumanEntity getHolder();
}
