// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.util.TemporaryListener;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Chicken;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Explosive_Turkey extends Ability implements Listener
{
    public Explosive_Turkey() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("radius", 4.0);
        this.addModifier("duration", 4.0);
        this.addModifier("knockback", 1.0);
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
        final double n = abilityResult.getModifier("duration") * 10.0;
        final double modifier = abilityResult.getModifier("damage");
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        final double modifier2 = abilityResult.getModifier("knockback");
        final Vector multiply = cachedStats.getPlayer().getEyeLocation().getDirection().clone().multiply(0.6);
        final Chicken chicken = (Chicken)cachedStats.getPlayer().getWorld().spawnEntity(cachedStats.getPlayer().getLocation().add(0.0, 1.3, 0.0).add(multiply), EntityType.CHICKEN);
        final ChickenHandler chickenHandler = new ChickenHandler(chicken);
        chicken.setInvulnerable(true);
        chicken.setSilent(true);
        chicken.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048.0);
        chicken.setHealth(2048.0);
        chicken.setVelocity(multiply);
        new BukkitRunnable() {
            int ti = 0;
            final /* synthetic */ double val$trajRatio = chicken.getVelocity().getX() / chicken.getVelocity().getZ();
            
            public void run() {
                if (this.ti++ > n || chicken.isDead()) {
                    chickenHandler.close();
                    this.cancel();
                    return;
                }
                chicken.setVelocity(multiply);
                if (this.ti % 4 == 0) {
                    chicken.getWorld().playSound(chicken.getLocation(), Sound.ENTITY_CHICKEN_HURT, 2.0f, 1.0f);
                }
                chicken.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, chicken.getLocation().add(0.0, 0.3, 0.0), 0);
                chicken.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, chicken.getLocation().add(0.0, 0.3, 0.0), 1, 0.0, 0.0, 0.0, 0.05);
                final double n = chicken.getVelocity().getX() / chicken.getVelocity().getZ();
                if (chicken.isOnGround() || Math.abs(this.val$trajRatio - n) > 0.1) {
                    chickenHandler.close();
                    this.cancel();
                    chicken.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, chicken.getLocation().add(0.0, 0.3, 0.0), 128, 0.0, 0.0, 0.0, 0.25);
                    chicken.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, chicken.getLocation().add(0.0, 0.3, 0.0), 24, 0.0, 0.0, 0.0, 0.25);
                    chicken.getWorld().playSound(chicken.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0f, 1.5f);
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(chicken.getLocation())) {
                        if (!entity.isDead() && entity.getLocation().distanceSquared(chicken.getLocation()) < pow && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                            entity.setVelocity(entity.getLocation().toVector().subtract(chicken.getLocation().toVector()).multiply(0.1 * modifier2).setY(0.4 * modifier2));
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public static class ChickenHandler extends TemporaryListener
    {
        private final Chicken chicken;
        
        public ChickenHandler(final Chicken chicken) {
            super(new HandlerList[] { EntityDeathEvent.getHandlerList() });
            this.chicken = chicken;
        }
        
        @Override
        public void close() {
            this.chicken.remove();
            super.close();
        }
        
        @EventHandler
        public void a(final EntityDeathEvent entityDeathEvent) {
            if (entityDeathEvent.getEntity().equals(this.chicken)) {
                entityDeathEvent.getDrops().clear();
                entityDeathEvent.setDroppedExp(0);
            }
        }
    }
}
