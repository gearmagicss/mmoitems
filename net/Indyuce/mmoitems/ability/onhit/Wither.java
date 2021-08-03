// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Wither extends Ability
{
    public Wither() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 8.0);
        this.addModifier("duration", 3.0);
        this.addModifier("amplifier", 1.0);
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
        new BukkitRunnable() {
            final Location loc = target.getLocation();
            double y = 0.0;
            
            public void run() {
                if (this.y > 3.0) {
                    this.cancel();
                }
                for (int i = 0; i < 3; ++i) {
                    this.y += 0.07;
                    for (int j = 0; j < 3; ++j) {
                        final double n = this.y * 3.141592653589793 + j * 3.141592653589793 * 2.0 / 3.0;
                        this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(Math.cos(n) * (3.0 - this.y) / 2.5, this.y, Math.sin(n) * (3.0 - this.y) / 2.5), 1, (Object)new Particle.DustOptions(Color.BLACK, 1.0f));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int)(abilityResult.getModifier("duration") * 20.0), (int)abilityResult.getModifier("amplifier")));
    }
}
