// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Bouncy_Fireball extends Ability
{
    public Bouncy_Fireball() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 20.0);
        this.addModifier("damage", 5.0);
        this.addModifier("ignite", 40.0);
        this.addModifier("speed", 1.0);
        this.addModifier("radius", 4.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2.0f, 0.0f);
        new BukkitRunnable() {
            int j = 0;
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().setY(0).normalize().multiply(0.5 * abilityResult.getModifier("speed"));
            final Location loc = cachedStats.getPlayer().getLocation().clone().add(0.0, 1.2, 0.0);
            int bounces = 0;
            double y = 0.3;
            
            public void run() {
                ++this.j;
                if (this.j > 100) {
                    this.loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, this.loc, 32, 0.0, 0.0, 0.0, 0.05);
                    this.loc.getWorld().playSound(this.loc, Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 1.0f);
                    this.cancel();
                    return;
                }
                this.loc.add(this.vec);
                this.loc.add(0.0, this.y, 0.0);
                if (this.y > -0.6) {
                    this.y -= 0.05;
                }
                this.loc.getWorld().spawnParticle(Particle.LAVA, this.loc, 0);
                this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 4, 0.0, 0.0, 0.0, 0.03);
                this.loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, this.loc, 1, 0.0, 0.0, 0.0, 0.03);
                if (this.loc.getBlock().getType().isSolid()) {
                    this.loc.add(0.0, -this.y, 0.0);
                    this.loc.add(this.vec.clone().multiply(-1));
                    this.y = 0.4;
                    ++this.bounces;
                    this.loc.getWorld().playSound(this.loc, Sound.ENTITY_BLAZE_HURT, 3.0f, 2.0f);
                }
                if (this.bounces > 2) {
                    final double modifier = abilityResult.getModifier("radius");
                    final double modifier2 = abilityResult.getModifier("damage");
                    final double modifier3 = abilityResult.getModifier("ignite");
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                        if (entity.getLocation().distanceSquared(this.loc) < modifier * modifier && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier2, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            entity.setFireTicks((int)(modifier3 * 20.0));
                        }
                    }
                    this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 12, 2.0, 2.0, 2.0, 0.0);
                    this.loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, this.loc, 48, 0.0, 0.0, 0.0, 0.2);
                    this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.0f);
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
