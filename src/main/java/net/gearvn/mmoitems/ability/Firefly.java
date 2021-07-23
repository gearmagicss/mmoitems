// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Location;
import java.util.Iterator;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.util.Vector;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Firefly extends Ability
{
    public Firefly() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("duration", 2.5);
        this.addModifier("knockback", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            int j = 0;
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 20.0;
            
            public void run() {
                ++this.j;
                if (this.j > this.val$duration) {
                    this.cancel();
                }
                if (cachedStats.getPlayer().getLocation().getBlock().getType() == Material.WATER) {
                    cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getVelocity().multiply(3).setY(1.8));
                    cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0f, 0.5f);
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 32, 0.0, 0.0, 0.0, 0.2);
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.CLOUD, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 32, 0.0, 0.0, 0.0, 0.2);
                    this.cancel();
                    return;
                }
                for (final Entity entity : cachedStats.getPlayer().getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                        final double modifier = abilityResult.getModifier("damage");
                        final double modifier2 = abilityResult.getModifier("knockback");
                        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0f, 0.5f);
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.LAVA, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 32);
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.SMOKE_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 24, 0.0, 0.0, 0.0, 0.3);
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.FLAME, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 24, 0.0, 0.0, 0.0, 0.3);
                        entity.setVelocity(cachedStats.getPlayer().getVelocity().setY(0.3).multiply(1.7 * modifier2));
                        cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getEyeLocation().getDirection().multiply(-3).setY(0.5));
                        new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        this.cancel();
                        return;
                    }
                }
                final Location add = cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0);
                for (double n = 0.0; n < 6.283185307179586; n += 0.3490658503988659) {
                    final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(0.6 * Math.cos(n), 0.6 * Math.sin(n), 0.0), add);
                    add.add(rotateFunc);
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, add, 0);
                    if (Firefly.random.nextDouble() < 0.3) {
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.FLAME, add, 0);
                    }
                    add.add(rotateFunc.multiply(-1));
                }
                cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getEyeLocation().getDirection());
                cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 1.0f, 1.0f);
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
