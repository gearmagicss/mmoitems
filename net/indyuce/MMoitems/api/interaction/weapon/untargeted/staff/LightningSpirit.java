// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import io.lumine.mythic.lib.api.MMORayTraceResult;
import org.bukkit.Location;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.version.VersionSound;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class LightningSpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_BLAST.toSound(), 2.0f, 2.0f);
        final Location eyeLocation = cachedStats.getPlayer().getEyeLocation();
        final MMORayTraceResult rayTrace = MythicLib.plugin.getVersion().getWrapper().rayTrace(cachedStats.getPlayer(), n2, entity -> MMOUtils.canDamage(cachedStats.getPlayer(), entity));
        if (rayTrace.hasHit()) {
            new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }).applyEffectsAndDamage(cachedStats, nbtItem, rayTrace.getHit());
        }
        rayTrace.draw(eyeLocation, cachedStats.getPlayer().getEyeLocation().getDirection(), 2.0, location -> location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 0));
    }
}
