// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.type.RBA_DoubleButton;

public class RBA_Experience extends RBA_DoubleButton
{
    public static final String FURNACE_EXPERIENCE = "exp";
    public static final double DEFAULT = 0.35;
    @NotNull
    final ItemStack doubleButton;
    
    public RBA_Experience(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.doubleButton = RecipeMakerGUI.addLore(ItemFactory.of(Material.EXPERIENCE_BOTTLE).name("§aExperience").lore((Iterable)SilentNumbers.chop("This recipe gives experience when crafted, how much?", 65, "§7")).build(), SilentNumbers.toArrayList((Object[])new String[] { "" }));
    }
    
    @NotNull
    @Override
    public String getDoubleConfigPath() {
        return "exp";
    }
    
    @Nullable
    @Override
    public QuickNumberRange getRange() {
        return new QuickNumberRange(Double.valueOf(0.0), (Double)null);
    }
    
    @Override
    public boolean requireInteger() {
        return false;
    }
    
    @Override
    public double getDefaultValue() {
        return 0.35;
    }
    
    @NotNull
    @Override
    public ItemStack getDoubleButton() {
        return this.doubleButton;
    }
}
