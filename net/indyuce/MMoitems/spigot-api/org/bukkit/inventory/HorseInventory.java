// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

public interface HorseInventory extends Inventory
{
    ItemStack getSaddle();
    
    ItemStack getArmor();
    
    void setSaddle(final ItemStack p0);
    
    void setArmor(final ItemStack p0);
}
