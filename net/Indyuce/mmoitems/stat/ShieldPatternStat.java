// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.Optional;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.Set;
import java.util.Collection;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.block.BlockState;
import java.util.List;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.block.Banner;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Iterator;
import org.bukkit.block.banner.PatternType;
import net.Indyuce.mmoitems.stat.data.ShieldPatternData;
import org.bukkit.block.banner.Pattern;
import org.bukkit.DyeColor;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class ShieldPatternStat extends StringStat
{
    public ShieldPatternStat() {
        super("SHIELD_PATTERN", Material.SHIELD, "Shield Pattern", new String[] { "The color & patterns", "of your shield." }, new String[] { "all" }, new Material[] { Material.SHIELD });
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final ShieldPatternData shieldPatternData = new ShieldPatternData(configurationSection.contains("color") ? DyeColor.valueOf(configurationSection.getString("color").toUpperCase().replace("-", "_").replace(" ", "_")) : null, new Pattern[0]);
        for (final String s : configurationSection.getKeys(false)) {
            if (!s.equalsIgnoreCase("color")) {
                shieldPatternData.add(new Pattern(DyeColor.valueOf(configurationSection.getString(s + ".color").toUpperCase().replace("-", "_").replace(" ", "_")), PatternType.valueOf(configurationSection.getString(s + ".pattern").toUpperCase().replace("-", "_").replace(" ", "_"))));
            }
        }
        return shieldPatternData;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final Banner blockState = (Banner)((BlockStateMeta)itemStackBuilder.getMeta()).getBlockState();
        final ShieldPatternData shieldPatternData = (ShieldPatternData)statData;
        blockState.setBaseColor(shieldPatternData.getBaseColor());
        blockState.setPatterns((List)shieldPatternData.getPatterns());
        ((BlockStateMeta)itemStackBuilder.getMeta()).setBlockState((BlockState)blockState);
        itemStackBuilder.getMeta().addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.SHIELD_PATTERN, new Object[] { 0 }).enable("Write in the chat the color of your shield.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            editionInventory.getEditedSection().set("shield-pattern.color", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully reset the shield color.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            new StatEdition(editionInventory, ItemStats.SHIELD_PATTERN, new Object[] { 1 }).enable("Write in the chat the pattern you want to add.", ChatColor.AQUA + "Format: [PATTERN_TYPE] [DYE_COLOR]");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.DROP_ONE_SLOT && editionInventory.getEditedSection().contains("shield-pattern")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("shield-pattern").getKeys(false);
            final String str = new ArrayList<String>(keys).get(keys.size() - 1);
            if (str.equalsIgnoreCase("color")) {
                return;
            }
            editionInventory.getEditedSection().set("shield-pattern." + str, (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed the last pattern.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        if ((int)array[0] == 1) {
            final String[] split = str.split(" ");
            Validate.isTrue(split.length == 2, str + " is not a valid [PATTERN_TYPE] [DYE_COLOR].");
            final PatternType value = PatternType.valueOf(split[0].toUpperCase().replace("-", "_").replace(" ", "_"));
            final DyeColor value2 = DyeColor.valueOf(split[1].toUpperCase().replace("-", "_").replace(" ", "_"));
            final int nextAvailableKey = this.getNextAvailableKey(editionInventory.getEditedSection().getConfigurationSection("shield-pattern"));
            Validate.isTrue(nextAvailableKey >= 0, "You can have more than 100 shield patterns on a single item.");
            editionInventory.getEditedSection().set("shield-pattern." + nextAvailableKey + ".pattern", (Object)value.name());
            editionInventory.getEditedSection().set("shield-pattern." + nextAvailableKey + ".color", (Object)value2.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + MMOUtils.caseOnWords(value.name().toLowerCase().replace("_", " ")) + " successfully added.");
            return;
        }
        editionInventory.getEditedSection().set("shield-pattern.color", (Object)DyeColor.valueOf(str.toUpperCase().replace("-", "_").replace(" ", "_")).name());
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Shield color successfully changed.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            final ShieldPatternData shieldPatternData = optional.get();
            list.add(ChatColor.GRAY + "* Base Color: " + ((shieldPatternData.getBaseColor() != null) ? (ChatColor.GREEN + MMOUtils.caseOnWords(shieldPatternData.getBaseColor().name().toLowerCase().replace("_", " "))) : (ChatColor.RED + "None")));
            shieldPatternData.getPatterns().forEach(pattern -> list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + pattern.getPattern().name() + ChatColor.GRAY + " - " + ChatColor.GREEN + pattern.getColor().name()));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left Click to change the shield color.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Shift Left Click to reset the shield color.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right Click to add a pattern.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Drop to remove the last pattern.");
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        if (readMMOItem.getNBT().getItem().getItemMeta() instanceof BlockStateMeta && ((BlockStateMeta)readMMOItem.getNBT().getItem().getItemMeta()).hasBlockState() && ((BlockStateMeta)readMMOItem.getNBT().getItem().getItemMeta()).getBlockState() instanceof Banner) {
            final Banner banner = (Banner)((BlockStateMeta)readMMOItem.getNBT().getItem().getItemMeta()).getBlockState();
            final ShieldPatternData shieldPatternData = new ShieldPatternData(banner.getBaseColor(), new Pattern[0]);
            shieldPatternData.addAll(banner.getPatterns());
            readMMOItem.setData(ItemStats.SHIELD_PATTERN, shieldPatternData);
        }
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new ShieldPatternData(DyeColor.YELLOW, new Pattern[0]);
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        return null;
    }
    
    private int getNextAvailableKey(final ConfigurationSection configurationSection) {
        for (int i = 0; i < 100; ++i) {
            if (!configurationSection.contains("" + i)) {
                return i;
            }
        }
        return -1;
    }
}
