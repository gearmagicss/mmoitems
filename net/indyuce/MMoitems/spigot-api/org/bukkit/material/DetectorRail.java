// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class DetectorRail extends ExtendedRails implements PressureSensor
{
    public DetectorRail() {
        super(Material.DETECTOR_RAIL);
    }
    
    @Deprecated
    public DetectorRail(final int type) {
        super(type);
    }
    
    public DetectorRail(final Material type) {
        super(type);
    }
    
    @Deprecated
    public DetectorRail(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public DetectorRail(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public boolean isPressed() {
        return (this.getData() & 0x8) == 0x8;
    }
    
    public void setPressed(final boolean isPressed) {
        this.setData((byte)(isPressed ? (this.getData() | 0x8) : (this.getData() & 0xFFFFFFF7)));
    }
    
    @Override
    public DetectorRail clone() {
        return (DetectorRail)super.clone();
    }
}
