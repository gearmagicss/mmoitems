// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class ItemSpawnEvent extends EntitySpawnEvent
{
    public ItemSpawnEvent(final Item spawnee) {
        super(spawnee);
    }
    
    @Deprecated
    public ItemSpawnEvent(final Item spawnee, final Location loc) {
        this(spawnee);
    }
    
    @Override
    public Item getEntity() {
        return (Item)this.entity;
    }
}
