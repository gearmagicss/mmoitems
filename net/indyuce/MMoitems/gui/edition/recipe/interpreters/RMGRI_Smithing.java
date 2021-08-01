// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.interpreters;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;

public class RMGRI_Smithing implements RMG_RecipeInterpreter
{
    @NotNull
    ProvidedUIFilter inputItem;
    @NotNull
    ProvidedUIFilter outputItem;
    @NotNull
    ProvidedUIFilter inputIngot;
    @NotNull
    ProvidedUIFilter outputIngot;
    @NotNull
    final ConfigurationSection section;
    public static final String emptyIngredients = "v AIR -|v AIR -";
    
    @NotNull
    String toYML(@NotNull final ProvidedUIFilter obj, @NotNull final ProvidedUIFilter obj2) {
        return obj + "|" + obj2;
    }
    
    @NotNull
    public ProvidedUIFilter getInputItem() {
        return this.inputItem;
    }
    
    public void setInputItem(@NotNull final ProvidedUIFilter inputItem) {
        this.inputItem = inputItem;
    }
    
    @NotNull
    public ProvidedUIFilter getOutputItem() {
        return this.outputItem;
    }
    
    public void setOutputItem(@NotNull final ProvidedUIFilter outputItem) {
        this.outputItem = outputItem;
    }
    
    @NotNull
    public ProvidedUIFilter getInputIngot() {
        return this.inputIngot;
    }
    
    public void setInputIngot(@NotNull final ProvidedUIFilter inputIngot) {
        this.inputIngot = inputIngot;
    }
    
    @NotNull
    public ProvidedUIFilter getOutputIngot() {
        return this.outputIngot;
    }
    
    public void setOutputIngot(@NotNull final ProvidedUIFilter outputIngot) {
        this.outputIngot = outputIngot;
    }
    
    @NotNull
    public ConfigurationSection getSection() {
        return this.section;
    }
    
    public RMGRI_Smithing(@NotNull final ConfigurationSection section) {
        this.section = section;
        final String updateIngredients = updateIngredients(this.section.getString("input"));
        final String updateIngredients2 = updateIngredients(this.section.getString("output"));
        final String[] split = updateIngredients.split("\\|");
        final String[] split2 = updateIngredients2.split("\\|");
        final ProvidedUIFilter fromString = ProvidedUIFilter.getFromString(split[0], (FriendlyFeedbackProvider)null);
        final ProvidedUIFilter fromString2 = ProvidedUIFilter.getFromString(split2[0], (FriendlyFeedbackProvider)null);
        final ProvidedUIFilter fromString3 = ProvidedUIFilter.getFromString(split[1], (FriendlyFeedbackProvider)null);
        final ProvidedUIFilter fromString4 = ProvidedUIFilter.getFromString(split2[1], (FriendlyFeedbackProvider)null);
        this.inputItem = ((fromString != null) ? fromString : RecipeMakerGUI.AIR.clone());
        this.inputIngot = ((fromString3 != null) ? fromString3 : RecipeMakerGUI.AIR.clone());
        this.outputItem = ((fromString2 != null) ? fromString2 : RecipeMakerGUI.AIR.clone());
        this.outputIngot = ((fromString4 != null) ? fromString4 : RecipeMakerGUI.AIR.clone());
    }
    
    public void setInput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n == 0) {
            this.setInputItem(providedUIFilter);
        }
        else if (n == 1) {
            this.setInputIngot(providedUIFilter);
        }
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getInput(final int n) {
        if (n == 0) {
            return this.getInputItem();
        }
        if (n == 1) {
            return this.getInputIngot();
        }
        return null;
    }
    
    public void setOutput(final int n, @NotNull final ProvidedUIFilter providedUIFilter) {
        if (n == 0) {
            this.setOutputItem(providedUIFilter);
        }
        else if (n == 1) {
            this.setOutputIngot(providedUIFilter);
        }
    }
    
    @Nullable
    @Override
    public ProvidedUIFilter getOutput(final int n) {
        if (n == 0) {
            return this.getOutputItem();
        }
        if (n == 1) {
            return this.getOutputIngot();
        }
        return null;
    }
    
    @Override
    public void editInput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter providedUIFilter, final int n) {
        this.setInput(n, providedUIFilter);
        configurationSection.set("input", (Object)this.toYML(this.getInputItem(), this.getInputIngot()));
    }
    
    @Override
    public void editOutput(@NotNull final ConfigurationSection configurationSection, @NotNull final ProvidedUIFilter providedUIFilter, final int n) {
        this.setOutput(n, providedUIFilter);
        configurationSection.set("output", (Object)this.toYML(this.getOutputItem(), this.getOutputIngot()));
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
    public static String updateIngredients(@Nullable final String s) {
        if (s == null || s.isEmpty()) {
            return "v AIR -|v AIR -";
        }
        if (s.contains("|")) {
            final String[] split = s.split("\\|");
            if (split.length == 2) {
                return s;
            }
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 2; ++i) {
                if (i != 0) {
                    sb.append("|");
                }
                if (i < split.length) {
                    sb.append(RecipeMakerGUI.poofFromLegacy(split[i]));
                }
                else {
                    sb.append("v AIR -");
                }
            }
            return sb.toString();
        }
        else {
            if (s.contains(" ")) {
                final StringBuilder sb2 = new StringBuilder();
                final String[] split2 = s.split(" ");
                for (int j = 0; j < 2; ++j) {
                    if (j != 0) {
                        sb2.append("|");
                    }
                    if (j < split2.length) {
                        sb2.append(RecipeMakerGUI.poofFromLegacy(split2[j]));
                    }
                    else {
                        sb2.append("v AIR -");
                    }
                }
                return sb2.toString();
            }
            return RecipeMakerGUI.poofFromLegacy(s) + "|v AIR 0";
        }
    }
}
