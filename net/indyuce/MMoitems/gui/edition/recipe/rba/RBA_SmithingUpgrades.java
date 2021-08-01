// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import net.Indyuce.mmoitems.api.crafting.recipe.SmithingCombinationType;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.type.RBA_ChooseableButton;

public class RBA_SmithingUpgrades extends RBA_ChooseableButton
{
    @NotNull
    final ItemStack chooseableButton;
    public static final String SMITH_UPGRADES = "upgrades";
    static ArrayList<String> smithingList;
    
    public RBA_SmithingUpgrades(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.chooseableButton = ItemFactory.of(Material.ANVIL).name("§aUpgrades Transfer").lore((Iterable)SilentNumbers.chop("What will happen to the upgrades of the ingredients? Will upgraded ingredients produce an upgraded output item?", 65, "§7")).build();
    }
    
    @NotNull
    @Override
    public ItemStack getChooseableButton() {
        return this.chooseableButton;
    }
    
    @NotNull
    @Override
    public String getChooseableConfigPath() {
        return "upgrades";
    }
    
    @NotNull
    @Override
    public ArrayList<String> getChooseableList() {
        return getSmithingList();
    }
    
    @NotNull
    @Override
    public String getDefaultValue() {
        return SmithingCombinationType.MAXIMUM.toString();
    }
    
    @NotNull
    @Override
    public String getChooseableDefinition(@NotNull final String s) {
        SmithingCombinationType smithingCombinationType = SmithingCombinationType.MAXIMUM;
        try {
            smithingCombinationType = SmithingCombinationType.valueOf(this.getCurrentChooseableValue());
        }
        catch (IllegalArgumentException ex) {}
        switch (smithingCombinationType) {
            case EVEN: {
                return "Will take the average of the upgrade levels of the combined items.";
            }
            case NONE: {
                return "Will ignore the upgrade levels of any ingredients.";
            }
            case MAXIMUM: {
                return "Output will have the upgrade level of the most upgraded ingredient.";
            }
            case MINIMUM: {
                return "Output will have the upgrade level of the least-upgraded upgradeable ingredient.";
            }
            case ADDITIVE: {
                return "The upgrade levels of the ingredients will be added, and the result will be the crafted item's level.";
            }
            default: {
                return "Unknown behaviour. Add description in net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_SmithingUpgrades";
            }
        }
    }
    
    @NotNull
    static ArrayList<String> getSmithingList() {
        if (RBA_SmithingUpgrades.smithingList != null) {
            return RBA_SmithingUpgrades.smithingList;
        }
        RBA_SmithingUpgrades.smithingList = new ArrayList<String>();
        final SmithingCombinationType[] values = SmithingCombinationType.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            RBA_SmithingUpgrades.smithingList.add(values[i].toString());
        }
        return RBA_SmithingUpgrades.smithingList;
    }
}
