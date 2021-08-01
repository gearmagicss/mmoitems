// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.rba;

import net.Indyuce.mmoitems.api.crafting.recipe.SmithingCombinationType;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.type.RBA_ChooseableButton;

public class RBA_SmithingEnchantments extends RBA_ChooseableButton
{
    @NotNull
    final ItemStack chooseableButton;
    public static final String SMITH_ENCHANTS = "enchantments";
    
    public RBA_SmithingEnchantments(@NotNull final RecipeMakerGUI recipeMakerGUI) {
        super(recipeMakerGUI);
        this.chooseableButton = ItemFactory.of(Material.ENCHANTING_TABLE).name("§aEnchantment Transfer").lore((Iterable)SilentNumbers.chop("What will happen to the enchantments of the ingredients? Will enchanted ingredients produce an enchanted output item?", 65, "§7")).build();
    }
    
    @NotNull
    @Override
    public ItemStack getChooseableButton() {
        return this.chooseableButton;
    }
    
    @NotNull
    @Override
    public String getChooseableConfigPath() {
        return "enchantments";
    }
    
    @NotNull
    @Override
    public ArrayList<String> getChooseableList() {
        return RBA_SmithingUpgrades.getSmithingList();
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
                return "For each enchantment, will take the average of that enchantment's level across the ingredients.";
            }
            case NONE: {
                return "Will ignore the enchantments of any ingredients.";
            }
            case MAXIMUM: {
                return "Output will have the best enchantment from each ingredient";
            }
            case MINIMUM: {
                return "Output will have worst enchantment from each ingredient with that enchantment.";
            }
            case ADDITIVE: {
                return "The enchantments of all ingredients will add together.";
            }
            default: {
                return "Unknown behaviour. Add description in net.Indyuce.mmoitems.gui.edition.recipe.rba.RBA_SmithingEnchantments";
            }
        }
    }
}
