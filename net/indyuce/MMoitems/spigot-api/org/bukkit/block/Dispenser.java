// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.inventory.InventoryHolder;

public interface Dispenser extends BlockState, InventoryHolder
{
    BlockProjectileSource getBlockProjectileSource();
    
    boolean dispense();
}
