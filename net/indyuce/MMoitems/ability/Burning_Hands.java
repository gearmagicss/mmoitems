// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.Location;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Burning_Hands extends Ability
{
    public Burning_Hands() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 3.0);
        this.addModifier("damage", 2.0);
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
            final /* synthetic */ double val$duration = abilityResult.getModifier("duration") * 10.0;
            final /* synthetic */ double val$damage = abilityResult.getModifier("damage") / 2.0;
            
            public void run() {
                if (this.j++ > this.val$duration) {
                    this.cancel();
                }
                final Location add = cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0);
                add.getWorld().playSound(add, Sound.BLOCK_FIRE_AMBIENT, 1.0f, 1.0f);
                for (double n = -45.0; n < 45.0; n += 5.0) {
                    final double n2 = (n + cachedStats.getPlayer().getEyeLocation().getYaw() + 90.0) * 3.141592653589793 / 180.0;
                    final Vector vector = new Vector(Math.cos(n2), (Burning_Hands.random.nextDouble() - 0.5) * 0.2, Math.sin(n2));
                    final Location add2 = add.clone().add(vector.clone().setY(0));
                    add2.getWorld().spawnParticle(Particle.FLAME, add2, 0, vector.getX(), vector.getY(), vector.getZ(), 0.5);
                    if (this.j % 2 == 0) {
                        add2.getWorld().spawnParticle(Particle.SMOKE_NORMAL, add2, 0, vector.getX(), vector.getY(), vector.getZ(), 0.5);
                    }
                }
                if (this.j % 5 == 0) {
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(add)) {
                        if (entity.getLocation().distanceSquared(add) < 60.0 && cachedStats.getPlayer().getEyeLocation().getDirection().angle(entity.getLocation().toVector().subtract(cachedStats.getPlayer().getLocation().toVector())) < 0.5235987755982988 && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(this.val$damage, new DamageType[] { DamageType.SKILL, DamageType.MAGIC }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
}
