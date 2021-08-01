// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.interpreters;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.ArrayList;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;

public class RMGRI_Shaped implements RMG_RecipeInterpreter
{
    @NotNull
    final ProvidedUIFilter[][] inputRecipe;
    @NotNull
    final ProvidedUIFilter[][] outputRecipe;
    @NotNull
    final ConfigurationSection section;
    public static final String emptyRow = "v AIR 0|v AIR 0|v AIR 0";
    
    @NotNull
    ProvidedUIFilter[][] buildIngredientsFromList(@NotNull final List<String> list) {
        final ProvidedUIFilter[][] array = new ProvidedUIFilter[3][3];
        for (int i = 0; i < 3; ++i) {
            final String[] split = updateRow((list.size() > i) ? list.get(i) : null).split("\\|");
            for (int j = 0; j < 3; ++j) {
                ProvidedUIFilter providedUIFilter = ProvidedUIFilter.getFromString((split.length > j) ? split[j] : null, (FriendlyFeedbackProvider)null);
                if (providedUIFilter == null) {
                    providedUIFilter = RecipeMakerGUI.AIR.clone();
                }
                array[i][j] = providedUIFilter;
            }
        }
        return array;
    }
    
    @NotNull
    ArrayList<String> toYML(@NotNull final ProvidedUIFilter[][] array) {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 3; ++i) {
            final ProvidedUIFilter[] array2 = (array.length > i) ? array[i] : new ProvidedUIFilter[3];
            final StringBuilder sb = new StringBuilder();
            for (ProvidedUIFilter clone : array2) {
                if (clone == null) {
                    clone = RecipeMakerGUI.AIR.clone();
                }
                if (sb.length() != 0) {
                    sb.append("|");
                }
                sb.append(clone);
            }
            list.add(sb.toString());
        }
        return list;
    }
    
    public void setInput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n < 0 || n > 8) {
            return;
        }
        this.inputRecipe[SilentNumbers.floor(n / 3.0)][n - 3 * SilentNumbers.floor(n / 3.0)] = providedUIFilter;
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getInput(final int n) {
        if (n < 0 || n > 8) {
            return null;
        }
        return this.inputRecipe[SilentNumbers.floor(n / 3.0)][n - 3 * SilentNumbers.floor(n / 3.0)];
    }
    
    public void setOutput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n < 0 || n > 8) {
            return;
        }
        this.outputRecipe[SilentNumbers.floor(n / 3.0)][n - 3 * SilentNumbers.floor(n / 3.0)] = providedUIFilter;
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getOutput(final int n) {
        if (n < 0 || n > 8) {
            return null;
        }
        return this.outputRecipe[SilentNumbers.floor(n / 3.0)][n - 3 * SilentNumbers.floor(n / 3.0)];
    }
    
    @NotNull
    public ConfigurationSection getSection() {
        return this.section;
    }
    
    public RMGRI_Shaped(@NotNull final ConfigurationSection section) {
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
    
    @NotNull
    public static String updateRow(@Nullable final String s) {
        if (s == null || s.isEmpty()) {
            return "v AIR 0|v AIR 0|v AIR 0";
        }
        if (s.contains("|")) {
            final String[] split = s.split("\\|");
            if (split.length == 3) {
                return s;
            }
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; ++i) {
                if (i != 0) {
                    sb.append("|");
                }
                if (i < split.length) {
                    sb.append(RecipeMakerGUI.poofFromLegacy(split[i]));
                }
                else {
                    sb.append("v AIR 0");
                }
            }
            return sb.toString();
        }
        else {
            if (s.contains(" ")) {
                final StringBuilder sb2 = new StringBuilder();
                final String[] split2 = s.split(" ");
                for (int j = 0; j < 3; ++j) {
                    if (j != 0) {
                        sb2.append("|");
                    }
                    if (j < split2.length) {
                        sb2.append(RecipeMakerGUI.poofFromLegacy(split2[j]));
                    }
                    else {
                        sb2.append("v AIR 0");
                    }
                }
                return sb2.toString();
            }
            return RecipeMakerGUI.poofFromLegacy(s) + "|v AIR 0|v AIR 0";
        }
    }
}
