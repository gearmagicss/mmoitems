// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import org.bukkit.entity.Entity;

public interface EntityEquipment
{
    ItemStack getItemInMainHand();
    
    void setItemInMainHand(final ItemStack p0);
    
    ItemStack getItemInOffHand();
    
    void setItemInOffHand(final ItemStack p0);
    
    @Deprecated
    ItemStack getItemInHand();
    
    @Deprecated
    void setItemInHand(final ItemStack p0);
    
    ItemStack getHelmet();
    
    void setHelmet(final ItemStack p0);
    
    ItemStack getChestplate();
    
    void setChestplate(final ItemStack p0);
    
    ItemStack getLeggings();
    
    void setLeggings(final ItemStack p0);
    
    ItemStack getBoots();
    
    void setBoots(final ItemStack p0);
    
    ItemStack[] getArmorContents();
    
    void setArmorContents(final ItemStack[] p0);
    
    void clear();
    
    @Deprecated
    float getItemInHandDropChance();
    
    @Deprecated
    void setItemInHandDropChance(final float p0);
    
    float getItemInMainHandDropChance();
    
    void setItemInMainHandDropChance(final float p0);
    
    float getItemInOffHandDropChance();
    
    void setItemInOffHandDropChance(final float p0);
    
    float getHelmetDropChance();
    
    void setHelmetDropChance(final float p0);
    
    float getChestplateDropChance();
    
    void setChestplateDropChance(final float p0);
    
    float getLeggingsDropChance();
    
    void setLeggingsDropChance(final float p0);
    
    float getBootsDropChance();
    
    void setBootsDropChance(final float p0);
    
    Entity getHolder();
}
