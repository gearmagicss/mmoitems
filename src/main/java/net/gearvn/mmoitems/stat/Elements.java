// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.ElementListData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.ElementsEdition;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.random.RandomElementListData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.Element;
import java.util.HashMap;
import net.Indyuce.mmoitems.stat.type.Previewable;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Elements extends ItemStat implements Previewable
{
    static HashMap<Element, String> defenseNBTpaths;
    static HashMap<Element, String> damageNBTpaths;
    
    public Elements() {
        super("ELEMENT", Material.SLIME_BALL, "Elements", new String[] { "The elements of your item." }, new String[] { "slashing", "piercing", "blunt", "offhand", "range", "tool", "armor", "gem_stone" }, new Material[0]);
        if (Elements.defenseNBTpaths == null) {
            Elements.defenseNBTpaths = new HashMap<Element, String>();
            Elements.damageNBTpaths = new HashMap<Element, String>();
            for (final Element element : Element.values()) {
                Elements.defenseNBTpaths.put(element, "MMOITEMS_" + element.name() + "_DEFENSE");
                Elements.damageNBTpaths.put(element, "MMOITEMS_" + element.name() + "_DAMAGE");
            }
        }
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        return new RandomElementListData((ConfigurationSection)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new ElementsEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("element")) {
            editionInventory.getEditedSection().set("element", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Elements successfully removed.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String string = array[0].toString();
        final NumericStatFormula numericStatFormula = new NumericStatFormula(s);
        numericStatFormula.fillConfigurationSection(editionInventory.getEditedSection(), "element." + string);
        final String s2 = string.split("\\.")[0];
        if (editionInventory.getEditedSection().contains("element")) {
            if (editionInventory.getEditedSection().getConfigurationSection("element").contains(s2) && editionInventory.getEditedSection().getConfigurationSection("element." + s2).getKeys(false).isEmpty()) {
                editionInventory.getEditedSection().set("element." + s2, (Object)null);
            }
            if (editionInventory.getEditedSection().getConfigurationSection("element").getKeys(false).isEmpty()) {
                editionInventory.getEditedSection().set("element", (Object)null);
            }
        }
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + MMOUtils.caseOnWords(string.replace(".", " ")) + ChatColor.GRAY + " successfully changed to " + ChatColor.GOLD + numericStatFormula.toString() + ChatColor.GRAY + ".");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            final RandomElementListData randomElementListData = optional.get();
            randomElementListData.getDamageElements().forEach(element -> list.add(ChatColor.GRAY + "* " + element.getName() + " Damage: " + ChatColor.RED + randomElementListData.getDamage(element) + " (%)"));
            randomElementListData.getDefenseElements().forEach(element2 -> list.add(ChatColor.GRAY + "* " + element2.getName() + " Damage: " + ChatColor.RED + randomElementListData.getDefense(element2) + " (%)"));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to access the elements edition menu.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove all the elements.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new ElementListData();
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final ElementListData elementListData = (ElementListData)statData;
        for (final Element element : elementListData.getDamageElements()) {
            final String string = element.name().toLowerCase() + "-damage";
            itemStackBuilder.getLore().insert(string, DoubleStat.formatPath(ItemStat.translate(string), true, elementListData.getDamage(element)));
        }
        for (final Element element2 : elementListData.getDefenseElements()) {
            final String string2 = element2.name().toLowerCase() + "-defense";
            itemStackBuilder.getLore().insert(string2, DoubleStat.formatPath(ItemStat.translate(string2), true, elementListData.getDefense(element2)));
        }
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ElementListData elementListData = (ElementListData)statData;
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        for (final Element key : elementListData.getDamageElements()) {
            list.add(new ItemTag((String)Elements.damageNBTpaths.get(key), (Object)elementListData.getDamage(key)));
        }
        for (final Element key2 : elementListData.getDefenseElements()) {
            list.add(new ItemTag((String)Elements.defenseNBTpaths.get(key2), (Object)elementListData.getDefense(key2)));
        }
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        for (final Element element : Element.values()) {
            if (readMMOItem.getNBT().hasTag((String)Elements.damageNBTpaths.get(element))) {
                list.add(ItemTag.getTagAtPath((String)Elements.damageNBTpaths.get(element), readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE));
            }
            if (readMMOItem.getNBT().hasTag((String)Elements.defenseNBTpaths.get(element))) {
                list.add(ItemTag.getTagAtPath((String)Elements.defenseNBTpaths.get(element), readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE));
            }
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ElementListData elementListData = new ElementListData();
        boolean b = false;
        for (final Element element : Element.values()) {
            final ItemTag tagAtPath = ItemTag.getTagAtPath((String)Elements.damageNBTpaths.get(element), (ArrayList)list);
            final ItemTag tagAtPath2 = ItemTag.getTagAtPath((String)Elements.defenseNBTpaths.get(element), (ArrayList)list);
            if (tagAtPath != null) {
                elementListData.setDamage(element, (double)tagAtPath.getValue());
                b = true;
            }
            if (tagAtPath2 != null) {
                elementListData.setDefense(element, (double)tagAtPath2.getValue());
                b = true;
            }
        }
        if (b) {
            return elementListData;
        }
        return null;
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        Validate.isTrue(statData instanceof ElementListData, "Current Data is not ElementListData");
        Validate.isTrue(randomStatData instanceof RandomElementListData, "Template Data is not RandomElementListData");
        for (final Element element : Element.values()) {
            final NumericStatFormula damage = ((RandomElementListData)randomStatData).getDamage(element);
            final NumericStatFormula defense = ((RandomElementListData)randomStatData).getDefense(element);
            final double calculate = damage.calculate(0.0, -2.5);
            final double calculate2 = damage.calculate(0.0, 2.5);
            final double calculate3 = defense.calculate(0.0, -2.5);
            final double calculate4 = defense.calculate(0.0, 2.5);
            if (calculate != 0.0 || calculate2 != 0.0) {
                final String string = element.name().toLowerCase() + "-damage";
                String s;
                if (SilentNumbers.round(calculate, 2) == SilentNumbers.round(calculate2, 2)) {
                    s = DoubleStat.formatPath(ItemStat.translate(string), true, calculate);
                }
                else {
                    s = DoubleStat.formatPath(ItemStat.translate(string), true, calculate, calculate2);
                }
                itemStackBuilder.getLore().insert(string, s);
            }
            if (calculate3 != 0.0 || calculate4 != 0.0) {
                final String string2 = element.name().toLowerCase() + "-defense";
                String s2;
                if (SilentNumbers.round(calculate3, 2) == SilentNumbers.round(calculate4, 2)) {
                    s2 = DoubleStat.formatPath(ItemStat.translate(string2), true, calculate3);
                }
                else {
                    s2 = DoubleStat.formatPath(ItemStat.translate(string2), true, calculate3, calculate4);
                }
                itemStackBuilder.getLore().insert(string2, s2);
            }
        }
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    static {
        Elements.defenseNBTpaths = null;
        Elements.damageNBTpaths = null;
    }
}
