// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;

public class StringStat extends ItemStat
{
    public StringStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return new StringData(o.toString());
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        itemStackBuilder.getLore().insert(this.getPath(), statData.toString());
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)statData.toString()));
        return list;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + this.getName() + ".");
        }
        else {
            new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the text you want.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        editionInventory.getEditedSection().set(this.getPath(), (Object)s);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to " + MythicLib.plugin.parseColors(s) + ChatColor.GRAY + ".");
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final StringData stringData = (StringData)this.getLoadedNBT(list);
        if (stringData != null) {
            readMMOItem.setData(this, stringData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new StringData((String)tagAtPath.getValue());
        }
        return null;
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            final String colors = MythicLib.plugin.parseColors(optional.get().toString());
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + ((colors.length() > 40) ? (colors.substring(0, 40) + "...") : colors));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringData("");
    }
}
