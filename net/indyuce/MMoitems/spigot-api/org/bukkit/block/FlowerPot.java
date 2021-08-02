// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.material.MaterialData;

public interface FlowerPot extends BlockState
{
    MaterialData getContents();
    
    void setContents(final MaterialData p0);
}
