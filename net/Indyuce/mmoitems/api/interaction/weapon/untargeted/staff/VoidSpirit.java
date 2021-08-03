// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class VoidSpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        final Vector direction = cachedStats.getPlayer().getEyeLocation().getDirection();
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_WITHER_SHOOT, 2.0f, 2.0f);
        final ShulkerBullet shulkerBullet = (ShulkerBullet)cachedStats.getPlayer().getWorld().spawnEntity(cachedStats.getPlayer().getLocation().add(0.0, 1.0, 0.0), EntityType.valueOf("SHULKER_BULLET"));
        shulkerBullet.setShooter((ProjectileSource)cachedStats.getPlayer());
        new BukkitRunnable() {
            double ti = 0.0;
            
            public void run() {
                this.ti += 0.1;
                if (shulkerBullet.isDead() || this.ti >= n2 / 4.0) {
                    shulkerBullet.remove();
                    this.cancel();
                }
                shulkerBullet.setVelocity(direction);
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        MMOItems.plugin.getEntities().registerCustomEntity((Entity)shulkerBullet, new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }), 0.0, cachedStats, nbtItem);
    }
}
