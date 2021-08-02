// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class Diode extends MaterialData implements Directional, Redstone
{
    protected static final BlockFace DEFAULT_DIRECTION;
    protected static final int DEFAULT_DELAY = 1;
    protected static final boolean DEFAULT_STATE = false;
    
    static {
        DEFAULT_DIRECTION = BlockFace.NORTH;
    }
    
    public Diode() {
        this(Diode.DEFAULT_DIRECTION, 1, true);
    }
    
    public Diode(final BlockFace facingDirection) {
        this(facingDirection, 1, false);
    }
    
    public Diode(final BlockFace facingDirection, final int delay) {
        this(facingDirection, delay, false);
    }
    
    public Diode(final BlockFace facingDirection, final int delay, final boolean state) {
        super(state ? Material.DIODE_BLOCK_ON : Material.DIODE_BLOCK_OFF);
        this.setFacingDirection(facingDirection);
        this.setDelay(delay);
    }
    
    @Deprecated
    public Diode(final int type) {
        super(type);
    }
    
    public Diode(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Diode(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Diode(final Material type, final byte data) {
        super(type, data);
    }
    
    public void setDelay(int delay) {
        if (delay > 4) {
            delay = 4;
        }
        if (delay < 1) {
            delay = 1;
        }
        final byte newData = (byte)(this.getData() & 0x3);
        this.setData((byte)(newData | delay - 1 << 2));
    }
    
    public int getDelay() {
        return (this.getData() >> 2) + 1;
    }
    
    @Override
    public void setFacingDirection(final BlockFace face) {
        final int delay = this.getDelay();
        byte data = 0;
        switch (face) {
            case EAST: {
                data = 1;
                break;
            }
            case SOUTH: {
                data = 2;
                break;
            }
            case WEST: {
                data = 3;
                break;
            }
            default: {
                data = 0;
                break;
            }
        }
        this.setData(data);
        this.setDelay(delay);
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
        return String.valueOf(super.toString()) + " facing " + this.getFacing() + " with " + this.getDelay() + " ticks delay";
    }
    
    @Override
    public Diode clone() {
        return (Diode)super.clone();
    }
    
    @Override
    public boolean isPowered() {
        return this.getItemType() == Material.DIODE_BLOCK_ON;
    }
}
