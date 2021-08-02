// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import org.bukkit.block.BrewingStand;

public interface BrewerInventory extends Inventory
{
    ItemStack getIngredient();
    
    void setIngredient(final ItemStack p0);
    
    ItemStack getFuel();
    
    void setFuel(final ItemStack p0);
    
    BrewingStand getHolder();
}
