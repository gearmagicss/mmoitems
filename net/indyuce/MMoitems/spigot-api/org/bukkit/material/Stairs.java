// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Stairs extends MaterialData implements Directional
{
    @Deprecated
    public Stairs(final int type) {
        super(type);
    }
    
    public Stairs(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Stairs(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Stairs(final Material type, final byte data) {
        super(type, data);
    }
    
    public BlockFace getAscendingDirection() {
        final byte data = this.getData();
        switch (data & 0x3) {
            default: {
                return BlockFace.EAST;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.SOUTH;
            }
            case 3: {
                return BlockFace.NORTH;
            }
        }
    }
    
    public BlockFace getDescendingDirection() {
        return this.getAscendingDirection().getOppositeFace();
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case NORTH: {
                data = 3;
                break;
            }
            case SOUTH: {
                data = 2;
                break;
            }
            default: {
                data = 0;
                break;
            }
            case WEST: {
                data = 1;
                break;
            }
        }
        this.setData((byte)((this.getData() & 0xC) | data));
    }
    
    @Override
    public BlockFace getFacing() {
        return this.getDescendingDirection();
    }
    
    public boolean isInverted() {
        return (this.getData() & 0x4) != 0x0;
    }
    
    public void setInverted(final boolean inv) {
        int dat = this.getData() & 0x3;
        if (inv) {
            dat |= 0x4;
        }
        this.setData((byte)dat);
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing() + (this.isInverted() ? " inverted" : "");
    }
    
    @Override
    public Stairs clone() {
        return (Stairs)super.clone();
    }
}
