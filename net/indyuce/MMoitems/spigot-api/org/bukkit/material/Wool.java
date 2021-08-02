// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Wool extends MaterialData implements Colorable
{
    public Wool() {
        super(Material.WOOL);
    }
    
    public Wool(final DyeColor color) {
        this();
        this.setColor(color);
    }
    
    @Deprecated
    public Wool(final int type) {
        super(type);
    }
    
    public Wool(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Wool(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Wool(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public DyeColor getColor() {
        return DyeColor.getByWoolData(this.getData());
    }
    
    @Override
    public void setColor(final DyeColor color) {
        this.setData(color.getWoolData());
    }
    
    @Override
    public String toString() {
        return this.getColor() + " " + super.toString();
    }
    
    @Override
    public Wool clone() {
        return (Wool)super.clone();
    }
}
