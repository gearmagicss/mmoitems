// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.SkullType;
import org.bukkit.OfflinePlayer;

public interface Skull extends BlockState
{
    boolean hasOwner();
    
    @Deprecated
    String getOwner();
    
    @Deprecated
    boolean setOwner(final String p0);
    
    OfflinePlayer getOwningPlayer();
    
    void setOwningPlayer(final OfflinePlayer p0);
    
    BlockFace getRotation();
    
    void setRotation(final BlockFace p0);
    
    SkullType getSkullType();
    
    void setSkullType(final SkullType p0);
}
