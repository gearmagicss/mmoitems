// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.entity.EntityType;

public interface CreatureSpawner extends BlockState
{
    EntityType getSpawnedType();
    
    void setSpawnedType(final EntityType p0);
    
    void setCreatureTypeByName(final String p0);
    
    String getCreatureTypeName();
    
    int getDelay();
    
    void setDelay(final int p0);
}
