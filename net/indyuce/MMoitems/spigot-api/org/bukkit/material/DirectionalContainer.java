// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class DirectionalContainer extends MaterialData implements Directional
{
    @Deprecated
    public DirectionalContainer(final int type) {
        super(type);
    }
    
    public DirectionalContainer(final Material type) {
        super(type);
    }
    
    @Deprecated
    public DirectionalContainer(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public DirectionalContainer(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        byte data = 0;
        switch (face) {
            case NORTH: {
                data = 2;
                break;
            }
            case SOUTH: {
                data = 3;
                break;
            }
            case WEST: {
                data = 4;
                break;
            }
            default: {
                data = 5;
                break;
            }
        }
        this.setData(data);
    }
    
    @Override
    public BlockFace getFacing() {
        final byte data = this.getData();
        switch (data) {
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
            case 4: {
                return BlockFace.WEST;
            }
            default: {
                return BlockFace.EAST;
            }
        }
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing();
    }
    
    @Override
    public DirectionalContainer clone() {
        return (DirectionalContainer)super.clone();
    }
}
