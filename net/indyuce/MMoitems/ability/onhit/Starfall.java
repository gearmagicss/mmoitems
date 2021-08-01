// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import java.util.Random;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Starfall extends Ability
{
    public Starfall() {
        super(new CastingMode[] { CastingMode.ON_HIT });
        this.addModifier("cooldown", 8.0);
        this.addModifier("damage", 3.5);
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
        new BukkitRunnable() {
            final double ran = Starfall.random.nextDouble() * 3.141592653589793 * 2.0;
            final Location loc = target.getLocation().add(Math.cos(this.ran) * 3.0, 6.0, Math.sin(this.ran) * 3.0);
            final Vector vec = target.getLocation().add(0.0, 0.65, 0.0).toVector().subtract(this.loc.toVector()).multiply(0.05);
            double ti = 0.0;
            
            public void run() {
                this.loc.getWorld().playSound(this.loc, VersionSound.BLOCK_NOTE_BLOCK_HAT.toSound(), 2.0f, 2.0f);
                for (int i = 0; i < 2; ++i) {
                    this.ti += 0.05;
                    this.loc.add(this.vec);
                    this.loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, this.loc, 1, 0.04, 0.0, 0.04, 0.0);
                    if (this.ti >= 1.0) {
                        this.loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, this.loc, 24, 0.0, 0.0, 0.0, 0.12);
                        this.loc.getWorld().playSound(this.loc, VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 1.0f, 2.0f);
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        itemAttackResult.addDamage(abilityResult.getModifier("damage"));
    }
}
