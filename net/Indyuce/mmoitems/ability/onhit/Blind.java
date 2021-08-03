// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Color;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.util.Vector;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Blind extends Ability
{
    public Blind() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 5.0);
        this.addModifier("cooldown", 9.0);
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
        target.getWorld().playSound(target.getLocation(), VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 1.0f, 2.0f);
        for (double n = 0.0; n < 6.283185307179586; n += 0.1308996938995747) {
            for (double n2 = 0.0; n2 < 2.0; ++n2) {
                final Location location = target.getLocation();
                location.getWorld().spawnParticle(Particle.REDSTONE, location.add(MMOUtils.rotateFunc(new Vector(Math.cos(n), 1.0 + Math.cos(n + 3.141592653589793 * n2) * 0.5, Math.sin(n)), cachedStats.getPlayer().getLocation())), 1, (Object)new Particle.DustOptions(Color.BLACK, 1.0f));
            }
        }
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int)(abilityResult.getModifier("duration") * 20.0), 0));
    }
}
