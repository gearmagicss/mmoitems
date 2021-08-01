// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Regen_Ally extends Ability
{
    public Regen_Ally() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("heal", 7.0);
        this.addModifier("duration", 3.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new FriendlyTargetAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            double ti = 0.0;
            double a = 0.0;
            final double duration = Math.min(abilityResult.getModifier("duration"), 60.0) * 20.0;
            final double hps = abilityResult.getModifier("heal") / this.duration * 4.0;
            final /* synthetic */ LivingEntity val$target = ((FriendlyTargetAbilityResult)abilityResult).getTarget();
            
            public void run() {
                ++this.ti;
                if (this.ti > this.duration || this.val$target.isDead()) {
                    this.cancel();
                    return;
                }
                this.a += 0.19634954084936207;
                this.val$target.getWorld().spawnParticle(Particle.HEART, this.val$target.getLocation().add(1.3 * Math.cos(this.a), 0.3, 1.3 * Math.sin(this.a)), 0);
                if (this.ti % 4.0 == 0.0) {
                    MMOUtils.heal(this.val$target, this.hps);
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public static class FriendlyTargetAbilityResult extends AbilityResult
    {
        private final LivingEntity target;
        
        public FriendlyTargetAbilityResult(final AbilityData abilityData, final Player player, final LivingEntity livingEntity) {
            super(abilityData);
            this.target = ((livingEntity != null) ? livingEntity : MythicLib.plugin.getVersion().getWrapper().rayTrace(player, 50.0, entity -> entity instanceof Player && MMOUtils.canDamage(player, entity)).getHit());
        }
        
        public LivingEntity getTarget() {
            return this.target;
        }
        
        @Override
        public boolean isSuccessful() {
            return this.target != null;
        }
    }
}
