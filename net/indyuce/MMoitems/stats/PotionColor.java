// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.inventory.meta.PotionMeta;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
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
import net.Indyuce.mmoitems.stat.data.ColorData;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class PotionColor extends StringStat
{
    public PotionColor() {
        super("POTION_COLOR", Material.POTION, "Potion Color", new String[] { "The color of your potion.", "(Doesn't impact the effects)." }, new String[] { "all" }, new Material[] { Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW });
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof String, "Must specify a string");
        return new ColorData((String)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.POTION_COLOR, new Object[0]).enable("Write in the chat the RGB color you want.", ChatColor.AQUA + "Format: {Red} {Green} {Blue}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set("potion-color", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed Potion Color.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        final String[] split = str.split(" ");
        Validate.isTrue(split.length == 3, "Use this format: {Red} {Green} {Blue}. Example: '75 0 130' stands for Purple.");
        final String[] array2 = split;
        for (int length = array2.length, i = 0; i < length; ++i) {
            final int int1 = Integer.parseInt(array2[i]);
            Validate.isTrue(int1 >= 0 && int1 < 256, "Color must be between 0 and 255");
        }
        editionInventory.getEditedSection().set("potion-color", (Object)str);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Potion Color successfully changed to " + str + ".");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        list.add(optional.isPresent() ? (ChatColor.GREEN + optional.get().toString()) : (ChatColor.RED + "Uncolored"));
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the potion color.");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        if (itemStackBuilder.getItemStack().getType().name().contains("POTION") || itemStackBuilder.getItemStack().getType() == Material.TIPPED_ARROW) {
            ((PotionMeta)itemStackBuilder.getMeta()).setColor(((ColorData)statData).getColor());
        }
    }
}
