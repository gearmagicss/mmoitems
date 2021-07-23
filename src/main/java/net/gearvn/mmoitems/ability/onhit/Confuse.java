// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Confuse extends Ability
{
    public Confuse() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 7.0);
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
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_SHEEP_DEATH, 1.0f, 2.0f);
        new BukkitRunnable() {
            final Location loc = target.getLocation();
            final double rads = Math.toRadians(cachedStats.getPlayer().getEyeLocation().getYaw() - 90.0f);
            double ti = this.rads;
            
            public void run() {
                for (int i = 0; i < 3; ++i) {
                    this.ti += 0.20943951023931953;
                    this.loc.getWorld().spawnParticle(Particle.SPELL_WITCH, this.loc.clone().add(Math.cos(this.ti), 1.0, Math.sin(this.ti)), 0);
                }
                if (this.ti >= 6.283185307179586 + this.rads) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        final Location clone = target.getLocation().clone();
        clone.setYaw(target.getLocation().getYaw() - 180.0f);
        target.teleport(clone);
    }
}
