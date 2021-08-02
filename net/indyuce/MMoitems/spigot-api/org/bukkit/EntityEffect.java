// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum EntityEffect
{
    HURT("HURT", 0, 2), 
    DEATH("DEATH", 1, 3), 
    WOLF_SMOKE("WOLF_SMOKE", 2, 6), 
    WOLF_HEARTS("WOLF_HEARTS", 3, 7), 
    WOLF_SHAKE("WOLF_SHAKE", 4, 8), 
    SHEEP_EAT("SHEEP_EAT", 5, 10), 
    IRON_GOLEM_ROSE("IRON_GOLEM_ROSE", 6, 11), 
    VILLAGER_HEART("VILLAGER_HEART", 7, 12), 
    VILLAGER_ANGRY("VILLAGER_ANGRY", 8, 13), 
    VILLAGER_HAPPY("VILLAGER_HAPPY", 9, 14), 
    WITCH_MAGIC("WITCH_MAGIC", 10, 15), 
    ZOMBIE_TRANSFORM("ZOMBIE_TRANSFORM", 11, 16), 
    FIREWORK_EXPLODE("FIREWORK_EXPLODE", 12, 17);
    
    private final byte data;
    private static final Map<Byte, EntityEffect> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        EntityEffect[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final EntityEffect entityEffect = values[i];
            EntityEffect.BY_DATA.put(entityEffect.data, entityEffect);
        }
    }
    
    private EntityEffect(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static EntityEffect getByData(final byte data) {
        return EntityEffect.BY_DATA.get(data);
    }
}
