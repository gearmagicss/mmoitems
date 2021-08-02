// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class WoodenStep extends Wood
{
    protected static final Material DEFAULT_TYPE;
    protected static final boolean DEFAULT_INVERTED = false;
    
    static {
        DEFAULT_TYPE = Material.WOOD_STEP;
    }
    
    public WoodenStep() {
        this(WoodenStep.DEFAULT_SPECIES, false);
    }
    
    public WoodenStep(final TreeSpecies species) {
        this(species, false);
    }
    
    public WoodenStep(final TreeSpecies species, final boolean inv) {
        super(WoodenStep.DEFAULT_TYPE, species);
        this.setInverted(inv);
    }
    
    @Deprecated
    public WoodenStep(final int type) {
        super(type);
    }
    
    @Deprecated
    public WoodenStep(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public WoodenStep(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isInverted() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setInverted(final boolean inv) {
        int dat = this.getData() & 0x7;
        if (inv) {
            dat |= 0x8;
        }
        this.setData((byte)dat);
    }
    
    @Override
    public WoodenStep clone() {
        return (WoodenStep)super.clone();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " " + this.getSpecies() + (this.isInverted() ? " inverted" : "");
    }
}
