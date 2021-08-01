// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;

public abstract class RecipeButtonAction
{
    @NotNull
    final RecipeMakerGUI inv;
    
    @NotNull
    public RecipeMakerGUI getInv() {
        return this.inv;
    }
    
    public RecipeButtonAction(@NotNull final RecipeMakerGUI inv) {
        this.inv = inv;
    }
    
    public abstract boolean runPrimary();
    
    public abstract void primaryProcessInput(@NotNull final String p0, final Object... p1);
    
    public abstract boolean runSecondary();
    
    public abstract void secondaryProcessInput(@NotNull final String p0, final Object... p1);
    
    @NotNull
    public abstract ItemStack getButton();
    
    public void clickSFX() {
        this.getInv().getPlayer().playSound(this.getInv().getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }
}
