// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Swiftness extends Ability
{
    public Swiftness() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 15.0);
        this.addModifier("duration", 4.0);
        this.addModifier("amplifier", 1.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("duration");
        final int n = (int)abilityResult.getModifier("amplifier");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ZOMBIE_PIGMAN_ANGRY.toSound(), 1.0f, 0.3f);
        for (double n2 = 0.0; n2 <= 2.0; n2 += 0.2) {
            for (double n3 = 0.0; n3 < 6.283185307179586; n3 += 0.19634954084936207) {
                if (Swiftness.random.nextDouble() <= 0.7) {
                    cachedStats.getPlayer().getWorld().spawnParticle(Particle.SPELL_INSTANT, cachedStats.getPlayer().getLocation().add(Math.cos(n3), n2, Math.sin(n3)), 0);
                }
            }
        }
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int)(modifier * 20.0), n));
        cachedStats.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int)(modifier * 20.0), n));
    }
}
