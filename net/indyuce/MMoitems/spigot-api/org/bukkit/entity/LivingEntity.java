// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffectType;
import java.util.Collection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.Material;
import java.util.Set;
import org.bukkit.block.Block;
import java.util.List;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.attribute.Attributable;

public interface LivingEntity extends Attributable, Entity, Damageable, ProjectileSource
{
    double getEyeHeight();
    
    double getEyeHeight(final boolean p0);
    
    Location getEyeLocation();
    
    @Deprecated
    List<Block> getLineOfSight(final HashSet<Byte> p0, final int p1);
    
    List<Block> getLineOfSight(final Set<Material> p0, final int p1);
    
    @Deprecated
    Block getTargetBlock(final HashSet<Byte> p0, final int p1);
    
    Block getTargetBlock(final Set<Material> p0, final int p1);
    
    @Deprecated
    List<Block> getLastTwoTargetBlocks(final HashSet<Byte> p0, final int p1);
    
    List<Block> getLastTwoTargetBlocks(final Set<Material> p0, final int p1);
    
    int getRemainingAir();
    
    void setRemainingAir(final int p0);
    
    int getMaximumAir();
    
    void setMaximumAir(final int p0);
    
    int getMaximumNoDamageTicks();
    
    void setMaximumNoDamageTicks(final int p0);
    
    double getLastDamage();
    
    @Deprecated
    int _INVALID_getLastDamage();
    
    void setLastDamage(final double p0);
    
    @Deprecated
    void _INVALID_setLastDamage(final int p0);
    
    int getNoDamageTicks();
    
    void setNoDamageTicks(final int p0);
    
    Player getKiller();
    
    boolean addPotionEffect(final PotionEffect p0);
    
    boolean addPotionEffect(final PotionEffect p0, final boolean p1);
    
    boolean addPotionEffects(final Collection<PotionEffect> p0);
    
    boolean hasPotionEffect(final PotionEffectType p0);
    
    void removePotionEffect(final PotionEffectType p0);
    
    Collection<PotionEffect> getActivePotionEffects();
    
    boolean hasLineOfSight(final Entity p0);
    
    boolean getRemoveWhenFarAway();
    
    void setRemoveWhenFarAway(final boolean p0);
    
    EntityEquipment getEquipment();
    
    void setCanPickupItems(final boolean p0);
    
    boolean getCanPickupItems();
    
    boolean isLeashed();
    
    Entity getLeashHolder() throws IllegalStateException;
    
    boolean setLeashHolder(final Entity p0);
    
    boolean isGliding();
    
    void setGliding(final boolean p0);
    
    void setAI(final boolean p0);
    
    boolean hasAI();
    
    void setCollidable(final boolean p0);
    
    boolean isCollidable();
}
