// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.ApplySoulboundEvent;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
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

public class SoulbindingChance extends DoubleStat implements ConsumableItemInteraction
{
    private static final Random random;
    
    public SoulbindingChance() {
        super("SOULBINDING_CHANCE", VersionMaterial.ENDER_EYE.toMaterial(), "Soulbinding Chance", new String[] { "Defines the chance your item has to", "link another item to your soul,", "preventing other players from using it." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final Player player = playerData.getPlayer();
        final double stat = consumable.getNBTItem().getStat("SOULBINDING_CHANCE");
        if (stat <= 0.0) {
            return false;
        }
        if (nbtItem.getItem().getAmount() > 1) {
            Message.CANT_BIND_STACKED.format(ChatColor.RED, new String[0]).send(player, "soulbound");
            return false;
        }
        final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(nbtItem);
        if (volatileMMOItem.hasData(ItemStats.SOULBOUND)) {
            final SoulboundData soulboundData = (SoulboundData)volatileMMOItem.getData(ItemStats.SOULBOUND);
            Message.CANT_BIND_ITEM.format(ChatColor.RED, "#player#", soulboundData.getName(), "#level#", MMOUtils.intToRoman(soulboundData.getLevel())).send(player, "soulbound");
            return false;
        }
        if (SoulbindingChance.random.nextDouble() >= stat / 100.0) {
            Message.UNSUCCESSFUL_SOULBOUND.format(ChatColor.RED, new String[0]).send(player, "soulbound");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return true;
        }
        final ApplySoulboundEvent applySoulboundEvent = new ApplySoulboundEvent(playerData, consumable.getMMOItem(), nbtItem);
        Bukkit.getPluginManager().callEvent((Event)applySoulboundEvent);
        if (applySoulboundEvent.isCancelled()) {
            return false;
        }
        final int n = (int)Math.max(1.0, consumable.getNBTItem().getStat("SOULBOUND_LEVEL"));
        final LiveMMOItem liveMMOItem;
        (liveMMOItem = new LiveMMOItem(nbtItem)).setData(ItemStats.SOULBOUND, new SoulboundData(player.getUniqueId(), player.getName(), n));
        nbtItem.getItem().setItemMeta(liveMMOItem.newBuilder().build().getItemMeta());
        Message.SUCCESSFULLY_BIND_ITEM.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(nbtItem.getItem()), "#level#", MMOUtils.intToRoman(n)).send(player, "soulbound");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        return true;
    }
    
    static {
        random = new Random();
    }
}
