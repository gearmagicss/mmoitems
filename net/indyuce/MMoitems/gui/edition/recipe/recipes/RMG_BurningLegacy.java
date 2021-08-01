// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.recipes;

import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMG_RecipeInterpreter;
import java.util.Iterator;
import org.bukkit.inventory.Inventory;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_CookingTime;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_Experience;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_HideFromBook;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMGRI_BurningLegacy;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

public class RMG_BurningLegacy extends RecipeMakerGUI
{
    @NotNull
    HashMap<Integer, Integer> inputLinks;
    @NotNull
    final RMGRI_BurningLegacy interpreter;
    
    public RMG_BurningLegacy(@NotNull final Player player, @NotNull final MMOItemTemplate mmoItemTemplate, @NotNull final String s, @NotNull final RecipeRegistry recipeRegistry) {
        super(player, mmoItemTemplate, s, recipeRegistry);
        this.inputLinks = new HashMap<Integer, Integer>();
        this.addButton(new RBA_HideFromBook(this));
        this.addButton(new RBA_Experience(this));
        this.addButton(new RBA_CookingTime(this));
        if (!this.isShowingInput()) {
            this.switchInput();
        }
        this.interpreter = new RMGRI_BurningLegacy(this.getNameSection());
        this.inputLinks.put(40, 0);
    }
    
    @Override
    public int getButtonsRow() {
        return 2;
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
