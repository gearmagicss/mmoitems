// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class RedstoneTorch extends Torch implements Redstone
{
    public RedstoneTorch() {
        super(Material.REDSTONE_TORCH_ON);
    }
    
    @Deprecated
    public RedstoneTorch(final int type) {
        super(type);
    }
    
    public RedstoneTorch(final Material type) {
        super(type);
    }
    
    @Deprecated
    public RedstoneTorch(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public RedstoneTorch(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isPowered() {
        return this.getItemType() == Material.REDSTONE_TORCH_ON;
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }
    
    @Override
    public RedstoneTorch clone() {
        return (RedstoneTorch)super.clone();
    }
}
