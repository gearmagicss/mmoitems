// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public abstract class SimpleAttachableMaterialData extends MaterialData implements Attachable
{
    @Deprecated
    public SimpleAttachableMaterialData(final int type) {
        super(type);
    }
    
    public SimpleAttachableMaterialData(final int type, final BlockFace direction) {
        this(type);
        this.setFacingDirection(direction);
    }
    
    public SimpleAttachableMaterialData(final Material type, final BlockFace direction) {
        this(type);
        this.setFacingDirection(direction);
    }
    
    public SimpleAttachableMaterialData(final Material type) {
        super(type);
    }
    
    @Deprecated
    public SimpleAttachableMaterialData(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public SimpleAttachableMaterialData(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public BlockFace getFacing() {
        final BlockFace attachedFace = this.getAttachedFace();
        return (attachedFace == null) ? null : attachedFace.getOppositeFace();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + " facing " + this.getFacing();
    }
    
    @Override
    public SimpleAttachableMaterialData clone() {
        return (SimpleAttachableMaterialData)super.clone();
    }
}
