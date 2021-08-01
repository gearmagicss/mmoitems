// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import org.bukkit.ChatColor;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class RBA_InputOutput extends RecipeButtonAction
{
    boolean showingInput;
    @NotNull
    final ItemStack button;
    
    public RBA_InputOutput(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.button = RecipeMakerGUI.addLore(ItemFactory.of(Material.CRAFTING_TABLE).name("§cSwitch to Output Mode").lore((Iterable)SilentNumbers.chop("INPUT is the ingredients of the recipe, but (like milk buckets when crafting a cake) these ingredients may not be entirely consumed. In such cases, use the OUTPUT mode to specify what the ingredients will turn into.", 63, "§7")).build(), SilentNumbers.toArrayList((Object[])new String[] { "" }));
        this.showingInput = true;
    }
    
    @Override
    public boolean runPrimary() {
        this.getInv().switchInput();
        this.getInv().refreshInventory();
        this.clickSFX();
        return true;
    }
    
    @Override
    public void primaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @Override
    public boolean runSecondary() {
        return false;
    }
    
    @Override
    public void secondaryProcessInput(@NotNull final String s, final Object... array) {
    }
    
    @NotNull
    @Override
    public ItemStack getButton() {
        return RecipeMakerGUI.addLore(this.button.clone(), SilentNumbers.toArrayList((Object[])new String[] { "§7Currently Showing: " + (this.getInv().isShowingInput() ? "§6INPUT" : "§3OUTPUT"), "", ChatColor.YELLOW + "\u25ba" + " Left click to switch mode." }));
    }
}
