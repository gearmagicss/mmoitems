// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class ExtendedRails extends Rails
{
    @Deprecated
    public ExtendedRails(final int type) {
        super(type);
    }
    
    public ExtendedRails(final Material type) {
        super(type);
    }
    
    @Deprecated
    public ExtendedRails(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public ExtendedRails(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isCurve() {
        return false;
    }
    
    @Deprecated
    @Override
    protected byte getConvertedData() {
        return (byte)(this.getData() & 0x7);
    }
    
    @Override
    public void setDirection(final BlockFace face, final boolean isOnSlope) {
        final boolean extraBitSet = (this.getData() & 0x8) == 0x8;
        if (face != BlockFace.WEST && face != BlockFace.EAST && face != BlockFace.NORTH && face != BlockFace.SOUTH) {
            throw new IllegalArgumentException("Detector rails and powered rails cannot be set on a curve!");
        }
        super.setDirection(face, isOnSlope);
        this.setData((byte)(extraBitSet ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Override
    public ExtendedRails clone() {
        return (ExtendedRails)super.clone();
    }
}
