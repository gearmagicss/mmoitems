// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Iterator;
import net.Indyuce.mmoitems.MMOUtils;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeListEdition;
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
            new RecipeListEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
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
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String s2 = (String)array[0];
        switch (s2) {
            case "recipe": {
                final int intValue = (int)array[2];
                Validate.notNull((Object)MMOItems.plugin.getRecipes().getWorkbenchIngredient(s), "Invalid ingredient");
                if (array[1].equals("shaped")) {
                    final List stringList = editionInventory.getEditedSection().getStringList("crafting.shaped.1");
                    final String[] split = stringList.get(intValue / 3).split(" ");
                    split[intValue % 3] = s;
                    stringList.set(intValue / 3, split[0] + " " + split[1] + " " + split[2]);
                    final Iterator<String> iterator = stringList.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().equals("AIR AIR AIR")) {
                            continue;
                        }
                        editionInventory.getEditedSection().set("crafting.shaped.1", (Object)stringList);
                        editionInventory.registerTemplateEdition();
                        break;
                    }
                    break;
                }
                final List stringList2 = editionInventory.getEditedSection().getStringList("crafting.shapeless.1");
                stringList2.set(intValue, s);
                final Iterator<String> iterator2 = stringList2.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().equals("AIR")) {
                        continue;
                    }
                    editionInventory.getEditedSection().set("crafting.shapeless.1", (Object)stringList2);
                    editionInventory.registerTemplateEdition();
                    break;
                }
                break;
            }
            case "item": {
                final String[] split2 = s.split(" ");
                Validate.isTrue(split2.length == 3, "Invalid format");
                Validate.notNull((Object)MMOItems.plugin.getRecipes().getWorkbenchIngredient(split2[0]), "Invalid ingredient");
                final int int1 = Integer.parseInt(split2[1]);
                final double double1 = MMOUtils.parseDouble(split2[2]);
                editionInventory.getEditedSection().set("crafting." + array[1] + ".1.item", (Object)split2[0]);
                editionInventory.getEditedSection().set("crafting." + array[1] + ".1.time", (Object)int1);
                editionInventory.getEditedSection().set("crafting." + array[1] + ".1.experience", (Object)double1);
                editionInventory.registerTemplateEdition();
                break;
            }
            case "smithing": {
                final String[] split3 = s.split(" ");
                Validate.isTrue(split3.length == 2, "Invalid format");
                Validate.notNull((Object)MMOItems.plugin.getRecipes().getWorkbenchIngredient(split3[0]), "Invalid first ingredient");
                Validate.notNull((Object)MMOItems.plugin.getRecipes().getWorkbenchIngredient(split3[1]), "Invalid second ingredient");
                editionInventory.getEditedSection().set("crafting.smithing.1.input1", (Object)split3[0]);
                editionInventory.getEditedSection().set("crafting.smithing.1.input2", (Object)split3[1]);
                editionInventory.registerTemplateEdition();
                break;
            }
            default: {
                throw new IllegalArgumentException("Recipe type not recognized");
            }
        }
    }
    
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
