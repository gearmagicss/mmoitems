// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.MMOItems;
import java.util.logging.Level;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
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

public class RandomUnsocket extends DoubleStat implements ConsumableItemInteraction
{
    public RandomUnsocket() {
        super("RANDOM_UNSOCKET", Material.BOWL, "Random Unsocket", new String[] { "Number of gems (rounded down)", "that will pop out of an item when", "this is applied." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        if (!consumable.getMMOItem().hasData(ItemStats.RANDOM_UNSOCKET)) {
            return false;
        }
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
        final LiveMMOItem liveMMOItem = new LiveMMOItem(nbtItem);
        final ArrayList<MMOItem> gemstones = liveMMOItem.extractGemstones();
        if (gemstones.size() == 0) {
            Message.RANDOM_UNSOCKET_GEM_TOO_OLD.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(inventoryClickEvent.getCurrentItem())).send((CommandSender)player);
            return false;
        }
        final DoubleData doubleData = (DoubleData)consumable.getMMOItem().getData(ItemStats.RANDOM_UNSOCKET);
        int floor = 1;
        if (doubleData != null) {
            floor = SilentNumbers.floor(doubleData.getValue());
        }
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        while (floor > 0 && gemstones.size() > 0) {
            int floor2 = SilentNumbers.floor(SilentNumbers.randomRange(0.0, (double)gemstones.size()));
            if (floor2 >= gemstones.size()) {
                floor2 = gemstones.size() - 1;
            }
            final MMOItem mmoItem = gemstones.get(floor2);
            gemstones.remove(floor2);
            try {
                final ItemStack build = mmoItem.newBuilder().build();
                if (SilentNumbers.isAir(build)) {
                    continue;
                }
                list.add(build);
                String s;
                if (mmoItem.getAsGemColor() != null) {
                    s = mmoItem.getAsGemColor();
                }
                else {
                    s = GemSocketsData.getUncoloredGemSlot();
                }
                liveMMOItem.removeGemStone(mmoItem.getAsGemUUID(), s);
                --floor;
                Message.RANDOM_UNSOCKET_SUCCESS.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(inventoryClickEvent.getCurrentItem()), "#gem#", MMOUtils.getDisplayName(build)).send((CommandSender)player);
            }
            catch (Throwable t) {
                MMOItems.print(Level.WARNING, "Could not unsocket gem from item $u{0}$b: $f{1}", "Stat §eRandom Unsocket", SilentNumbers.getItemName(inventoryClickEvent.getCurrentItem()), t.getMessage());
            }
        }
        liveMMOItem.setData(ItemStats.GEM_SOCKETS, StatHistory.from(liveMMOItem, ItemStats.GEM_SOCKETS).recalculate(liveMMOItem.getUpgradeLevel()));
        inventoryClickEvent.setCurrentItem(liveMMOItem.newBuilder().build());
        final Iterator<ItemStack> iterator = player.getInventory().addItem((ItemStack[])list.toArray(new ItemStack[0])).values().iterator();
        while (iterator.hasNext()) {
            player.getWorld().dropItem(player.getLocation(), (ItemStack)iterator.next());
        }
        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0f, 2.0f);
        return true;
    }
}
