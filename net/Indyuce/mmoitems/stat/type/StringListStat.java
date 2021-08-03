// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.Iterator;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.apache.commons.lang.Validate;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.StringListData;
import org.bukkit.Material;

public class StringListStat extends ItemStat
{
    public StringListStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final JsonArray jsonArray = new JsonArray();
        final Iterator<String> iterator = ((StringListData)statData).getList().iterator();
        while (iterator.hasNext()) {
            jsonArray.add((String)iterator.next());
        }
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonArray.toString()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the line you want to add.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains(this.getPath())) {
            final List stringList = editionInventory.getEditedSection().getStringList(this.getPath());
            if (stringList.isEmpty()) {
                return;
            }
            final String s = stringList.get(stringList.size() - 1);
            stringList.remove(s);
            editionInventory.getEditedSection().set(this.getPath(), (Object)(stringList.isEmpty() ? null : stringList));
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed '" + MythicLib.plugin.parseColors(s) + ChatColor.GRAY + "'.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final List<String> list = editionInventory.getEditedSection().contains(this.getPath()) ? editionInventory.getEditedSection().getStringList(this.getPath()) : new ArrayList<String>();
        list.add(s);
        editionInventory.getEditedSection().set(this.getPath(), (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " Stat successfully added.");
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
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
            try {
                return new StringListData(new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonArray());
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getList().forEach(s -> list.add(ChatColor.GRAY + MythicLib.plugin.parseColors(s)));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a permission.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last permission.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringListData();
    }
}
