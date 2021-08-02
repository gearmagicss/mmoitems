// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import java.util.ArrayList;
import org.bukkit.Material;
import java.util.List;

public class Step extends TexturedMaterial
{
    private static final List<Material> textures;
    
    static {
        (textures = new ArrayList<Material>()).add(Material.STONE);
        Step.textures.add(Material.SANDSTONE);
        Step.textures.add(Material.WOOD);
        Step.textures.add(Material.COBBLESTONE);
        Step.textures.add(Material.BRICK);
        Step.textures.add(Material.SMOOTH_BRICK);
        Step.textures.add(Material.NETHER_BRICK);
        Step.textures.add(Material.QUARTZ_BLOCK);
    }
    
    public Step() {
        super(Material.STEP);
    }
    
    @Deprecated
    public Step(final int type) {
        super(type);
    }
    
    public Step(final Material type) {
        super(Step.textures.contains(type) ? Material.STEP : type);
        if (Step.textures.contains(type)) {
            this.setMaterial(type);
        }
    }
    
    @Deprecated
    public Step(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public Step(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public List<Material> getTextures() {
        return Step.textures;
    }
    
    public boolean isInverted() {
        return (this.getData() & 0x8) != 0x0;
    }
    
    public void setInverted(final boolean inv) {
        int dat = this.getData() & 0x7;
        if (inv) {
            dat |= 0x8;
        }
        this.setData((byte)dat);
    }
    
    @Deprecated
    @Override
    protected int getTextureIndex() {
        return this.getData() & 0x7;
    }
    
    @Deprecated
    @Override
    protected void setTextureIndex(final int idx) {
        this.setData((byte)((this.getData() & 0x8) | idx));
    }
    
    @Override
    public Step clone() {
        return (Step)super.clone();
    }
    
    @Override
    public String toString() {
        return String.valueOf(super.toString()) + (this.isInverted() ? "inverted" : "");
    }
}
