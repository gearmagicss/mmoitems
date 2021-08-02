// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Door extends MaterialData implements Directional, Openable
{
    @Deprecated
    public Door() {
        super(Material.WOODEN_DOOR);
    }
    
    @Deprecated
    public Door(final int type) {
        super(type);
    }
    
    public Door(final Material type) {
        super(type);
    }
    
    public Door(final Material type, final BlockFace face) {
        this(type, face, false);
    }
    
    public Door(final Material type, final BlockFace face, final boolean isOpen) {
        super(type);
        this.setTopHalf(false);
        this.setFacingDirection(face);
        this.setOpen(isOpen);
    }
    
    public Door(final Material type, final boolean isHingeRight) {
        super(type);
        this.setTopHalf(true);
        this.setHinge(isHingeRight);
    }
    
    public Door(final TreeSpecies species, final BlockFace face) {
        this(getWoodDoorOfSpecies(species), face, false);
    }
    
    public Door(final TreeSpecies species, final BlockFace face, final boolean isOpen) {
        this(getWoodDoorOfSpecies(species), face, isOpen);
    }
    
    public Door(final TreeSpecies species, final boolean isHingeRight) {
        this(getWoodDoorOfSpecies(species), isHingeRight);
    }
    
    @Deprecated
    public Door(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Door(final Material type, final byte data) {
        super(type, data);
    }
    
    public static Material getWoodDoorOfSpecies(final TreeSpecies species) {
        switch (species) {
            default: {
                return Material.WOODEN_DOOR;
            }
            case BIRCH: {
                return Material.BIRCH_DOOR;
            }
            case REDWOOD: {
                return Material.SPRUCE_DOOR;
            }
            case JUNGLE: {
                return Material.JUNGLE_DOOR;
            }
            case ACACIA: {
                return Material.ACACIA_DOOR;
            }
            case DARK_OAK: {
                return Material.DARK_OAK_DOOR;
            }
        }
    }
    
    @Override
    public boolean isOpen() {
        return (this.getData() & 0x4) == 0x4;
    }
    
    @Override
    public void setOpen(final boolean isOpen) {
        this.setData((byte)(isOpen ? (this.getData() | 0x4) : (this.getData() & 0xFFFFFFFB)));
    }
    
    public boolean isTopHalf() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setTopHalf(final boolean isTopHalf) {
        this.setData((byte)(isTopHalf ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Deprecated
    public BlockFace getHingeCorner() {
        return BlockFace.SELF;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.isTopHalf() ? "TOP" : "BOTTOM") + " half of " + super.toString();
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0xC);
        switch (face) {
            case WEST: {
                data |= 0x0;
                break;
            }
            case NORTH: {
                data |= 0x1;
                break;
            }
            case EAST: {
                data |= 0x2;
                break;
            }
            case SOUTH: {
                data |= 0x3;
                break;
            }
        }
        this.setData(data);
    }
    
    @Override
    public BlockFace getFacing() {
        final byte data = (byte)(this.getData() & 0x3);
        switch (data) {
            case 0: {
                return BlockFace.WEST;
            }
            case 1: {
                return BlockFace.NORTH;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            default: {
                throw new IllegalStateException("Unknown door facing (data: " + data + ")");
            }
        }
    }
    
    public boolean getHinge() {
        return (this.getData() & 0x1) == 0x1;
    }
    
    public void setHinge(final boolean isHingeRight) {
        this.setData((byte)(isHingeRight ? (this.getData() | 0x1) : (this.getData() & 0xFFFFFFFE)));
    }
    
    @Override
    public Door clone() {
        return (Door)super.clone();
    }
}
