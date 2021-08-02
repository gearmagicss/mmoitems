// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Instrument
{
    PIANO("PIANO", 0, 0), 
    BASS_DRUM("BASS_DRUM", 1, 1), 
    SNARE_DRUM("SNARE_DRUM", 2, 2), 
    STICKS("STICKS", 3, 3), 
    BASS_GUITAR("BASS_GUITAR", 4, 4);
    
    private final byte type;
    private static final Map<Byte, Instrument> BY_DATA;
    
    static {
        BY_DATA = Maps.newHashMap();
        Instrument[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Instrument instrument = values[i];
            Instrument.BY_DATA.put(instrument.getType(), instrument);
        }
    }
    
    private Instrument(final String name, final int ordinal, final int type) {
        this.type = (byte)type;
    }
    
    @Deprecated
    public byte getType() {
        return this.type;
    }
    
    @Deprecated
    public static Instrument getByType(final byte type) {
        return Instrument.BY_DATA.get(type);
    }
}
