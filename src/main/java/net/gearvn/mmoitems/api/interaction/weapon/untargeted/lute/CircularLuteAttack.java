// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.stat.data.ProjectileParticlesData;
import org.bukkit.Particle;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonObject;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.util.SoundReader;
import org.bukkit.util.Vector;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class CircularLuteAttack implements LuteAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2, final Vector vector, final SoundReader soundReader) {
        new BukkitRunnable() {
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection().multiply(0.4);
            final Location loc = cachedStats.getPlayer().getEyeLocation();
            int ti = 0;
            
            public void run() {
                if (this.ti++ > n2) {
                    this.cancel();
                }
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 3; ++i) {
                    this.loc.add(this.vec.add(vector));
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                        break;
                    }
                    final double n = this.ti / 3.0;
                    final Vector rotateFunc = MMOUtils.rotateFunc(new Vector(Math.cos(n), Math.sin(n), 0.0).multiply(0.3), this.loc);
                    if (nbtItem.hasTag("MMOITEMS_PROJECTILE_PARTICLES")) {
                        final JsonObject jsonObject = (JsonObject)MythicLib.plugin.getJson().parse(nbtItem.getString("MMOITEMS_PROJECTILE_PARTICLES"), (Class)JsonObject.class);
                        final Particle value = Particle.valueOf(jsonObject.get("Particle").getAsString());
                        if (ProjectileParticlesData.isColorable(value)) {
                            final double double1 = Double.parseDouble(String.valueOf(jsonObject.get("Red")));
                            final double double2 = Double.parseDouble(String.valueOf(jsonObject.get("Green")));
                            final double double3 = Double.parseDouble(String.valueOf(jsonObject.get("Blue")));
                            ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(rotateFunc), double1, double2, double3);
                            ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(rotateFunc.multiply(-1)), double1, double2, double3);
                        }
                        else {
                            ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(rotateFunc), 0.0, 0.0, 0.0);
                            ProjectileParticlesData.shootParticle(cachedStats.getPlayer(), value, this.loc.clone().add(rotateFunc.multiply(-1)), 0.0, 0.0, 0.0);
                        }
                    }
                    else {
                        this.loc.getWorld().spawnParticle(Particle.NOTE, this.loc.clone().add(rotateFunc), 0);
                        this.loc.getWorld().spawnParticle(Particle.NOTE, this.loc.clone().add(rotateFunc.multiply(-1)), 0);
                    }
                    if (i == 0) {
                        soundReader.play(this.loc, 2.0f, (float)(0.5 + this.ti / n2));
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(cachedStats.getPlayer(), this.loc, entity)) {
                            new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE }).applyEffectsAndDamage(cachedStats, nbtItem, (LivingEntity)entity);
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
