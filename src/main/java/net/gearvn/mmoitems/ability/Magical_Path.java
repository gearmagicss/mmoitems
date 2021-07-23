// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.Particle;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Magical_Path extends Ability
{
    public Magical_Path() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 3.0);
        this.addModifier("cooldown", 15.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().setAllowFlight(true);
        cachedStats.getPlayer().setFlying(true);
        cachedStats.getPlayer().setVelocity(cachedStats.getPlayer().getVelocity().setY(0.5));
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 1.0f, 1.0f);
        new MagicalPathHandler(cachedStats.getPlayer(), abilityResult.getModifier("duration"));
    }
    
    public static class MagicalPathHandler extends BukkitRunnable implements Listener
    {
        private final Player player;
        private final long duration;
        private boolean safe;
        private int j;
        
        public MagicalPathHandler(final Player player, final double n) {
            this.safe = true;
            this.j = 0;
            this.player = player;
            this.duration = (long)(n * 10.0);
            this.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        }
        
        public void close() {
            this.player.setAllowFlight(false);
            HandlerList.unregisterAll((Listener)this);
            this.cancel();
        }
        
        @EventHandler(priority = EventPriority.LOW)
        public void a(final EntityDamageEvent entityDamageEvent) {
            if (this.safe && entityDamageEvent.getEntity().equals(this.player) && entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.FALL) {
                entityDamageEvent.setCancelled(true);
                this.safe = false;
                this.player.getWorld().spawnParticle(Particle.SPELL, this.player.getLocation(), 8, 0.35, 0.0, 0.35, 0.08);
                this.player.getWorld().spawnParticle(Particle.SPELL_INSTANT, this.player.getLocation(), 16, 0.35, 0.0, 0.35, 0.08);
                this.player.getWorld().playSound(this.player.getLocation(), VersionSound.ENTITY_ENDERMAN_HURT.toSound(), 1.0f, 2.0f);
            }
        }
        
        @EventHandler
        public void b(final PlayerQuitEvent playerQuitEvent) {
            this.close();
        }
        
        public void run() {
            if (this.j++ > this.duration) {
                this.player.getWorld().playSound(this.player.getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 1.0f, 1.0f);
                this.player.setAllowFlight(false);
                this.cancel();
                return;
            }
            this.player.getWorld().spawnParticle(Particle.SPELL, this.player.getLocation(), 8, 0.5, 0.0, 0.5, 0.1);
            this.player.getWorld().spawnParticle(Particle.SPELL_INSTANT, this.player.getLocation(), 16, 0.5, 0.0, 0.5, 0.1);
        }
    }
}
