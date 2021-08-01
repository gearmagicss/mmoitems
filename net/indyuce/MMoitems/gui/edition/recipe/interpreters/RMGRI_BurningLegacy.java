// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.interpreters;

import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.Nullable;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;

public class RMGRI_BurningLegacy implements RMG_RecipeInterpreter
{
    @NotNull
    ProvidedUIFilter input;
    @NotNull
    final ConfigurationSection section;
    public static final String ITEM = "item";
    public static final String TIME = "time";
    public static final String EXPERIENCE = "experience";
    
    @NotNull
    public ProvidedUIFilter getInput() {
        return this.input;
    }
    
    public void setInput(@Nullable final ProvidedUIFilter providedUIFilter) {
        this.input = ((providedUIFilter == null) ? RecipeMakerGUI.AIR : providedUIFilter);
    }
    
    @NotNull
    public ConfigurationSection getSection() {
        return this.section;
    }
    
    public RMGRI_BurningLegacy(@NotNull final ConfigurationSection section) {
        this.section = section;
        this.input = ProvidedUIFilter.getFromString(RecipeMakerGUI.poofFromLegacy(section.getString("input")), (FriendlyFeedbackProvider)null);
        if (this.input == null) {
            this.input = RecipeMakerGUI.AIR.clone();
        }
    }
    
    @Override
    public void editInput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter input, final int n) {
        if (n != 0) {
            return;
        }
        this.setInput(input);
        configurationSection.set("item", (Object)input.toString());
    }
    
    @Override
    public void editOutput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter providedUIFilter, final int n) {
    }
    
    @Override
    public void deleteInput(@NotNull final ConfigurationSection configurationSection, final int n) {
        this.editInput(configurationSection, RecipeMakerGUI.AIR.clone(), n);
    }
    
    @Override
    public void deleteOutput(@NotNull final ConfigurationSection configurationSection, final int n) {
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getInput(final int n) {
        if (n == 0) {
            return this.input;
        }
        return null;
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getOutput(final int n) {
        return null;
    }
}
