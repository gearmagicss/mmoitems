// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class Comparator extends MaterialData implements Directional, Redstone
{
    protected static final BlockFace DEFAULT_DIRECTION;
    protected static final boolean DEFAULT_SUBTRACTION_MODE = false;
    protected static final boolean DEFAULT_STATE = false;
    
    static {
        DEFAULT_DIRECTION = BlockFace.NORTH;
    }
    
    public Comparator() {
        this(Comparator.DEFAULT_DIRECTION, false, false);
    }
    
    public Comparator(final BlockFace facingDirection) {
        this(facingDirection, false, false);
    }
    
    public Comparator(final BlockFace facingDirection, final boolean isSubtraction) {
        this(facingDirection, isSubtraction, false);
    }
    
    public Comparator(final BlockFace facingDirection, final boolean isSubtraction, final boolean state) {
        super(state ? Material.REDSTONE_COMPARATOR_ON : Material.REDSTONE_COMPARATOR_OFF);
        this.setFacingDirection(facingDirection);
        this.setSubtractionMode(isSubtraction);
    }
    
    @Deprecated
    public Comparator(final int type) {
        super(type);
    }
    
    public Comparator(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Comparator(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Comparator(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setSubtractionMode(final boolean isSubtraction) {
        this.setData((byte)((this.getData() & 0xB) | (isSubtraction ? 4 : 0)));
    }
    
    public boolean isSubtractionMode() {
        return (this.getData() & 0x4) != 0x0;
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        int data = this.getData() & 0xC;
        switch (face) {
            case EAST: {
                data |= 0x1;
                break;
            }
            case SOUTH: {
                data |= 0x2;
                break;
            }
            case WEST: {
                data |= 0x3;
                break;
            }
            default: {
                data |= 0x0;
                break;
            }
        }
        this.setData((byte)data);
    }
    
    @Override
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x3);
        switch (data) {
            default: {
                return BlockFace.NORTH;
            }
            case 1: {
                return BlockFace.EAST;
            }
            case 2: {
                return BlockFace.SOUTH;
            }
            case 3: {
                return BlockFace.WEST;
            }
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing() + " in " + (this.isSubtractionMode() ? "subtraction" : "comparator") + " mode";
    }
    
    @Override
    public Comparator clone() {
        return (Comparator)super.clone();
    }
    
    @Override
    public boolean isPowered() {
        return this.getItemType() == Material.REDSTONE_COMPARATOR_ON;
    }
    
    public boolean isBeingPowered() {
        return (this.getData() & 0x8) != 0x0;
    }
}
