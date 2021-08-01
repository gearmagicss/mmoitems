// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Color;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Corrupt extends Ability
{
    public Corrupt() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 8.0);
        this.addModifier("duration", 4.0);
        this.addModifier("amplifier", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new LocationAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final Location target = ((LocationAbilityResult)abilityResult).getTarget();
        final double modifier = abilityResult.getModifier("damage");
        final double modifier2 = abilityResult.getModifier("duration");
        final double modifier3 = abilityResult.getModifier("amplifier");
        final double n = 2.7;
        target.add(0.0, -1.0, 0.0);
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 1.0f, 0.5f);
        for (double n2 = 0.0; n2 < 6.283185307179586; n2 += 0.08726646259971647) {
            final Location add = target.clone().add(Math.cos(n2) * n, 1.0, Math.sin(n2) * n);
            for (double n3 = 0.5 + Corrupt.random.nextDouble(), n4 = 0.0; n4 < n3; n4 += 0.1) {
                add.getWorld().spawnParticle(Particle.REDSTONE, add.clone().add(0.0, n4, 0.0), 1, (Object)new Particle.DustOptions(Color.PURPLE, 1.0f));
            }
        }
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(target) <= n * n) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                ((LivingEntity)entity).removePotionEffect(PotionEffectType.WITHER);
                ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int)(modifier2 * 20.0), (int)modifier3));
            }
        }
    }
}
