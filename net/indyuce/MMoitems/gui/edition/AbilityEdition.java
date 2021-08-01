// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.version.VersionMaterial;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.api.ability.Ability;

public class AbilityEdition extends EditionInventory
{
    private final String configKey;
    private Ability ability;
    private static final DecimalFormat modifierFormat;
    private static final int[] slots;
    
    public AbilityEdition(final Player player, final MMOItemTemplate mmoItemTemplate, final String configKey) {
        super(player, mmoItemTemplate);
        this.configKey = configKey;
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Ability Edition");
        int i = 0;
        final String string = this.getEditedSection().getString("ability." + this.configKey + ".type");
        final String s = (string == null) ? "" : string.toUpperCase().replace(" ", "_").replace("-", "_").replaceAll("[^A-Z_]", "");
        this.ability = (MMOItems.plugin.getAbilities().hasAbility(s) ? MMOItems.plugin.getAbilities().getAbility(s) : null);
        final ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Ability");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Choose what ability your weapon will cast.");
        lore.add("");
        lore.add(ChatColor.GRAY + "Current Value: " + ((this.ability == null) ? (ChatColor.RED + "No ability selected.") : (ChatColor.GOLD + this.ability.getName())));
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Left click to select.");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
        itemMeta.setLore((List)lore);
        itemStack.setItemMeta(itemMeta);
        if (this.ability != null) {
            final String string2 = this.getEditedSection().getString("ability." + this.configKey + ".mode");
            final Ability.CastingMode safeValue = Ability.CastingMode.safeValueOf((string2 == null) ? "" : string2.toUpperCase().replace(" ", "_").replace("-", "_").replaceAll("[^A-Z_]", ""));
            final ItemStack itemStack2 = new ItemStack(Material.ARMOR_STAND);
            final ItemMeta itemMeta2 = itemStack2.getItemMeta();
            itemMeta2.setDisplayName(ChatColor.GREEN + "Casting Mode");
            final ArrayList<String> lore2 = new ArrayList<String>();
            lore2.add(ChatColor.GRAY + "Choose what action the player needs to");
            lore2.add(ChatColor.GRAY + "perform in order to cast your ability.");
            lore2.add("");
            lore2.add(ChatColor.GRAY + "Current Value: " + ((safeValue == null) ? (ChatColor.RED + "No mode selected.") : (ChatColor.GOLD + safeValue.getName())));
            lore2.add("");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Left click to select.");
            lore2.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
            itemMeta2.setLore((List)lore2);
            itemStack2.setItemMeta(itemMeta2);
            inventory.setItem(30, itemStack2);
        }
        if (this.ability != null) {
            final ConfigurationSection configurationSection = this.getEditedSection().getConfigurationSection("ability." + this.configKey);
            for (final String s2 : this.ability.getModifiers()) {
                final ItemStack item = VersionMaterial.GRAY_DYE.toItem();
                final ItemMeta itemMeta3 = item.getItemMeta();
                itemMeta3.setDisplayName(ChatColor.GREEN + MMOUtils.caseOnWords(s2.toLowerCase().replace("-", " ")));
                final ArrayList<String> lore3 = new ArrayList<String>();
                lore3.add("" + ChatColor.GRAY + ChatColor.ITALIC + "This is an ability modifier. Changing this");
                lore3.add("" + ChatColor.GRAY + ChatColor.ITALIC + "value will slightly customize the ability.");
                lore3.add("");
                try {
                    lore3.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + (configurationSection.contains(s2) ? new NumericStatFormula(configurationSection.get(s2)).toString() : AbilityEdition.modifierFormat.format(this.ability.getDefaultValue(s2))));
                }
                catch (IllegalArgumentException ex) {
                    lore3.add(ChatColor.GRAY + "Could not read value. Using default");
                }
                lore3.add(ChatColor.GRAY + "Default Value: " + ChatColor.GOLD + AbilityEdition.modifierFormat.format(this.ability.getDefaultValue(s2)));
                lore3.add("");
                lore3.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
                lore3.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
                itemMeta3.setLore((List)lore3);
                item.setItemMeta(itemMeta3);
                inventory.setItem(AbilityEdition.slots[i++], MythicLib.plugin.getVersion().getWrapper().getNBTItem(item).addTag(new ItemTag[] { new ItemTag("abilityModifier", (Object)s2) }).toItem());
            }
        }
        final ItemStack item2 = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta4 = item2.getItemMeta();
        itemMeta4.setDisplayName(ChatColor.RED + "- No Modifier -");
        item2.setItemMeta(itemMeta4);
        final ItemStack itemStack3 = new ItemStack(Material.BARRIER);
        final ItemMeta itemMeta5 = itemStack3.getItemMeta();
        itemMeta5.setDisplayName(ChatColor.GREEN + "\u21e8" + " Ability List");
        itemStack3.setItemMeta(itemMeta5);
        while (i < AbilityEdition.slots.length) {
            inventory.setItem(AbilityEdition.slots[i++], item2);
        }
        this.addEditionInventoryItems(inventory, false);
        inventory.setItem(28, itemStack);
        inventory.setItem(6, itemStack3);
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory() || !MMOUtils.isMetaItem(currentItem, false)) {
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "\u21e8" + " Ability List")) {
            new AbilityListEdition(this.player, this.template).open(this.getPreviousPage());
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Ability")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ABILITIES, new Object[] { this.configKey, "ability" }).enable("Write in the chat the ability you want.", "You can access the ability list by typing " + ChatColor.AQUA + "/mi list ability");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("ability." + this.configKey + ".type")) {
                this.getEditedSection().set("ability." + this.configKey, (Object)null);
                if (this.getEditedSection().contains("ability") && this.getEditedSection().getConfigurationSection("ability").getKeys(false).size() == 0) {
                    this.getEditedSection().set("ability", (Object)null);
                }
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the ability.");
            }
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Casting Mode")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ABILITIES, new Object[] { this.configKey, "mode" }).enable(new String[0]);
                this.player.sendMessage("");
                this.player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Available Casting Modes");
                final Iterator<Ability.CastingMode> iterator = this.ability.getSupportedCastingModes().iterator();
                while (iterator.hasNext()) {
                    this.player.sendMessage("* " + ChatColor.GREEN + iterator.next().name());
                }
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("ability." + this.configKey + ".mode")) {
                this.getEditedSection().set("ability." + this.configKey + ".mode", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the casting mode.");
            }
            return;
        }
        final String string = MythicLib.plugin.getVersion().getWrapper().getNBTItem(currentItem).getString("abilityModifier");
        if (string.equals("")) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(this, ItemStats.ABILITIES, new Object[] { this.configKey, string }).enable("Write in the chat the value you want.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("ability." + this.configKey + "." + string)) {
            this.getEditedSection().set("ability." + this.configKey + "." + string, (Object)null);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset " + ChatColor.GOLD + MMOUtils.caseOnWords(string.replace("-", " ")) + ChatColor.GRAY + ".");
        }
    }
    
    static {
        modifierFormat = new DecimalFormat("0.###");
        slots = new int[] { 23, 24, 25, 32, 33, 34, 41, 42, 43 };
    }
}
