// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

public interface EnchantingInventory extends Inventory
{
    void setItem(final ItemStack p0);
    
    ItemStack getItem();
    
    void setSecondary(final ItemStack p0);
    
    ItemStack getSecondary();
}
