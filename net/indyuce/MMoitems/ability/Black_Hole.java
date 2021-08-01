// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Black_Hole extends Ability
{
    public Black_Hole() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("radius", 2.0);
        this.addModifier("duration", 2.0);
        this.addModifier("cooldown", 35.0);
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
        final double n = abilityResult.getModifier("duration") * 20.0;
        final double modifier = abilityResult.getModifier("radius");
        target.getWorld().playSound(target, VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 3.0f, 1.0f);
        new BukkitRunnable() {
            int ti = 0;
            final double r = 4.0;
            
            public void run() {
                if (this.ti++ > Math.min(300.0, n)) {
                    this.cancel();
                }
                target.getWorld().playSound(target, VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 2.0f, 2.0f);
                target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target, 0);
                for (int i = 0; i < 3; ++i) {
                    final double n = Black_Hole.random.nextDouble() * 3.141592653589793 * 2.0;
                    final double n2 = Black_Hole.random.nextDouble() * 2.0 - 1.0;
                    final Location add = target.clone().add(Math.cos(n) * Math.sin(n2 * 3.141592653589793 * 2.0) * 4.0, n2 * 4.0, Math.sin(n) * Math.sin(n2 * 3.141592653589793 * 2.0) * 4.0);
                    final Vector subtract = target.toVector().subtract(add.toVector());
                    add.getWorld().spawnParticle(Particle.SMOKE_LARGE, add, 0, subtract.getX(), subtract.getY(), subtract.getZ(), 0.1);
                }
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
                    if (entity.getLocation().distanceSquared(target) < Math.pow(modifier, 2.0) && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                        entity.setVelocity(MMOUtils.normalize(target.clone().subtract(entity.getLocation()).toVector()).multiply(0.5));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
