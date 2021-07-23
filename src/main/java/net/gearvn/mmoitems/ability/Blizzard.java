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
import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Location;
import org.bukkit.entity.Snowball;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Blizzard extends Ability
{
    public Blizzard() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 2.5);
        this.addModifier("damage", 2.0);
        this.addModifier("inaccuracy", 10.0);
        this.addModifier("force", 1.0);
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
        new BukkitRunnable() {
            int j = 0;
            final SnowballThrower handler = new SnowballThrower(abilityResult.getModifier("damage"));
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 10.0;
            final /* synthetic */ double val$inaccuracy = abilityResult.getModifier("inaccuracy");
            final /* synthetic */ double val$force = abilityResult.getModifier("force");
            
            public void run() {
                if (this.j++ > this.val$duration) {
                    this.handler.close(100L);
                    this.cancel();
                    return;
                }
                final Location eyeLocation = cachedStats.getPlayer().getEyeLocation();
                eyeLocation.setPitch((float)(eyeLocation.getPitch() + (Blizzard.random.nextDouble() - 0.5) * this.val$inaccuracy));
                eyeLocation.setYaw((float)(eyeLocation.getYaw() + (Blizzard.random.nextDouble() - 0.5) * this.val$inaccuracy));
                eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
                final Snowball snowball = (Snowball)cachedStats.getPlayer().launchProjectile((Class)Snowball.class);
                snowball.setVelocity(eyeLocation.getDirection().multiply(1.3 * this.val$force));
                this.handler.entities.add(snowball.getUniqueId());
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
    
    public static class SnowballThrower extends TemporaryListener
    {
        private final List<UUID> entities;
        private final double damage;
        
        public SnowballThrower(final double damage) {
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
