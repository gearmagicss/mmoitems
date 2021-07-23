// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui;

import net.Indyuce.mmoitems.gui.edition.ItemEdition;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryAction;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.edition.NewItemEdition;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import io.lumine.mythic.utils.adventure.text.TextComponent;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.utils.adventure.text.Component;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.List;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.ChatColor;
import java.util.Collection;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import java.util.LinkedHashMap;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class ItemBrowser extends PluginInventory
{
    private final Map<String, ItemStack> cached;
    private final Type type;
    private boolean deleteMode;
    private static final int[] slots;
    
    public ItemBrowser(final Player player) {
        this(player, null);
    }
    
    public ItemBrowser(final Player player, final Type type) {
        super(player);
        this.cached = new LinkedHashMap<String, ItemStack>();
        this.type = type;
    }
    
    @Override
    public Inventory getInventory() {
        final int n = (this.page - 1) * ItemBrowser.slots.length;
        final int n2 = this.page * ItemBrowser.slots.length;
        int i = 0;
        if (this.type == null) {
            final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Item Explorer");
            final ArrayList<Type> list = (ArrayList<Type>)new ArrayList<Object>(MMOItems.plugin.getTypes().getAll());
            for (int j = n; j < Math.min(n2, list.size()); ++j) {
                final Type type = list.get(j);
                final int size = MMOItems.plugin.getTemplates().getTemplates(type).size();
                final ItemStack item = type.getItem();
                item.setAmount(Math.max(1, Math.min(64, size)));
                final ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + type.getName() + ChatColor.DARK_GRAY + " (Click to browse)");
                itemMeta.addItemFlags(ItemFlag.values());
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "There " + ((size != 1) ? "are" : "is") + " " + ((size < 1) ? ("" + ChatColor.RED + ChatColor.ITALIC + "no") : ("" + ChatColor.GOLD + ChatColor.ITALIC + size)) + ChatColor.GRAY + ChatColor.ITALIC + " item" + ((size != 1) ? "s" : "") + " in that type.");
                itemMeta.setLore((List)lore);
                item.setItemMeta(itemMeta);
                inventory.setItem(ItemBrowser.slots[i++], NBTItem.get(item).addTag(new ItemTag[] { new ItemTag("typeId", (Object)type.getId()) }).toItem());
            }
            final ItemStack item2 = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
            final ItemMeta itemMeta2 = item2.getItemMeta();
            itemMeta2.setDisplayName(ChatColor.RED + "- No type -");
            item2.setItemMeta(itemMeta2);
            final ItemStack itemStack = new ItemStack(Material.ARROW);
            final ItemMeta itemMeta3 = itemStack.getItemMeta();
            itemMeta3.setDisplayName(ChatColor.GREEN + "Next Page");
            itemStack.setItemMeta(itemMeta3);
            final ItemStack itemStack2 = new ItemStack(Material.ARROW);
            final ItemMeta itemMeta4 = itemStack2.getItemMeta();
            itemMeta4.setDisplayName(ChatColor.GREEN + "Previous Page");
            itemStack2.setItemMeta(itemMeta4);
            while (i < ItemBrowser.slots.length) {
                inventory.setItem(ItemBrowser.slots[i++], item2);
            }
            inventory.setItem(18, (this.page > 1) ? itemStack2 : null);
            inventory.setItem(26, (n2 >= MMOItems.plugin.getTypes().getAll().size()) ? null : itemStack);
            return inventory;
        }
        final ItemStack item3 = VersionMaterial.RED_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta5 = item3.getItemMeta();
        itemMeta5.setDisplayName(ChatColor.RED + "- Error -");
        final ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "An error occurred while");
        lore2.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "trying to generate that item.");
        itemMeta5.setLore((List)lore2);
        item3.setItemMeta(itemMeta5);
        final ArrayList<MMOItemTemplate> list2 = (ArrayList<MMOItemTemplate>)new ArrayList<Object>(MMOItems.plugin.getTemplates().getTemplates(this.type));
        final Inventory inventory2 = Bukkit.createInventory((InventoryHolder)this, 54, (this.deleteMode ? "Delete Mode: " : "Item Explorer: ") + this.type.getName());
        for (int k = n; k < Math.min(n2, list2.size()); ++k) {
            final MMOItemTemplate mmoItemTemplate = list2.get(k);
            final ItemStack build = mmoItemTemplate.newBuilder(this.playerData.getRPG()).build().newBuilder().build();
            if (build == null || build.getType() == Material.AIR) {
                this.cached.put(mmoItemTemplate.getId(), item3);
                inventory2.setItem(ItemBrowser.slots[i++], item3);
            }
            else {
                final NBTItem value = NBTItem.get(build);
                final List loreComponents = value.getLoreComponents();
                loreComponents.add(Component.empty());
                if (this.deleteMode) {
                    loreComponents.add(LegacyComponent.parse(ChatColor.RED + "\u2716" + " CLICK TO DELETE " + "\u2716"));
                    value.setDisplayNameComponent((Component)((TextComponent.Builder)((TextComponent.Builder)Component.text().append(LegacyComponent.parse(ChatColor.RED + "DELETE: "))).append(value.getDisplayNameComponent())).build());
                }
                loreComponents.add(LegacyComponent.parse(ChatColor.YELLOW + "\u25b8" + " Left click to obtain this item."));
                loreComponents.add(LegacyComponent.parse(ChatColor.YELLOW + "\u25b8" + " Right click to edit this item."));
                value.setLoreComponents(loreComponents);
                this.cached.put(mmoItemTemplate.getId(), value.toItem());
                inventory2.setItem(ItemBrowser.slots[i++], (ItemStack)this.cached.get(mmoItemTemplate.getId()));
            }
        }
        final ItemStack item4 = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta6 = item4.getItemMeta();
        itemMeta6.setDisplayName(ChatColor.RED + "- No Item -");
        item4.setItemMeta(itemMeta6);
        final ItemStack itemStack3 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta7 = itemStack3.getItemMeta();
        itemMeta7.setDisplayName(ChatColor.GREEN + "Next Page");
        itemStack3.setItemMeta(itemMeta7);
        final ItemStack itemStack4 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta8 = itemStack4.getItemMeta();
        itemMeta8.setDisplayName(ChatColor.GREEN + "\u21e8" + " Back");
        itemStack4.setItemMeta(itemMeta8);
        final ItemStack itemStack5 = new ItemStack(VersionMaterial.WRITABLE_BOOK.toMaterial());
        final ItemMeta itemMeta9 = itemStack5.getItemMeta();
        itemMeta9.setDisplayName(ChatColor.GREEN + "Create New");
        itemStack5.setItemMeta(itemMeta9);
        final ItemStack itemStack6 = new ItemStack(VersionMaterial.CAULDRON.toMaterial());
        final ItemMeta itemMeta10 = itemStack6.getItemMeta();
        itemMeta10.setDisplayName(ChatColor.RED + (this.deleteMode ? "Cancel Deletion" : "Delete Item"));
        itemStack6.setItemMeta(itemMeta10);
        final ItemStack itemStack7 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta11 = itemStack7.getItemMeta();
        itemMeta11.setDisplayName(ChatColor.GREEN + "Previous Page");
        itemStack7.setItemMeta(itemMeta11);
        if (this.type == Type.BLOCK) {
            final ItemStack itemStack8 = new ItemStack(Material.HOPPER);
            final ItemMeta itemMeta12 = itemStack8.getItemMeta();
            itemMeta12.setDisplayName(ChatColor.GREEN + "Download Default Resourcepack");
            itemMeta12.setLore((List)Arrays.asList(ChatColor.LIGHT_PURPLE + "Only seeing stone blocks?", "", ChatColor.RED + "By downloading the default resourcepack you can", ChatColor.RED + "edit the blocks however you want.", ChatColor.RED + "You will still have to add it to your server!"));
            itemStack8.setItemMeta(itemMeta12);
            inventory2.setItem(45, itemStack8);
        }
        while (i < ItemBrowser.slots.length) {
            inventory2.setItem(ItemBrowser.slots[i++], item4);
        }
        if (!this.deleteMode) {
            inventory2.setItem(51, itemStack5);
        }
        inventory2.setItem(47, itemStack6);
        inventory2.setItem(49, itemStack4);
        inventory2.setItem(18, (this.page > 1) ? itemStack7 : null);
        inventory2.setItem(26, (n2 >= list2.size()) ? null : itemStack3);
        return inventory2;
    }
    
    public Type getType() {
        return this.type;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory()) {
            return;
        }
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        if (MMOUtils.isMetaItem(currentItem, false)) {
            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Next Page")) {
                ++this.page;
                this.open();
            }
            else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Previous Page")) {
                --this.page;
                this.open();
            }
            else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "\u21e8" + " Back")) {
                new ItemBrowser(this.getPlayer()).open();
            }
            else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Cancel Deletion")) {
                this.deleteMode = false;
                this.open();
            }
            else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Create New")) {
                new NewItemEdition(this).enable("Write in the chat the text you want.");
            }
            else if (this.type != null && currentItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Delete Item")) {
                this.deleteMode = true;
                this.open();
            }
            else if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Download Default Resourcepack")) {
                MythicLib.plugin.getVersion().getWrapper().sendJson(this.getPlayer(), "[{\"text\":\"Click to download!\",\"color\":\"green\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://drive.google.com/uc?id=1FjV7y-2cn8qzSiktZ2CUXmkdjepXdj5N\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"https://drive.google.com/uc?id=1FjV7y-2cn8qzSiktZ2CUXmkdjepXdj5N\",\"italic\":true,\"color\":\"white\"}]}}]");
                this.getPlayer().closeInventory();
            }
            else if (this.type == null && !currentItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "- No type -")) {
                new ItemBrowser(this.getPlayer(), MMOItems.plugin.getTypes().get(NBTItem.get(currentItem).getString("typeId"))).open();
            }
        }
        final String string = NBTItem.get(currentItem).getString("MMOITEMS_ITEM_ID");
        if (string.equals("")) {
            return;
        }
        if (this.deleteMode) {
            MMOItems.plugin.getTemplates().deleteTemplate(this.type, string);
            this.deleteMode = false;
            this.open();
        }
        else {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                this.getPlayer().getInventory().addItem(new ItemStack[] { NBTItem.get(currentItem).getBoolean("UNSTACKABLE") ? MMOItems.plugin.getItem(this.type, string, this.playerData) : this.removeLastLoreLines(NBTItem.get(currentItem)) });
                this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 2.0f);
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
                new ItemEdition(this.getPlayer(), MMOItems.plugin.getTemplates().getTemplate(this.type, string)).open();
            }
        }
    }
    
    private ItemStack removeLastLoreLines(final NBTItem nbtItem) {
        final List loreComponents = nbtItem.getLoreComponents();
        nbtItem.setLoreComponents((List)loreComponents.subList(0, loreComponents.size() - 3));
        return nbtItem.toItem();
    }
    
    static {
        slots = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34 };
    }
}
