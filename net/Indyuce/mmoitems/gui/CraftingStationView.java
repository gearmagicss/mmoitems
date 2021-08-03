// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui;

import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import net.Indyuce.mmoitems.listener.CustomSoundListener;
import org.bukkit.event.Event;
import net.Indyuce.mmoitems.api.crafting.recipe.Recipe;
import net.Indyuce.mmoitems.api.event.PlayerUseCraftingStationEvent;
import java.util.UUID;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.crafting.CraftingStatus;
import net.Indyuce.mmoitems.api.item.util.ConfigItems;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.crafting.IngredientInventory;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.Layout;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;

public class CraftingStationView extends PluginInventory
{
    private final CraftingStation station;
    private final Layout layout;
    private List<CheckedRecipe> recipes;
    private IngredientInventory ingredients;
    private int queueOffset;
    
    public CraftingStationView(final Player player, final CraftingStation station, final int page) {
        super(player);
        this.station = station;
        this.layout = station.getLayout();
        this.page = page;
        this.updateData();
    }
    
    public CraftingStation getStation() {
        return this.station;
    }
    
    void updateData() {
        this.ingredients = new IngredientInventory(this.player);
        this.recipes = this.station.getAvailableRecipes(this.playerData, this.ingredients);
    }
    
    @Override
    public Inventory getInventory() {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)this, this.layout.getSize(), this.station.getName().replace("#page#", "" + this.page).replace("#max#", "" + this.station.getMaxPage()));
        final int n = (this.page - 1) * this.layout.getRecipeSlots().size();
        final int n2 = this.page * this.layout.getRecipeSlots().size();
        for (int i = n; i < n2; ++i) {
            if (i >= this.recipes.size()) {
                if (this.station.getItemOptions().hasNoRecipe()) {
                    inventory.setItem((int)this.layout.getRecipeSlots().get(i - n), this.station.getItemOptions().getNoRecipe());
                }
            }
            else {
                inventory.setItem((int)this.layout.getRecipeSlots().get(i - n), this.recipes.get(i).display());
            }
        }
        if (n2 < this.recipes.size()) {
            inventory.setItem(this.layout.getRecipeNextSlot(), ConfigItems.NEXT_PAGE.getItem());
        }
        if (this.page > 1) {
            inventory.setItem(this.layout.getRecipePreviousSlot(), ConfigItems.PREVIOUS_PAGE.getItem());
        }
        final CraftingStatus.CraftingQueue queue = this.playerData.getCrafting().getQueue(this.station);
        for (int j = this.queueOffset; j < this.queueOffset + this.layout.getQueueSlots().size(); ++j) {
            if (j >= queue.getCrafts().size()) {
                if (this.station.getItemOptions().hasNoQueueItem()) {
                    inventory.setItem((int)this.layout.getQueueSlots().get(j - this.queueOffset), this.station.getItemOptions().getNoQueueItem());
                }
            }
            else {
                inventory.setItem((int)this.layout.getQueueSlots().get(j - this.queueOffset), ConfigItems.QUEUE_ITEM_DISPLAY.newBuilder(queue.getCrafts().get(j), j + 1).build());
            }
        }
        if (this.queueOffset + this.layout.getQueueSlots().size() < queue.getCrafts().size()) {
            inventory.setItem(this.layout.getQueueNextSlot(), ConfigItems.NEXT_IN_QUEUE.getItem());
        }
        if (this.queueOffset > 0) {
            inventory.setItem(this.layout.getQueuePreviousSlot(), ConfigItems.PREVIOUS_IN_QUEUE.getItem());
        }
        new BukkitRunnable() {
            public void run() {
                if (inventory.getViewers().size() < 1) {
                    this.cancel();
                    return;
                }
                for (int i = CraftingStationView.this.queueOffset; i < CraftingStationView.this.queueOffset + CraftingStationView.this.layout.getQueueSlots().size(); ++i) {
                    if (i >= queue.getCrafts().size()) {
                        inventory.setItem((int)CraftingStationView.this.layout.getQueueSlots().get(i - CraftingStationView.this.queueOffset), CraftingStationView.this.station.getItemOptions().hasNoQueueItem() ? CraftingStationView.this.station.getItemOptions().getNoQueueItem() : null);
                    }
                    else {
                        inventory.setItem((int)CraftingStationView.this.layout.getQueueSlots().get(i - CraftingStationView.this.queueOffset), ConfigItems.QUEUE_ITEM_DISPLAY.newBuilder(queue.getCrafts().get(i), i + 1).build());
                    }
                }
            }
        }.runTaskTimerAsynchronously((Plugin)MMOItems.plugin, 0L, 20L);
        if (this.station.getItemOptions().hasFill()) {
            for (int k = 0; k < this.layout.getSize(); ++k) {
                if (inventory.getItem(k) == null || inventory.getItem(k).getType() == Material.AIR) {
                    inventory.setItem(k, this.station.getItemOptions().getFill());
                }
            }
        }
        return inventory;
    }
    
    @Override
    public void whenClicked(final InventoryClickEvent inventoryClickEvent) {
        if (!this.playerData.isOnline()) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        if (!MMOUtils.isMetaItem(inventoryClickEvent.getCurrentItem(), false)) {
            return;
        }
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem());
        if (nbtItem.getString("ItemId").equals("PREVIOUS_IN_QUEUE")) {
            --this.queueOffset;
            this.open();
            return;
        }
        if (nbtItem.getString("ItemId").equals("NEXT_IN_QUEUE")) {
            ++this.queueOffset;
            this.open();
            return;
        }
        if (nbtItem.getString("ItemId").equals("NEXT_PAGE")) {
            ++this.page;
            this.open();
            return;
        }
        if (nbtItem.getString("ItemId").equals("PREVIOUS_PAGE")) {
            --this.page;
            this.open();
            return;
        }
        final NBTItem nbtItem2 = MythicLib.plugin.getVersion().getWrapper().getNBTItem(inventoryClickEvent.getCurrentItem());
        final String string = nbtItem2.getString("recipeId");
        if (!string.equals("")) {
            final CheckedRecipe recipe = this.getRecipe(string);
            if (inventoryClickEvent.isRightClick()) {
                new CraftingStationPreview(this, recipe).open();
                return;
            }
            this.processRecipe(recipe);
            this.open();
        }
        final String string2;
        if (!(string2 = nbtItem2.getString("queueId")).equals("")) {
            final CraftingStatus.CraftingQueue.CraftingInfo craft = this.playerData.getCrafting().getQueue(this.station).getCraft(UUID.fromString(string2));
            final CraftingRecipe recipe2 = craft.getRecipe();
            if (craft.isReady()) {
                final PlayerUseCraftingStationEvent playerUseCraftingStationEvent = new PlayerUseCraftingStationEvent(this.playerData, this.station, recipe2, PlayerUseCraftingStationEvent.StationAction.CRAFTING_QUEUE);
                Bukkit.getPluginManager().callEvent((Event)playerUseCraftingStationEvent);
                if (playerUseCraftingStationEvent.isCancelled()) {
                    return;
                }
                this.playerData.getCrafting().getQueue(this.station).remove(craft);
                recipe2.getTriggers().forEach(trigger -> trigger.whenCrafting(this.playerData));
                final ItemStack generate = recipe2.getOutput().generate(this.playerData.getRPG());
                CustomSoundListener.stationCrafting(generate, this.player);
                if (!recipe2.hasOption(Recipe.RecipeOption.SILENT_CRAFT)) {
                    this.player.playSound(this.player.getLocation(), this.station.getSound(), 1.0f, 1.0f);
                }
                if (recipe2.hasOption(Recipe.RecipeOption.OUTPUT_ITEM)) {
                    new SmartGive(this.player).give(new ItemStack[] { generate });
                }
            }
            else {
                final PlayerUseCraftingStationEvent playerUseCraftingStationEvent2 = new PlayerUseCraftingStationEvent(this.playerData, this.station, recipe2, PlayerUseCraftingStationEvent.StationAction.CANCEL_QUEUE);
                Bukkit.getPluginManager().callEvent((Event)playerUseCraftingStationEvent2);
                if (playerUseCraftingStationEvent2.isCancelled()) {
                    return;
                }
                this.playerData.getCrafting().getQueue(this.station).remove(craft);
                this.player.playSound(this.player.getLocation(), this.station.getSound(), 1.0f, 1.0f);
                final Iterator<Ingredient> iterator = craft.getRecipe().getIngredients().iterator();
                while (iterator.hasNext()) {
                    new SmartGive(this.player).give(new ItemStack[] { iterator.next().generateItemStack(this.playerData.getRPG()) });
                }
            }
            this.updateData();
            this.open();
        }
    }
    
    public void processRecipe(final CheckedRecipe checkedRecipe) {
        if (!checkedRecipe.areConditionsMet()) {
            Message.CONDITIONS_NOT_MET.format(ChatColor.RED, new String[0]).send((CommandSender)this.player);
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return;
        }
        if (!checkedRecipe.allIngredientsHad()) {
            Message.NOT_ENOUGH_MATERIALS.format(ChatColor.RED, new String[0]).send((CommandSender)this.player);
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return;
        }
        if (!checkedRecipe.getRecipe().canUse(this.playerData, this.ingredients, checkedRecipe, this.station)) {
            this.updateData();
            return;
        }
        final PlayerUseCraftingStationEvent playerUseCraftingStationEvent = new PlayerUseCraftingStationEvent(this.playerData, this.station, checkedRecipe, PlayerUseCraftingStationEvent.StationAction.INTERACT_WITH_RECIPE);
        Bukkit.getPluginManager().callEvent((Event)playerUseCraftingStationEvent);
        if (playerUseCraftingStationEvent.isCancelled()) {
            return;
        }
        checkedRecipe.getRecipe().whenUsed(this.playerData, this.ingredients, checkedRecipe, this.station);
        checkedRecipe.getIngredients().forEach(checkedIngredient -> checkedIngredient.getPlayerIngredient().reduceItem(checkedIngredient.getIngredient().getAmount()));
        checkedRecipe.getConditions().forEach(checkedCondition -> checkedCondition.getCondition().whenCrafting(this.playerData));
        this.updateData();
    }
    
    private CheckedRecipe getRecipe(final String anObject) {
        for (final CheckedRecipe checkedRecipe : this.recipes) {
            if (checkedRecipe.getRecipe().getId().equals(anObject)) {
                return checkedRecipe;
            }
        }
        return null;
    }
}
