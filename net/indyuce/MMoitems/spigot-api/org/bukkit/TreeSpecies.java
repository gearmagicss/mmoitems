// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum TreeSpecies
{
    GENERIC("GENERIC", 0, 0), 
    REDWOOD("REDWOOD", 1, 1), 
    BIRCH("BIRCH", 2, 2), 
    JUNGLE("JUNGLE", 3, 3), 
    ACACIA("ACACIA", 4, 4), 
    DARK_OAK("DARK_OAK", 5, 5);
    
    private final byte data;
    private static final Map<Byte, TreeSpecies> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        TreeSpecies[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final TreeSpecies species = values[i];
            TreeSpecies.BY_DATA.put(species.data, species);
        }
    }
    
    private TreeSpecies(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static TreeSpecies getByData(final byte data) {
        return TreeSpecies.BY_DATA.get(data);
    }
}
