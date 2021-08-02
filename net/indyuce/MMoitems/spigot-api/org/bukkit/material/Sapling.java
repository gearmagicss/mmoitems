// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;

public class Sapling extends Wood
{
    public Sapling() {
        this(Sapling.DEFAULT_SPECIES);
    }
    
    public Sapling(final TreeSpecies species) {
        this(species, false);
    }
    
    public Sapling(final TreeSpecies species, final boolean isInstantGrowable) {
        this(Material.SAPLING, species, isInstantGrowable);
    }
    
    public Sapling(final Material type) {
        this(type, Sapling.DEFAULT_SPECIES, false);
    }
    
    public Sapling(final Material type, final TreeSpecies species) {
        this(type, species, false);
    }
    
    public Sapling(final Material type, final TreeSpecies species, final boolean isInstantGrowable) {
        super(type, species);
        this.setIsInstantGrowable(isInstantGrowable);
    }
    
    @Deprecated
    public Sapling(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Sapling(final Material type, final byte data) {
        super(type, data);
    }
    
    public boolean isInstantGrowable() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setIsInstantGrowable(final boolean isInstantGrowable) {
        this.setData(isInstantGrowable ? ((byte)((this.getData() & 0x7) | 0x8)) : ((byte)(this.getData() & 0x7)));
    }
    
    @Override
    public String toString() {
        return this.getSpecies() + " " + (this.isInstantGrowable() ? " IS_INSTANT_GROWABLE " : "") + " " + super.toString();
    }
    
    @Override
    public Sapling clone() {
        return (Sapling)super.clone();
    }
}
