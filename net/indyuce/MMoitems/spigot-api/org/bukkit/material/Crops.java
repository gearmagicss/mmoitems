// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.CropState;
import org.bukkit.Material;

public class Crops extends MaterialData
{
    protected static final Material DEFAULT_TYPE;
    protected static final CropState DEFAULT_STATE;
    
    static {
        DEFAULT_TYPE = Material.CROPS;
        DEFAULT_STATE = CropState.SEEDED;
    }
    
    public Crops() {
        this(Crops.DEFAULT_TYPE, Crops.DEFAULT_STATE);
    }
    
    public Crops(final CropState state) {
        this(Crops.DEFAULT_TYPE, state);
        this.setState(state);
    }
    
    public Crops(final Material type, final CropState state) {
        super(type);
        this.setState(state);
    }
    
    @Deprecated
    public Crops(final int type) {
        super(type);
    }
    
    public Crops(final Material type) {
        this(type, Crops.DEFAULT_STATE);
    }
    
    @Deprecated
    public Crops(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Crops(final Material type, final byte data) {
        super(type, data);
    }
    
    public CropState getState() {
        switch (this.getItemType()) {
            case CROPS:
            case CARROT:
            case POTATO: {
                return CropState.getByData((byte)(this.getData() & 0x7));
            }
            case NETHER_WARTS:
            case BEETROOT_BLOCK: {
                return CropState.getByData((byte)(((this.getData() & 0x3) * 7 + 2) / 3));
            }
            default: {
                throw new IllegalArgumentException("Block type is not a crop");
            }
        }
    }
    
    public void setState(final CropState state) {
        switch (this.getItemType()) {
            case CROPS:
            case CARROT:
            case POTATO: {
                this.setData((byte)((this.getData() & 0x8) | state.getData()));
                break;
            }
            case NETHER_WARTS:
            case BEETROOT_BLOCK: {
                this.setData((byte)((this.getData() & 0xC) | state.getData() >> 1));
                break;
            }
            default: {
                throw new IllegalArgumentException("Block type is not a crop");
            }
        }
    }
    
    @Override
    public String toString() {
        return this.getState() + " " + super.toString();
    }
    
    @Override
    public Crops clone() {
        return (Crops)super.clone();
    }
}
