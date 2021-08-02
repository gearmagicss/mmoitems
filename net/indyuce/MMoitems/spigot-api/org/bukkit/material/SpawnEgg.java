// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.entity.EntityType;
import org.bukkit.Material;

public class SpawnEgg extends MaterialData
{
    public SpawnEgg() {
        super(Material.MONSTER_EGG);
    }
    
    @Deprecated
    public SpawnEgg(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public SpawnEgg(final byte data) {
        super(Material.MONSTER_EGG, data);
    }
    
    public SpawnEgg(final EntityType type) {
        this();
        this.setSpawnedType(type);
    }
    
    @Deprecated
    public EntityType getSpawnedType() {
        return EntityType.fromId(this.getData());
    }
    
    @Deprecated
    public void setSpawnedType(final EntityType type) {
        this.setData((byte)type.getTypeId());
    }
    
    @Override
    public String toString() {
        return "SPAWN EGG{" + this.getSpawnedType() + "}";
    }
    
    @Override
    public SpawnEgg clone() {
        return (SpawnEgg)super.clone();
    }
}
