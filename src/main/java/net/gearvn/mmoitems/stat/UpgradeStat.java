// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.api.item.util.DynamicLore;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.event.item.UpgradeItemEvent;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.UpgradingEdition;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import org.bukkit.Material;
import java.util.Random;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class UpgradeStat extends ItemStat implements ConsumableItemInteraction
{
    private static final Random random;
    
    public UpgradeStat() {
        super("UPGRADE", Material.FLINT, "Item Upgrading", new String[] { "Upgrading your item improves its", "current stats. It requires either a", "consumable or a specific crafting ", "station. Upgrading may sometimes &cfail&7..." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "consumable", "accessory" }, new Material[0]);
    }
    
    @Override
    public UpgradeData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        return new UpgradeData((ConfigurationSection)o);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)statData.toString()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new UpgradingEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("upgrade")) {
            editionInventory.getEditedSection().set("upgrade", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the upgrading setup.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        if (array[0].equals("ref")) {
            editionInventory.getEditedSection().set("upgrade.reference", (Object)s);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Upgrading reference successfully changed to " + ChatColor.GOLD + s + ChatColor.GRAY + ".");
            return;
        }
        if (array[0].equals("max")) {
            final int int1 = Integer.parseInt(s);
            editionInventory.getEditedSection().set("upgrade.max", (Object)int1);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Max upgrades successfully set to " + ChatColor.GOLD + int1 + ChatColor.GRAY + ".");
            return;
        }
        if (array[0].equals("rate")) {
            final double double1 = MMOUtils.parseDouble(s);
            editionInventory.getEditedSection().set("upgrade.success", (Object)double1);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Upgrading rate successfully set to " + ChatColor.GOLD + double1 + "%" + ChatColor.GRAY + ".");
            return;
        }
        Validate.isTrue(MMOItems.plugin.getUpgrades().hasTemplate(s), "Could not find any upgrade template with ID '" + s + "'.");
        editionInventory.getEditedSection().set("upgrade.template", (Object)s);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Upgrading template successfully changed to " + ChatColor.GOLD + s + ChatColor.GRAY + ".");
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                return new UpgradeData(new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject());
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to setup upgrading.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new UpgradeData(null, null, false, false, 0, 0.0);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final VolatileMMOItem mmoItem = consumable.getMMOItem();
        final Player player = playerData.getPlayer();
        if (!mmoItem.hasData(ItemStats.UPGRADE) || !nbtItem.hasTag(ItemStats.UPGRADE.getNBTPath())) {
            return false;
        }
        if (nbtItem.getItem().getAmount() > 1) {
            Message.CANT_UPGRADED_STACK.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
            return false;
        }
        final LiveMMOItem liveMMOItem = new LiveMMOItem(nbtItem);
        final UpgradeData upgradeData = (UpgradeData)liveMMOItem.getData(ItemStats.UPGRADE);
        if (!upgradeData.canLevelUp()) {
            Message.MAX_UPGRADES_HIT.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
            return false;
        }
        final UpgradeData upgradeData2 = (UpgradeData)mmoItem.getData(ItemStats.UPGRADE);
        if (!upgradeData2.matchesReference(upgradeData)) {
            Message.WRONG_UPGRADE_REFERENCE.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
            return false;
        }
        final UpgradeItemEvent upgradeItemEvent = new UpgradeItemEvent(playerData, mmoItem, liveMMOItem, upgradeData2, upgradeData);
        Bukkit.getPluginManager().callEvent((Event)upgradeItemEvent);
        if (upgradeItemEvent.isCancelled()) {
            return false;
        }
        upgradeData.upgrade(liveMMOItem);
        final NBTItem buildNBT = liveMMOItem.newBuilder().buildNBT();
        if (MMOItems.plugin.getLanguage().upgradeRequirementsCheck && !playerData.getRPG().canUse(buildNBT, false)) {
            Message.UPGRADE_REQUIREMENT_SAFE_CHECK.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
            return false;
        }
        if (UpgradeStat.random.nextDouble() > upgradeData2.getSuccess() * upgradeData.getSuccess()) {
            Message.UPGRADE_FAIL.format(ChatColor.RED, new String[0]).send((CommandSender)player);
            if (upgradeData.destroysOnFail()) {
                inventoryClickEvent.getCurrentItem().setAmount(0);
            }
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 2.0f);
            return true;
        }
        Message.UPGRADE_SUCCESS.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(inventoryClickEvent.getCurrentItem())).send((CommandSender)player);
        inventoryClickEvent.getCurrentItem().setItemMeta(new DynamicLore(buildNBT).build().getItemMeta());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        return true;
    }
    
    static {
        random = new Random();
    }
}
