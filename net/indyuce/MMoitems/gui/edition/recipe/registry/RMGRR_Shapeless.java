// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry;

import java.util.Iterator;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import io.lumine.mythic.lib.api.crafting.outputs.MRORecipe;
import io.lumine.mythic.lib.api.crafting.recipes.ShapedRecipe;
import io.lumine.mythic.lib.api.crafting.uifilters.UIFilter;
import net.Indyuce.mmoitems.api.crafting.MMOItemUIFilter;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import io.lumine.mythic.lib.api.crafting.recipes.ShapelessRecipe;
import java.util.Collection;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicRecipeIngredient;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.NamespacedKey;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RMG_Shapeless;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class RMGRR_Shapeless implements RecipeRegistry
{
    @NotNull
    final ItemStack displayListItem;
    
    public RMGRR_Shapeless() {
        this.displayListItem = RecipeMakerGUI.rename(new ItemStack(Material.OAK_LOG), FFPMMOItems.get().getExampleFormat() + "Shapeless Recipe");
    }
    
    @NotNull
    @Override
    public String getRecipeTypeName() {
        return "Shapeless";
    }
    
    @NotNull
    @Override
    public String getRecipeConfigPath() {
        return "shapeless";
    }
    
    @NotNull
    @Override
    public ItemStack getDisplayListItem() {
        return this.displayListItem;
    }
    
    @Override
    public void openForPlayer(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        new RMG_Shapeless(editionInventory.getPlayer(), editionInventory.getEdited(), s, this).open(editionInventory.getPreviousPage());
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
        final ArrayList<MythicRecipeIngredient> list = new ArrayList<MythicRecipeIngredient>();
        final ArrayList list2 = new ArrayList<String>(section.getStringList("input"));
        boolean b = false;
        for (final String anObject : list2) {
            if (anObject != null) {
                if ("AIR".equals(anObject)) {
                    continue;
                }
                final ProvidedUIFilter ingredient = RecipeMakerGUI.readIngredientFrom(anObject, friendlyFeedbackProvider);
                b = true;
                list.add(new MythicRecipeIngredient(ingredient));
            }
        }
        if (!b) {
            throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Shapeless recipe containing only AIR, $fignored$b.", new String[0]));
        }
        final ShapelessRecipe shapelessRecipe = new ShapelessRecipe(namespacedKey.getKey(), (ArrayList)list);
        final ShapedRecipe shapedRecipeFromList = RMGRR_Shaped.shapedRecipeFromList(namespacedKey.getKey(), new ArrayList<String>(section.getStringList("output")), friendlyFeedbackProvider);
        final int int1 = section.getInt("amount", 1);
        final boolean boolean1 = section.getBoolean("hidden", false);
        final MythicRecipeBlueprint mythicRecipeBlueprint = new MythicRecipeBlueprint((MythicRecipe)shapelessRecipe, (MythicRecipeOutput)new MRORecipe(ShapedRecipe.single(namespacedKey.getKey(), new ProvidedUIFilter[] { new ProvidedUIFilter((UIFilter)MMOItemUIFilter.get(), mmoItemTemplate.getType().getId(), mmoItemTemplate.getId(), Math.max(int1, 1)) }), shapedRecipeFromList), namespacedKey);
        mythicRecipeBlueprint.deploy(MythicRecipeStation.WORKBENCH, (Ref)ref);
        if (boolean1) {
            ref.setValue((Object)null);
        }
        return mythicRecipeBlueprint;
    }
}
