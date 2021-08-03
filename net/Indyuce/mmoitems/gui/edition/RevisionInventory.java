// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.event.inventory.InventoryAction;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import io.lumine.mythic.lib.version.VersionMaterial;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RevisionInventory extends EditionInventory
{
    static ItemStack name;
    static ItemStack lore;
    static ItemStack enchantments;
    static ItemStack upgrades;
    static ItemStack gemstones;
    static ItemStack soulbind;
    static ItemStack external;
    static ItemStack revisionID;
    static final String REVISION = "§6Revision ID";
    
    public RevisionInventory(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
        if (RevisionInventory.revisionID == null) {
            RevisionInventory.name = ItemFactory.of(Material.NAME_TAG).name("§3Name").lore((Iterable)SilentNumbers.chop("The display name of the old item will be transferred to the new one", 40, "§7")).build();
            RevisionInventory.lore = ItemFactory.of(VersionMaterial.WRITABLE_BOOK.toMaterial()).name("§dLore").lore((Iterable)SilentNumbers.chop("Specifically keeps lore lines that begin with the color code §n&7", 40, "§7")).build();
            RevisionInventory.enchantments = ItemFactory.of(Material.EXPERIENCE_BOTTLE).name("§bEnchantments").lore((Iterable)SilentNumbers.chop("This keeps specifically enchantments that are not accounted for in upgrades nor gem stones (presumably added by the player).", 40, "§7")).build();
            RevisionInventory.upgrades = ItemFactory.of(Material.NETHER_STAR).name("§aUpgrades").lore((Iterable)SilentNumbers.chop("Will this item retain the upgrade level after updating? Only the Upgrade Level is kept (as long as it does not exceed the new max).", 40, "§7")).build();
            RevisionInventory.gemstones = ItemFactory.of(Material.EMERALD).name("§eGem Stones").lore((Iterable)SilentNumbers.chop("Will the item retain its gem stones when updating? (Note that this allows gemstone overflow - will keep ALL old gemstones even if you reduced the gem sockets)", 40, "§7")).build();
            RevisionInventory.soulbind = ItemFactory.of(Material.ENDER_EYE).name("§cSoulbind").lore((Iterable)SilentNumbers.chop("If the old item is soulbound, updating will transfer the soulbind to the new item.", 40, "§7")).build();
            RevisionInventory.external = ItemFactory.of(Material.SPRUCE_SIGN).name("§9External SH").lore((Iterable)SilentNumbers.chop("Data registered onto the item's StatHistory by external plugins (like GemStones but not removable)", 40, "§7")).build();
            RevisionInventory.revisionID = ItemFactory.of(Material.ITEM_FRAME).name("§6Revision ID").lore((Iterable)SilentNumbers.chop("The updater is always active, increasing this number will update all instances of this MMOItem without further action.", 40, "§7")).build();
        }
    }
    
    @NotNull
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Revision Manager");
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack itemStack = null;
            Boolean b = null;
            Object value = null;
            switch (i) {
                case 19: {
                    itemStack = RevisionInventory.name.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepName();
                    break;
                }
                case 20: {
                    itemStack = RevisionInventory.lore.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepLore();
                    break;
                }
                case 21: {
                    itemStack = RevisionInventory.enchantments.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepEnchantments();
                    break;
                }
                case 22: {
                    itemStack = RevisionInventory.external.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepExternalSH();
                    break;
                }
                case 28: {
                    itemStack = RevisionInventory.upgrades.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepUpgrades();
                    break;
                }
                case 29: {
                    itemStack = RevisionInventory.gemstones.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepGemStones();
                    break;
                }
                case 30: {
                    itemStack = RevisionInventory.soulbind.clone();
                    b = MMOItems.plugin.getLanguage().revisionOptions.shouldKeepSoulbind();
                    break;
                }
                case 33: {
                    value = this.getEditedSection().getInt(ItemStats.REVISION_ID.getPath(), 1);
                    itemStack = RevisionInventory.revisionID.clone();
                    break;
                }
            }
            if (itemStack != null) {
                if (b != null) {
                    inventory.setItem(i, this.addLore(itemStack, "", "§8Enabled (in config)? §6" + b.toString()));
                }
                else if (value != null) {
                    inventory.setItem(i, this.addLore(itemStack, "", "§8Current Value: §6" + value));
                }
                else {
                    inventory.setItem(i, itemStack);
                }
            }
        }
        this.addEditionInventoryItems(inventory, true);
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory() || !MMOUtils.isMetaItem(currentItem, false)) {
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals("§6Revision ID")) {
            final int int1 = this.getEditedSection().getInt(ItemStats.REVISION_ID.getPath(), 1);
            int n;
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
                n = Math.max(int1 - 1, 1);
            }
            else {
                n = Math.min(int1 + 1, Integer.MAX_VALUE);
            }
            this.getEditedSection().set(ItemStats.REVISION_ID.getPath(), (Object)n);
            this.registerTemplateEdition();
            inventoryClickEvent.setCurrentItem(this.addLore(RevisionInventory.revisionID.clone(), "", "§8Current Value: §6" + n));
        }
    }
    
    @NotNull
    ItemStack addLore(@NotNull final ItemStack itemStack, final String... a) {
        ArrayList lore = new ArrayList();
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.getLore() != null) {
            lore = new ArrayList(itemMeta.getLore());
        }
        lore.addAll(Arrays.asList(a));
        itemMeta.setLore((List)lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
