// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Corrosion extends Ability
{
    public Corrosion() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT });
        this.addModifier("duration", 4.0);
        this.addModifier("amplifier", 1.0);
        this.addModifier("radius", 5.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new LocationAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final Location target = ((LocationAbilityResult)abilityResult).getTarget();
        final int n = (int)(abilityResult.getModifier("duration") * 20.0);
        final int n2 = (int)abilityResult.getModifier("amplifier");
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        target.getWorld().spawnParticle(Particle.SLIME, target, 48, 2.0, 2.0, 2.0, 0.0);
        target.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, target, 32, 2.0, 2.0, 2.0, 0.0);
        target.getWorld().playSound(target, Sound.BLOCK_BREWING_STAND_BREW, 2.0f, 0.0f);
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
            if (entity.getLocation().distanceSquared(target) < pow && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                ((LivingEntity)entity).removePotionEffect(PotionEffectType.POISON);
                ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.POISON, n, n2));
            }
        }
    }
}
