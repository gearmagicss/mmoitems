// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Particle;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.particle.api.ParticleType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class ParticlesEdition extends EditionInventory
{
    public ParticlesEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Particles E.: " + this.template.getId());
        final int[] array = { 37, 38, 39, 40, 41, 42, 43 };
        int i = 0;
        ParticleType value = null;
        try {
            value = ParticleType.valueOf(this.getEditedSection().getString("item-particles.type"));
        }
        catch (Exception ex) {}
        final ItemStack item = VersionMaterial.PINK_STAINED_GLASS.toItem();
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Particle Pattern");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "The particle pattern defines how");
        lore.add(ChatColor.GRAY + "particles behave, what pattern they follow");
        lore.add(ChatColor.GRAY + "when displayed or what shape they form.");
        lore.add("");
        lore.add(ChatColor.GRAY + "Current Value: " + ((value == null) ? (ChatColor.RED + "No type selected.") : (ChatColor.GOLD + value.getDefaultName())));
        if (value != null) {
            lore.add("" + ChatColor.GRAY + ChatColor.ITALIC + value.getDescription());
        }
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to change this value.");
        itemMeta.setLore((List)lore);
        item.setItemMeta(itemMeta);
        Particle value2 = null;
        try {
            value2 = Particle.valueOf(this.getEditedSection().getString("item-particles.particle"));
        }
        catch (Exception ex2) {}
        final ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        final ItemMeta itemMeta2 = itemStack.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.GREEN + "Particle");
        final ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add(ChatColor.GRAY + "Defines what particle is used");
        lore2.add(ChatColor.GRAY + "in the particle effect.");
        lore2.add("");
        lore2.add(ChatColor.GRAY + "Current Value: " + ((value2 == null) ? (ChatColor.RED + "No particle selected.") : (ChatColor.GOLD + MMOUtils.caseOnWords(value2.name().toLowerCase().replace("_", " ")))));
        lore2.add("");
        lore2.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        lore2.add(ChatColor.YELLOW + "\u25ba" + " Right click to change this value.");
        itemMeta2.setLore((List)lore2);
        itemStack.setItemMeta(itemMeta2);
        if (value != null) {
            final ConfigurationSection configurationSection = this.getEditedSection().getConfigurationSection("item-particles");
            for (final String s : value.getModifiers()) {
                final ItemStack item2 = VersionMaterial.GRAY_DYE.toItem();
                final ItemMeta itemMeta3 = item2.getItemMeta();
                itemMeta3.setDisplayName(ChatColor.GREEN + MMOUtils.caseOnWords(s.toLowerCase().replace("-", " ")));
                final ArrayList<String> lore3 = new ArrayList<String>();
                lore3.add("" + ChatColor.GRAY + ChatColor.ITALIC + "This is a pattern modifier.");
                lore3.add("" + ChatColor.GRAY + ChatColor.ITALIC + "Changing this value will slightly");
                lore3.add("" + ChatColor.GRAY + ChatColor.ITALIC + "customize the particle pattern.");
                lore3.add("");
                lore3.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + (configurationSection.contains(s) ? configurationSection.getDouble(s) : value.getModifier(s)));
                itemMeta3.setLore((List)lore3);
                item2.setItemMeta(itemMeta3);
                inventory.setItem(array[i++], NBTItem.get(item2).addTag(new ItemTag[] { new ItemTag("patternModifierId", (Object)s) }).toItem());
            }
        }
        if (ParticleData.isColorable(value2)) {
            final int int1 = this.getEditedSection().getInt("item-particles.color.red");
            final int int2 = this.getEditedSection().getInt("item-particles.color.green");
            final int int3 = this.getEditedSection().getInt("item-particles.color.blue");
            final ItemStack item3 = VersionMaterial.RED_DYE.toItem();
            final ItemMeta itemMeta4 = item3.getItemMeta();
            itemMeta4.setDisplayName(ChatColor.GREEN + "Particle Color");
            final ArrayList<String> lore4 = new ArrayList<String>();
            lore4.add(ChatColor.GRAY + "The RGB color of your particle.");
            lore4.add("");
            lore4.add(ChatColor.GRAY + "Current Value (R-G-B):");
            lore4.add("" + ChatColor.RED + ChatColor.BOLD + int1 + ChatColor.GRAY + " - " + ChatColor.GREEN + ChatColor.BOLD + int2 + ChatColor.GRAY + " - " + ChatColor.BLUE + ChatColor.BOLD + int3);
            lore4.add("");
            lore4.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
            lore4.add(ChatColor.YELLOW + "\u25ba" + " Right click to change this value.");
            itemMeta4.setLore((List)lore4);
            item3.setItemMeta(itemMeta4);
            inventory.setItem(25, item3);
        }
        final ItemStack item4 = VersionMaterial.GRAY_STAINED_GLASS_PANE.toItem();
        final ItemMeta itemMeta5 = item4.getItemMeta();
        itemMeta5.setDisplayName(ChatColor.RED + "- No Modifier -");
        item4.setItemMeta(itemMeta5);
        while (i < array.length) {
            inventory.setItem(array[i++], item4);
        }
        this.addEditionInventoryItems(inventory, true);
        inventory.setItem(21, item);
        inventory.setItem(23, itemStack);
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        inventoryClickEvent.setCancelled(true);
        if (inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory() || !MMOUtils.isMetaItem(currentItem, false)) {
            return;
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Particle")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ITEM_PARTICLES, new Object[] { "particle" }).enable("Write in the chat the particle you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("item-particles.particle")) {
                this.getEditedSection().set("item-particles.particle", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the particle.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Particle Color")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ITEM_PARTICLES, new Object[] { "particle-color" }).enable("Write in the chat the RGB color you want.", ChatColor.AQUA + "Format: [RED] [GREEN] [BLUE]");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("item-particles.color")) {
                this.getEditedSection().set("item-particles.color", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the particle color.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Particle Pattern")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ITEM_PARTICLES, new Object[] { "particle-type" }).enable("Write in the chat the particle type you want.");
                this.player.sendMessage("");
                this.player.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "Available Particles Patterns");
                final ParticleType[] values = ParticleType.values();
                for (int length = values.length, i = 0; i < length; ++i) {
                    this.player.sendMessage("* " + ChatColor.GREEN + values[i].name());
                }
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("item-particles.type")) {
                this.getEditedSection().set("item-particles.type", (Object)null);
                for (final String str : this.getEditedSection().getConfigurationSection("item-particles").getKeys(false)) {
                    if (!str.equals("particle")) {
                        this.getEditedSection().set("item-particles." + str, (Object)null);
                    }
                }
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the particle pattern.");
            }
        }
        final String string = NBTItem.get(currentItem).getString("patternModifierId");
        if (string.equals("")) {
            return;
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(this, ItemStats.ITEM_PARTICLES, new Object[] { string }).enable("Write in the chat the value you want.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("item-particles." + string)) {
            this.getEditedSection().set("item-particles." + string, (Object)null);
            this.registerTemplateEdition();
            this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset " + ChatColor.GOLD + string + ChatColor.GRAY + ".");
        }
    }
}
