// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Contamination extends Ability
{
    public Contamination() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 2.0);
        this.addModifier("duration", 8.0);
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
        final double n = Math.min(30.0, abilityResult.getModifier("duration")) * 20.0;
        target.add(0.0, 0.1, 0.0);
        new BukkitRunnable() {
            double ti = 0.0;
            int j = 0;
            final double dps = abilityResult.getModifier("damage") / 2.0;
            
            public void run() {
                ++this.j;
                if (this.j >= n) {
                    this.cancel();
                }
                target.getWorld().spawnParticle(Particle.REDSTONE, target.clone().add(Math.cos(this.ti / 3.0) * 5.0, 0.0, Math.sin(this.ti / 3.0) * 5.0), 1, (Object)new Particle.DustOptions(Color.PURPLE, 1.0f));
                for (int i = 0; i < 3; ++i) {
                    this.ti += 0.09817477042468103;
                    final double n = Math.sin(this.ti / 2.0) * 4.0;
                    for (double n2 = 0.0; n2 < 6.283185307179586; n2 += 2.0943951023931953) {
                        target.getWorld().spawnParticle(Particle.SPELL_WITCH, target.clone().add(n * Math.cos(n2 + this.ti / 4.0), 0.0, n * Math.sin(n2 + this.ti / 4.0)), 0);
                    }
                }
                if (this.j % 10 == 0) {
                    target.getWorld().playSound(target, VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 2.0f, 1.0f);
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(target)) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(target) <= 25.0) {
                            MythicLib.plugin.getDamage().damage(cachedStats.getPlayer(), (LivingEntity)entity, new AttackResult(this.dps, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }), false);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
