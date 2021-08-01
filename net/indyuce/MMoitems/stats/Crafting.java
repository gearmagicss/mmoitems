// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.jetbrains.annotations.Nullable;
import org.bukkit.configuration.ConfigurationSection;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import net.Indyuce.mmoitems.gui.edition.recipe.rba.RecipeButtonAction;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import io.lumine.mythic.lib.api.crafting.uimanager.UIFilterManager;
import net.Indyuce.mmoitems.gui.edition.recipe.interpreters.RMG_RecipeInterpreter;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Crafting extends ItemStat
{
    public Crafting() {
        super("CRAFTING", VersionMaterial.CRAFTING_TABLE.toMaterial(), "Crafting", new String[] { "The crafting recipes of your item.", "Changing a recipe requires &o/mi reload recipes&7." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new RecipeBrowserGUI(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("crafting")) {
            editionInventory.getEditedSection().set("crafting", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Crafting recipes successfully removed. Make sure you reload active recipes using " + ChatColor.RED + "/mi reload recipes" + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to access the crafting edition menu.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove all crafting recipes.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringData(null);
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull String str, final Object... array) {
        final int intValue = (int)array[0];
        switch (intValue) {
            case 0:
            case 1: {
                final int index = str.indexOf(32);
                Object fromString = null;
                if (index > 0) {
                    String str2 = str.substring(index + 1);
                    if (SilentNumbers.DoubleTryParse(str2)) {
                        str2 += "..";
                    }
                    fromString = QuickNumberRange.getFromString(str2);
                }
                if (index <= 0 || fromString != null) {
                    if (fromString == null) {
                        fromString = new QuickNumberRange(Double.valueOf(1.0), (Double)null);
                    }
                    else {
                        str = str.substring(0, index);
                    }
                    if (str.contains(".")) {
                        final String[] split = str.split("\\.");
                        str = "m " + split[0] + " " + split[1] + " " + fromString;
                    }
                    else {
                        str = "v " + str + " - " + fromString;
                    }
                }
                final RMG_RecipeInterpreter rmg_RecipeInterpreter = (RMG_RecipeInterpreter)array[1];
                final int intValue2 = (int)array[2];
                final ProvidedUIFilter uiFilter = UIFilterManager.getUIFilter(str, editionInventory.getFFP());
                if (uiFilter == null) {
                    throw new IllegalArgumentException("");
                }
                if (!uiFilter.isValid(editionInventory.getFFP())) {
                    throw new IllegalArgumentException("");
                }
                final ConfigurationSection section = RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(RecipeMakerGUI.getSection(editionInventory.getEditedSection(), "crafting"), ((RecipeMakerGUI)editionInventory).getRecipeRegistry().getRecipeConfigPath()), ((RecipeMakerGUI)editionInventory).getRecipeName());
                if (intValue == 0) {
                    rmg_RecipeInterpreter.editInput(section, uiFilter, intValue2);
                }
                else {
                    rmg_RecipeInterpreter.editOutput(section, uiFilter, intValue2);
                }
                editionInventory.registerTemplateEdition();
                break;
            }
            case 2:
            case 3: {
                if (array.length < 2) {
                    return;
                }
                if (!(array[1] instanceof RecipeButtonAction)) {
                    return;
                }
                if (intValue == 2) {
                    ((RecipeButtonAction)array[1]).primaryProcessInput(str, array);
                }
                else {
                    ((RecipeButtonAction)array[1]).secondaryProcessInput(str, array);
                }
                editionInventory.registerTemplateEdition();
                break;
            }
            default: {
                editionInventory.registerTemplateEdition();
                break;
            }
        }
    }
    
    @Nullable
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return null;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
}
