// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry;

import net.Indyuce.mmoitems.api.crafting.recipe.SmithingCombinationType;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CustomSmithingRecipe;
import io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicRecipeIngredient;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMGRI_Smithing;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.NamespacedKey;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RMG_Smithing;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class RMGRR_Smithing implements RecipeRegistry
{
    @NotNull
    final ItemStack displayListItem;
    
    public RMGRR_Smithing() {
        this.displayListItem = RecipeMakerGUI.rename(new ItemStack(Material.SMITHING_TABLE), FFPMMOItems.get().getExampleFormat() + "Smithing Recipe");
    }
    
    @NotNull
    @Override
    public String getRecipeTypeName() {
        return "Smithing";
    }
    
    @NotNull
    @Override
    public String getRecipeConfigPath() {
        return "smithing";
    }
    
    @NotNull
    @Override
    public ItemStack getDisplayListItem() {
        return this.displayListItem;
    }
    
    @Override
    public void openForPlayer(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        new RMG_Smithing(editionInventory.getPlayer(), editionInventory.getEdited(), s, this).open(editionInventory.getPreviousPage());
    }
    
    @NotNull
    @Override
    public MythicRecipeBlueprint sendToMythicLib(@NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final ConfigurationSection configurationSection, @NotNull final String s, @NotNull final Ref<NamespacedKey> ref, @NotNull final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        RecipeMakerGUI.moveInput(configurationSection, s);
        final ConfigurationSection section = RecipeMakerGUI.getSection(configurationSection, s);
        final NamespacedKey namespacedKey = (NamespacedKey)ref.getValue();
        if (namespacedKey == null) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Illegal (Null) Namespace", new String[0]));
        }
        final String[] split = RMGRI_Smithing.updateIngredients(section.getString("input")).split("\\|");
        final ProvidedUIFilter ingredient = RecipeMakerGUI.readIngredientFrom(split[0], friendlyFeedbackProvider);
        final ProvidedUIFilter ingredient2 = RecipeMakerGUI.readIngredientFrom(split[1], friendlyFeedbackProvider);
        if (ingredient.isAir() || ingredient2.isAir()) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Smithing recipe containing AIR, $fignored$b.", new String[0]));
        }
        final MythicRecipeIngredient mythicRecipeIngredient = new MythicRecipeIngredient(ingredient);
        final MythicRecipeIngredient mythicRecipeIngredient2 = new MythicRecipeIngredient(ingredient2);
        final ShapelessRecipe shapelessRecipe = new ShapelessRecipe(namespacedKey.getKey(), new MythicRecipeIngredient[] { mythicRecipeIngredient });
        final ShapelessRecipe shapelessRecipe2 = new ShapelessRecipe(namespacedKey.getKey(), new MythicRecipeIngredient[] { mythicRecipeIngredient2 });
        final String[] split2 = RMGRI_Smithing.updateIngredients(section.getString("output")).split("\\|");
        final ProvidedUIFilter ingredient3 = RecipeMakerGUI.readIngredientFrom(split2[0], friendlyFeedbackProvider);
        final ProvidedUIFilter ingredient4 = RecipeMakerGUI.readIngredientFrom(split2[1], friendlyFeedbackProvider);
        final ShapedRecipe mainInputConsumption = ingredient3.isAir() ? null : ShapedRecipe.single(namespacedKey.getKey(), new ProvidedUIFilter[] { ingredient3 });
        final ShapedRecipe ingotInputConsumption = ingredient4.isAir() ? null : ShapedRecipe.single(namespacedKey.getKey(), new ProvidedUIFilter[] { ingredient4 });
        final CustomSmithingRecipe customSmithingRecipe = new CustomSmithingRecipe(mmoItemTemplate, section.getBoolean("drop-gems", false), this.readSCT(section.getString("upgrades")), this.readSCT(section.getString("enchantments")), section.getInt("amount", 1));
        customSmithingRecipe.setMainInputConsumption((MythicRecipe)mainInputConsumption);
        customSmithingRecipe.setIngotInputConsumption((MythicRecipe)ingotInputConsumption);
        final MythicRecipeBlueprint mythicRecipeBlueprint = new MythicRecipeBlueprint((MythicRecipe)shapelessRecipe, (MythicRecipeOutput)customSmithingRecipe, namespacedKey);
        mythicRecipeBlueprint.addSideCheck("ingot", (MythicRecipe)shapelessRecipe2);
        mythicRecipeBlueprint.deploy(MythicRecipeStation.SMITHING, (Ref)ref);
        return mythicRecipeBlueprint;
    }
    
    @NotNull
    SmithingCombinationType readSCT(@Nullable final String s) {
        if (s == null) {
            return SmithingCombinationType.MAXIMUM;
        }
        try {
            return SmithingCombinationType.valueOf(s);
        }
        catch (IllegalArgumentException ex) {
            return SmithingCombinationType.MAXIMUM;
        }
    }
}
