// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.bukkit.potion.PotionEffect;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectData
{
    private final PotionEffectType type;
    private final double duration;
    private final int level;
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PotionEffectData && ((PotionEffectData)o).getLevel() == this.getLevel() && ((PotionEffectData)o).getDuration() == this.getDuration() && this.type.equals((Object)((PotionEffectData)o).getType());
    }
    
    public PotionEffectData(final PotionEffectType potionEffectType, final int n) {
        this(potionEffectType, MMOUtils.getEffectDuration(potionEffectType) / 20.0, n);
    }
    
    public PotionEffectData(final PotionEffectType type, final double duration, final int level) {
        this.type = type;
        this.duration = duration;
        this.level = level;
    }
    
    public PotionEffectType getType() {
        return this.type;
    }
    
    public double getDuration() {
        return this.duration;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public PotionEffect toEffect() {
        return new PotionEffect(this.type, (int)(this.duration * 20.0), this.level - 1, true, false);
    }
}
