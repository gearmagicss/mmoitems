// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Iterator;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.DeconstructItemEvent;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class CanDeconstruct extends BooleanStat implements ConsumableItemInteraction
{
    public CanDeconstruct() {
        super("CAN_DECONSTRUCT", Material.PAPER, "Can Deconstruct?", new String[] { "Players can deconstruct their item", "using this consumable, creating", "another random item." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final String string = nbtItem.getString("MMOITEMS_TIER");
        if (string.equals("") || !consumable.getNBTItem().getBoolean("MMOITEMS_CAN_DECONSTRUCT")) {
            return false;
        }
        final List<ItemStack> deconstructedLoot = MMOItems.plugin.getTiers().get(string).getDeconstructedLoot(playerData);
        if (deconstructedLoot.isEmpty()) {
            return false;
        }
        final DeconstructItemEvent deconstructItemEvent = new DeconstructItemEvent(playerData, consumable.getMMOItem(), nbtItem, deconstructedLoot);
        Bukkit.getPluginManager().callEvent((Event)deconstructItemEvent);
        if (deconstructItemEvent.isCancelled()) {
            return false;
        }
        final Player player = playerData.getPlayer();
        Message.SUCCESSFULLY_DECONSTRUCTED.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(inventoryClickEvent.getCurrentItem())).send((CommandSender)player);
        inventoryClickEvent.getCurrentItem().setAmount(inventoryClickEvent.getCurrentItem().getAmount() - 1);
        final Iterator<ItemStack> iterator = player.getInventory().addItem((ItemStack[])deconstructedLoot.toArray(new ItemStack[0])).values().iterator();
        while (iterator.hasNext()) {
            player.getWorld().dropItem(player.getLocation(), (ItemStack)iterator.next());
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        return true;
    }
}
