// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Freezing_Curse extends Ability
{
    public Freezing_Curse() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 7.0);
        this.addModifier("duration", 3.0);
        this.addModifier("damage", 3.0);
        this.addModifier("radius", 3.0);
        this.addModifier("amplifier", 1.0);
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
        if (target == null) {
            itemAttackResult.setSuccessful(false);
            return;
        }
        new BukkitRunnable() {
            final double rads = Math.toRadians(cachedStats.getPlayer().getEyeLocation().getYaw() - 90.0f);
            double ti = this.rads;
            int j = 0;
            
            public void run() {
                if (this.j++ % 2 == 0) {
                    target.getWorld().playSound(target, VersionSound.BLOCK_NOTE_BLOCK_PLING.toSound(), 2.0f, (float)(0.5 + (this.ti - this.rads) / 6.283185307179586 * 1.5));
                }
                for (int i = 0; i < 2; ++i) {
                    this.ti += 0.09817477042468103;
                    target.getWorld().spawnParticle(Particle.SPELL_INSTANT, target.clone().add(Math.cos(this.ti) * 3.0, 0.1, Math.sin(this.ti) * 3.0), 0);
                }
                if (this.ti > 6.283185307179586 + this.rads) {
                    target.getWorld().playSound(target, Sound.BLOCK_GLASS_BREAK, 3.0f, 0.5f);
                    for (double n = 0.0; n < 6.283185307179586; n += 0.09817477042468103) {
                        target.getWorld().spawnParticle(Particle.CLOUD, target.clone().add(Math.cos(n) * 3.0, 0.1, Math.sin(n) * 3.0), 0);
                    }
                    final double modifier = abilityResult.getModifier("radius");
                    final double modifier2 = abilityResult.getModifier("amplifier");
                    final double modifier3 = abilityResult.getModifier("duration");
                    final double modifier4 = abilityResult.getModifier("damage");
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
                        if (entity.getLocation().distanceSquared(target) < modifier * modifier && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier4, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            ((LivingEntity)entity).removePotionEffect(PotionEffectType.SLOW);
                            ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(modifier3 * 20.0), (int)modifier2));
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
