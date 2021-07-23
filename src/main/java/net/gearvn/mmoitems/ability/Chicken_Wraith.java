// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEggThrowEvent;
import java.util.ArrayList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.HandlerList;
import java.util.List;
import net.Indyuce.mmoitems.api.util.TemporaryListener;
import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Chicken_Wraith extends Ability
{
    public Chicken_Wraith() {
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
            final EggHandler handler = new EggHandler(abilityResult.getModifier("damage"));
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
                eyeLocation.setPitch((float)(eyeLocation.getPitch() + (Chicken_Wraith.random.nextDouble() - 0.5) * this.val$inaccuracy));
                eyeLocation.setYaw((float)(eyeLocation.getYaw() + (Chicken_Wraith.random.nextDouble() - 0.5) * this.val$inaccuracy));
                eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
                final Egg egg = (Egg)cachedStats.getPlayer().launchProjectile((Class)Egg.class);
                egg.setVelocity(eyeLocation.getDirection().multiply(1.3 * this.val$force));
                this.handler.entities.add(egg.getEntityId());
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
    
    public static class EggHandler extends TemporaryListener
    {
        private final List<Integer> entities;
        private final double damage;
        
        public EggHandler(final double damage) {
            super(new HandlerList[] { EntityDamageByEntityEvent.getHandlerList() });
            this.entities = new ArrayList<Integer>();
            this.damage = damage;
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void a(final PlayerEggThrowEvent playerEggThrowEvent) {
            if (this.entities.contains(playerEggThrowEvent.getEgg().getEntityId())) {
                playerEggThrowEvent.setHatching(false);
            }
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void b(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (this.entities.contains(entityDamageByEntityEvent.getDamager().getEntityId())) {
                entityDamageByEntityEvent.setDamage(this.damage);
            }
        }
    }
}
