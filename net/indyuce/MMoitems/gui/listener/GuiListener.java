// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.listener;

import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.Iterator;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeListGUI;
import net.Indyuce.mmoitems.gui.ItemBrowser;
import net.Indyuce.mmoitems.gui.edition.ItemEdition;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.gui.PluginInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class GuiListener implements Listener
{
    @EventHandler
    public void a(final InventoryClickEvent inventoryClickEvent) {
        final Player player = (Player)inventoryClickEvent.getWhoClicked();
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        if (inventoryClickEvent.getInventory().getHolder() instanceof PluginInventory) {
            final PluginInventory pluginInventory = (PluginInventory)inventoryClickEvent.getInventory().getHolder();
            pluginInventory.whenClicked(inventoryClickEvent);
            if (!(pluginInventory instanceof EditionInventory) || inventoryClickEvent.getInventory() != inventoryClickEvent.getClickedInventory() || !MMOUtils.isMetaItem(currentItem, false) || !currentItem.getItemMeta().getDisplayName().startsWith(ChatColor.GREEN + "")) {
                return;
            }
            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "\u2724" + " Get the Item! " + "\u2724")) {
                if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
                    final Iterator<ItemStack> iterator = player.getInventory().addItem(new ItemStack[] { inventoryClickEvent.getInventory().getItem(4) }).values().iterator();
                    while (iterator.hasNext()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)iterator.next());
                    }
                    if (NBTItem.get(inventoryClickEvent.getInventory().getItem(4)).getBoolean("MMOITEMS_UNSTACKABLE")) {
                        ((EditionInventory)pluginInventory).updateCachedItem();
                        inventoryClickEvent.getInventory().setItem(4, ((RecipeMakerGUI)pluginInventory).getCachedItem());
                    }
                }
                else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
                    final Iterator<ItemStack> iterator2 = player.getInventory().addItem(new ItemStack[] { inventoryClickEvent.getInventory().getItem(4) }).values().iterator();
                    while (iterator2.hasNext()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)iterator2.next());
                    }
                    ((RecipeMakerGUI)pluginInventory).updateCachedItem();
                    inventoryClickEvent.getInventory().setItem(4, ((RecipeMakerGUI)pluginInventory).getCachedItem());
                }
            }
            final MMOItemTemplate edited = ((RecipeMakerGUI)pluginInventory).getEdited();
            if (currentItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "\u21e8" + " Back")) {
                if (pluginInventory instanceof ItemEdition) {
                    new ItemBrowser(player, edited.getType()).open();
                }
                else if (pluginInventory instanceof RecipeListGUI) {
                    new RecipeBrowserGUI(player, edited).open(((RecipeMakerGUI)pluginInventory).getPreviousPage());
                }
                else if (pluginInventory instanceof RecipeMakerGUI) {
                    new RecipeListGUI(player, edited, ((RecipeMakerGUI)pluginInventory).getRecipeRegistry()).open(((RecipeMakerGUI)pluginInventory).getPreviousPage());
                }
                else {
                    new ItemEdition(player, edited).onPage(((RecipeMakerGUI)pluginInventory).getPreviousPage()).open();
                }
            }
        }
    }
}
