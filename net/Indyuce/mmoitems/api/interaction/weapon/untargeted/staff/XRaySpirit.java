// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff;

import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.api.MMORayTraceResult;
import org.bukkit.Location;
import org.bukkit.Color;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.util.Vector;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.PlayerStats;

public class XRaySpirit implements StaffAttackHandler
{
    @Override
    public void handle(final PlayerStats.CachedStats cachedStats, final NBTItem nbtItem, final double n, final double n2) {
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2.0f, 2.0f);
        final double radians = Math.toRadians(cachedStats.getPlayer().getEyeLocation().getYaw() + 160.0f);
        final Location add = cachedStats.getPlayer().getEyeLocation().add(new Vector(Math.cos(radians), 0.0, Math.sin(radians)).multiply(0.5));
        final MMORayTraceResult rayTrace = MythicLib.plugin.getVersion().getWrapper().rayTrace(cachedStats.getPlayer(), n2, entity -> MMOUtils.canDamage(cachedStats.getPlayer(), entity));
        if (rayTrace.hasHit()) {
            new ItemAttackResult(n, new DamageType[] { DamageType.WEAPON, DamageType.MAGIC }).applyEffectsAndDamage(cachedStats, nbtItem, rayTrace.getHit());
        }
        rayTrace.draw(add, cachedStats.getPlayer().getEyeLocation().getDirection(), 2.0, Color.BLACK);
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.4f, 2.0f);
    }
}
