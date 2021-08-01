// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.recipes;

import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMG_RecipeInterpreter;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_DropGems;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_SmithingEnchantments;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_SmithingUpgrades;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_InputOutput;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMGRI_Smithing;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class RMG_Smithing extends RecipeMakerGUI
{
    @NotNull
    final HashMap<Integer, Integer> inputLinks;
    @NotNull
    final RMGRI_Smithing interpreter;
    
    public RMG_Smithing(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final String s, @NotNull final RecipeRegistry recipeRegistry) {
        super(player, mmoItemTemplate, s, recipeRegistry);
        this.inputLinks = new HashMap<Integer, Integer>();
        this.interpreter = new RMGRI_Smithing(RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(this.getEditedSection(), "crafting"), this.getRecipeRegistry().getRecipeConfigPath()), this.getRecipeName()));
        this.inputLinks.put(39, 0);
        this.inputLinks.put(41, 1);
        this.addButton(new RBA_InputOutput(this));
        this.addButton(new RBA_SmithingUpgrades(this));
        this.addButton(new RBA_SmithingEnchantments(this));
        this.addButton(new RBA_DropGems(this));
    }
    
    @Override
    public void putRecipe(@NotNull final Inventory inventory) {
        for (final Integer key : this.inputLinks.keySet()) {
            inventory.setItem((int)key, this.getDisplay(this.isShowingInput(), this.inputLinks.get(key)));
        }
    }
    
    @Override
    int getInputSlot(final int i) {
        final Integer n = this.inputLinks.get(i);
        return (n != null) ? n : -1;
    }
    
    @Override
    public int getButtonsRow() {
        return 1;
    }
    
    @NotNull
    @Override
    public RMG_RecipeInterpreter getInterpreter() {
        return this.interpreter;
    }
}
