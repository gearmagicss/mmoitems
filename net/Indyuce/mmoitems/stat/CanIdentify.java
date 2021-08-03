// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.item.util.identify.IdentifiedItem;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.IdentifyItemEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class CanIdentify extends BooleanStat implements ConsumableItemInteraction
{
    public CanIdentify() {
        super("CAN_IDENTIFY", Material.PAPER, "Can Identify?", new String[] { "Players can identify & make their", "item usable using this consumable." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        if (type != null) {
            return false;
        }
        if (!consumable.getNBTItem().getBoolean("MMOITEMS_CAN_IDENTIFY") || !nbtItem.hasTag("MMOITEMS_UNIDENTIFIED_ITEM")) {
            return false;
        }
        final Player player = playerData.getPlayer();
        if (nbtItem.getItem().getAmount() > 1) {
            Message.CANNOT_IDENTIFY_STACKED_ITEMS.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            return false;
        }
        final IdentifyItemEvent identifyItemEvent = new IdentifyItemEvent(playerData, consumable.getMMOItem(), nbtItem);
        Bukkit.getPluginManager().callEvent((Event)identifyItemEvent);
        if (identifyItemEvent.isCancelled()) {
            return false;
        }
        inventoryClickEvent.setCurrentItem(new IdentifiedItem(nbtItem).identify());
        Message.SUCCESSFULLY_IDENTIFIED.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)player);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        return true;
    }
}
