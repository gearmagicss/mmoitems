// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.util.message.Message;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Arrays;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
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
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class Permission extends StringListStat implements ItemRestriction
{
    public Permission() {
        super("PERMISSION", VersionMaterial.OAK_SIGN.toMaterial(), "Permission", new String[] { "The permission needed to use this item." }, new String[] { "!block", "all" }, new Material[0]);
    }
    
    @Override
    public StringListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof List, "Must specify a string list");
        return new StringListData((List<String>)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.PERMISSION, new Object[0]).enable("Write in the chat the permission you want your item to require.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("permission")) {
            final List stringList = editionInventory.getEditedSection().getStringList("permission");
            if (stringList.size() < 1) {
                return;
            }
            final String str = stringList.get(stringList.size() - 1);
            stringList.remove(str);
            editionInventory.getEditedSection().set("permission", (Object)((stringList.size() == 0) ? null : stringList));
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        Validate.isTrue(!s.contains("|"), "Your perm node must not contain any | symbol.");
        final List<String> list = editionInventory.getEditedSection().contains("permission") ? editionInventory.getEditedSection().getStringList("permission") : new ArrayList<String>();
        list.add(s);
        editionInventory.getEditedSection().set("permission", (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Permission successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().getList().forEach(str -> list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + str));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add a compatible permission.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last permission.");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)String.join("|", ((StringListData)statData).getList())));
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
            return new StringListData(new ArrayList<String>(Arrays.asList(((String)tagAtPath.getValue()).split("\\|"))));
        }
        return null;
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        final String string = nbtItem.getString("MMOITEMS_PERMISSION");
        if (!string.equals("") && !rpgPlayer.getPlayer().hasPermission("mmoitems.bypass.item") && MMOItems.plugin.getConfig().getBoolean("permissions.items")) {
            final String[] split = string.split("\\|");
            for (int length = split.length, i = 0; i < length; ++i) {
                if (!rpgPlayer.getPlayer().hasPermission(split[i])) {
                    if (b) {
                        Message.NOT_ENOUGH_PERMS.format(ChatColor.RED, new String[0]).send(rpgPlayer.getPlayer(), "cant-use-item");
                        rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
