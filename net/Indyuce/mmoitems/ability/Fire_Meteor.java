// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Fire_Meteor extends Ability
{
    public Fire_Meteor() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("knockback", 1.0);
        this.addModifier("radius", 4.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 3.0f, 1.0f);
        new BukkitRunnable() {
            double ti = 0.0;
            final Location loc = cachedStats.getPlayer().getLocation().clone().add(0.0, 10.0, 0.0);
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().multiply(1.3).setY(-1).normalize();
            
            public void run() {
                ++this.ti;
                if (this.ti > 40.0) {
                    this.cancel();
                }
                this.loc.add(this.vec);
                this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 4, 0.2, 0.2, 0.2, 0.0);
                if (this.loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid() || this.loc.getBlock().getType().isSolid()) {
                    this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 3.0f, 0.6f);
                    this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 10, 2.0, 2.0, 2.0, 0.0);
                    this.loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, this.loc, 32, 0.0, 0.0, 0.0, 0.3);
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 32, 0.0, 0.0, 0.0, 0.3);
                    final double modifier = abilityResult.getModifier("damage");
                    final double modifier2 = abilityResult.getModifier("knockback");
                    final double modifier3 = abilityResult.getModifier("radius");
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(this.loc) < modifier3 * modifier3) {
                            new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            entity.setVelocity(entity.getLocation().toVector().subtract(this.loc.toVector()).multiply(0.1 * modifier2).setY(0.4 * modifier2));
                        }
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
