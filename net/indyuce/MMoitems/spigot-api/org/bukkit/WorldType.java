// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum WorldType
{
    NORMAL("NORMAL", 0, "DEFAULT"), 
    FLAT("FLAT", 1, "FLAT"), 
    VERSION_1_1("VERSION_1_1", 2, "DEFAULT_1_1"), 
    LARGE_BIOMES("LARGE_BIOMES", 3, "LARGEBIOMES"), 
    AMPLIFIED("AMPLIFIED", 4, "AMPLIFIED"), 
    CUSTOMIZED("CUSTOMIZED", 5, "CUSTOMIZED");
    
    private static final Map<String, WorldType> BY_NAME;
    private final String name;
    
    static {
        BY_NAME = Maps.newHashMap();
        WorldType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final WorldType type = values[i];
            WorldType.BY_NAME.put(type.name, type);
        }
    }
    
    private WorldType(final String name2, final int ordinal, final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static WorldType getByName(final String name) {
        return WorldType.BY_NAME.get(name.toUpperCase());
    }
}
