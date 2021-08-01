// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted;

import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.api.MMORayTraceResult;
import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.Sound;
import org.bukkit.Color;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.util.Vector;
import net.Indyuce.mmoitems.api.interaction.util.UntargetedDurabilityItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Musket extends UntargetedWeapon
{
    public Musket(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem, WeaponType.RIGHT_CLICK);
    }
    
    @Override
    public void untargetedAttack(final EquipmentSlot equipmentSlot) {
        final PlayerStats.CachedStats temporary = this.getPlayerData().getStats().newTemporary(io.lumine.mythic.lib.api.player.EquipmentSlot.fromBukkit(equipmentSlot));
        if (!this.applyWeaponCosts(1.0 / this.getValue(temporary.getStat(ItemStats.ATTACK_SPEED), MMOItems.plugin.getConfig().getDouble("default.attack-speed")), PlayerData.CooldownType.ATTACK)) {
            return;
        }
        final UntargetedDurabilityItem untargetedDurabilityItem = new UntargetedDurabilityItem(this.getPlayer(), this.getNBTItem(), equipmentSlot);
        if (untargetedDurabilityItem.isBroken()) {
            return;
        }
        if (untargetedDurabilityItem.isValid()) {
            untargetedDurabilityItem.decreaseDurability(1).update();
        }
        final double stat = temporary.getStat(ItemStats.ATTACK_DAMAGE);
        final double value = this.getValue(this.getNBTItem().getStat(ItemStats.RANGE.getId()), MMOItems.plugin.getConfig().getDouble("default.range"));
        final double value2 = this.getValue(this.getNBTItem().getStat(ItemStats.RECOIL.getId()), MMOItems.plugin.getConfig().getDouble("default.recoil"));
        final double stat2 = this.getNBTItem().getStat(ItemStats.KNOCKBACK.getId());
        if (stat2 > 0.0) {
            this.getPlayer().setVelocity(this.getPlayer().getVelocity().add(this.getPlayer().getEyeLocation().getDirection().setY(0).normalize().multiply(-1.0 * stat2).setY(-0.2)));
        }
        final double radians = Math.toRadians(this.getPlayer().getEyeLocation().getYaw() + 160.0f);
        final Location add = this.getPlayer().getEyeLocation().add(new Vector(Math.cos(radians), 0.0, Math.sin(radians)).multiply(0.5));
        add.setPitch((float)(add.getPitch() + (Musket.RANDOM.nextDouble() - 0.5) * 2.0 * value2));
        add.setYaw((float)(add.getYaw() + (Musket.RANDOM.nextDouble() - 0.5) * 2.0 * value2));
        final Vector direction = add.getDirection();
        final MMORayTraceResult rayTrace = MythicLib.plugin.getVersion().getWrapper().rayTrace(temporary.getPlayer(), direction, value, entity -> MMOUtils.canDamage(temporary.getPlayer(), entity));
        if (rayTrace.hasHit()) {
            new ItemAttackResult(stat, new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE, DamageType.PHYSICAL }).applyEffectsAndDamage(temporary, this.getNBTItem(), rayTrace.getHit());
        }
        rayTrace.draw(add, direction, 2.0, Color.BLACK);
        this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 2.0f, 2.0f);
    }
}
