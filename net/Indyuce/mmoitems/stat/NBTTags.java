// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Iterator;
import com.google.gson.JsonArray;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
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
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class NBTTags extends StringListStat
{
    public NBTTags() {
        super("CUSTOM_NBT", Material.NAME_TAG, "NBT Tags", new String[] { "Custom NBT Tags." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.NBT_TAGS, new Object[0]).enable("Write in the chat the NBT tag you want to add.", ChatColor.AQUA + "Format: {Tag Name} {Tag Value}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("custom-nbt")) {
            final List stringList = editionInventory.getEditedSection().getStringList("custom-nbt");
            if (stringList.size() < 1) {
                return;
            }
            final String str = stringList.get(stringList.size() - 1);
            stringList.remove(str);
            editionInventory.getEditedSection().set("custom-nbt", (Object)stringList);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed '" + str + "'.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        Validate.isTrue(s.split(" ").length > 1, "Use this format: {Tag Name} {Tag Value}");
        final List<String> list = editionInventory.getEditedSection().contains("custom-nbt") ? editionInventory.getEditedSection().getStringList("custom-nbt") : new ArrayList<String>();
        list.add(s);
        editionInventory.getEditedSection().set("custom-nbt", (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "StringListStat successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getList().forEach(str -> list.add(ChatColor.GRAY + str));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a tag.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last tag.");
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final JsonArray jsonArray = new JsonArray();
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        for (final String s : ((StringListData)statData).getList()) {
            jsonArray.add(s);
            list.add(new ItemTag(s.substring(0, s.indexOf(32)), this.calculateObjectType(s.substring(s.indexOf(32) + 1))));
        }
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonArray.toString()));
        return list;
    }
    
    public Object calculateObjectType(final String s) {
        if (s.equalsIgnoreCase("true")) {
            return true;
        }
        if (s.equalsIgnoreCase("false")) {
            return false;
        }
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            try {
                return Double.parseDouble(s);
            }
            catch (NumberFormatException ex2) {
                if (s.contains("[") && s.contains("]")) {
                    final ArrayList<String> list = new ArrayList<String>();
                    final String[] split = s.replace("[", "").replace("]", "").split(",");
                    for (int length = split.length, i = 0; i < length; ++i) {
                        list.add(split[i].replace("\"", ""));
                    }
                    return list;
                }
                return s;
            }
        }
    }
}
