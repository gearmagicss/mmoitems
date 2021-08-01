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

public class RBA_CookingTime extends RBA_DoubleButton
{
    public static final String FURNACE_TIME = "time";
    public static final double DEFAULT = 200.0;
    @NotNull
    final ItemStack doubleButton;
    
    public RBA_CookingTime(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.doubleButton = RecipeMakerGUI.addLore(ItemFactory.of(Material.CLOCK).name("§cDuration").lore((Iterable)SilentNumbers.chop("How long it takes this recipe to finish 'cooking' x)", 65, "§7")).build(), SilentNumbers.toArrayList((Object[])new String[] { "" }));
    }
    
    @NotNull
    @Override
    public String getDoubleConfigPath() {
        return "time";
    }
    
    @Nullable
    @Override
    public QuickNumberRange getRange() {
        return new QuickNumberRange(Double.valueOf(0.0), (Double)null);
    }
    
    @Override
    public boolean requireInteger() {
        return true;
    }
    
    @Override
    public double getDefaultValue() {
        return 200.0;
    }
    
    @NotNull
    @Override
    public ItemStack getDoubleButton() {
        return this.doubleButton;
    }
}
