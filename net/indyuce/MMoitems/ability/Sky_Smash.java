// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Iterator;
import org.bukkit.Location;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
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

public class Sky_Smash extends Ability
{
    public Sky_Smash() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("damage", 3.0);
        this.addModifier("knock-up", 1.0);
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
        final double modifier2 = abilityResult.getModifier("knock-up");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 2.0f, 0.5f);
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 254));
        final Location add = cachedStats.getPlayer().getEyeLocation().add(cachedStats.getPlayer().getEyeLocation().getDirection().multiply(3));
        add.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, add, 0);
        add.getWorld().spawnParticle(Particle.SMOKE_LARGE, add, 16, 0.0, 0.0, 0.0, 0.1);
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(add)) {
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(add) < 10.0) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                final Location clone = cachedStats.getPlayer().getEyeLocation().clone();
                clone.setPitch(-70.0f);
                entity.setVelocity(clone.getDirection().multiply(1.2 * modifier2));
            }
        }
    }
}
