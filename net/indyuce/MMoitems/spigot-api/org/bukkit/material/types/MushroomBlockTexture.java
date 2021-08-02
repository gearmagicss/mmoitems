// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material.types;

import com.google.common.collect.Maps;
import org.bukkit.block.BlockFace;
import java.util.Map;

public enum MushroomBlockTexture
{
    ALL_PORES("ALL_PORES", 0, 0, (BlockFace)null), 
    CAP_NORTH_WEST("CAP_NORTH_WEST", 1, 1, BlockFace.NORTH_WEST), 
    CAP_NORTH("CAP_NORTH", 2, 2, BlockFace.NORTH), 
    CAP_NORTH_EAST("CAP_NORTH_EAST", 3, 3, BlockFace.NORTH_EAST), 
    CAP_WEST("CAP_WEST", 4, 4, BlockFace.WEST), 
    CAP_TOP("CAP_TOP", 5, 5, BlockFace.UP), 
    CAP_EAST("CAP_EAST", 6, 6, BlockFace.EAST), 
    CAP_SOUTH_WEST("CAP_SOUTH_WEST", 7, 7, BlockFace.SOUTH_WEST), 
    CAP_SOUTH("CAP_SOUTH", 8, 8, BlockFace.SOUTH), 
    CAP_SOUTH_EAST("CAP_SOUTH_EAST", 9, 9, BlockFace.SOUTH_EAST), 
    STEM_SIDES("STEM_SIDES", 10, 10, (BlockFace)null), 
    ALL_CAP("ALL_CAP", 11, 14, BlockFace.SELF), 
    ALL_STEM("ALL_STEM", 12, 15, (BlockFace)null);
    
    private static final Map<Byte, MushroomBlockTexture> BY_DATA;
    private static final Map<BlockFace, MushroomBlockTexture> BY_BLOCKFACE;
    private final Byte data;
    private final BlockFace capFace;
    
    static {
        BY_DATA = Maps.newHashMap();
        BY_BLOCKFACE = Maps.newHashMap();
        MushroomBlockTexture[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final MushroomBlockTexture type = values[i];
            MushroomBlockTexture.BY_DATA.put(type.data, type);
            MushroomBlockTexture.BY_BLOCKFACE.put(type.capFace, type);
        }
    }
    
    private MushroomBlockTexture(final String name, final int ordinal, final int data, final BlockFace capFace) {
        this.data = (byte)data;
        this.capFace = capFace;
    }
    
    @Deprecated
    public byte getData() {
        return this.data;
    }
    
    public BlockFace getCapFace() {
        return this.capFace;
    }
    
    @Deprecated
    public static MushroomBlockTexture getByData(final byte data) {
        return MushroomBlockTexture.BY_DATA.get(data);
    }
    
    public static MushroomBlockTexture getCapByFace(final BlockFace face) {
        return MushroomBlockTexture.BY_BLOCKFACE.get(face);
    }
}
