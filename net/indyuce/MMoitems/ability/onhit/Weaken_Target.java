// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.onhit;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.TargetAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Weaken_Target extends Ability implements Listener
{
    public final Map<UUID, WeakenedInfo> marked;
    
    public Weaken_Target() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT });
        this.marked = new HashMap<UUID, WeakenedInfo>();
        this.addModifier("duration", 4.0);
        this.addModifier("extra-damage", 40.0);
        this.addModifier("cooldown", 10.0);
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
        this.marked.put(target.getUniqueId(), new WeakenedInfo(abilityResult.getModifier("extra-damage")));
        this.effect(target.getLocation());
        target.getWorld().playSound(target.getLocation(), VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 2.0f, 1.5f);
        new BukkitRunnable() {
            final long duration = (long)(abilityResult.getModifier("duration") * 1000.0);
            
            public void run() {
                if (!Weaken_Target.this.marked.containsKey(target.getUniqueId()) || Weaken_Target.this.marked.get(target.getUniqueId()).date + this.duration < System.currentTimeMillis()) {
                    this.cancel();
                    return;
                }
                for (double n = 0.0; n < 6.283185307179586; n += 0.17453292519943295) {
                    target.getWorld().spawnParticle(Particle.SMOKE_NORMAL, target.getLocation().clone().add(Math.cos(n) * 0.7, 0.1, Math.sin(n) * 0.7), 0);
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 20L);
    }
    
    @EventHandler
    public void a(final EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION && entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        final Entity entity = entityDamageEvent.getEntity();
        if (this.marked.containsKey(entity.getUniqueId())) {
            entityDamageEvent.setDamage(entityDamageEvent.getDamage() * (1.0 + this.marked.get(entity.getUniqueId()).extraDamage));
            this.effect(entity.getLocation());
            this.marked.remove(entity.getUniqueId());
            entity.getWorld().playSound(entity.getLocation(), VersionSound.ENTITY_ENDERMAN_DEATH.toSound(), 2.0f, 2.0f);
        }
    }
    
    @EventHandler
    public void b(final PlayerItemConsumeEvent playerItemConsumeEvent) {
        final Player player = playerItemConsumeEvent.getPlayer();
        if (playerItemConsumeEvent.getItem().getType() == Material.MILK_BUCKET && this.marked.containsKey(player.getUniqueId())) {
            this.marked.remove(player.getUniqueId());
            player.getWorld().playSound(player.getLocation(), VersionSound.ENTITY_ENDERMAN_DEATH.toSound(), 2.0f, 2.0f);
        }
    }
    
    private void effect(final Location location) {
        new BukkitRunnable() {
            double y = 0.0;
            
            public void run() {
                for (int i = 0; i < 3; ++i) {
                    this.y += 0.07;
                    for (int j = 0; j < 3; ++j) {
                        location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(Math.cos(this.y * 3.141592653589793 + j * 3.141592653589793 * 2.0 / 3.0) * (3.0 - this.y) / 2.5, this.y, Math.sin(this.y * 3.141592653589793 + j * 3.141592653589793 * 2.0 / 3.0) * (3.0 - this.y) / 2.5), 1, (Object)new Particle.DustOptions(Color.BLACK, 1.0f));
                    }
                }
                if (this.y > 3.0) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public static class WeakenedInfo
    {
        private final long date;
        private final double extraDamage;
        
        public WeakenedInfo(final double n) {
            this.date = System.currentTimeMillis();
            this.extraDamage = n / 100.0;
        }
    }
}
