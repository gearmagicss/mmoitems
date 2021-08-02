// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class Button extends SimpleAttachableMaterialData implements Redstone
{
    public Button() {
        super(Material.STONE_BUTTON);
    }
    
    @Deprecated
    public Button(final int type) {
        super(type);
    }
    
    public Button(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Button(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Button(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isPowered() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setPowered(final boolean bool) {
        this.setData((byte)(bool ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Override
    public BlockFace getAttachedFace() {
        final byte data = (byte)(this.getData() & 0x7);
        switch (data) {
            case 0: {
                return BlockFace.UP;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            case 4: {
                return BlockFace.SOUTH;
            }
            case 5: {
                return BlockFace.DOWN;
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        byte data = (byte)(this.getData() & 0x8);
        switch (face) {
            case DOWN: {
                data |= 0x0;
                break;
            }
            case EAST: {
                data |= 0x1;
                break;
            }
            case WEST: {
                data |= 0x2;
                break;
            }
            case SOUTH: {
                data |= 0x3;
                break;
            }
            case NORTH: {
                data |= 0x4;
                break;
            }
            case UP: {
                data |= 0x5;
                break;
            }
        }
        this.setData(data);
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    @Override
    public Button clone() {
        return (Button)super.clone();
    }
}
