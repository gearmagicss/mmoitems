// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Bed extends MaterialData implements Directional
{
    public Bed() {
        super(Material.BED_BLOCK);
    }
    
    public Bed(final BlockFace direction) {
        this();
        this.setFacingDirection(direction);
    }
    
    @Deprecated
    public Bed(final int type) {
        super(type);
    }
    
    public Bed(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Bed(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Bed(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isHeadOfBed() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setHeadOfBed(final boolean isHeadOfBed) {
        this.setData((byte)(isHeadOfBed ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case SOUTH: {
                data = 0;
                break;
            }
            case WEST: {
                data = 1;
                break;
            }
            case NORTH: {
                data = 2;
                break;
            }
            default: {
                data = 3;
                break;
            }
        }
        if (this.isHeadOfBed()) {
            data |= 0x8;
        }
        this.setData(data);
    }
    
    @Override
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x7);
        switch (data) {
            case 0: {
                return BlockFace.SOUTH;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            default: {
                return BlockFace.EAST;
            }
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.isHeadOfBed() ? "HEAD" : "FOOT") + " of " + super.toString() + " facing " + this.getFacing();
    }
    
    @Override
    public Bed clone() {
        return (Bed)super.clone();
    }
}
