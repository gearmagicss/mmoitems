// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum CropState
{
    SEEDED("SEEDED", 0, 0), 
    GERMINATED("GERMINATED", 1, 1), 
    VERY_SMALL("VERY_SMALL", 2, 2), 
    SMALL("SMALL", 3, 3), 
    MEDIUM("MEDIUM", 4, 4), 
    TALL("TALL", 5, 5), 
    VERY_TALL("VERY_TALL", 6, 6), 
    RIPE("RIPE", 7, 7);
    
    private final byte data;
    private static final Map<Byte, CropState> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        CropState[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final CropState cropState = values[i];
            CropState.BY_DATA.put(cropState.getData(), cropState);
        }
    }
    
    private CropState(final String name, final int ordinal, final int data) {
        this.data = (byte)data;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    @Deprecated
    public static CropState getByData(final byte data) {
        return CropState.BY_DATA.get(data);
    }
}
