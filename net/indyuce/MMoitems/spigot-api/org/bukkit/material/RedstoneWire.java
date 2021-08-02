// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class RedstoneWire extends MaterialData implements Redstone
{
    public RedstoneWire() {
        super(Material.REDSTONE_WIRE);
    }
    
    @Deprecated
    public RedstoneWire(final int type) {
        super(type);
    }
    
    public RedstoneWire(final Material type) {
        super(type);
    }
    
    @Deprecated
    public RedstoneWire(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public RedstoneWire(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isPowered() {
        return this.getData() > 0;
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    @Override
    public RedstoneWire clone() {
        return (RedstoneWire)super.clone();
    }
}
