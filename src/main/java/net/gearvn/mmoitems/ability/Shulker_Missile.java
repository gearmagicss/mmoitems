// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventHandler;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Location;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Shulker_Missile extends Ability implements Listener
{
    public Shulker_Missile() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 12.0);
        this.addModifier("damage", 5.0);
        this.addModifier("effect-duration", 5.0);
        this.addModifier("duration", 5.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            double n = 0.0;
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration");
            
            public void run() {
                final double n = this.n;
                this.n = n + 1.0;
                if (n > 3.0) {
                    this.cancel();
                    return;
                }
                final Vector target = ((VectorAbilityResult)abilityResult).getTarget();
                cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
                final ShulkerBullet shulkerBullet = (ShulkerBullet)cachedStats.getPlayer().getWorld().spawnEntity(cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), EntityType.SHULKER_BULLET);
                shulkerBullet.setShooter((ProjectileSource)cachedStats.getPlayer());
                MMOItems.plugin.getEntities().registerCustomEntity((Entity)shulkerBullet, new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.MAGIC, DamageType.PROJECTILE }), abilityResult.getModifier("effect-duration"));
                new BukkitRunnable() {
                    double ti = 0.0;
                    
                    public void run() {
                        ++this.ti;
                        if (shulkerBullet.isDead() || this.ti >= BukkitRunnable.this.val$duration * 20.0) {
                            shulkerBullet.remove();
                            this.cancel();
                        }
                        shulkerBullet.setVelocity(target);
                    }
                }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 3L);
    }
    
    @EventHandler
    public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.getDamager() instanceof ShulkerBullet && entityDamageByEntityEvent.getEntity() instanceof LivingEntity) {
            final ShulkerBullet shulkerBullet = (ShulkerBullet)entityDamageByEntityEvent.getDamager();
            final LivingEntity livingEntity = (LivingEntity)entityDamageByEntityEvent.getEntity();
            if (!MMOItems.plugin.getEntities().isCustomEntity((Entity)shulkerBullet)) {
                return;
            }
            if (!MMOUtils.canDamage((Entity)livingEntity)) {
                entityDamageByEntityEvent.setCancelled(true);
                return;
            }
            final Object[] entityData = MMOItems.plugin.getEntities().getEntityData((Entity)shulkerBullet);
            final AttackResult attackResult = (AttackResult)entityData[0];
            final double n = (double)entityData[1] * 20.0;
            if (entityData.length > 2) {
                ((ItemAttackResult)attackResult).applyEffects((PlayerStats.CachedStats)entityData[2], (NBTItem)entityData[3], livingEntity);
            }
            entityDamageByEntityEvent.setDamage(attackResult.getDamage());
            new BukkitRunnable() {
                final Location loc = livingEntity.getLocation();
                double y = 0.0;
                
                public void run() {
                    if (this.y == 0.0) {
                        livingEntity.removePotionEffect(PotionEffectType.LEVITATION);
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, (int)n, 0));
                    }
                    for (int i = 0; i < 3; ++i) {
                        this.y += 0.04;
                        for (int j = 0; j < 2; ++j) {
                            final double n = this.y * 3.141592653589793 * 1.3 + j * 3.141592653589793;
                            this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(Math.cos(n), this.y, Math.sin(n)), 1, (Object)new Particle.DustOptions(Color.MAROON, 1.0f));
                        }
                    }
                    if (this.y >= 2.0) {
                        this.cancel();
                    }
                }
            }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        }
    }
}
