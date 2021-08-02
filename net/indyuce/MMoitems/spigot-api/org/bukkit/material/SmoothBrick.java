// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import java.util.ArrayList;
import org.bukkit.Material;
import java.util.List;

public class SmoothBrick extends TexturedMaterial
{
    private static final List<Material> textures;
    
    static {
        (textures = new ArrayList<Material>()).add(Material.STONE);
        SmoothBrick.textures.add(Material.MOSSY_COBBLESTONE);
        SmoothBrick.textures.add(Material.COBBLESTONE);
        SmoothBrick.textures.add(Material.SMOOTH_BRICK);
    }
    
    public SmoothBrick() {
        super(Material.SMOOTH_BRICK);
    }
    
    @Deprecated
    public SmoothBrick(final int type) {
        super(type);
    }
    
    public SmoothBrick(final Material type) {
        super(SmoothBrick.textures.contains(type) ? Material.SMOOTH_BRICK : type);
        if (SmoothBrick.textures.contains(type)) {
            this.setMaterial(type);
        }
    }
    
    @Deprecated
    public SmoothBrick(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public SmoothBrick(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public List<Material> getTextures() {
        return SmoothBrick.textures;
    }
    
    @Override
    public SmoothBrick clone() {
        return (SmoothBrick)super.clone();
    }
}
