// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Particle;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Shadow_Veil extends Ability implements Listener
{
    public Shadow_Veil() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 35.0);
        this.addModifier("duration", 5.0);
        this.addModifier("deception", 1.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("duration");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 3.0f, 0.0f);
        final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
        while (iterator.hasNext()) {
            iterator.next().hidePlayer((Plugin)MMOItems.plugin, cachedStats.getPlayer());
        }
        for (final Mob mob : cachedStats.getPlayer().getWorld().getEntitiesByClass((Class)Mob.class)) {
            if (mob.getTarget() != null && mob.getTarget().equals(cachedStats.getPlayer())) {
                mob.setTarget((LivingEntity)null);
            }
        }
        new ShadowVeilHandler(cachedStats.getPlayer(), modifier).setDeceptions(SilentNumbers.floor(abilityResult.getModifier("deception")));
    }
    
    public static class ShadowVeilHandler extends BukkitRunnable implements Listener
    {
        private final Player player;
        private final double duration;
        private final Location loc;
        int deceptions;
        double ti;
        double y;
        boolean cancelled;
        
        public void setDeceptions(final int deceptions) {
            this.deceptions = deceptions;
        }
        
        public ShadowVeilHandler(final Player player, final double duration) {
            this.deceptions = 1;
            this.ti = 0.0;
            this.y = 0.0;
            this.player = player;
            this.duration = duration;
            this.loc = player.getLocation();
            this.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
            Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
        }
        
        private void close() {
            if (this.ti < 0.0) {
                return;
            }
            this.player.getWorld().spawnParticle(Particle.SMOKE_LARGE, this.player.getLocation().add(0.0, 1.0, 0.0), 32, 0.0, 0.0, 0.0, 0.13);
            this.player.getWorld().playSound(this.player.getLocation(), VersionSound.ENTITY_ENDERMAN_TELEPORT.toSound(), 3.0f, 0.0f);
            this.ti = -1.0;
            EntityDamageByEntityEvent.getHandlerList().unregister((Listener)this);
            EntityTargetEvent.getHandlerList().unregister((Listener)this);
            final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                iterator.next().showPlayer((Plugin)MMOItems.plugin, this.player);
            }
            this.cancel();
        }
        
        public void run() {
            final double ti = this.ti;
            this.ti = ti + 1.0;
            if (ti > this.duration * 20.0 || !this.player.isOnline()) {
                this.close();
                return;
            }
            if (this.y < 4.0) {
                for (int i = 0; i < 5; ++i) {
                    this.y += 0.04;
                    for (int j = 0; j < 4; ++j) {
                        final double n = this.y * 3.141592653589793 * 0.8 + j * 3.141592653589793 / 2.0;
                        this.player.getWorld().spawnParticle(Particle.SMOKE_LARGE, this.loc.clone().add(Math.cos(n) * 2.5, this.y, Math.sin(n) * 2.5), 0);
                    }
                }
            }
        }
        
        @EventHandler
        public void cancelShadowVeil(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (entityDamageByEntityEvent.getDamager().equals(this.player)) {
                --this.deceptions;
                if (this.deceptions <= 0) {
                    this.close();
                }
            }
        }
        
        @EventHandler
        public void cancelMobTarget(final EntityTargetEvent entityTargetEvent) {
            if (entityTargetEvent.getTarget() != null && entityTargetEvent.getTarget().equals(this.player)) {
                entityTargetEvent.setCancelled(true);
            }
        }
    }
}
