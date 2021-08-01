// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.Sound;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Life_Ender extends Ability
{
    public Life_Ender() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 5.0);
        this.addModifier("knockback", 1.0);
        this.addModifier("radius", 4.0);
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
        final double modifier2 = abilityResult.getModifier("knockback");
        final double modifier3 = abilityResult.getModifier("radius");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 2.0f, 1.0f);
        new BukkitRunnable() {
            int ti = 0;
            final Location source = target.clone().add(5.0 * Math.cos(Life_Ender.random.nextDouble() * 2.0 * 3.141592653589793), 20.0, 5.0 * Math.sin(Life_Ender.random.nextDouble() * 2.0 * 3.141592653589793));
            final Vector vec = target.subtract(this.source).toVector().multiply(0.03333333333333333);
            
            public void run() {
                if (this.ti == 0) {
                    target.setDirection(this.vec);
                }
                for (int i = 0; i < 2; ++i) {
                    ++this.ti;
                    this.source.add(this.vec);
                    for (double n = 0.0; n < 6.283185307179586; n += 0.5235987755982988) {
                        final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(Math.cos(n), Math.sin(n), 0.0), target);
                        this.source.getWorld().spawnParticle(Particle.SMOKE_LARGE, this.source, 0, rotateFunc.getX(), rotateFunc.getY(), rotateFunc.getZ(), 0.1);
                    }
                }
                if (this.ti >= 30) {
                    this.source.getWorld().playSound(this.source, Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 1.0f);
                    this.source.getWorld().spawnParticle(Particle.FLAME, this.source, 64, 0.0, 0.0, 0.0, 0.25);
                    this.source.getWorld().spawnParticle(Particle.LAVA, this.source, 32);
                    for (double n2 = 0.0; n2 < 6.283185307179586; n2 += 0.1308996938995747) {
                        this.source.getWorld().spawnParticle(Particle.SMOKE_LARGE, this.source, 0, Math.cos(n2), 0.0, Math.sin(n2), 0.5);
                    }
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.source)) {
                        if (entity.getLocation().distanceSquared(this.source) < modifier3 * modifier3 && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            entity.setVelocity(entity.getLocation().subtract(this.source).toVector().setY(0.75).normalize().multiply(modifier2));
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
