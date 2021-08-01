// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Random;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Magical_Shield extends Ability
{
    public Magical_Shield() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("power", 40.0);
        this.addModifier("radius", 5.0);
        this.addModifier("duration", 5.0);
        this.addModifier("cooldown", 35.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData, ((LivingEntity)cachedStats.getPlayer()).isOnGround());
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("duration");
        final double pow = Math.pow(abilityResult.getModifier("radius"), 2.0);
        final double n = abilityResult.getModifier("power") / 100.0;
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 3.0f, 0.0f);
        new MagicalShield(cachedStats.getPlayer().getLocation().clone(), modifier, pow, n);
    }
    
    public static class MagicalShield extends BukkitRunnable implements Listener
    {
        private final Location loc;
        private final double duration;
        private final double radius;
        private final double power;
        int ti;
        
        public MagicalShield(final Location loc, final double duration, final double radius, final double power) {
            this.ti = 0;
            this.loc = loc;
            this.duration = duration;
            this.radius = radius;
            this.power = power;
            this.runTaskTimer((Plugin)MMOItems.plugin, 0L, 3L);
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        }
        
        private void close() {
            this.cancel();
            EntityDamageEvent.getHandlerList().unregister((Listener)this);
        }
        
        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void a(final EntityDamageEvent entityDamageEvent) {
            if (entityDamageEvent.getEntity() instanceof Player && entityDamageEvent.getEntity().getWorld().equals(this.loc.getWorld()) && entityDamageEvent.getEntity().getLocation().distanceSquared(this.loc) < this.radius) {
                entityDamageEvent.setDamage(entityDamageEvent.getDamage() * (1.0 - this.power));
            }
        }
        
        public void run() {
            ++this.ti;
            if (this.ti > this.duration * 20.0 / 3.0) {
                this.close();
            }
            for (double a = 0.0; a < 1.5707963267948966; a += 3.141592653589793 / (28 + Magical_Shield.random.nextInt(5))) {
                for (double n = 0.0; n < 6.283185307179586; n += 3.141592653589793 / (14 + Magical_Shield.random.nextInt(5))) {
                    this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(2.5 * Math.cos(n + a) * Math.sin(a), 2.5 * Math.cos(a), 2.5 * Math.sin(n + a) * Math.sin(a)), 1, (Object)new Particle.DustOptions(Color.FUCHSIA, 1.0f));
                }
            }
        }
    }
}
