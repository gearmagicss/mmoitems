// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.type.RBA_BooleanButton;

public class RBA_DropGems extends RBA_BooleanButton
{
    public static final String SMITH_GEMS = "drop-gems";
    @NotNull
    final ItemStack booleanButton;
    
    public RBA_DropGems(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.booleanButton = RecipeMakerGUI.addLore(ItemFactory.of(Material.EMERALD).name("§aDrop Gemstones").lore((Iterable)SilentNumbers.chop("Usually, gemstones that dont fit the new item are lost. Enable this to make them drop (and be recovered) instead.", 65, "§7")).build(), SilentNumbers.toArrayList((Object[])new String[] { "" }));
    }
    
    @NotNull
    @Override
    public String getBooleanConfigPath() {
        return "drop-gems";
    }
    
    @NotNull
    @Override
    public ItemStack getBooleanButton() {
        return this.booleanButton;
    }
}
