// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Holy_Missile extends Ability
{
    public Holy_Missile() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("duration", 4.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double n = abilityResult.getModifier("duration") * 10.0;
        final double modifier = abilityResult.getModifier("damage");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 1.0f, 1.0f);
        new BukkitRunnable() {
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().multiply(0.45);
            final Location loc = cachedStats.getPlayer().getLocation().clone().add(0.0, 1.3, 0.0);
            double ti = 0.0;
            
            public void run() {
                final double ti = this.ti;
                this.ti = ti + 1.0;
                if (ti > n) {
                    this.cancel();
                }
                this.loc.getWorld().playSound(this.loc, VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 2.0f, 1.0f);
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 2; ++i) {
                    this.loc.add(this.vec);
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                    }
                    for (double n = -3.141592653589793; n < 3.141592653589793; n += 1.5707963267948966) {
                        final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(Math.cos(n + this.ti / 4.0), Math.sin(n + this.ti / 4.0), 0.0), this.loc);
                        this.loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, this.loc, 0, rotateFunc.getX(), rotateFunc.getY(), rotateFunc.getZ(), 0.08);
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 1);
                            this.loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, this.loc, 32, 0.0, 0.0, 0.0, 0.2);
                            this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
                            new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
