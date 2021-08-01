// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Frozen_Aura extends Ability implements Listener
{
    public Frozen_Aura() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 6.0);
        this.addModifier("amplifier", 1.0);
        this.addModifier("radius", 10.0);
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
            double j = 0.0;
            int ti = 0;
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 20.0;
            final /* synthetic */ double val$radiusSquared = Math.pow(abilityResult.getModifier("radius"), 2.0);
            final /* synthetic */ double val$amplifier = abilityResult.getModifier("amplifier") - 1.0;
            
            public void run() {
                if (this.ti++ > this.val$duration) {
                    this.cancel();
                }
                this.j += 0.05235987755982988;
                for (double n = 0.0; n < 6.283185307179586; n += 1.5707963267948966) {
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.SPELL_INSTANT, cachedStats.getPlayer().getLocation().add(Math.cos(n + this.j) * 2.0, 1.0 + Math.sin(n + this.j * 7.0) / 3.0, Math.sin(n + this.j) * 2.0), 0);
                }
                if (this.ti % 2 == 0) {
                    cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.BLOCK_SNOW_BREAK, 1.0f, 1.0f);
                }
                if (this.ti % 7 == 0) {
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(cachedStats.getPlayer().getLocation())) {
                        if (entity.getLocation().distanceSquared(cachedStats.getPlayer().getLocation()) < this.val$radiusSquared && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            ((LivingEntity)entity).removePotionEffect(PotionEffectType.SLOW);
                            ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, (int)this.val$amplifier));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
