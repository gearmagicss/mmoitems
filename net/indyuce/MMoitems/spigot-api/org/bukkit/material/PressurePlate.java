// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class PressurePlate extends MaterialData implements PressureSensor
{
    public PressurePlate() {
        super(Material.WOOD_PLATE);
    }
    
    @Deprecated
    public PressurePlate(final int type) {
        super(type);
    }
    
    public PressurePlate(final Material type) {
        super(type);
    }
    
    @Deprecated
    public PressurePlate(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public PressurePlate(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isPressed() {
        return this.getData() == 1;
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + (this.isPressed() ? " PRESSED" : "");
    }
    
    @Override
    public PressurePlate clone() {
        return (PressurePlate)super.clone();
    }
}
