// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.BreakSoulboundEvent;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import java.util.Random;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.DoubleStat;

public class SoulbindingBreakChance extends DoubleStat implements ConsumableItemInteraction
{
    private static final Random random;
    
    public SoulbindingBreakChance() {
        super("SOULBOUND_BREAK_CHANCE", VersionMaterial.ENDER_EYE.toMaterial(), "Soulbound Break Chance", new String[] { "The chance of breaking an item's", "soulbound when drag & drop'd on it.", "This chance is lowered depending", "on the soulbound's level." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final Player player = playerData.getPlayer();
        final double stat = consumable.getNBTItem().getStat("SOULBOUND_BREAK_CHANCE");
        if (stat <= 0.0) {
            return false;
        }
        final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(nbtItem);
        if (!volatileMMOItem.hasData(ItemStats.SOULBOUND)) {
            Message.NO_SOULBOUND.format(ChatColor.RED, new String[0]).send(player, "soulbound");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return false;
        }
        final SoulboundData soulboundData = (SoulboundData)volatileMMOItem.getData(ItemStats.SOULBOUND);
        if (Math.max(1.0, consumable.getNBTItem().getStat(ItemStats.SOULBOUND_LEVEL.getId())) < soulboundData.getLevel()) {
            Message.LOW_SOULBOUND_LEVEL.format(ChatColor.RED, "#level#", MMOUtils.intToRoman(soulboundData.getLevel())).send(player, "soulbound");
            return false;
        }
        if (SoulbindingBreakChance.random.nextDouble() < stat / 100.0) {
            final BreakSoulboundEvent breakSoulboundEvent = new BreakSoulboundEvent(playerData, consumable.getMMOItem(), nbtItem);
            Bukkit.getPluginManager().callEvent((Event)breakSoulboundEvent);
            if (breakSoulboundEvent.isCancelled()) {
                return false;
            }
            final LiveMMOItem liveMMOItem;
            (liveMMOItem = new LiveMMOItem(nbtItem)).removeData(ItemStats.SOULBOUND);
            nbtItem.getItem().setItemMeta(liveMMOItem.newBuilder().build().getItemMeta());
            Message.SUCCESSFULLY_BREAK_BIND.format(ChatColor.YELLOW, "#level#", MMOUtils.intToRoman(soulboundData.getLevel())).send(player, "soulbound");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 2.0f);
        }
        else {
            Message.UNSUCCESSFUL_SOULBOUND_BREAK.format(ChatColor.RED, new String[0]).send(player, "soulbound");
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 0.0f);
        }
        return true;
    }
    
    static {
        random = new Random();
    }
}
