// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import java.util.List;
import org.bukkit.potion.PotionData;
import org.bukkit.Particle;

public interface AreaEffectCloud extends Entity
{
    int getDuration();
    
    void setDuration(final int p0);
    
    int getWaitTime();
    
    void setWaitTime(final int p0);
    
    int getReapplicationDelay();
    
    void setReapplicationDelay(final int p0);
    
    int getDurationOnUse();
    
    void setDurationOnUse(final int p0);
    
    float getRadius();
    
    void setRadius(final float p0);
    
    float getRadiusOnUse();
    
    void setRadiusOnUse(final float p0);
    
    float getRadiusPerTick();
    
    void setRadiusPerTick(final float p0);
    
    Particle getParticle();
    
    void setParticle(final Particle p0);
    
    void setBasePotionData(final PotionData p0);
    
    PotionData getBasePotionData();
    
    boolean hasCustomEffects();
    
    List<PotionEffect> getCustomEffects();
    
    boolean addCustomEffect(final PotionEffect p0, final boolean p1);
    
    boolean removeCustomEffect(final PotionEffectType p0);
    
    boolean hasCustomEffect(final PotionEffectType p0);
    
    void clearCustomEffects();
    
    Color getColor();
    
    void setColor(final Color p0);
    
    ProjectileSource getSource();
    
    void setSource(final ProjectileSource p0);
}
