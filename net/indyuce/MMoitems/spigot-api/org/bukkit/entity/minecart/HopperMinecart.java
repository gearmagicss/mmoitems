// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity.minecart;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.Minecart;

public interface HopperMinecart extends Minecart, InventoryHolder
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
}
