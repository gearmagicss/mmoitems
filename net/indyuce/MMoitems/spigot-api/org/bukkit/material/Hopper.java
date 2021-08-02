// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class Hopper extends MaterialData implements Directional, Redstone
{
    protected static final BlockFace DEFAULT_DIRECTION;
    protected static final boolean DEFAULT_ACTIVE = true;
    
    static {
        DEFAULT_DIRECTION = BlockFace.DOWN;
    }
    
    public Hopper() {
        this(Hopper.DEFAULT_DIRECTION, true);
    }
    
    public Hopper(final BlockFace facingDirection) {
        this(facingDirection, true);
    }
    
    public Hopper(final BlockFace facingDirection, final boolean isActive) {
        super(Material.HOPPER);
        this.setFacingDirection(facingDirection);
        this.setActive(isActive);
    }
    
    @Deprecated
    public Hopper(final int type) {
        super(type);
    }
    
    public Hopper(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Hopper(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Hopper(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setActive(final boolean isActive) {
        this.setData((byte)((this.getData() & 0x7) | (isActive ? 0 : 8)));
    }
    
    public boolean isActive() {
        return (this.getData() & 0x8) == 0x0;
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        int data = this.getData() & 0x8;
        switch (face) {
            case DOWN: {
                data |= 0x0;
                break;
            }
            case NORTH: {
                data |= 0x2;
                break;
            }
            case SOUTH: {
                data |= 0x3;
                break;
            }
            case WEST: {
                data |= 0x4;
                break;
            }
            case EAST: {
                data |= 0x5;
                break;
            }
        }
        this.setData((byte)data);
    }
    
    @Override
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x7);
        switch (data) {
            default: {
                return BlockFace.DOWN;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            case 4: {
                return BlockFace.WEST;
            }
            case 5: {
                return BlockFace.EAST;
            }
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing();
    }
    
    @Override
    public Hopper clone() {
        return (Hopper)super.clone();
    }
    
    @Override
    public boolean isPowered() {
        return (this.getData() & 0x8) != 0x0;
    }
}
