// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba.type;

import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;

public abstract class RBA_BooleanButton extends RecipeButtonAction
{
    public RBA_BooleanButton(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
    }
    
    public boolean isEnabled() {
        return this.getInv().getNameSection().getBoolean(this.getBooleanConfigPath(), false);
    }
    
    @NotNull
    public abstract String getBooleanConfigPath();
    
    @Override
    public boolean runPrimary() {
        this.getInv().getNameSection().set(this.getBooleanConfigPath(), (Object)!this.isEnabled());
        this.clickSFX();
        this.getInv().registerTemplateEdition();
        return true;
    }
    
    @Override
    public void primaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @Override
    public boolean runSecondary() {
        this.getInv().getNameSection().set(this.getBooleanConfigPath(), (Object)null);
        this.clickSFX();
        this.getInv().registerTemplateEdition();
        return true;
    }
    
    @Override
    public void secondaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @NotNull
    public abstract ItemStack getBooleanButton();
    
    @NotNull
    @Override
    public ItemStack getButton() {
        return RecipeMakerGUI.addLore(this.getBooleanButton().clone(), SilentNumbers.toArrayList((Object[])new String[] { "", "§7Current Value: " + (this.isEnabled() ? "§aTRUE" : "§cFALSE"), "", ChatColor.YELLOW + "\u25ba" + " Right click to reset §8(to§4 FALSE§8)§e.", ChatColor.YELLOW + "\u25ba" + " Left click to toggle this option." }));
    }
}
