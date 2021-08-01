// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba.type;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;

public abstract class RBA_DoubleButton extends RecipeButtonAction
{
    @NotNull
    public final String[] amountLog;
    @NotNull
    public final String[] integerLog;
    
    public RBA_DoubleButton(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.amountLog = new String[] { FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Write in the chat a number, ex $e2.5$b.", new String[0]) };
        this.integerLog = new String[] { FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Write in the chat an integer number, ex $e8$b.", new String[0]) };
    }
    
    public double getValue() {
        return this.getInv().getNameSection().getDouble(this.getDoubleConfigPath(), this.getDefaultValue());
    }
    
    @NotNull
    public abstract String getDoubleConfigPath();
    
    @Override
    public boolean runPrimary() {
        new StatEdition(this.getInv(), ItemStats.CRAFTING, new Object[] { 2, this }).enable(this.requireInteger() ? this.integerLog : this.amountLog);
        return true;
    }
    
    @Override
    public void primaryProcessInput(@NotNull final String s, final Object... array) {
        Double obj;
        if (this.requireInteger()) {
            final Integer integerParse = SilentNumbers.IntegerParse(s);
            if (integerParse == null) {
                throw new IllegalArgumentException("Expected integer number instead of $u" + s);
            }
            obj = (double)integerParse;
        }
        else {
            obj = SilentNumbers.DoubleParse(s);
            if (obj == null) {
                throw new IllegalArgumentException("Expected a number instead of $u" + s);
            }
        }
        if (this.getRange() != null && !this.getRange().inRange((double)obj)) {
            throw new IllegalArgumentException("Number $r" + obj + "$b is out of range. Expected " + this.getRange().toStringColored());
        }
        this.getInv().getNameSection().set(this.getDoubleConfigPath(), (Object)obj);
    }
    
    @Nullable
    public abstract QuickNumberRange getRange();
    
    public abstract boolean requireInteger();
    
    @Override
    public boolean runSecondary() {
        this.getInv().getNameSection().set(this.getDoubleConfigPath(), (Object)null);
        this.clickSFX();
        this.getInv().registerTemplateEdition();
        return true;
    }
    
    @Override
    public void secondaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    public abstract double getDefaultValue();
    
    @NotNull
    public abstract ItemStack getDoubleButton();
    
    @NotNull
    @Override
    public ItemStack getButton() {
        return RecipeMakerGUI.addLore(this.getDoubleButton().clone(), SilentNumbers.toArrayList((Object[])new String[] { "", "§7Current Value: " + this.getValue(), "", ChatColor.YELLOW + "\u25ba" + " Right click to reset §8(to§4 " + this.getDefaultValue() + "§8)§e.", ChatColor.YELLOW + "\u25ba" + " Left click to toggle this option." }));
    }
}
