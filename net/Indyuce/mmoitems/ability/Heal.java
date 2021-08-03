// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Heal extends Ability
{
    public Heal() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("heal", 4.0);
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
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.HEART, cachedStats.getPlayer().getLocation().add(0.0, 0.75, 0.0), 16, 1.0, 1.0, 1.0, 0.0);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, cachedStats.getPlayer().getLocation().add(0.0, 0.75, 0.0), 16, 1.0, 1.0, 1.0, 0.0);
        MMOUtils.heal((LivingEntity)cachedStats.getPlayer(), abilityResult.getModifier("heal"));
    }
}
