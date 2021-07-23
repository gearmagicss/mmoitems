// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Heavy_Charge extends Ability
{
    public Heavy_Charge() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("knockback", 1.0);
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
        new BukkitRunnable() {
            double ti = 0.0;
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().setY(-1);
            final /* synthetic */ double val$knockback = abilityResult.getModifier("knockback");
            
            public void run() {
                ++this.ti;
                if (this.ti > 20.0) {
                    this.cancel();
                }
                if (this.ti < 9.0) {
                    cachedStats.getPlayer().setVelocity(this.vec);
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 3, 0.13, 0.13, 0.13, 0.0);
                }
                for (final Entity entity : cachedStats.getPlayer().getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0f, 1.0f);
                        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation().add(0.0, 1.0, 0.0), 0);
                        entity.setVelocity(cachedStats.getPlayer().getVelocity().setY(0.3).multiply(1.7 * this.val$knockback));
                        cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getVelocity().setX(0).setY(0).setZ(0));
                        new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        this.cancel();
                        break;
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
