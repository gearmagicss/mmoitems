// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Sparkle extends Ability
{
    public Sparkle() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("damage", 4.0);
        this.addModifier("limit", 5.0);
        this.addModifier("radius", 6.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new TargetAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final LivingEntity target = ((TargetAbilityResult)abilityResult).getTarget();
        final double modifier = abilityResult.getModifier("damage");
        final double modifier2 = abilityResult.getModifier("radius");
        final double modifier3 = abilityResult.getModifier("limit");
        new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), target);
        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation().add(0.0, 1.0, 0.0), 0);
        target.getWorld().playSound(target.getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_TWINKLE.toSound(), 2.0f, 2.0f);
        int n = 0;
        for (final Entity entity : target.getNearbyEntities(modifier2, modifier2, modifier2)) {
            if (n < modifier3 && entity instanceof LivingEntity && entity != cachedStats.getPlayer() && !(entity instanceof ArmorStand)) {
                ++n;
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                entity.getWorld().playSound(entity.getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_TWINKLE.toSound(), 2.0f, 2.0f);
                final Location add = target.getLocation().add(0.0, 0.75, 0.0);
                final Location add2 = entity.getLocation().add(0.0, 0.75, 0.0);
                for (double n2 = 0.0; n2 < 1.0; n2 += 0.04) {
                    target.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, add.clone().add(add2.toVector().subtract(add.toVector()).multiply(n2)), 3, 0.1, 0.1, 0.1, 0.008);
                }
            }
        }
    }
}
