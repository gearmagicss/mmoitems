// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.event.EventHandler;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.ArrayList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.HandlerList;
import java.util.List;
import net.Indyuce.mmoitems.api.util.TemporaryListener;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Corrupted_Fangs extends Ability implements Listener
{
    public Corrupted_Fangs() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 5.0);
        this.addModifier("cooldown", 12.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
        this.addModifier("fangs", 6.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        new BukkitRunnable() {
            double fangAmount = abilityResult.getModifier("fangs");
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().setY(0).multiply(2);
            final Location loc = cachedStats.getPlayer().getLocation();
            double ti = 0.0;
            final FangsHandler handler = new FangsHandler(cachedStats, abilityResult.getModifier("damage"));
            
            public void run() {
                final double ti = this.ti;
                this.ti = ti + 1.0;
                if (ti >= this.fangAmount) {
                    this.handler.close(60L);
                    this.cancel();
                    return;
                }
                this.loc.add(this.vec);
                this.handler.entities.add(((EvokerFangs)cachedStats.getPlayer().getWorld().spawnEntity(this.loc, EntityType.EVOKER_FANGS)).getEntityId());
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public class FangsHandler extends TemporaryListener
    {
        private final List<Integer> entities;
        private final PlayerStats.CachedStats stats;
        private final double damage;
        
        public FangsHandler(final PlayerStats.CachedStats stats, final double damage) {
            super(new HandlerList[] { EntityDamageByEntityEvent.getHandlerList() });
            this.entities = new ArrayList<Integer>();
            this.stats = stats;
            this.damage = damage;
        }
        
        @EventHandler
        public void a(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
            if (entityDamageByEntityEvent.getDamager() instanceof EvokerFangs && this.entities.contains(entityDamageByEntityEvent.getDamager().getEntityId())) {
                entityDamageByEntityEvent.setDamage(0.0);
                if (MMOUtils.canDamage(this.stats.getPlayer(), entityDamageByEntityEvent.getEntity())) {
                    new AttackResult(this.damage, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(this.stats.getPlayer(), (LivingEntity)entityDamageByEntityEvent.getEntity());
                }
                else {
                    entityDamageByEntityEvent.setCancelled(true);
                }
            }
        }
    }
}
