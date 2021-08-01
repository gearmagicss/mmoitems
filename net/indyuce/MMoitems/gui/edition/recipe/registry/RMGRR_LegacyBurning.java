// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry;

import net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy.BurningRecipeInformation;
import net.Indyuce.mmoitems.manager.RecipeManager;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.NamespacedKey;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RMG_BurningLegacy;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import net.Indyuce.mmoitems.api.recipe.CraftingType;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public abstract class RMGRR_LegacyBurning implements RecipeRegistry
{
    @NotNull
    ItemStack displayListItem;
    
    @NotNull
    public abstract CraftingType getLegacyBurningType();
    
    @NotNull
    public static String capitalizeFirst(@NotNull final String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    @NotNull
    @Override
    public String getRecipeConfigPath() {
        return this.getLegacyBurningType().name().toLowerCase();
    }
    
    @NotNull
    @Override
    public String getRecipeTypeName() {
        return "§8{§4§oL§8} " + capitalizeFirst(this.getRecipeConfigPath());
    }
    
    @NotNull
    @Override
    public ItemStack getDisplayListItem() {
        if (this.displayListItem == null) {
            this.displayListItem = RecipeMakerGUI.rename(this.getLegacyBurningType().getItem(), FFPMMOItems.get().getExampleFormat() + capitalizeFirst(this.getRecipeConfigPath()) + " Recipe");
            this.displayListItem = RecipeMakerGUI.addLore(this.displayListItem, SilentNumbers.toArrayList((Object[])new String[] { " " }));
            this.displayListItem = RecipeMakerGUI.addLore(this.displayListItem, SilentNumbers.chop("§4To accept input amounts, this recipe requires recipe-amounts to be enabled in the config.yml", 60, "§4"));
        }
        return this.displayListItem;
    }
    
    @Override
    public void openForPlayer(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        new RMG_BurningLegacy(editionInventory.getPlayer(), editionInventory.getEdited(), s, this).open(editionInventory.getPreviousPage());
    }
    
    @NotNull
    @Override
    public MythicRecipeBlueprint sendToMythicLib(@NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final ConfigurationSection configurationSection, @NotNull final String s, @NotNull final Ref<NamespacedKey> ref, @NotNull final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        Validate.isTrue(ref.getValue() != null);
        final ConfigurationSection section = RecipeMakerGUI.getSection(configurationSection, s);
        final String string = section.getString("item");
        if (string == null) {
            throw new IllegalArgumentException("Missing input ingredient");
        }
        MMOItems.plugin.getRecipes().registerBurningRecipe(this.getLegacyBurningType().getBurningType(), mmoItemTemplate.newBuilder(0, null).build(), new BurningRecipeInformation(RecipeManager.getWorkbenchIngredient(string), (float)section.getDouble("exp", 0.35), section.getInt("time", SilentNumbers.round(200.0))), section.getInt("amount", 1), (NamespacedKey)ref.getValue(), section.getBoolean("hidden", false));
        throw new IllegalArgumentException("");
    }
}
