// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Particle;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Earthquake extends Ability
{
    public Earthquake() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 3.0);
        this.addModifier("duration", 2.0);
        this.addModifier("amplifier", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return ((LivingEntity)cachedStats.getPlayer()).isOnGround() ? new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity) : new SimpleAbilityResult(abilityData, false);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().setY(0);
            final Location loc = cachedStats.getPlayer().getLocation();
            int ti = 0;
            final List<Integer> hit = new ArrayList<Integer>();
            final /* synthetic */ double val$damage = abilityResult.getModifier("damage");
            final /* synthetic */ double val$slowDuration = abilityResult.getModifier("duration");
            final /* synthetic */ double val$slowAmplifier = abilityResult.getModifier("amplifier");
            
            public void run() {
                ++this.ti;
                if (this.ti > 20) {
                    this.cancel();
                }
                this.loc.add(this.vec);
                this.loc.getWorld().spawnParticle(Particle.CLOUD, this.loc, 5, 0.5, 0.0, 0.5, 0.0);
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_GRAVEL_BREAK, 2.0f, 1.0f);
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && this.loc.distanceSquared(entity.getLocation()) < 2.0 && !this.hit.contains(entity.getEntityId())) {
                        this.hit.add(entity.getEntityId());
                        new AttackResult(this.val$damage, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(this.val$slowDuration * 20.0), (int)this.val$slowAmplifier));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
