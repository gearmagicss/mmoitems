// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class CanUnsocket extends BooleanStat implements ConsumableItemInteraction
{
    public CanUnsocket() {
        super("CAN_UNSOCKET", Material.PAPER, "Can Unsocket?", new String[] { "This item, when used on another item, if", "that other item has Gem Stones", "may be used to remove those Gems." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        if (type == null) {
            return false;
        }
        final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(nbtItem);
        if (!volatileMMOItem.hasData(ItemStats.GEM_SOCKETS)) {
            return false;
        }
        final GemSocketsData gemSocketsData = (GemSocketsData)volatileMMOItem.getData(ItemStats.GEM_SOCKETS);
        if (gemSocketsData == null || gemSocketsData.getGemstones().size() == 0) {
            return false;
        }
        final Player player = playerData.getPlayer();
        if (new LiveMMOItem(nbtItem).extractGemstones().size() == 0) {
            Message.RANDOM_UNSOCKET_GEM_TOO_OLD.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(inventoryClickEvent.getCurrentItem())).send((CommandSender)player);
            return false;
        }
        return true;
    }
}
