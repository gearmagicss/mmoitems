// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.map;

import java.util.List;
import org.bukkit.World;

public interface MapView
{
    @Deprecated
    short getId();
    
    boolean isVirtual();
    
    Scale getScale();
    
    void setScale(final Scale p0);
    
    int getCenterX();
    
    int getCenterZ();
    
    void setCenterX(final int p0);
    
    void setCenterZ(final int p0);
    
    World getWorld();
    
    void setWorld(final World p0);
    
    List<MapRenderer> getRenderers();
    
    void addRenderer(final MapRenderer p0);
    
    boolean removeRenderer(final MapRenderer p0);
    
    public enum Scale
    {
        CLOSEST("CLOSEST", 0, 0), 
        CLOSE("CLOSE", 1, 1), 
        NORMAL("NORMAL", 2, 2), 
        FAR("FAR", 3, 3), 
        FARTHEST("FARTHEST", 4, 4);
        
        private byte value;
        
        private Scale(final String name, final int ordinal, final int value) {
            this.value = (byte)value;
        }
        
        @Deprecated
        public static Scale valueOf(final byte value) {
            switch (value) {
                case 0: {
                    return Scale.CLOSEST;
                }
                case 1: {
                    return Scale.CLOSE;
                }
                case 2: {
                    return Scale.NORMAL;
                }
                case 3: {
                    return Scale.FAR;
                }
                case 4: {
                    return Scale.FARTHEST;
                }
                default: {
                    return null;
                }
            }
        }
        
        @Deprecated
        public byte getValue() {
            return this.value;
        }
    }
}
