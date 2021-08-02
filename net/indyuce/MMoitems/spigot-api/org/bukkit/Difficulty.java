// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Difficulty
{
    PEACEFUL("PEACEFUL", 0, 0), 
    EASY("EASY", 1, 1), 
    NORMAL("NORMAL", 2, 2), 
    HARD("HARD", 3, 3);
    
    private final int value;
    private static final Map<Integer, Difficulty> BY_ID;
    
    static {
        BY_ID = Maps.newHashMap();
        Difficulty[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Difficulty diff = values[i];
            Difficulty.BY_ID.put(diff.value, diff);
        }
    }
    
    private Difficulty(final String name, final int ordinal, final int value) {
        this.value = value;
    }
    
    @Deprecated
    public int getValue() {
        return this.value;
    }
    
    @Deprecated
    public static Difficulty getByValue(final int value) {
        return Difficulty.BY_ID.get(value);
    }
}
