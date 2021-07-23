// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Lightning_Beam extends Ability
{
    public Lightning_Beam() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 8.0);
        this.addModifier("radius", 5.0);
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
        final Location firstNonSolidBlock = this.getFirstNonSolidBlock(((LocationAbilityResult)abilityResult).getTarget());
        final double modifier = abilityResult.getModifier("damage");
        final double modifier2 = abilityResult.getModifier("radius");
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(firstNonSolidBlock)) {
            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(firstNonSolidBlock) <= modifier2 * modifier2) {
                new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
            }
        }
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 1.0f, 0.0f);
        firstNonSolidBlock.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, firstNonSolidBlock, 64, 0.0, 0.0, 0.0, 0.2);
        firstNonSolidBlock.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, firstNonSolidBlock, 32, 0.0, 0.0, 0.0, 0.2);
        final Vector vector = new Vector(0.0, 0.3, 0.0);
        for (double n = 0.0; n < 40.0; n += 0.3) {
            firstNonSolidBlock.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, firstNonSolidBlock.add(vector), 6, 0.1, 0.1, 0.1, 0.01);
        }
    }
    
    private Location getFirstNonSolidBlock(final Location location) {
        final Location clone = location.clone();
        for (int i = 0; i < 5; ++i) {
            if (!location.add(0.0, 1.0, 0.0).getBlock().getType().isSolid()) {
                return location;
            }
        }
        return clone;
    }
}
