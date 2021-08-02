// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.GrassSpecies;
import org.bukkit.Material;

public class LongGrass extends MaterialData
{
    public LongGrass() {
        super(Material.LONG_GRASS);
    }
    
    public LongGrass(final GrassSpecies species) {
        this();
        this.setSpecies(species);
    }
    
    @Deprecated
    public LongGrass(final int type) {
        super(type);
    }
    
    public LongGrass(final Material type) {
        super(type);
    }
    
    @Deprecated
    public LongGrass(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public LongGrass(final Material type, final byte data) {
        super(type, data);
    }
    
    public GrassSpecies getSpecies() {
        return GrassSpecies.getByData(this.getData());
    }
    
    public void setSpecies(final GrassSpecies species) {
        this.setData(species.getData());
    }
    
    @Override
    public String toString() {
        return this.getSpecies() + " " + super.toString();
    }
    
    @Override
    public LongGrass clone() {
        return (LongGrass)super.clone();
    }
}
