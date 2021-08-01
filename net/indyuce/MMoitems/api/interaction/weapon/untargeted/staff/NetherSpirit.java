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
import org.bukkit.Particle;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class NetherSpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        new BukkitRunnable() {
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection().multiply(0.3);
            final Location loc = cachedStats.getPlayer().getEyeLocation();
            int ti = 0;
            
            public void run() {
                if (this.ti++ % 2 == 0) {
                    this.loc.getWorld().playSound(this.loc, Sound.BLOCK_FIRE_AMBIENT, 2.0f, 2.0f);
                }
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 3; ++i) {
                    this.loc.add(this.vec);
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                        break;
                    }
                    this.loc.getWorld().spawnParticle(Particle.FLAME, this.loc, 2, 0.07, 0.07, 0.07, 0.0);
                    this.loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, this.loc, 0);
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }).applyEffectsAndDamage(cachedStats, nbtItem, (LivingEntity)entity);
                            this.loc.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, this.loc, 0);
                            this.cancel();
                            return;
                        }
                    }
                }
                if (this.ti >= n2) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
