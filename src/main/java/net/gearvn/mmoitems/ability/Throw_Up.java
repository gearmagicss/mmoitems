// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import java.util.Random;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.util.NoClipItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Throw_Up extends Ability implements Listener
{
    public Throw_Up() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("duration", 2.5);
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
            final /* synthetic */ double val$dps = abilityResult.getModifier("damage") / 2.0;
            
            public void run() {
                ++this.j;
                if (this.j > this.val$duration) {
                    this.cancel();
                }
                final Location eyeLocation = cachedStats.getPlayer().getEyeLocation();
                eyeLocation.setPitch((float)(eyeLocation.getPitch() + (Throw_Up.random.nextDouble() - 0.5) * 30.0));
                eyeLocation.setYaw((float)(eyeLocation.getYaw() + (Throw_Up.random.nextDouble() - 0.5) * 30.0));
                if (this.j % 5 == 0) {
                    for (final Entity entity : MMOUtils.getNearbyChunkEntities(eyeLocation)) {
                        if (entity.getLocation().distanceSquared(eyeLocation) < 40.0 && cachedStats.getPlayer().getEyeLocation().getDirection().angle(entity.getLocation().toVector().subtract(cachedStats.getPlayer().getLocation().toVector())) < 0.5235987755982988 && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                            new AttackResult(this.val$dps, new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        }
                    }
                }
                eyeLocation.getWorld().playSound(eyeLocation, Sound.ENTITY_ZOMBIE_HURT, 1.0f, 1.0f);
                final NoClipItem noClipItem = new NoClipItem(cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0), new ItemStack(Material.ROTTEN_FLESH));
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)MMOItems.plugin, noClipItem::close, 40L);
                noClipItem.getEntity().setVelocity(eyeLocation.getDirection().multiply(0.8));
                cachedStats.getPlayer().getWorld().spawnParticle(Particle.SMOKE_LARGE, cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0), 0, eyeLocation.getDirection().getX(), eyeLocation.getDirection().getY(), eyeLocation.getDirection().getZ(), 1.0);
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 2L);
    }
}
