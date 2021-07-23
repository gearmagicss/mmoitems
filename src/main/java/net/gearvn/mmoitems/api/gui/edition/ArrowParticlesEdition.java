// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import io.lumine.mythic.lib.version.VersionMaterial;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;

public class ArrowParticlesEdition extends EditionInventory
{
    public ArrowParticlesEdition(final Player player, final MMOItemTemplate mmoItemTemplate) {
        super(player, mmoItemTemplate);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, 54, "Arrow Particles: " + this.template.getId());
        Particle value = null;
        try {
            value = Particle.valueOf(this.getEditedSection().getString("arrow-particles.particle"));
        }
        catch (Exception ex) {}
        final ItemStack itemStack = new ItemStack(Material.BLAZE_POWDER);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Particle");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "The particle which is displayed around the");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "arrow. Fades away when the arrow lands.");
        lore.add("");
        lore.add(ChatColor.GRAY + "Current Value: " + ((value == null) ? (ChatColor.RED + "No particle selected.") : (ChatColor.GOLD + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")))));
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        lore.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
        itemMeta.setLore((List)lore);
        itemStack.setItemMeta(itemMeta);
        final ItemStack item = VersionMaterial.GRAY_DYE.toItem();
        final ItemMeta itemMeta2 = item.getItemMeta();
        itemMeta2.setDisplayName(ChatColor.GREEN + "Amount");
        final ArrayList<String> lore2 = new ArrayList<String>();
        lore2.add("");
        lore2.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + this.getEditedSection().getInt("arrow-particles.amount"));
        lore2.add("");
        lore2.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        lore2.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
        itemMeta2.setLore((List)lore2);
        item.setItemMeta(itemMeta2);
        final ItemStack item2 = VersionMaterial.GRAY_DYE.toItem();
        final ItemMeta itemMeta3 = item2.getItemMeta();
        itemMeta3.setDisplayName(ChatColor.GREEN + "Offset");
        final ArrayList<String> lore3 = new ArrayList<String>();
        lore3.add("");
        lore3.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + this.getEditedSection().getDouble("arrow-particles.offset"));
        lore3.add("");
        lore3.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        lore3.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
        itemMeta3.setLore((List)lore3);
        item2.setItemMeta(itemMeta3);
        if (value != null) {
            final ConfigurationSection configurationSection = this.getEditedSection().getConfigurationSection("arrow-particles");
            if (ParticleData.isColorable(value)) {
                final int int1 = configurationSection.getInt("color.red");
                final int int2 = configurationSection.getInt("color.green");
                final int int3 = configurationSection.getInt("color.blue");
                final ItemStack item3 = VersionMaterial.GRAY_DYE.toItem();
                final ItemMeta itemMeta4 = item3.getItemMeta();
                itemMeta4.setDisplayName(ChatColor.GREEN + "Particle Color");
                final ArrayList<String> lore4 = new ArrayList<String>();
                lore4.add("");
                lore4.add(ChatColor.GRAY + "Current Value (R-G-B):");
                lore4.add("" + ChatColor.RED + ChatColor.BOLD + int1 + ChatColor.GRAY + " - " + ChatColor.GREEN + ChatColor.BOLD + int2 + ChatColor.GRAY + " - " + ChatColor.BLUE + ChatColor.BOLD + int3);
                lore4.add("");
                lore4.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
                lore4.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
                itemMeta4.setLore((List)lore4);
                item3.setItemMeta(itemMeta4);
                inventory.setItem(41, item3);
            }
            else {
                final ItemStack item4 = VersionMaterial.GRAY_DYE.toItem();
                final ItemMeta itemMeta5 = item4.getItemMeta();
                itemMeta5.setDisplayName(ChatColor.GREEN + "Speed");
                final ArrayList<String> lore5 = new ArrayList<String>();
                lore5.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "The speed at which your particle");
                lore5.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "flies off in random directions.");
                lore5.add("");
                lore5.add(ChatColor.GRAY + "Current Value: " + ChatColor.GOLD + configurationSection.getDouble("speed"));
                lore5.add("");
                lore5.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
                lore5.add(ChatColor.YELLOW + "\u25ba" + " Right click to reset.");
                itemMeta5.setLore((List)lore5);
                item4.setItemMeta(itemMeta5);
                inventory.setItem(41, item4);
            }
        }
        this.addEditionInventoryItems(inventory, true);
        inventory.setItem(30, itemStack);
        inventory.setItem(23, item);
        inventory.setItem(32, item2);
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
                new StatEdition(this, ItemStats.ARROW_PARTICLES, new Object[] { "particle" }).enable("Write in the chat the particle you want.");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("arrow-particles.particle")) {
                this.getEditedSection().set("arrow-particles", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the particle.");
            }
        }
        if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Particle Color")) {
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                new StatEdition(this, ItemStats.ARROW_PARTICLES, new Object[] { "color" }).enable("Write in the chat the RGB color you want.", ChatColor.AQUA + "Format: [RED] [GREEN] [BLUE]");
            }
            if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("arrow-particles.color")) {
                this.getEditedSection().set("arrow-particles.color", (Object)null);
                this.registerTemplateEdition();
                this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the particle color.");
            }
        }
        for (final String s : new String[] { "amount", "offset", "speed" }) {
            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + MMOUtils.caseOnWords(s))) {
                if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                    new StatEdition(this, ItemStats.ARROW_PARTICLES, new Object[] { s }).enable("Write in the chat the " + s + " you want.");
                }
                if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && this.getEditedSection().contains("arrow-particles." + s)) {
                    this.getEditedSection().set("arrow-particles." + s, (Object)null);
                    this.registerTemplateEdition();
                    this.player.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the " + s + ".");
                }
            }
        }
    }
}
