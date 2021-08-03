// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.util.TemporaryListener;
import org.bukkit.util.Vector;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class TNT_Throw extends Ability implements Listener
{
    public TNT_Throw() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("force", 1.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final Vector multiply = ((VectorAbilityResult)abilityResult).getTarget().multiply(2.0 * abilityResult.getModifier("force"));
        final TNTPrimed tntPrimed = (TNTPrimed)cachedStats.getPlayer().getWorld().spawnEntity(cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), EntityType.PRIMED_TNT);
        tntPrimed.setFuseTicks(80);
        tntPrimed.setVelocity(multiply);
        new CancelTeamDamage(cachedStats.getPlayer(), tntPrimed);
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 0.0f);
        cachedStats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 12, 0.0, 0.0, 0.0, 0.1);
    }
    
    public static class CancelTeamDamage extends TemporaryListener
    {
        private final Player player;
        private final TNTPrimed tnt;
        
        public CancelTeamDamage(final Player player, final TNTPrimed tnt) {
            super(new HandlerList[] { EntityDamageByEntityEvent.getHandlerList() });
            this.player = player;
            this.tnt = tnt;
            this.close(100L);
        }
        
        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (entityDamageByEntityEvent.getDamager().equals(this.tnt) && !MMOUtils.canDamage(this.player, entityDamageByEntityEvent.getEntity())) {
                entityDamageByEntityEvent.setCancelled(true);
            }
        }
    }
}
