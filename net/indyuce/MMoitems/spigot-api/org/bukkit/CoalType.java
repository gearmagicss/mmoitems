// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum CoalType
{
    COAL("COAL", 0, 0), 
    CHARCOAL("CHARCOAL", 1, 1);
    
    private final byte data;
    private static final Map<Byte, CoalType> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        CoalType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final CoalType type = values[i];
            CoalType.BY_DATA.put(type.data, type);
        }
    }
    
    private CoalType(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static CoalType getByData(final byte data) {
        return CoalType.BY_DATA.get(data);
    }
}
