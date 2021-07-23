// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import java.util.Iterator;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Circular_Slash extends Ability
{
    public Circular_Slash() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("radius", 3.0);
        this.addModifier("knockback", 1.0);
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
        final double modifier = abilityResult.getModifier("damage");
        final double modifier2 = abilityResult.getModifier("radius");
        final double modifier3 = abilityResult.getModifier("knockback");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 2.0f, 0.5f);
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 254));
        for (final Entity entity : cachedStats.getPlayer().getNearbyEntities(modifier2, modifier2, modifier2)) {
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                final Vector vector = entity.getLocation().toVector();
                final Vector vector2 = cachedStats.getPlayer().getLocation().toVector();
                final double n = 0.5;
                entity.setVelocity(vector.subtract(vector2).multiply(0.5 * modifier3).setY((modifier3 == 0.0) ? 0.0 : n));
            }
        }
        for (double n2 = 12.0 + modifier2 * 2.5, n3 = 0.0; n3 < 6.283185307179586; n3 += 3.141592653589793 / n2) {
            final Location clone = cachedStats.getPlayer().getLocation().clone();
            clone.add(Math.cos(n3) * modifier2, 0.75, Math.sin(n3) * modifier2);
            clone.getWorld().spawnParticle(Particle.SMOKE_LARGE, clone, 0);
        }
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0);
    }
}
