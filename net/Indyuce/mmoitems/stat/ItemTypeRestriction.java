// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.TypeSet;
import net.Indyuce.mmoitems.api.Type;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.StringData;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.ChatColor;
import java.util.Optional;
import java.util.ArrayList;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.StringListData;
import org.apache.commons.lang.Validate;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class ItemTypeRestriction extends StringStat
{
    public ItemTypeRestriction() {
        super("ITEM_TYPE_RESTRICTION", Material.EMERALD, "Item Type Restriction", new String[] { "This option defines the item types", "on which your gem can be applied." }, new String[] { "gem_stone" }, new Material[0]);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.ITEM_TYPE_RESTRICTION, new Object[0]).enable("Write in the chat the item type you want your gem to support.", "Supported formats: WEAPON or BLUNT, PIERCING, SLASHING, OFFHAND, EXTRA.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains(this.getPath())) {
            final List stringList = editionInventory.getEditedSection().getStringList("" + this.getPath());
            if (stringList.size() < 1) {
                return;
            }
            final String str = stringList.get(stringList.size() - 1);
            stringList.remove(str);
            editionInventory.getEditedSection().set("" + this.getPath(), (Object)((stringList.size() == 0) ? null : stringList));
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String replace = s.toUpperCase().replace(" ", "_").replace("-", "_");
        Validate.isTrue(this.isValid(replace), replace + " is not a valid item type/set. You can enter WEAPON, BLUNT, PIERCING, SLASHING, OFFHAND, EXTRA, as well as other item types here: /mi list type.");
        final List<String> list = editionInventory.getEditedSection().contains(this.getPath()) ? editionInventory.getEditedSection().getStringList("" + this.getPath()) : new ArrayList<String>();
        list.add(replace);
        editionInventory.getEditedSection().set(this.getPath(), (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Your gem now supports " + replace + ".");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getList().forEach(str -> list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + str));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "Compatible with any type.");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a supported item type/set.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last element.");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((statData instanceof StringListData) ? String.join(",", ((StringListData)statData).getList()) : ((StringData)statData).getString())));
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final StringListData stringListData = (StringListData)this.getLoadedNBT(list);
        if (stringListData != null) {
            readMMOItem.setData(this, stringListData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            final String e = (String)tagAtPath.getValue();
            final ArrayList<String> list2 = new ArrayList<String>();
            if (e.contains(",")) {
                for (final String e2 : e.split(",")) {
                    if (!e2.isEmpty()) {
                        list2.add(e2);
                    }
                }
            }
            else {
                list2.add(e);
            }
            return new StringListData(list2);
        }
        return null;
    }
    
    private boolean isValid(final String s) {
        if (s.equals("WEAPON")) {
            return true;
        }
        final Iterator<Type> iterator = MMOItems.plugin.getTypes().getAll().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(s)) {
                return true;
            }
        }
        final TypeSet[] values = TypeSet.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            if (values[i].name().equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringListData();
    }
}
