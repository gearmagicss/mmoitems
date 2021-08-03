// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import io.lumine.mythic.lib.MythicLib;
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
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class Lore extends StringListStat implements GemStoneStat
{
    public Lore() {
        super("LORE", VersionMaterial.WRITABLE_BOOK.toMaterial(), "Lore", new String[] { "The item lore." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.LORE, new Object[0]).enable("Write in the chat the lore line you want to add.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("lore")) {
            final List stringList = editionInventory.getEditedSection().getStringList("lore");
            if (stringList.isEmpty()) {
                return;
            }
            final String s = stringList.get(stringList.size() - 1);
            stringList.remove(s);
            editionInventory.getEditedSection().set("lore", (Object)(stringList.isEmpty() ? null : stringList));
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed '" + MythicLib.plugin.parseColors(s) + ChatColor.GRAY + "'.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final List<String> list = editionInventory.getEditedSection().contains("lore") ? editionInventory.getEditedSection().getStringList("lore") : new ArrayList<String>();
        list.add(s);
        editionInventory.getEditedSection().set("lore", (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Lore successfully added.");
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
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a line.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last line.");
    }
}
