// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba.type;

import java.util.Iterator;
import org.bukkit.ChatColor;
import java.util.Collection;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;

public abstract class RBA_ChooseableButton extends RecipeButtonAction
{
    public RBA_ChooseableButton(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
    }
    
    @Override
    public boolean runPrimary() {
        int index = this.getChooseableList().indexOf(this.getCurrentChooseableValue());
        if (index == -1) {
            return this.runSecondary();
        }
        if (++index >= this.getChooseableList().size()) {
            index = 0;
        }
        this.getInv().getNameSection().set(this.getChooseableConfigPath(), (Object)this.getChooseableList().get(index));
        this.clickSFX();
        this.getInv().registerTemplateEdition();
        return true;
    }
    
    @Override
    public boolean runSecondary() {
        this.getInv().getNameSection().set(this.getChooseableConfigPath(), (Object)null);
        this.clickSFX();
        this.getInv().registerTemplateEdition();
        return true;
    }
    
    @NotNull
    public abstract ItemStack getChooseableButton();
    
    @NotNull
    @Override
    public ItemStack getButton() {
        final String currentChooseableValue = this.getCurrentChooseableValue();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("");
        list.add("§7Current Value:§3 " + currentChooseableValue);
        list.addAll(SilentNumbers.chop(this.getChooseableDefinition(currentChooseableValue), 50, "  §b§o"));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to return to default value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to cycle through the options:");
        for (final String str : this.getChooseableList()) {
            String str2 = ChatColor.GOLD.toString();
            if (str.equals(currentChooseableValue)) {
                str2 = ChatColor.RED.toString() + ChatColor.BOLD;
            }
            list.add(str2 + "  " + "\u25b8" + " §7" + str);
        }
        return RecipeMakerGUI.addLore(this.getChooseableButton().clone(), list);
    }
    
    @NotNull
    public abstract String getChooseableConfigPath();
    
    @NotNull
    public abstract ArrayList<String> getChooseableList();
    
    @NotNull
    public String getCurrentChooseableValue() {
        final String string = this.getInv().getNameSection().getString(this.getChooseableConfigPath());
        return (string != null) ? string : this.getDefaultValue();
    }
    
    @NotNull
    public abstract String getDefaultValue();
    
    @NotNull
    public abstract String getChooseableDefinition(@NotNull final String p0);
    
    @Override
    public void secondaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @Override
    public void primaryProcessInput(@NotNull final String s, final Object... array) {
    }
}
