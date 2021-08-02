// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.TreeSpecies;
import org.bukkit.Material;

public class Leaves extends Wood
{
    protected static final Material DEFAULT_TYPE;
    protected static final boolean DEFAULT_DECAYABLE = true;
    
    static {
        DEFAULT_TYPE = Material.LEAVES;
    }
    
    public Leaves() {
        this(Leaves.DEFAULT_TYPE, Leaves.DEFAULT_SPECIES, true);
    }
    
    public Leaves(final TreeSpecies species) {
        this(Leaves.DEFAULT_TYPE, species, true);
    }
    
    public Leaves(final TreeSpecies species, final boolean isDecayable) {
        this(Leaves.DEFAULT_TYPE, species, isDecayable);
    }
    
    @Deprecated
    public Leaves(final int type) {
        super(type);
    }
    
    public Leaves(final Material type) {
        this(type, Leaves.DEFAULT_SPECIES, true);
    }
    
    public Leaves(final Material type, final TreeSpecies species) {
        this(type, species, true);
    }
    
    public Leaves(final Material type, final TreeSpecies species, final boolean isDecayable) {
        super(type, species);
        this.setDecayable(isDecayable);
    }
    
    @Deprecated
    public Leaves(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Leaves(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isDecaying() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setDecaying(final boolean isDecaying) {
        this.setData((byte)((this.getData() & 0x3) | (isDecaying ? 8 : (this.getData() & 0x4))));
    }
    
    public boolean isDecayable() {
        return (this.getData() & 0x4) == 0x0;
    }
    
    public void setDecayable(final boolean isDecayable) {
        this.setData((byte)((this.getData() & 0x3) | (isDecayable ? (this.getData() & 0x8) : 4)));
    }
    
    @Override
    public String toString() {
        return this.getSpecies() + (this.isDecayable() ? " DECAYABLE " : " PERMANENT ") + (this.isDecaying() ? " DECAYING " : " ") + super.toString();
    }
    
    @Override
    public Leaves clone() {
        return (Leaves)super.clone();
    }
}
