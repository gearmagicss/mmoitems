// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Shockwave extends Ability
{
    public Shockwave() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 7.5);
        this.addModifier("knock-up", 1.0);
        this.addModifier("length", 5.0);
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
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection().setY(0);
            final Location loc = cachedStats.getPlayer().getLocation();
            int ti = 0;
            final List<Integer> hit = new ArrayList<Integer>();
            final /* synthetic */ double val$length = abilityResult.getModifier("length");
            final /* synthetic */ double val$knockUp = abilityResult.getModifier("knock-up");
            
            public void run() {
                ++this.ti;
                if (this.ti >= Math.min(20.0, this.val$length)) {
                    this.cancel();
                }
                this.loc.add(this.vec);
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_GRAVEL_BREAK, 1.0f, 2.0f);
                this.loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, this.loc, 12, 0.5, 0.0, 0.5, 0.0, (Object)Material.DIRT.createBlockData());
                for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                    if (entity.getLocation().distance(this.loc) < 1.1 && entity instanceof LivingEntity && !entity.equals(cachedStats.getPlayer()) && !this.hit.contains(entity.getEntityId())) {
                        this.hit.add(entity.getEntityId());
                        entity.playEffect(EntityEffect.HURT);
                        entity.setVelocity(entity.getVelocity().setY(0.4 * this.val$knockUp));
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
