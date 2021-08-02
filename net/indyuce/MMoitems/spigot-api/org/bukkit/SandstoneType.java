// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SandstoneType
{
    CRACKED("CRACKED", 0, 0), 
    GLYPHED("GLYPHED", 1, 1), 
    SMOOTH("SMOOTH", 2, 2);
    
    private final byte data;
    private static final Map<Byte, SandstoneType> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        SandstoneType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final SandstoneType type = values[i];
            SandstoneType.BY_DATA.put(type.data, type);
        }
    }
    
    private SandstoneType(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static SandstoneType getByData(final byte data) {
        return SandstoneType.BY_DATA.get(data);
    }
}
