// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class Cauldron extends MaterialData
{
    private static final int CAULDRON_FULL = 3;
    private static final int CAULDRON_EMPTY = 0;
    
    public Cauldron() {
        super(Material.CAULDRON);
    }
    
    @Deprecated
    public Cauldron(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Cauldron(final byte data) {
        super(Material.CAULDRON, data);
    }
    
    public boolean isFull() {
        return this.getData() >= 3;
    }
    
    public boolean isEmpty() {
        return this.getData() <= 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.isEmpty() ? "EMPTY" : (this.isFull() ? "FULL" : new StringBuilder(String.valueOf(this.getData())).append("/3 FULL").toString())) + " CAULDRON";
    }
    
    @Override
    public Cauldron clone() {
        return (Cauldron)super.clone();
    }
}
