// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.util.Vector;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Particle;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Tactical_Grenade extends Ability
{
    public Tactical_Grenade() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
        this.addModifier("knock-up", 1.0);
        this.addModifier("damage", 4.0);
        this.addModifier("radius", 4.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new TargetAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            int j = 0;
            final Location loc = cachedStats.getPlayer().getLocation().add(0.0, 0.1, 0.0);
            final double radius = abilityResult.getModifier("radius");
            final double knockup = 0.7 * abilityResult.getModifier("knock-up");
            final List<Integer> hit = new ArrayList<Integer>();
            final /* synthetic */ LivingEntity val$target = ((TargetAbilityResult)abilityResult).getTarget();
            
            public void run() {
                ++this.j;
                if (this.val$target.isDead() || !this.val$target.getWorld().equals(this.loc.getWorld()) || this.j > 200) {
                    this.cancel();
                    return;
                }
                final Vector vector = this.val$target.getLocation().add(0.0, 0.1, 0.0).subtract(this.loc).toVector();
                this.loc.add((vector.length() < 3.0) ? vector : vector.normalize().multiply(3));
                this.loc.getWorld().spawnParticle(Particle.CLOUD, this.loc, 32, 1.0, 0.0, 1.0, 0.0);
                this.loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, this.loc, 16, 1.0, 0.0, 1.0, 0.05);
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_ANVIL_LAND, 2.0f, 0.0f);
                this.loc.getWorld().playSound(this.loc, Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.0f);
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                    if (!this.hit.contains(entity.getEntityId()) && MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(this.loc) < this.radius * this.radius) {
                        this.hit.add(entity.getEntityId());
                        if (entity.equals(this.val$target)) {
                            this.cancel();
                        }
                        new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        entity.setVelocity(entity.getVelocity().add(Tactical_Grenade.this.offsetVector(this.knockup)));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 12L);
    }
    
    private Vector offsetVector(final double n) {
        return new Vector(2.0 * (Tactical_Grenade.random.nextDouble() - 0.5), n, 2.0 * (Tactical_Grenade.random.nextDouble() - 0.5));
    }
}
