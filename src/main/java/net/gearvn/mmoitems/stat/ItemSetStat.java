// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.ItemSet;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class ItemSetStat extends StringStat
{
    public ItemSetStat() {
        super("SET", Material.LEATHER_CHESTPLATE, "Item Set", new String[] { "Item sets can give to the player extra", "bonuses that depend on how many items", "from the same set your wear." }, new String[] { "!gem_stone", "!consumable", "!material", "!block", "!miscellaneous", "all" }, new Material[0]);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        super.whenClicked(editionInventory, inventoryClickEvent);
        if (inventoryClickEvent.getAction() != InventoryAction.PICKUP_HALF) {
            editionInventory.getPlayer().sendMessage(ChatColor.GREEN + "Available Item Sets:");
            final StringBuilder sb = new StringBuilder();
            for (final ItemSet set : MMOItems.plugin.getSets().getAll()) {
                sb.append(ChatColor.GREEN).append(set.getId()).append(ChatColor.GRAY).append(" (").append(set.getName()).append(ChatColor.GRAY).append("), ");
            }
            if (sb.length() > 1) {
                sb.setLength(sb.length() - 2);
            }
            editionInventory.getPlayer().sendMessage(sb.toString());
        }
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.getLore().insert("set", MMOItems.plugin.getSets().get(statData.toString()).getLoreTag());
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        Validate.notNull((Object)MMOItems.plugin.getSets().get(statData.toString()), "Could not find item set with ID '" + statData.toString() + "'");
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)statData.toString()));
        return list;
    }
    
    @NotNull
    @Override
    public String getNBTPath() {
        return "MMOITEMS_ITEM_SET";
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        Validate.notNull((Object)MMOItems.plugin.getSets().get(str), "Couldn't find the set named '" + str + "'.");
        super.whenInput(editionInventory, str, array);
    }
}
