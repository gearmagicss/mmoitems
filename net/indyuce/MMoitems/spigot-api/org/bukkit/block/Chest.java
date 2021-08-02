// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface Chest extends BlockState, InventoryHolder
{
    Inventory getBlockInventory();
}
