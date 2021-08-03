// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import java.util.Iterator;
import org.bukkit.Location;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
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

public class Minor_Explosion extends Ability
{
    public Minor_Explosion() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT });
        this.addModifier("damage", 6.0);
        this.addModifier("knockback", 1.0);
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
        final double modifier = abilityResult.getModifier("damage");
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        final double modifier2 = abilityResult.getModifier("knockback");
        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.add(0.0, 0.1, 0.0), 32, 1.7, 1.7, 1.7, 0.0);
        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target, 64, 0.0, 0.0, 0.0, 0.3);
        target.getWorld().playSound(target, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
            if (entity.getLocation().distanceSquared(target) < pow && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                entity.setVelocity(MMOUtils.normalize(entity.getLocation().subtract(target).toVector().setY(0)).setY(0.2).multiply(2.0 * modifier2));
            }
        }
    }
}
