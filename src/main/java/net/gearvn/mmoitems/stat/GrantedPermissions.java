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
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class GrantedPermissions extends StringListStat implements GemStoneStat
{
    public GrantedPermissions() {
        super("GRANTED_PERMISSIONS", Material.NAME_TAG, "Granted Permissions", new String[] { "A list of permissions that will,", "be granted by the item." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.GRANTED_PERMISSIONS, new Object[0]).enable("Write in the chat the permission you want to add.");
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
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Permission successfully added.");
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
}
