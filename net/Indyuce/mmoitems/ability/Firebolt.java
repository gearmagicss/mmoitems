// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

public class Firebolt extends Ability
{
    public Firebolt() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("ignite", 3.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 1.0f, 1.0f);
        new BukkitRunnable() {
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().multiply(0.8);
            final Location loc = cachedStats.getPlayer().getEyeLocation();
            int ti = 0;
            
            public void run() {
                ++this.ti;
                if (this.ti > 20) {
                    this.cancel();
                }
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_FIRE_AMBIENT, 2.0f, 1.0f);
                for (int i = 0; i < 2; ++i) {
                    this.loc.add(this.vec);
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                    }
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 5, 0.12, 0.12, 0.12, 0.0);
                    if (Firebolt.random.nextDouble() < 0.3) {
                        this.loc.getWorld().spawnParticle(Particle.LAVA, this.loc, 0);
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 32, 0.0, 0.0, 0.0, 0.1);
                            this.loc.getWorld().spawnParticle(Particle.LAVA, this.loc, 8, 0.0, 0.0, 0.0, 0.0);
                            this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                            this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 1.0f);
                            new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            entity.setFireTicks((int)abilityResult.getModifier("ignite") * 20);
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
