// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.api.item.util.ConfigItems;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.PlayerUseCraftingStationEvent;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.IngredientInventory;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.crafting.ConfigMMOItem;

public class CraftingRecipe extends Recipe
{
    private final ConfigMMOItem output;
    private final double craftingTime;
    
    public CraftingRecipe(final ConfigurationSection configurationSection) {
        super(configurationSection);
        this.craftingTime = configurationSection.getDouble("crafting-time");
        this.output = new ConfigMMOItem(configurationSection.getConfigurationSection("output"));
    }
    
    public double getCraftingTime() {
        return this.craftingTime;
    }
    
    public boolean isInstant() {
        return this.craftingTime <= 0.0;
    }
    
    public ConfigMMOItem getOutput() {
        return this.output;
    }
    
    @Override
    public void whenUsed(final PlayerData playerData, final IngredientInventory ingredientInventory, final CheckedRecipe checkedRecipe, final CraftingStation craftingStation) {
        if (!playerData.isOnline()) {
            return;
        }
        if (!this.hasOption(RecipeOption.SILENT_CRAFT)) {
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), craftingStation.getSound(), 1.0f, 1.0f);
        }
        if (this.isInstant()) {
            final PlayerUseCraftingStationEvent playerUseCraftingStationEvent = new PlayerUseCraftingStationEvent(playerData, craftingStation, checkedRecipe, PlayerUseCraftingStationEvent.StationAction.INSTANT_RECIPE);
            Bukkit.getPluginManager().callEvent((Event)playerUseCraftingStationEvent);
            if (playerUseCraftingStationEvent.isCancelled()) {
                return;
            }
            if (this.hasOption(RecipeOption.OUTPUT_ITEM)) {
                new SmartGive(playerData.getPlayer()).give(new ItemStack[] { this.getOutput().generate(playerData.getRPG()) });
            }
            checkedRecipe.getRecipe().getTriggers().forEach(trigger -> trigger.whenCrafting(playerData));
        }
        else {
            playerData.getCrafting().getQueue(craftingStation).add(this);
        }
    }
    
    @Override
    public boolean canUse(final PlayerData playerData, final IngredientInventory ingredientInventory, final CheckedRecipe checkedRecipe, final CraftingStation craftingStation) {
        if (this.isInstant()) {
            return true;
        }
        if (!playerData.getCrafting().getQueue(craftingStation).isFull(craftingStation)) {
            return true;
        }
        if (!playerData.isOnline()) {
            return false;
        }
        Message.CRAFTING_QUEUE_FULL.format(ChatColor.RED, new String[0]).send((CommandSender)playerData.getPlayer());
        playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        return false;
    }
    
    @Override
    public ItemStack display(final CheckedRecipe checkedRecipe) {
        return ConfigItems.CRAFTING_RECIPE_DISPLAY.newBuilder(checkedRecipe).build();
    }
    
    @Override
    public CheckedRecipe evaluateRecipe(final PlayerData playerData, final IngredientInventory ingredientInventory) {
        return new CheckedRecipe(this, playerData, ingredientInventory);
    }
}
