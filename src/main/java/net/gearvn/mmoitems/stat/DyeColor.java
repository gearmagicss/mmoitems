// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.jetbrains.annotations.Nullable;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.ColorData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class DyeColor extends ItemStat
{
    public DyeColor() {
        super("DYE_COLOR", VersionMaterial.RED_DYE.toMaterial(), "Dye Color", new String[] { "The color of your item", "(for dyeable items).", "In RGB." }, new String[] { "all" }, new Material[] { Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, VersionMaterial.LEATHER_HORSE_ARMOR.toMaterial() });
    }
    
    @Override
    public ColorData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof String, "Must specify a string");
        return new ColorData((String)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.DYE_COLOR, new Object[0]).enable("Write in the chat the RGB color you want.", ChatColor.AQUA + "Format: {Red} {Green} {Blue}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set("dye-color", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed Dye Color.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        final String[] split = str.split(" ");
        Validate.isTrue(split.length == 3, "Use this format: {Red} {Green} {Blue}.");
        final String[] array2 = split;
        for (int length = array2.length, i = 0; i < length; ++i) {
            final int int1 = Integer.parseInt(array2[i]);
            Validate.isTrue(int1 >= 0 && int1 < 256, "Color must be between 0 and 255");
        }
        editionInventory.getEditedSection().set("dye-color", (Object)str);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Dye Color successfully changed to " + str + ".");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(ChatColor.GRAY + "Current Value: " + (optional.isPresent() ? (ChatColor.GREEN + optional.get().toString()) : (ChatColor.RED + "None")));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the dye color.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new ColorData(0, 0, 0);
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ItemMeta itemMeta = readMMOItem.getNBT().getItem().getItemMeta();
        if (itemMeta instanceof LeatherArmorMeta) {
            final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
            list.add(new ItemTag(this.getNBTPath(), (Object)((LeatherArmorMeta)itemMeta).getColor()));
            final StatData loadedNBT = this.getLoadedNBT(list);
            if (loadedNBT != null) {
                readMMOItem.setData(this, loadedNBT);
            }
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new ColorData((Color)tagAtPath.getValue());
        }
        return null;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (itemStackBuilder.getMeta() instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)itemStackBuilder.getMeta()).setColor(((ColorData)statData).getColor());
        }
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
}
