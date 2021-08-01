// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.interpreters;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import org.jetbrains.annotations.NotNull;
import org.bukkit.configuration.ConfigurationSection;

public interface RMG_RecipeInterpreter
{
    void editInput(@NotNull final ConfigurationSection p0, @NotNull final ProvidedUIFilter p1, final int p2);
    
    void editOutput(@NotNull final ConfigurationSection p0, @NotNull final ProvidedUIFilter p1, final int p2);
    
    void deleteInput(@NotNull final ConfigurationSection p0, final int p1);
    
    void deleteOutput(@NotNull final ConfigurationSection p0, final int p1);
    
    @Nullable
    ProvidedUIFilter getInput(final int p0);
    
    @Nullable
    ProvidedUIFilter getOutput(final int p0);
}
