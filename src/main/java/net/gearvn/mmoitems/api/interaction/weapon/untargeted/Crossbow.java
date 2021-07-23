// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted;

import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Arrow;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.interaction.util.UntargetedDurabilityItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.listener.ItemUse;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.GameMode;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Crossbow extends UntargetedWeapon
{
    public Crossbow(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem, WeaponType.RIGHT_CLICK);
    }
    
    @Override
    public void untargetedAttack(final EquipmentSlot equipmentSlot) {
        if (this.getPlayer().getGameMode() != GameMode.CREATIVE && !this.getPlayer().getInventory().containsAtLeast(new ItemStack(Material.ARROW), 1)) {
            return;
        }
        if (!ItemUse.eitherHandSuccess(this.getPlayer(), this.getNBTItem(), equipmentSlot)) {
            return;
        }
        final PlayerStats stats = this.getPlayerData().getStats();
        if (!this.applyWeaponCosts(1.0 / this.getValue(stats.getStat(ItemStats.ATTACK_SPEED), MMOItems.plugin.getConfig().getDouble("default.attack-speed")), PlayerData.CooldownType.ATTACK)) {
            return;
        }
        final UntargetedDurabilityItem untargetedDurabilityItem = new UntargetedDurabilityItem(this.getPlayer(), this.getNBTItem(), equipmentSlot);
        if (untargetedDurabilityItem.isBroken()) {
            return;
        }
        if (untargetedDurabilityItem.isValid()) {
            untargetedDurabilityItem.decreaseDurability(1).update();
        }
        if (this.getPlayer().getGameMode() != GameMode.CREATIVE) {
            this.getPlayer().getInventory().removeItem(new ItemStack[] { new ItemStack(Material.ARROW) });
        }
        this.getPlayer().getWorld().playSound(this.getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
        final Arrow arrow = (Arrow)this.getPlayer().launchProjectile((Class)Arrow.class);
        arrow.setVelocity(this.getPlayer().getEyeLocation().getDirection().multiply(3.0 * this.getValue(this.getNBTItem().getStat(ItemStats.ARROW_VELOCITY.getId()), 1.0)));
        this.getPlayer().setVelocity(this.getPlayer().getVelocity().setX(0).setZ(0));
        MMOItems.plugin.getEntities().registerCustomProjectile(this.getNBTItem(), stats.newTemporary(), (Entity)arrow, true);
    }
}
