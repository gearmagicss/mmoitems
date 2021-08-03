// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import java.util.List;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.gui.edition.SoundsEdition;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.SoundData;
import net.Indyuce.mmoitems.api.CustomSound;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.SoundListData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.SelfConsumable;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class CustomSounds extends ItemStat implements GemStoneStat, SelfConsumable
{
    public CustomSounds() {
        super("SOUNDS", Material.JUKEBOX, "Custom Sounds", new String[] { "The custom sounds your item will use." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public SoundListData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final SoundListData soundListData = new SoundListData();
        for (final CustomSound customSound : CustomSound.values()) {
            final String lowerCase = customSound.name().replace("_", "-").toLowerCase();
            if (configurationSection.contains(lowerCase)) {
                soundListData.set(customSound, new SoundData(configurationSection.get(lowerCase)));
            }
        }
        return soundListData;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new SoundsEdition(editionInventory.getPlayer(), editionInventory.getEdited()).open(editionInventory.getPage());
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("sounds")) {
            editionInventory.getEditedSection().set("sounds", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Custom Sounds successfully removed.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        final String str2 = (String)array[0];
        final String[] split = str.split(" ");
        Validate.isTrue(split.length == 3, str + " is not a valid [SOUND NAME] [VOLUME] [PITCH].");
        final String replace = split[0].replace("-", "_");
        final double double1 = MMOUtils.parseDouble(split[1]);
        final double double2 = MMOUtils.parseDouble(split[2]);
        editionInventory.getEditedSection().set("sounds." + str2 + ".sound", (Object)replace);
        editionInventory.getEditedSection().set("sounds." + str2 + ".volume", (Object)double1);
        editionInventory.getEditedSection().set("sounds." + str2 + ".pitch", (Object)double2);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.RED + MMOUtils.caseOnWords(str2.replace(".", " ")) + ChatColor.GRAY + " successfully changed to '" + replace + "'.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            optional.get().mapData().forEach((customSound, soundData) -> list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + MMOUtils.caseOnWords(customSound.getName().toLowerCase().replace("-", " ").replace("_", " ")) + ChatColor.GRAY + ": " + ChatColor.RED + soundData.getVolume() + " " + soundData.getPitch()));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to access the sounds edition menu.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove all custom sounds.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new SoundListData();
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final SoundListData soundListData;
        final ItemTag e;
        final String str;
        final SoundData soundData;
        final ArrayList<ItemTag> list2;
        final ItemTag e2;
        final ItemTag e3;
        ((SoundListData)statData).getCustomSounds().forEach(customSound -> {
            soundListData.get(customSound);
            customSound.getName().replace(" ", "_").toUpperCase();
            new ItemTag("MMOITEMS_SOUND_" + str, (Object)soundData.getSound());
            list2.add(e);
            new ItemTag("MMOITEMS_SOUND_" + str + "_VOL", (Object)soundData.getVolume());
            list2.add(e2);
            new ItemTag("MMOITEMS_SOUND_" + str + "_PIT", (Object)soundData.getPitch());
            list2.add(e3);
            return;
        });
        return list;
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final SoundListData soundListData = new SoundListData();
        for (final CustomSound customSound : CustomSound.values()) {
            final ItemTag tagAtPath = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name(), (ArrayList)list);
            final ItemTag tagAtPath2 = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name() + "_VOL", (ArrayList)list);
            final ItemTag tagAtPath3 = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name() + "_PIT", (ArrayList)list);
            if (tagAtPath != null && tagAtPath2 != null && tagAtPath3 != null) {
                final String s = (String)tagAtPath.getValue();
                final Double n = (Double)tagAtPath2.getValue();
                final Double n2 = (Double)tagAtPath3.getValue();
                if (!s.isEmpty()) {
                    soundListData.set(customSound, new SoundData(s, n, n2));
                }
            }
        }
        if (soundListData.getCustomSounds().size() > 0) {
            return soundListData;
        }
        return null;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        for (final CustomSound customSound : CustomSound.values()) {
            ItemTag tagAtPath = null;
            ItemTag tagAtPath2 = null;
            ItemTag tagAtPath3 = null;
            if (readMMOItem.getNBT().hasTag("MMOITEMS_SOUND_" + customSound.name())) {
                tagAtPath = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING);
            }
            if (readMMOItem.getNBT().hasTag("MMOITEMS_SOUND_" + customSound.name() + "_VOL")) {
                tagAtPath2 = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name() + "_VOL", readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE);
            }
            if (readMMOItem.getNBT().hasTag("MMOITEMS_SOUND_" + customSound.name() + "_PIT")) {
                tagAtPath3 = ItemTag.getTagAtPath("MMOITEMS_SOUND_" + customSound.name() + "_PIT", readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE);
            }
            if (tagAtPath != null && tagAtPath2 != null && tagAtPath3 != null) {
                list.add(tagAtPath);
                list.add(tagAtPath2);
                list.add(tagAtPath3);
            }
        }
        final SoundListData soundListData = (SoundListData)this.getLoadedNBT(list);
        if (soundListData != null) {
            readMMOItem.setData(ItemStats.CUSTOM_SOUNDS, soundListData);
        }
    }
    
    @Override
    public boolean onSelfConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.CUSTOM_SOUNDS)) {
            this.playDefaultSound(player);
            return false;
        }
        final SoundData value = ((SoundListData)volatileMMOItem.getData(ItemStats.CUSTOM_SOUNDS)).get(CustomSound.ON_CONSUME);
        if (value == null) {
            this.playDefaultSound(player);
            return false;
        }
        player.getWorld().playSound(player.getLocation(), value.getSound(), (float)value.getVolume(), (float)value.getPitch());
        return true;
    }
    
    void playDefaultSound(@NotNull final Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
    }
}
