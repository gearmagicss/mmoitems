// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.arcane;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.util.Vector;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Arcane_Hail extends Ability
{
    public Arcane_Hail() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 3.0);
        this.addModifier("duration", 4.0);
        this.addModifier("radius", 3.0);
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
        new BukkitRunnable() {
            int j = 0;
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 10.0;
            final /* synthetic */ Location val$loc = ((LocationAbilityResult)abilityResult).getTarget();
            final /* synthetic */ double val$radius = abilityResult.getModifier("radius");
            final /* synthetic */ double val$damage = abilityResult.getModifier("damage");
            
            public void run() {
                ++this.j;
                if (this.j > this.val$duration) {
                    this.cancel();
                    return;
                }
                final Location add = this.val$loc.clone().add(Arcane_Hail.this.randomCoordMultiplier() * this.val$radius, 0.0, Arcane_Hail.this.randomCoordMultiplier() * this.val$radius);
                add.getWorld().playSound(add, VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 1.0f, 0.0f);
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(add)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(add) <= 4.0) {
                        new AttackResult(this.val$damage, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                    }
                }
                add.getWorld().spawnParticle(Particle.SPELL_WITCH, add, 12, 0.0, 0.0, 0.0, 0.1);
                add.getWorld().spawnParticle(Particle.SMOKE_NORMAL, add, 6, 0.0, 0.0, 0.0, 0.1);
                final Vector vector = new Vector(Arcane_Hail.this.randomCoordMultiplier() * 0.03, 0.3, Arcane_Hail.this.randomCoordMultiplier() * 0.03);
                for (double n = 0.0; n < 60.0; ++n) {
                    add.getWorld().spawnParticle(Particle.SPELL_WITCH, add.add(vector), 0);
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
    
    private double randomCoordMultiplier() {
        return (Arcane_Hail.random.nextDouble() - 0.5) * 2.0;
    }
}
