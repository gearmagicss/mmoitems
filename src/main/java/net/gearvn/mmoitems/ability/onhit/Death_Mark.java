// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Death_Mark extends Ability
{
    public Death_Mark() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 7.0);
        this.addModifier("damage", 5.0);
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
        final double n = abilityResult.getModifier("duration") * 20.0;
        new BukkitRunnable() {
            double ti = 0.0;
            final /* synthetic */ double val$dps = abilityResult.getModifier("damage") / n * 20.0;
            
            public void run() {
                ++this.ti;
                if (this.ti > n || target == null || target.isDead()) {
                    this.cancel();
                    return;
                }
                target.getWorld().spawnParticle(Particle.SPELL_MOB, target.getLocation(), 4, 0.2, 0.0, 0.2, 0.0);
                if (this.ti % 20.0 == 0.0) {
                    MythicLib.plugin.getDamage().damage(cachedStats.getPlayer(), target, new AttackResult(this.val$dps, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }), false);
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_BLAZE_HURT, 1.0f, 2.0f);
        target.removePotionEffect(PotionEffectType.SLOW);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)n, (int)abilityResult.getModifier("amplifier")));
    }
}
