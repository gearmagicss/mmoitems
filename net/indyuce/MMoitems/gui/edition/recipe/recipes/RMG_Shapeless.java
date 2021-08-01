// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.recipes;

import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMG_RecipeInterpreter;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_HideFromBook;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_InputOutput;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMGRI_Shapeless;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class RMG_Shapeless extends RecipeMakerGUI
{
    @NotNull
    final HashMap<Integer, Integer> inputLinks;
    @NotNull
    final RMGRI_Shapeless interpreter;
    
    public RMG_Shapeless(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final String s, @NotNull final RecipeRegistry recipeRegistry) {
        super(player, mmoItemTemplate, s, recipeRegistry);
        this.inputLinks = new HashMap<Integer, Integer>();
        this.addButton(new RBA_InputOutput(this));
        this.addButton(new RBA_HideFromBook(this));
        this.interpreter = new RMGRI_Shapeless(RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(this.getEditedSection(), "crafting"), this.getRecipeRegistry().getRecipeConfigPath()), this.getRecipeName()));
        this.inputLinks.put(30, 0);
        this.inputLinks.put(31, 1);
        this.inputLinks.put(32, 2);
        this.inputLinks.put(39, 3);
        this.inputLinks.put(40, 4);
        this.inputLinks.put(41, 5);
        this.inputLinks.put(48, 6);
        this.inputLinks.put(49, 7);
        this.inputLinks.put(50, 8);
    }
    
    @Override
    public int getButtonsRow() {
        return 1;
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
    
    @NotNull
    @Override
    public RMG_RecipeInterpreter getInterpreter() {
        return this.interpreter;
    }
}
