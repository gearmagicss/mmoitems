// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.potion;

import java.util.Collection;

public interface PotionBrewer
{
    PotionEffect createEffect(final PotionEffectType p0, final int p1, final int p2);
    
    @Deprecated
    Collection<PotionEffect> getEffectsFromDamage(final int p0);
    
    Collection<PotionEffect> getEffects(final PotionType p0, final boolean p1, final boolean p2);
}
