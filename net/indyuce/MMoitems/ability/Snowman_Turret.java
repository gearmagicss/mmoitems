// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.ArrayList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.HandlerList;
import java.util.UUID;
import java.util.List;
import net.Indyuce.mmoitems.api.util.TemporaryListener;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.entity.Snowball;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.LocationAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Snowman_Turret extends Ability
{
    public Snowman_Turret() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 6.0);
        this.addModifier("cooldown", 35.0);
        this.addModifier("damage", 2.0);
        this.addModifier("radius", 20.0);
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
        final double min = Math.min(abilityResult.getModifier("duration") * 20.0, 300.0);
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        target.getWorld().playSound(target, VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 2.0f, 1.0f);
        final Snowman snowman = (Snowman)target.getWorld().spawnEntity(target.add(0.0, 1.0, 0.0), EntityType.SNOWMAN);
        snowman.setInvulnerable(true);
        snowman.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 254, true));
        new BukkitRunnable() {
            int ti = 0;
            double j = 0.0;
            final TurretHandler turret = new TurretHandler(abilityResult.getModifier("damage"));
            
            public void run() {
                if (this.ti++ > min || cachedStats.getPlayer().isDead() || snowman == null || snowman.isDead()) {
                    this.turret.close(60L);
                    snowman.remove();
                    this.cancel();
                }
                this.j += 0.1308996938995747;
                for (double n = 0.0; n < 3.0; ++n) {
                    snowman.getWorld().spawnParticle(Particle.SPELL_INSTANT, snowman.getLocation().add(Math.cos(this.j + n / 3.0 * 2.0 * 3.141592653589793) * 1.3, 1.0, Math.sin(this.j + n / 3.0 * 2.0 * 3.141592653589793) * 1.3), 0);
                }
                snowman.getWorld().spawnParticle(Particle.SPELL_INSTANT, snowman.getLocation().add(0.0, 1.0, 0.0), 1, 0.0, 0.0, 0.0, 0.2);
                if (this.ti % 2 == 0) {
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(snowman.getLocation())) {
                        if (!entity.equals(snowman) && MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(snowman.getLocation()) < pow) {
                            snowman.getWorld().playSound(snowman.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.3f);
                            final Snowball snowball = (Snowball)snowman.launchProjectile((Class)Snowball.class);
                            snowball.setVelocity(entity.getLocation().add(0.0, entity.getHeight() / 2.0, 0.0).toVector().subtract(snowman.getLocation().add(0.0, 1.0, 0.0).toVector()).normalize().multiply(1.3));
                            this.turret.entities.add(snowball.getUniqueId());
                            break;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public static class TurretHandler extends TemporaryListener
    {
        private final List<UUID> entities;
        private final double damage;
        
        public TurretHandler(final double damage) {
            super(new HandlerList[] { EntityDamageByEntityEvent.getHandlerList() });
            this.entities = new ArrayList<UUID>();
            this.damage = damage;
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (this.entities.contains(entityDamageByEntityEvent.getDamager().getUniqueId())) {
                entityDamageByEntityEvent.setDamage(this.damage);
            }
        }
    }
}
