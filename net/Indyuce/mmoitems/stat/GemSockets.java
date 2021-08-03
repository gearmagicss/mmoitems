// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import com.google.gson.JsonElement;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import com.google.gson.JsonArray;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.apache.commons.lang.Validate;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class GemSockets extends ItemStat
{
    public GemSockets() {
        super("GEM_SOCKETS", Material.EMERALD, "Gem Sockets", new String[] { "The amount of gem", "sockets your weapon has." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "accessory", "!gem_stone" }, new Material[0]);
    }
    
    @Override
    public GemSocketsData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new GemSocketsData((List<String>)o);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final GemSocketsData gemSocketsData = (GemSocketsData)statData;
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        ItemStat.translate("empty-gem-socket");
        final String translate = ItemStat.translate("filled-gem-socket");
        final ArrayList<String> list = new ArrayList<String>();
        for (final GemstoneData gemstoneData : gemSocketsData.getGemstones()) {
            String replacement2 = gemstoneData.getName();
            if (itemStackBuilder.getMMOItem().hasUpgradeTemplate()) {
                final int upgradeLevel = itemStackBuilder.getMMOItem().getUpgradeLevel();
                if (upgradeLevel != 0) {
                    final Integer level = gemstoneData.getLevel();
                    if (level != null) {
                        replacement2 = DisplayName.appendUpgradeLevel(replacement2, upgradeLevel - level);
                    }
                }
            }
            list.add(translate.replace("#", replacement2));
        }
        final String s;
        gemSocketsData.getEmptySlots().forEach(replacement -> list.add(s.replace("#", replacement)));
        itemStackBuilder.getLore().insert("gem-stones", list);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final GemSocketsData gemSocketsData = (GemSocketsData)statData;
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)gemSocketsData.toJson().toString()));
        return list;
    }
    
    @NotNull
    @Override
    public String getNBTPath() {
        return "MMOITEMS_GEM_STONES";
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
                final JsonObject asJsonObject = new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject();
                final GemSocketsData gemSocketsData = new GemSocketsData(this.toList(asJsonObject.getAsJsonArray("EmptySlots")));
                asJsonObject.getAsJsonArray("Gemstones").forEach(jsonElement -> gemSocketsData.add(new GemstoneData(jsonElement.getAsJsonObject())));
                return gemSocketsData;
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    private List<String> toList(final JsonArray jsonArray) {
        final ArrayList<String> list = new ArrayList<String>();
        jsonArray.forEach(jsonElement -> list.add(jsonElement.getAsString()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.GEM_SOCKETS, new Object[0]).enable("Write in the chat the COLOR of the gem socket you want to add.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains(this.getPath())) {
            final List stringList = editionInventory.getEditedSection().getStringList("" + this.getPath());
            if (stringList.size() < 1) {
                return;
            }
            final String str = stringList.get(stringList.size() - 1);
            stringList.remove(str);
            editionInventory.getEditedSection().set("" + this.getPath(), (Object)stringList);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed '" + str + ChatColor.GRAY + "'.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        final List<String> list = editionInventory.getEditedSection().contains(this.getPath()) ? editionInventory.getEditedSection().getStringList("" + this.getPath()) : new ArrayList<String>();
        list.add(str);
        editionInventory.getEditedSection().set("" + this.getPath(), (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + str + " successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getEmptySlots().forEach(str -> list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + str + " Gem Socket"));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "No Sockets");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a gem socket.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the socket.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new GemSocketsData(new ArrayList<String>());
    }
}
