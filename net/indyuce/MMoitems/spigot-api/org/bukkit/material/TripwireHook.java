// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.block.BlockFace;
import org.bukkit.Material;

public class TripwireHook extends SimpleAttachableMaterialData implements Redstone
{
    public TripwireHook() {
        super(Material.TRIPWIRE_HOOK);
    }
    
    @Deprecated
    public TripwireHook(final int type) {
        super(type);
    }
    
    @Deprecated
    public TripwireHook(final int type, final byte data) {
        super(type, data);
    }
    
    public TripwireHook(final BlockFace dir) {
        this();
        this.setFacingDirection(dir);
    }
    
    public boolean isConnected() {
        return (this.getData() & 0x4) != 0x0;
    }
    
    public void setConnected(final boolean connected) {
        int dat = this.getData() & 0xB;
        if (connected) {
            dat |= 0x4;
        }
        this.setData((byte)dat);
    }
    
    public boolean isActivated() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setActivated(final boolean act) {
        int dat = this.getData() & 0x7;
        if (act) {
            dat |= 0x8;
        }
        this.setData((byte)dat);
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        int dat = this.getData() & 0xC;
        switch (face) {
            case WEST: {
                dat |= 0x1;
                break;
            }
            case NORTH: {
                dat |= 0x2;
                break;
            }
            case EAST: {
                dat |= 0x3;
                break;
            }
        }
        this.setData((byte)dat);
    }
    
    @Override
    public BlockFace getAttachedFace() {
        switch (this.getData() & 0x3) {
            case 0: {
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
            default: {
                return null;
            }
        }
    }
    
    @Override
    public boolean isPowered() {
        return this.isActivated();
    }
    
    @Override
    public TripwireHook clone() {
        return (TripwireHook)super.clone();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing() + (this.isActivated() ? " Activated" : "") + (this.isConnected() ? " Connected" : "");
    }
}
