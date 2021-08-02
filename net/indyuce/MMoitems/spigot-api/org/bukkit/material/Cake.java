// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class Cake extends MaterialData
{
    public Cake() {
        super(Material.CAKE_BLOCK);
    }
    
    @Deprecated
    public Cake(final int type) {
        super(type);
    }
    
    public Cake(final Material type) {
        super(type);
    }
    
    @Deprecated
    public Cake(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Cake(final Material type, final byte data) {
        super(type, data);
    }
    
    public int getSlicesEaten() {
        return this.getData();
    }
    
    public int getSlicesRemaining() {
        return 6 - this.getData();
    }
    
    public void setSlicesEaten(final int n) {
        if (n < 6) {
            this.setData((byte)n);
        }
    }
    
    public void setSlicesRemaining(int n) {
        if (n > 6) {
            n = 6;
        }
        this.setData((byte)(6 - n));
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " " + this.getSlicesEaten() + "/" + this.getSlicesRemaining() + " slices eaten/remaining";
    }
    
    @Override
    public Cake clone() {
        return (Cake)super.clone();
    }
}
