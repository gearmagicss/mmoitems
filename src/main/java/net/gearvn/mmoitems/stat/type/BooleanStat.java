// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.ChatColor;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.BooleanData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.random.RandomBooleanData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import java.text.DecimalFormat;

public class BooleanStat extends ItemStat
{
    private static final DecimalFormat digit;
    
    public BooleanStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        if (o instanceof Boolean) {
            return new RandomBooleanData((boolean)o);
        }
        if (o instanceof Number) {
            return new RandomBooleanData(Double.parseDouble(o.toString()));
        }
        throw new IllegalArgumentException("Must specify a number (chance) or true/false");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (((BooleanData)statData).isEnabled()) {
            itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
            itemStackBuilder.getLore().insert(this.getPath(), MMOItems.plugin.getLanguage().getStatFormat(this.getPath()));
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (((BooleanData)statData).isEnabled()) {
            list.add(new ItemTag(this.getNBTPath(), (Object)true));
        }
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)(editionInventory.getEditedSection().getBoolean(this.getPath()) ? null : Boolean.valueOf(true)));
            editionInventory.registerTemplateEdition();
        }
        else if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the probability you want (a percentage)");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final double double1 = MMOUtils.parseDouble(s);
        Validate.isTrue(double1 >= 0.0 && double1 <= 100.0, "Chance must be between 0 and 100");
        editionInventory.getEditedSection().set(this.getPath(), (Object)(double1 / 100.0));
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + ChatColor.GREEN + BooleanStat.digit.format(double1) + "% Chance" + ChatColor.GRAY + ".");
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.BOOLEAN));
        }
        final BooleanData booleanData = (BooleanData)this.getLoadedNBT(list);
        if (booleanData != null) {
            readMMOItem.setData(this, booleanData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new BooleanData((boolean)tagAtPath.getValue());
        }
        return null;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            final double chance = optional.get().getChance();
            list.add(ChatColor.GRAY + "Current Value: " + ((chance >= 1.0) ? (ChatColor.GREEN + "True") : ((chance <= 0.0) ? (ChatColor.RED + "False") : (ChatColor.GREEN + BooleanStat.digit.format(chance * 100.0) + "% Chance"))));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "False");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to switch this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to choose a probability to have this option.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new BooleanData(false);
    }
    
    static {
        digit = new DecimalFormat("0.#");
    }
}
