// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.InventoryHolder;

public interface BrewingStand extends BlockState, InventoryHolder
{
    int getBrewingTime();
    
    void setBrewingTime(final int p0);
    
    int getFuelLevel();
    
    void setFuelLevel(final int p0);
    
    BrewerInventory getInventory();
}
