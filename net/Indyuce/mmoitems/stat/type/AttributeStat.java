// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public abstract class AttributeStat extends DoubleStat
{
    private final double offset;
    private final Attribute attribute;
    
    public AttributeStat(final String s, final Material material, final String s2, final String[] array, final Attribute attribute) {
        this(s, material, s2, array, attribute, 0.0);
    }
    
    public AttributeStat(final String s, final Material material, final String s2, final String[] array, final Attribute attribute, final double offset) {
        super(s, material, s2, array, new String[] { "!consumable", "!block", "!miscellaneous", "all" }, new Material[0]);
        this.offset = offset;
        this.attribute = attribute;
    }
    
    public Attribute getAttribute() {
        return this.attribute;
    }
    
    public double getOffset() {
        return this.offset;
    }
}
