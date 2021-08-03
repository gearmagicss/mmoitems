// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import net.Indyuce.mmoitems.listener.CustomSoundListener;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.RepairItemEvent;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class RepairPower extends DoubleStat implements ConsumableItemInteraction
{
    public RepairPower() {
        super("REPAIR", Material.ANVIL, "Repair", new String[] { "The amount of durability your item", "can repair when set an item." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final int n = (int)consumable.getNBTItem().getStat(ItemStats.REPAIR.getId());
        if (n <= 0) {
            return false;
        }
        final Player player = playerData.getPlayer();
        if ((nbtItem.hasTag("MMOITEMS_REPAIR_TYPE") || consumable.getNBTItem().hasTag("MMOITEMS_REPAIR_TYPE")) && !nbtItem.getString("MMOITEMS_REPAIR_TYPE").equals(consumable.getNBTItem().getString("MMOITEMS_REPAIR_TYPE"))) {
            Message.UNABLE_TO_REPAIR.format(ChatColor.RED, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)player);
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            return false;
        }
        if (nbtItem.hasTag("MMOITEMS_DURABILITY")) {
            final RepairItemEvent repairItemEvent = new RepairItemEvent(playerData, consumable.getMMOItem(), nbtItem, n);
            Bukkit.getPluginManager().callEvent((Event)repairItemEvent);
            if (repairItemEvent.isCancelled()) {
                return false;
            }
            final DurabilityItem durabilityItem = new DurabilityItem(player, nbtItem);
            if (durabilityItem.getDurability() < durabilityItem.getMaxDurability()) {
                nbtItem.getItem().setItemMeta(durabilityItem.addDurability(repairItemEvent.getRepaired()).toItem().getItemMeta());
                Message.REPAIRED_ITEM.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(nbtItem.getItem()), "#amount#", "" + repairItemEvent.getRepaired()).send((CommandSender)player);
                CustomSoundListener.playConsumableSound(consumable.getItem(), player);
            }
            return true;
        }
        else {
            if (nbtItem.getBoolean("Unbreakable") || !nbtItem.getItem().hasItemMeta() || !(nbtItem.getItem().getItemMeta() instanceof Damageable) || ((Damageable)nbtItem.getItem().getItemMeta()).getDamage() <= 0) {
                return false;
            }
            final RepairItemEvent repairItemEvent2 = new RepairItemEvent(playerData, consumable.getMMOItem(), nbtItem, n);
            Bukkit.getPluginManager().callEvent((Event)repairItemEvent2);
            if (repairItemEvent2.isCancelled()) {
                return false;
            }
            final ItemMeta itemMeta = nbtItem.getItem().getItemMeta();
            ((Damageable)itemMeta).setDamage(Math.max(0, ((Damageable)itemMeta).getDamage() - repairItemEvent2.getRepaired()));
            nbtItem.getItem().setItemMeta(itemMeta);
            Message.REPAIRED_ITEM.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(nbtItem.getItem()), "#amount#", "" + repairItemEvent2.getRepaired()).send((CommandSender)player);
            CustomSoundListener.playConsumableSound(consumable.getItem(), player);
            return true;
        }
    }
}
