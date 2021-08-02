// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory.meta;

import org.bukkit.block.BlockState;

public interface BlockStateMeta extends ItemMeta
{
    boolean hasBlockState();
    
    BlockState getBlockState();
    
    void setBlockState(final BlockState p0);
}
