// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Light_Dash extends Ability
{
    public Light_Dash() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 3.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("length", 1.0);
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
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection();
            final List<Integer> hit = new ArrayList<Integer>();
            final /* synthetic */ double val$length = abilityResult.getModifier("length");
            final /* synthetic */ double val$damage = abilityResult.getModifier("damage");
            
            public void run() {
                if (this.j++ > 10.0 * Math.min(10.0, this.val$length)) {
                    this.cancel();
                }
                cachedStats.getPlayer().setVelocity(this.vec);
                cachedStats.getPlayer().getWorld().spawnParticle(Particle.SMOKE_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0);
                cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDER_DRAGON_FLAP.toSound(), 1.0f, 2.0f);
                for (final Entity entity : cachedStats.getPlayer().getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (!this.hit.contains(entity.getEntityId()) && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                        this.hit.add(entity.getEntityId());
                        new AttackResult(this.val$damage, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
}
