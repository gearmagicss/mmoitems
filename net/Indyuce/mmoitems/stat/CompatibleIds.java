// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Iterator;
import com.google.gson.JsonArray;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.Collection;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.apache.commons.lang.Validate;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.StringListData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class CompatibleIds extends ItemStat
{
    public CompatibleIds() {
        super("COMPATIBLE_IDS", VersionMaterial.COMMAND_BLOCK.toMaterial(), "Compatible IDs", new String[] { "The item ids this skin is", "compatible with." }, new String[] { "skin" }, new Material[0]);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.COMPATIBLE_IDS, new Object[0]).enable("Write in the chat the item id you want to add.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("compatible-ids")) {
            final List stringList = editionInventory.getEditedSection().getStringList("compatible-ids");
            if (stringList.size() < 1) {
                return;
            }
            final String str = stringList.get(stringList.size() - 1);
            stringList.remove(str);
            editionInventory.getEditedSection().set("compatible-ids", (Object)stringList);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed '" + str + "'.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final List<String> list = editionInventory.getEditedSection().contains("compatible-ids") ? editionInventory.getEditedSection().getStringList("compatible-ids") : new ArrayList<String>();
        list.add(s.toUpperCase());
        editionInventory.getEditedSection().set("compatible-ids", (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Compatible IDs successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getList().forEach(str -> list.add(ChatColor.GRAY + str));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "Compatible with any item.");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a new id.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last id.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringListData();
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.getLore().insert("compatible-ids", new ArrayList<String>(((StringListData)statData).getList()));
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
}
