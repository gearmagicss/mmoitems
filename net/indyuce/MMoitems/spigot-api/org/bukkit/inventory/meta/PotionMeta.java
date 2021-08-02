// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory.meta;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import java.util.List;
import org.bukkit.potion.PotionData;

public interface PotionMeta extends ItemMeta
{
    void setBasePotionData(final PotionData p0);
    
    PotionData getBasePotionData();
    
    boolean hasCustomEffects();
    
    List<PotionEffect> getCustomEffects();
    
    boolean addCustomEffect(final PotionEffect p0, final boolean p1);
    
    boolean removeCustomEffect(final PotionEffectType p0);
    
    boolean hasCustomEffect(final PotionEffectType p0);
    
    @Deprecated
    boolean setMainEffect(final PotionEffectType p0);
    
    boolean clearCustomEffects();
    
    PotionMeta clone();
}
