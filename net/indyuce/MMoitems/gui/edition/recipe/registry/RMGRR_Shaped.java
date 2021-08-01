// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry;

import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMGRI_Shaped;
import io.lumine.mythic.lib.api.crafting.ingredients.ShapedIngredient;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import io.lumine.mythic.lib.api.crafting.outputs.MRORecipe;
import io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe;
import io.lumine.mythic.lib.api.crafting.uifilters.UIFilter;
import net.Indyuce.mmoitems.api.crafting.MMOItemUIFilter;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import java.util.Collection;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.NamespacedKey;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RMG_Shaped;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class RMGRR_Shaped implements RecipeRegistry
{
    @NotNull
    final ItemStack displayListItem;
    
    public RMGRR_Shaped() {
        this.displayListItem = RecipeMakerGUI.rename(new ItemStack(Material.CRAFTING_TABLE), FFPMMOItems.get().getExampleFormat() + "Shaped Recipe");
    }
    
    @NotNull
    @Override
    public String getRecipeConfigPath() {
        return "shaped";
    }
    
    @NotNull
    @Override
    public String getRecipeTypeName() {
        return "Shaped";
    }
    
    @NotNull
    @Override
    public ItemStack getDisplayListItem() {
        return this.displayListItem;
    }
    
    @Override
    public void openForPlayer(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        new RMG_Shaped(editionInventory.getPlayer(), editionInventory.getEdited(), s, this).open(editionInventory.getPreviousPage());
    }
    
    @NotNull
    @Override
    public MythicRecipeBlueprint sendToMythicLib(@NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final ConfigurationSection configurationSection, @NotNull final String s, @NotNull final Ref<NamespacedKey> ref, @NotNull final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        final ConfigurationSection moveInput = RecipeMakerGUI.moveInput(configurationSection, s);
        final NamespacedKey namespacedKey = (NamespacedKey)ref.getValue();
        if (namespacedKey == null) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Illegal (Null) Namespace", new String[0]));
        }
        final ShapedRecipe shapedRecipeFromList = shapedRecipeFromList(namespacedKey.getKey(), new ArrayList<String>(moveInput.getStringList("input")), friendlyFeedbackProvider);
        if (shapedRecipeFromList == null) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Shaped recipe containing only AIR, $fignored$b.", new String[0]));
        }
        final ShapedRecipe shapedRecipeFromList2 = shapedRecipeFromList(namespacedKey.getKey(), new ArrayList<String>(moveInput.getStringList("output")), friendlyFeedbackProvider);
        final int int1 = moveInput.getInt("amount", 1);
        final boolean boolean1 = moveInput.getBoolean("hidden", false);
        final MythicRecipeBlueprint mythicRecipeBlueprint = new MythicRecipeBlueprint((MythicRecipe)shapedRecipeFromList, (MythicRecipeOutput)new MRORecipe(ShapedRecipe.single(namespacedKey.getKey(), new ProvidedUIFilter[] { new ProvidedUIFilter((UIFilter)MMOItemUIFilter.get(), mmoItemTemplate.getType().getId(), mmoItemTemplate.getId(), Math.max(int1, 1)) }), shapedRecipeFromList2), namespacedKey);
        mythicRecipeBlueprint.deploy(MythicRecipeStation.WORKBENCH, (Ref)ref);
        if (boolean1) {
            ref.setValue((Object)null);
        }
        return mythicRecipeBlueprint;
    }
    
    @Nullable
    public static ShapedRecipe shapedRecipeFromList(@NotNull final String s, @NotNull final ArrayList<String> list, @NotNull final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        final ArrayList<ShapedIngredient> list2 = new ArrayList<ShapedIngredient>();
        boolean b = false;
        int n = 0;
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            final String updateRow = RMGRI_Shaped.updateRow(iterator.next());
            String[] array;
            if (updateRow.contains("|")) {
                array = updateRow.split("\\|");
            }
            else {
                array = updateRow.split(" ");
            }
            if (array.length != 3) {
                throw new IllegalArgumentException("Invalid crafting table row $u" + updateRow + "$b ($fNot exactly 3 ingredients wide$b).");
            }
            final ProvidedUIFilter ingredient = RecipeMakerGUI.readIngredientFrom(array[0], friendlyFeedbackProvider);
            final ProvidedUIFilter ingredient2 = RecipeMakerGUI.readIngredientFrom(array[1], friendlyFeedbackProvider);
            final ProvidedUIFilter ingredient3 = RecipeMakerGUI.readIngredientFrom(array[2], friendlyFeedbackProvider);
            if (!ingredient.isAir()) {
                b = true;
            }
            if (!ingredient2.isAir()) {
                b = true;
            }
            if (!ingredient3.isAir()) {
                b = true;
            }
            final ShapedIngredient e = new ShapedIngredient(ingredient, 0, -n);
            final ShapedIngredient e2 = new ShapedIngredient(ingredient2, 1, -n);
            final ShapedIngredient e3 = new ShapedIngredient(ingredient3, 2, -n);
            list2.add(e);
            list2.add(e2);
            list2.add(e3);
            ++n;
        }
        if (!b) {
            return null;
        }
        return ShapedRecipe.unsharpen(new ShapedRecipe(s, (ArrayList)list2));
    }
}
