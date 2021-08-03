// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import io.lumine.mythic.lib.version.VersionMaterial;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import java.util.List;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class AbilityListEdition extends EditionInventory
{
    private static final int[] slots;
    
    public AbilityListEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Ability List");
        int i = 0;
        if (this.getEditedSection().contains("ability")) {
            for (final String s : this.getEditedSection().getConfigurationSection("ability").getKeys(false)) {
                final String string = this.getEditedSection().getString("ability." + s + ".type");
                final String replace;
                final Ability ability = (string != null && MMOItems.plugin.getAbilities().hasAbility(replace = string.toUpperCase().replace(" ", "_").replace("-", "_"))) ? MMOItems.plugin.getAbilities().getAbility(replace) : null;
                final Ability.CastingMode safeValue = Ability.CastingMode.safeValueOf(this.getEditedSection().getString("ability." + s + ".mode"));
                final ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
                final ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName((ability != null) ? (ChatColor.GREEN + ability.getName()) : (ChatColor.RED + "! No Ability Selected !"));
                final ArrayList<String> lore = new ArrayList<String>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Cast Mode: " + ((safeValue != null) ? (ChatColor.GOLD + safeValue.getName()) : (ChatColor.RED + "Not Selected")));
                lore.add("");
                boolean b = false;
                if (ability != null) {
                    for (final String str : this.getEditedSection().getConfigurationSection("ability." + s).getKeys(false)) {
                        if (!str.equals("type") && !str.equals("mode") && ability.getModifiers().contains(str)) {
                            try {
                                lore.add(ChatColor.GRAY + "* " + MMOUtils.caseOnWords(str.toLowerCase().replace("-", " ")) + ": " + ChatColor.GOLD + new NumericStatFormula(this.getEditedSection().get("ability." + s + "." + str)).toString());
                                b = true;
                            }
                            catch (IllegalArgumentException ex) {
                                lore.add(ChatColor.GRAY + "* " + MMOUtils.caseOnWords(str.toLowerCase().replace("-", " ")) + ": " + ChatColor.GOLD + "Unreadable");
                            }
                        }
                    }
                }
                if (b) {
                    lore.add("");
                }
                lore.add(ChatColor.YELLOW + "\u25ba" + " Left click to edit.");
                lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove.");
                itemMeta.setLore((List)lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(AbilityListEdition.slots[i++], MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack).addTag(new ItemTag[] { new ItemTag("configKey", (Object)s) }).toItem());
            }
        }
        final ItemStack item = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta2 = item.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.RED + "- No Ability -");
        item.setItemMeta(itemMeta2);
        final ItemStack itemStack2 = new ItemStack(VersionMaterial.WRITABLE_BOOK.toMaterial());
        final ItemMeta itemMeta3 = itemStack2.getItemMeta();
        itemMeta3.setDisplayName(ChatColor.GREEN + "Add an ability...");
        itemStack2.setItemMeta(itemMeta3);
        inventory.setItem(40, itemStack2);
        while (i < AbilityListEdition.slots.length) {
            inventory.setItem(AbilityListEdition.slots[i++], item);
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
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Add an ability...")) {
            if (!this.getEditedSection().contains("ability")) {
                this.getEditedSection().createSection("ability.ability1");
                this.registerTemplateEdition();
                new AbilityEdition(this.player, this.template, "ability1").open(this.getPreviousPage());
                return;
            }
            if (this.getEditedSection().getConfigurationSection("ability").getKeys(false).size() > 6) {
                this.player.sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + "You've hit the 7 abilities per item limit.");
                return;
            }
            for (int i = 1; i < 8; ++i) {
                if (!this.getEditedSection().getConfigurationSection("ability").contains("ability" + i)) {
                    this.getEditedSection().createSection("ability.ability" + i);
                    this.registerTemplateEdition();
                    new AbilityEdition(this.player, this.template, "ability" + i).open(this.getPreviousPage());
                    break;
                }
            }
        }
        final String string = MythicLib.plugin.getVersion().getWrapper().getNBTItem(currentItem).getString("configKey");
        if (string.equals("")) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new AbilityEdition(this.player, this.template, string).open(this.getPreviousPage());
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("ability") && this.getEditedSection().getConfigurationSection("ability").contains(string)) {
            this.getEditedSection().set("ability." + string, (Object)null);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + ChatColor.GOLD + string + ChatColor.DARK_GRAY + " (Internal ID)" + ChatColor.GRAY + ".");
        }
    }
    
    static {
        slots = new int[] { 19, 20, 21, 22, 23, 24, 25 };
    }
}
