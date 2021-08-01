// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted;

import org.bukkit.entity.Entity;
import org.bukkit.Sound;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import io.lumine.mythic.lib.api.MMORayTraceResult;
import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.util.Vector;
import net.Indyuce.mmoitems.stat.StaffSpiritStat;
import net.Indyuce.mmoitems.api.interaction.util.UntargetedDurabilityItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Staff extends UntargetedWeapon
{
    public Staff(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem, WeaponType.LEFT_CLICK);
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
        final double value = this.getValue(temporary.getStat(ItemStats.ATTACK_DAMAGE), 1.0);
        final double value2 = this.getValue(this.getNBTItem().getStat(ItemStats.RANGE.getId()), MMOItems.plugin.getConfig().getDouble("default.range"));
        final StaffSpiritStat.StaffSpirit value3 = StaffSpiritStat.StaffSpirit.get(this.getNBTItem());
        if (value3 != null) {
            value3.getAttack().handle(temporary, this.getNBTItem(), value, value2);
            return;
        }
        final double radians = Math.toRadians(this.getPlayer().getEyeLocation().getYaw() + 160.0f);
        final Location add = this.getPlayer().getEyeLocation().add(new Vector(Math.cos(radians), 0.0, Math.sin(radians)).multiply(0.5));
        final MMORayTraceResult rayTrace = MythicLib.plugin.getVersion().getWrapper().rayTrace(temporary.getPlayer(), value2, entity -> MMOUtils.canDamage(temporary.getPlayer(), entity));
        if (rayTrace.hasHit()) {
            new ItemAttackResult(value, new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE, DamageType.MAGIC }).applyEffectsAndDamage(temporary, this.getNBTItem(), rayTrace.getHit());
        }
        rayTrace.draw(add, this.getPlayer().getEyeLocation().getDirection(), 2.0, location -> location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location, 0, 0.1, 0.1, 0.1, 0.0));
        this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), VersionSound.ENTITY_FIREWORK_ROCKET_TWINKLE.toSound(), 2.0f, 2.0f);
    }
    
    public void specialAttack(final LivingEntity livingEntity) {
        if (!MMOItems.plugin.getConfig().getBoolean("item-ability.staff.enabled")) {
            return;
        }
        if (!this.applyWeaponCosts(MMOItems.plugin.getConfig().getDouble("item-ability.staff.cooldown"), PlayerData.CooldownType.SPECIAL_ATTACK)) {
            return;
        }
        final double double1 = MMOItems.plugin.getConfig().getDouble("item-ability.staff.power");
        try {
            final Vector setY = livingEntity.getLocation().toVector().subtract(this.getPlayer().getLocation().toVector()).setY(0).normalize().multiply(1.75 * double1).setY(0.65 * double1);
            livingEntity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, livingEntity.getLocation().add(0.0, 1.0, 0.0), 0);
            livingEntity.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, livingEntity.getLocation().add(0.0, 1.0, 0.0), 16, 0.0, 0.0, 0.0, 0.1);
            livingEntity.setVelocity(setY);
            livingEntity.playEffect(EntityEffect.HURT);
            livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 2.0f);
        }
        catch (IllegalArgumentException ex) {}
    }
}
