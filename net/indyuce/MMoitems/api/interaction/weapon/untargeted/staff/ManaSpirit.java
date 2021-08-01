// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.Color;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class ManaSpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        new BukkitRunnable() {
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection().multiply(0.4);
            final Location loc = cachedStats.getPlayer().getEyeLocation();
            int ti = 0;
            final double r = 0.2;
            
            public void run() {
                if (this.ti++ > n2) {
                    this.cancel();
                }
                if (this.ti % 2 == 0) {
                    this.loc.getWorld().playSound(this.loc, Sound.BLOCK_SNOW_BREAK, 2.0f, 2.0f);
                }
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 3; ++i) {
                    this.loc.add(this.vec);
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                        break;
                    }
                    for (double n = 0.0; n < 6.283185307179586; n += 0.8975979010256552) {
                        final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(0.2 * Math.cos(n), 0.2 * Math.sin(n), 0.0), this.loc);
                        if (StaffAttackHandler.random.nextDouble() <= 0.6) {
                            this.loc.getWorld().spawnParticle(Particle.REDSTONE, this.loc.clone().add(rotateFunc), 1, (Object)new Particle.DustOptions(Color.AQUA, 1.0f));
                        }
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }).applyEffectsAndDamage(cachedStats, nbtItem, (LivingEntity)entity);
                            this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
