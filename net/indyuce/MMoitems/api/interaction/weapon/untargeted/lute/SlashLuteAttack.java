// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute;

import java.util.Iterator;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.ProjectileParticlesData;
import org.bukkit.Particle;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.util.SoundReader;
import org.bukkit.util.Vector;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class SlashLuteAttack implements LuteAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2, final Vector vector, final SoundReader soundReader) {
        new BukkitRunnable() {
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection();
            final Location loc = cachedStats.getPlayer().getLocation().add(0.0, 1.3, 0.0);
            double ti = 1.0;
            
            public void run() {
                final double ti = this.ti + 0.6;
                this.ti = ti;
                if (ti > 5.0) {
                    this.cancel();
                }
                soundReader.play(this.loc, 2.0f, (float)(0.5 + this.ti / n2));
                for (int i = -30; i < 30; i += 3) {
                    if (LuteAttackHandler.random.nextBoolean()) {
                        this.loc.setDirection(this.vec);
                        this.loc.setYaw(this.loc.getYaw() + i);
                        this.loc.setPitch(cachedStats.getPlayer().getEyeLocation().getPitch());
                        if (nbtItem.hasTag("MMOITEMS_PROJECTILE_PARTICLES")) {
                            final JsonObject jsonObject = (JsonObject)MythicLib.plugin.getJson().parse(nbtItem.getString("MMOITEMS_PROJECTILE_PARTICLES"), (Class)JsonObject.class);
                            final Particle value = Particle.valueOf(jsonObject.get("Particle").getAsString());
                            if (ProjectileParticlesData.isColorable(value)) {
                                ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(this.loc.getDirection().multiply(1.5 * this.ti)), Double.parseDouble(String.valueOf(jsonObject.get("Red"))), Double.parseDouble(String.valueOf(jsonObject.get("Green"))), Double.parseDouble(String.valueOf(jsonObject.get("Blue"))));
                            }
                            else {
                                ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(this.loc.getDirection().multiply(1.5 * this.ti)), 0.0, 0.0, 0.0);
                            }
                        }
                        else {
                            this.loc.getWorld().spawnParticle(Particle.NOTE, this.loc.clone().add(this.loc.getDirection().multiply(1.5 * this.ti)), 0);
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        for (final Entity entity : MMOUtils.getNearbyChunkEntities(cachedStats.getPlayer().getLocation())) {
            if (entity.getLocation().distanceSquared(cachedStats.getPlayer().getLocation()) < 40.0 && cachedStats.getPlayer().getEyeLocation().getDirection().angle(entity.getLocation().toVector().subtract(cachedStats.getPlayer().getLocation().toVector())) < 0.5235987755982988 && MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE }).applyEffectsAndDamage(cachedStats, nbtItem, (LivingEntity)entity);
            }
        }
    }
}
