// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.ChatColor;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.gui.edition.RevisionInventory;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class RevisionID extends ItemStat implements GemStoneStat
{
    public RevisionID() {
        super("REVISION_ID", Material.ITEM_FRAME, "Revision ID", new String[] { "The Revision ID is used to determine", "if an item is outdated or not. You", "should increase this whenever", "you make changes to your item!", "", "§6The updater is smart and will apply", "§6changes to the base stats of the item,", "§6keeping gemstones intact (for example)." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        if (o instanceof Integer) {
            return new NumericStatFormula((int)o, 0.0, 0.0, 0.0);
        }
        throw new IllegalArgumentException("Must specify a whole number");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)(int)((DoubleData)statData).getValue()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        new RevisionInventory(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.INTEGER));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new DoubleData((int)tagAtPath.getValue());
        }
        return null;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Revision ID: " + ChatColor.GREEN + (int)optional.get().getBase());
        }
        else {
            list.add(ChatColor.GRAY + "Current Revision ID: " + ChatColor.GREEN + "1");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to increase this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to decrease this value.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new DoubleData(0.0);
    }
}
