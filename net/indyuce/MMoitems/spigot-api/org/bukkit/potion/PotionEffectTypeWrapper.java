// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

public class PotionEffectTypeWrapper extends PotionEffectType
{
    protected PotionEffectTypeWrapper(final int id) {
        super(id);
    }
    
    @Override
    public double getDurationModifier() {
        return this.getType().getDurationModifier();
    }
    
    @Override
    public String getName() {
        return this.getType().getName();
    }
    
    public PotionEffectType getType() {
        return PotionEffectType.getById(this.getId());
    }
    
    @Override
    public boolean isInstant() {
        return this.getType().isInstant();
    }
}
