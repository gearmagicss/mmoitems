// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability.arcane;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Arcane_Rift extends Ability
{
    public Arcane_Rift() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 5.0);
        this.addModifier("amplifier", 2.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("speed", 1.0);
        this.addModifier("duration", 1.5);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        if (!((LivingEntity)cachedStats.getPlayer()).isOnGround()) {
            return new SimpleAbilityResult(abilityData, false);
        }
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final double modifier = abilityResult.getModifier("damage");
        final double modifier2 = abilityResult.getModifier("duration");
        final double modifier3 = abilityResult.getModifier("amplifier");
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_ENDERMAN_DEATH.toSound(), 2.0f, 0.5f);
        new BukkitRunnable() {
            final Vector vec = ((VectorAbilityResult)abilityResult).getTarget().setY(0).normalize().multiply(0.5 * abilityResult.getModifier("speed"));
            final Location loc = cachedStats.getPlayer().getLocation();
            int ti = 0;
            final int duration = (int)(20.0 * Math.min(abilityResult.getModifier("duration"), 10.0));
            final List<Integer> hit = new ArrayList<Integer>();
            
            public void run() {
                if (this.ti++ > this.duration) {
                    this.cancel();
                }
                this.loc.add(this.vec);
                this.loc.getWorld().spawnParticle(Particle.SPELL_WITCH, this.loc, 5, 0.5, 0.0, 0.5, 0.0);
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && this.loc.distanceSquared(entity.getLocation()) < 2.0 && !this.hit.contains(entity.getEntityId())) {
                        this.hit.add(entity.getEntityId());
                        new AttackResult(modifier, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(modifier2 * 20.0), (int)modifier3));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
