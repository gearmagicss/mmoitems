// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.api.item.util.ConfigItems;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import net.Indyuce.mmoitems.api.crafting.IngredientInventory;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.crafting.ingredient.MMOItemIngredient;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Random;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;
import net.Indyuce.mmoitems.api.crafting.ConfigMMOItem;

public class UpgradingRecipe extends Recipe
{
    private final ConfigMMOItem item;
    private final Ingredient ingredient;
    private static final Random random;
    
    public UpgradingRecipe(final ConfigurationSection configurationSection) {
        super(configurationSection);
        this.item = new ConfigMMOItem(configurationSection.getConfigurationSection("item"));
        this.ingredient = new MMOItemIngredient(this.item);
    }
    
    public ConfigMMOItem getItem() {
        return this.item;
    }
    
    @Override
    public void whenUsed(final PlayerData playerData, final IngredientInventory ingredientInventory, final CheckedRecipe checkedRecipe, final CraftingStation craftingStation) {
        final UpgradingRecipeInfo upgradingRecipeInfo = (UpgradingRecipeInfo)checkedRecipe;
        upgradingRecipeInfo.getUpgradeData().upgrade(upgradingRecipeInfo.getMMOItem());
        upgradingRecipeInfo.getUpgraded().setItemMeta(upgradingRecipeInfo.getMMOItem().newBuilder().build().getItemMeta());
        checkedRecipe.getRecipe().getTriggers().forEach(trigger -> trigger.whenCrafting(playerData));
        if (!playerData.isOnline()) {
            return;
        }
        Message.UPGRADE_SUCCESS.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(upgradingRecipeInfo.getUpgraded())).send((CommandSender)playerData.getPlayer());
        playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), craftingStation.getSound(), 1.0f, 1.0f);
    }
    
    @Override
    public boolean canUse(final PlayerData playerData, final IngredientInventory ingredientInventory, final CheckedRecipe checkedRecipe, final CraftingStation craftingStation) {
        final IngredientInventory.PlayerIngredient ingredient = ingredientInventory.getIngredient(this.ingredient, IngredientInventory.IngredientLookupMode.IGNORE_ITEM_LEVEL);
        if (ingredient == null) {
            if (!playerData.isOnline()) {
                return false;
            }
            Message.NOT_HAVE_ITEM_UPGRADE.format(ChatColor.RED, new String[0]).send((CommandSender)playerData.getPlayer());
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
            return false;
        }
        else {
            final UpgradingRecipeInfo upgradingRecipeInfo = (UpgradingRecipeInfo)checkedRecipe;
            if (!(upgradingRecipeInfo.mmoitem = new LiveMMOItem(MythicLib.plugin.getVersion().getWrapper().getNBTItem(ingredient.getFirstItem()))).hasData(ItemStats.UPGRADE)) {
                return false;
            }
            if (!(upgradingRecipeInfo.upgradeData = (UpgradeData)upgradingRecipeInfo.getMMOItem().getData(ItemStats.UPGRADE)).canLevelUp()) {
                if (!playerData.isOnline()) {
                    return false;
                }
                Message.MAX_UPGRADES_HIT.format(ChatColor.RED, new String[0]).send((CommandSender)playerData.getPlayer());
                playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
                return false;
            }
            else {
                if (UpgradingRecipe.random.nextDouble() <= upgradingRecipeInfo.getUpgradeData().getSuccess()) {
                    return true;
                }
                if (upgradingRecipeInfo.getUpgradeData().destroysOnFail()) {
                    upgradingRecipeInfo.getUpgraded().setAmount(upgradingRecipeInfo.getUpgraded().getAmount() - 1);
                }
                upgradingRecipeInfo.getIngredients().forEach(checkedIngredient -> checkedIngredient.getPlayerIngredient().reduceItem(checkedIngredient.getIngredient().getAmount()));
                if (!playerData.isOnline()) {
                    return false;
                }
                Message.UPGRADE_FAIL_STATION.format(ChatColor.RED, new String[0]).send((CommandSender)playerData.getPlayer());
                playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 2.0f);
                return false;
            }
        }
    }
    
    @Override
    public ItemStack display(final CheckedRecipe checkedRecipe) {
        return ConfigItems.UPGRADING_RECIPE_DISPLAY.newBuilder(checkedRecipe).build();
    }
    
    @Override
    public CheckedRecipe evaluateRecipe(final PlayerData playerData, final IngredientInventory ingredientInventory) {
        return new UpgradingRecipeInfo(this, playerData, ingredientInventory);
    }
    
    static {
        random = new Random();
    }
    
    public static class UpgradingRecipeInfo extends CheckedRecipe
    {
        private LiveMMOItem mmoitem;
        private UpgradeData upgradeData;
        
        public UpgradingRecipeInfo(final Recipe recipe, final PlayerData playerData, final IngredientInventory ingredientInventory) {
            super(recipe, playerData, ingredientInventory);
        }
        
        public UpgradeData getUpgradeData() {
            return this.upgradeData;
        }
        
        public LiveMMOItem getMMOItem() {
            return this.mmoitem;
        }
        
        public ItemStack getUpgraded() {
            return this.mmoitem.getNBT().getItem();
        }
    }
}
