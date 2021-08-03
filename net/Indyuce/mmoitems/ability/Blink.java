// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.Location;
import java.util.Set;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Blink extends Ability
{
    public Blink() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("range", 8.0);
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
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.SPELL_INSTANT, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 32, 0.0, 0.0, 0.0, 0.1);
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 1.0f, 1.0f);
        final Location add = cachedStats.getPlayer().getTargetBlock((Set)null, (int)abilityResult.getModifier("range")).getLocation().add(0.0, 1.0, 0.0);
        add.setYaw(cachedStats.getPlayer().getLocation().getYaw());
        add.setPitch(cachedStats.getPlayer().getLocation().getPitch());
        cachedStats.getPlayer().teleport(add);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.SPELL_INSTANT, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 32, 0.0, 0.0, 0.0, 0.1);
    }
}
