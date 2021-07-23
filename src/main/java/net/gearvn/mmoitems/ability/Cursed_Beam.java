// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
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

public class Cursed_Beam extends Ability
{
    public Cursed_Beam() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 8.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("duration", 5.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("duration");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        new BukkitRunnable() {
            final Vector dir = ((VectorAbilityResult)abilityResult).getTarget().multiply(0.3);
            final Location loc = cachedStats.getPlayer().getEyeLocation().clone();
            final double r = 0.4;
            int ti = 0;
            
            public void run() {
                ++this.ti;
                if (this.ti > 50) {
                    this.cancel();
                }
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (double n = 0.0; n < 4.0; ++n) {
                    this.loc.add(this.dir);
                    for (double n2 = 0.0; n2 < 6.283185307179586; n2 += 0.5235987755982988) {
                        final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(0.4 * Math.cos(n2), 0.4 * Math.sin(n2), 0.0), this.loc);
                        this.loc.add(rotateFunc);
                        this.loc.getWorld().spawnParticle(Particle.SPELL_WITCH, this.loc, 0);
                        this.loc.add(rotateFunc.multiply(-1));
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            Cursed_Beam.this.effect(entity);
                            final double modifier = abilityResult.getModifier("damage");
                            this.loc.getWorld().playSound(this.loc, VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 2.0f, 0.7f);
                            for (final Entity entity2 : nearbyChunkEntities) {
                                if (MMOUtils.canDamage(cachedStats.getPlayer(), entity2) && this.loc.distanceSquared(entity2.getLocation().add(0.0, 1.0, 0.0)) < 9.0) {
                                    new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity2);
                                    ((LivingEntity)entity2).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int)(modifier * 20.0), 0));
                                }
                            }
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    private void effect(final Entity entity) {
        new BukkitRunnable() {
            final Location loc2 = entity.getLocation();
            double y = 0.0;
            
            public void run() {
                for (int i = 0; i < 3; ++i) {
                    this.y += 0.05;
                    for (int j = 0; j < 2; ++j) {
                        final double n = this.y * 3.141592653589793 * 0.8 + j * 3.141592653589793;
                        this.loc2.getWorld().spawnParticle(Particle.SPELL_WITCH, this.loc2.clone().add(Math.cos(n) * 2.5, this.y, Math.sin(n) * 2.5), 0);
                    }
                }
                if (this.y >= 3.0) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
