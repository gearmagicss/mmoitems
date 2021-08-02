// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.InventoryHolder;

public interface Furnace extends BlockState, InventoryHolder
{
    short getBurnTime();
    
    void setBurnTime(final short p0);
    
    short getCookTime();
    
    void setCookTime(final short p0);
    
    FurnaceInventory getInventory();
}
