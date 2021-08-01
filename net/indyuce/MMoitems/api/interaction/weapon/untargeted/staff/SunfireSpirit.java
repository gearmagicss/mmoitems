// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionSound;
import org.bukkit.Particle;
import java.util.Set;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class SunfireSpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        new BukkitRunnable() {
            final Location target = SunfireSpirit.this.getGround(cachedStats.getPlayer().getTargetBlock((Set)null, (int)n2 * 2).getLocation()).add(0.0, 1.2, 0.0);
            final double a = StaffAttackHandler.random.nextDouble() * 3.141592653589793 * 2.0;
            final Location loc = this.target.clone().add(Math.cos(this.a) * 4.0, 10.0, Math.sin(this.a) * 4.0);
            final Vector vec = this.target.toVector().subtract(this.loc.toVector()).multiply(0.015);
            double ti = 0.0;
            
            public void run() {
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_FIRE_AMBIENT, 2.0f, 2.0f);
                for (int i = 0; i < 4; ++i) {
                    this.ti += 0.015;
                    this.loc.add(this.vec);
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 0, 0.03, 0.0, 0.03, 0.0);
                    if (this.ti >= 1.0) {
                        this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 24, 0.0, 0.0, 0.0, 0.12);
                        this.loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, this.loc, 24, 0.0, 0.0, 0.0, 0.12);
                        this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                        this.loc.getWorld().playSound(this.loc, VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 2.0f, 2.0f);
                        for (final Entity entity : MMOUtils.getNearbyChunkEntities(this.loc)) {
                            if (MMOUtils.canDamage(cachedStats.getPlayer(), entity) && entity.getLocation().distanceSquared(this.loc) <= 9.0) {
                                new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }).applyEffectsAndDamage(cachedStats, nbtItem, (LivingEntity)entity);
                            }
                        }
                        this.cancel();
                        break;
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
