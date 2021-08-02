// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum GameMode
{
    CREATIVE("CREATIVE", 0, 1), 
    SURVIVAL("SURVIVAL", 1, 0), 
    ADVENTURE("ADVENTURE", 2, 2), 
    SPECTATOR("SPECTATOR", 3, 3);
    
    private final int value;
    private static final Map<Integer, GameMode> BY_ID;
    
    static {
        BY_ID = Maps.newHashMap();
        GameMode[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final GameMode mode = values[i];
            GameMode.BY_ID.put(mode.getValue(), mode);
        }
    }
    
    private GameMode(final String name, final int ordinal, final int value) {
        this.value = value;
    }
    
    @Deprecated
    public int getValue() {
        return this.value;
    }
    
    @Deprecated
    public static GameMode getByValue(final int value) {
        return GameMode.BY_ID.get(value);
    }
}
