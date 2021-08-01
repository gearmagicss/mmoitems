// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry;

import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.NamespacedKey;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface RecipeRegistry
{
    @NotNull
    String getRecipeConfigPath();
    
    @NotNull
    String getRecipeTypeName();
    
    @NotNull
    ItemStack getDisplayListItem();
    
    void openForPlayer(@NotNull final EditionInventory p0, @NotNull final String p1, final Object... p2);
    
    @NotNull
    MythicRecipeBlueprint sendToMythicLib(@NotNull final MMOItemTemplate p0, @NotNull final ConfigurationSection p1, @NotNull final String p2, @NotNull final Ref<NamespacedKey> p3, @NotNull final FriendlyFeedbackProvider p4) throws IllegalArgumentException;
}
