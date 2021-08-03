// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import net.Indyuce.mmoitems.MMOUtils;
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

public class Vampirism extends Ability
{
    public Vampirism() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 8.0);
        this.addModifier("drain", 10.0);
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
            double ti = 0.0;
            final Location loc = target.getLocation();
            double dis = 0.0;
            
            public void run() {
                for (int i = 0; i < 4; ++i) {
                    this.ti += 0.75;
                    this.dis += ((this.ti <= 10.0) ? 0.15 : -0.15);
                    for (double n = 0.0; n < 6.283185307179586; n += 0.7853981633974483) {
                        this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(Math.cos(n + this.ti / 20.0) * this.dis, 0.0, Math.sin(n + this.ti / 20.0) * this.dis), 1, (Object)new Particle.DustOptions(Color.RED, 1.0f));
                    }
                }
                if (this.ti >= 17.0) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_WITCH_DRINK, 1.0f, 2.0f);
        MMOUtils.heal((LivingEntity)cachedStats.getPlayer(), itemAttackResult.getDamage() * abilityResult.getModifier("drain") / 100.0);
    }
}
