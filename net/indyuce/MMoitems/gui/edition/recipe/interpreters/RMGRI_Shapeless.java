// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.interpreters;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;

public class RMGRI_Shapeless implements RMG_RecipeInterpreter
{
    @NotNull
    final ProvidedUIFilter[] inputRecipe;
    @NotNull
    final ProvidedUIFilter[] outputRecipe;
    @NotNull
    final ConfigurationSection section;
    
    @NotNull
    ProvidedUIFilter[] buildIngredientsFromList(@NotNull final List<String> list) {
        final ProvidedUIFilter[] array = new ProvidedUIFilter[9];
        for (int i = 0; i < 9; ++i) {
            ProvidedUIFilter providedUIFilter = ProvidedUIFilter.getFromString(RecipeMakerGUI.poofFromLegacy((list.size() > i) ? list.get(i) : null), (FriendlyFeedbackProvider)null);
            if (providedUIFilter == null) {
                providedUIFilter = RecipeMakerGUI.AIR.clone();
            }
            array[i] = providedUIFilter;
        }
        return array;
    }
    
    @NotNull
    ArrayList<String> toYML(@NotNull final ProvidedUIFilter[] array) {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 9; ++i) {
            list.add(((array.length > i) ? array[i] : RecipeMakerGUI.AIR.clone()).toString());
        }
        return list;
    }
    
    public void setInput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n < 0 || n > 8) {
            return;
        }
        this.inputRecipe[n] = providedUIFilter;
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getInput(final int n) {
        if (n < 0 || n > 8) {
            return null;
        }
        return this.inputRecipe[n];
    }
    
    public void setOutput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n < 0 || n > 8) {
            return;
        }
        this.outputRecipe[n] = providedUIFilter;
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getOutput(final int n) {
        if (n < 0 || n > 8) {
            return null;
        }
        return this.outputRecipe[n];
    }
    
    @NotNull
    public ConfigurationSection getSection() {
        return this.section;
    }
    
    public RMGRI_Shapeless(@NotNull final ConfigurationSection section) {
        this.section = section;
        this.inputRecipe = this.buildIngredientsFromList(this.section.getStringList("input"));
        this.outputRecipe = this.buildIngredientsFromList(this.section.getStringList("output"));
    }
    
    @Override
    public void editInput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter providedUIFilter, final int n) {
        this.setInput(n, providedUIFilter);
        configurationSection.set("input", (Object)this.toYML(this.inputRecipe));
    }
    
    @Override
    public void editOutput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter providedUIFilter, final int n) {
        this.setOutput(n, providedUIFilter);
        configurationSection.set("output", (Object)this.toYML(this.outputRecipe));
    }
    
    @Override
    public void deleteInput(@NotNull final ConfigurationSection configurationSection, final int n) {
        this.editInput(configurationSection, RecipeMakerGUI.AIR.clone(), n);
    }
    
    @Override
    public void deleteOutput(@NotNull final ConfigurationSection configurationSection, final int n) {
        this.editOutput(configurationSection, RecipeMakerGUI.AIR.clone(), n);
    }
}
