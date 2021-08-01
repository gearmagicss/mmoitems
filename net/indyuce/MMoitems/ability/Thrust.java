// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Thrust extends Ability
{
    public Thrust() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("damage", 6.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("damage");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0f, 0.0f);
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 3));
        final Location clone = cachedStats.getPlayer().getEyeLocation().clone();
        final Vector multiply = ((VectorAbilityResult)abilityResult).getTarget().multiply(0.5);
        for (double n = 0.0; n < 7.0; n += 0.5) {
            clone.add(multiply);
            for (final Entity entity : MMOUtils.getNearbyChunkEntities(clone)) {
                if (MMOUtils.canDamage(cachedStats.getPlayer(), clone, entity)) {
                    new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                }
            }
            clone.getWorld().spawnParticle(Particle.SMOKE_LARGE, clone, 0);
        }
    }
}
