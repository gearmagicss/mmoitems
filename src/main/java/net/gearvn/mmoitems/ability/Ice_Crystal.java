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
import org.bukkit.entity.Entity;
import org.bukkit.Color;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
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

public class Ice_Crystal extends Ability
{
    public Ice_Crystal() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("duration", 3.0);
        this.addModifier("amplifier", 1.0);
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
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().multiply(0.45);
            final Location loc = cachedStats.getPlayer().getEyeLocation().clone().add(0.0, -0.3, 0.0);
            int ti = 0;
            
            public void run() {
                ++this.ti;
                if (this.ti > 25) {
                    this.cancel();
                }
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_GLASS_BREAK, 2.0f, 1.0f);
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 3; ++i) {
                    this.loc.add(this.vec);
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                    }
                    for (double n = 0.0; n < 0.4; n += 0.1) {
                        for (double n2 = 0.0; n2 < 6.283185307179586; n2 += 1.5707963267948966) {
                            final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(n * Math.cos(n2 + this.ti / 10.0), n * Math.sin(n2 + this.ti / 10.0), 0.0), this.loc);
                            this.loc.add(rotateFunc);
                            this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc, 1, (Object)new Particle.DustOptions(Color.WHITE, 0.7f));
                            this.loc.add(rotateFunc.multiply(-1));
                        }
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                            this.loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, this.loc, 48, 0.0, 0.0, 0.0, 0.2);
                            this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
                            new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(abilityResult.getModifier("duration") * 20.0), (int)abilityResult.getModifier("amplifier")));
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
