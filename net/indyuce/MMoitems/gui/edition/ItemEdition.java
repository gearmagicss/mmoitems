// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.Indyuce.mmoitems.stat.type.InternalStat;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class ItemEdition extends EditionInventory
{
    private static final int[] slots;
    
    public ItemEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final int n = (this.page - 1) * ItemEdition.slots.length;
        final int b = this.page * ItemEdition.slots.length;
        int i = 0;
        final List<Object> list = new ArrayList<Object>(this.getEdited().getType().getAvailableStats()).stream().filter(itemStat -> itemStat.hasValidMaterial(this.getCachedItem()) && !(itemStat instanceof InternalStat)).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList());
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Item Edition: " + this.getEdited().getId());
        for (int j = n; j < Math.min(list.size(), b); ++j) {
            final ItemStat itemStat2 = list.get(j);
            final ItemStack itemStack = new ItemStack(itemStat2.getDisplayMaterial());
            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.setDisplayName(ChatColor.GREEN + itemStat2.getName());
            final ArrayList<String> lore = new ArrayList<String>();
            final String[] lore2 = itemStat2.getLore();
            for (int length = lore2.length, k = 0; k < length; ++k) {
                lore.add(ChatColor.GRAY + MythicLib.plugin.parseColors(lore2[k]));
            }
            lore.add("");
            itemStat2.whenDisplayed(lore, this.getEventualStatData(itemStat2));
            itemMeta.setLore((List)lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(ItemEdition.slots[i++], MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack).addTag(new ItemTag[] { new ItemTag("guiStat", (Object)itemStat2.getId()) }).toItem());
        }
        final ItemStack item = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta2 = item.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.RED + "- No Item Stat -");
        item.setItemMeta(itemMeta2);
        final ItemStack itemStack2 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta3 = itemStack2.getItemMeta();
        itemMeta3.setDisplayName(ChatColor.GREEN + "Next Page");
        itemStack2.setItemMeta(itemMeta3);
        final ItemStack itemStack3 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta4 = itemStack3.getItemMeta();
        itemMeta4.setDisplayName(ChatColor.GREEN + "Previous Page");
        itemStack3.setItemMeta(itemMeta4);
        this.addEditionInventoryItems(inventory, true);
        while (i < ItemEdition.slots.length) {
            inventory.setItem(ItemEdition.slots[i++], item);
        }
        inventory.setItem(27, (this.page > 1) ? itemStack3 : null);
        inventory.setItem(35, (list.size() > b) ? itemStack2 : null);
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory()) {
            return;
        }
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        if (!MMOUtils.isMetaItem(currentItem, false) || inventoryClickEvent.getInventory().getItem(4) == null) {
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Next Page")) {
            ++this.page;
            this.open();
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Previous Page")) {
            --this.page;
            this.open();
        }
        final String string = NBTItem.get(currentItem).getString("guiStat");
        if (!string.equals("")) {
            MMOItems.plugin.getStats().get(string).whenClicked(this, inventoryClickEvent);
        }
    }
    
    public ItemEdition onPage(final int page) {
        this.page = page;
        return this;
    }
    
    static {
        slots = new int[] { 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
    }
}
