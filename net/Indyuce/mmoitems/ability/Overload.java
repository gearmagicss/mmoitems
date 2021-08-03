// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Overload extends Ability
{
    public Overload() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("radius", 6.0);
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
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 0.0f);
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_TWINKLE.toSound(), 2.0f, 0.0f);
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 254));
        for (final Entity entity : cachedStats.getPlayer().getNearbyEntities(modifier2, modifier2, modifier2)) {
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
            }
        }
        for (double n = 12.0 + modifier2 * 2.5, n2 = 0.0; n2 < 6.283185307179586; n2 += 3.141592653589793 / n) {
            final Location add = cachedStats.getPlayer().getLocation().clone().add(Math.cos(n2) * modifier2, 1.0, Math.sin(n2) * modifier2);
            cachedStats.getPlayer().getWorld().spawnParticle(Particle.CLOUD, add, 4, 0.0, 0.0, 0.0, 0.05);
            cachedStats.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, add, 4, 0.0, 0.0, 0.0, 0.05);
        }
    }
}
