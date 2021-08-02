// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import org.apache.commons.lang.Validate;
import com.google.common.collect.Maps;
import java.util.HashMap;

public enum Art
{
    KEBAB("KEBAB", 0, 0, 1, 1), 
    AZTEC("AZTEC", 1, 1, 1, 1), 
    ALBAN("ALBAN", 2, 2, 1, 1), 
    AZTEC2("AZTEC2", 3, 3, 1, 1), 
    BOMB("BOMB", 4, 4, 1, 1), 
    PLANT("PLANT", 5, 5, 1, 1), 
    WASTELAND("WASTELAND", 6, 6, 1, 1), 
    POOL("POOL", 7, 7, 2, 1), 
    COURBET("COURBET", 8, 8, 2, 1), 
    SEA("SEA", 9, 9, 2, 1), 
    SUNSET("SUNSET", 10, 10, 2, 1), 
    CREEBET("CREEBET", 11, 11, 2, 1), 
    WANDERER("WANDERER", 12, 12, 1, 2), 
    GRAHAM("GRAHAM", 13, 13, 1, 2), 
    MATCH("MATCH", 14, 14, 2, 2), 
    BUST("BUST", 15, 15, 2, 2), 
    STAGE("STAGE", 16, 16, 2, 2), 
    VOID("VOID", 17, 17, 2, 2), 
    SKULL_AND_ROSES("SKULL_AND_ROSES", 18, 18, 2, 2), 
    WITHER("WITHER", 19, 19, 2, 2), 
    FIGHTERS("FIGHTERS", 20, 20, 4, 2), 
    POINTER("POINTER", 21, 21, 4, 4), 
    PIGSCENE("PIGSCENE", 22, 22, 4, 4), 
    BURNINGSKULL("BURNINGSKULL", 23, 23, 4, 4), 
    SKELETON("SKELETON", 24, 24, 4, 3), 
    DONKEYKONG("DONKEYKONG", 25, 25, 4, 3);
    
    private int id;
    private int width;
    private int height;
    private static final HashMap<String, Art> BY_NAME;
    private static final HashMap<Integer, Art> BY_ID;
    
    static {
        BY_NAME = Maps.newHashMap();
        BY_ID = Maps.newHashMap();
        Art[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Art art = values[i];
            Art.BY_ID.put(art.id, art);
            Art.BY_NAME.put(art.toString().toLowerCase().replaceAll("_", ""), art);
        }
    }
    
    private Art(final String name, final int ordinal, final int id, final int width, final int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }
    
    public int getBlockWidth() {
        return this.width;
    }
    
    public int getBlockHeight() {
        return this.height;
    }
    
    @Deprecated
    public int getId() {
        return this.id;
    }
    
    @Deprecated
    public static Art getById(final int id) {
        return Art.BY_ID.get(id);
    }
    
    public static Art getByName(final String name) {
        Validate.notNull(name, "Name cannot be null");
        return Art.BY_NAME.get(name.toLowerCase().replaceAll("_", ""));
    }
}
