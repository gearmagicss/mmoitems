// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class MaterialData implements StatData, RandomStatData
{
    private Material material;
    
    public MaterialData(final Material material) {
        Validate.notNull((Object)material, "Material must not be null");
        this.material = material;
    }
    
    public void setMaterial(final Material material) {
        Validate.notNull((Object)material, "Material must not be null");
        this.material = material;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
