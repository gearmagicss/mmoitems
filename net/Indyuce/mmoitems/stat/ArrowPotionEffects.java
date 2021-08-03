// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.PotionEffectListData;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.potion.PotionEffectType;
import java.util.Set;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Arrays;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectListData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class ArrowPotionEffects extends ItemStat
{
    private final DecimalFormat durationFormat;
    
    public ArrowPotionEffects() {
        super("ARROW_POTION_EFFECTS", Material.TIPPED_ARROW, "Arrow Potion Effects", new String[] { "The effects to be applied when", "entities are shot by this bow" }, new String[] { "bow", "crossbow" }, new Material[0]);
        this.durationFormat = new DecimalFormat("0.#");
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        return new RandomPotionEffectListData((ConfigurationSection)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.ARROW_POTION_EFFECTS, new Object[0]).enable("Write in the chat the potion effect you want to add.", ChatColor.AQUA + "Format: [POTION_EFFECT] [DURATION] [AMPLIFIER]");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("arrow-potion-effects")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("arrow-potion-effects").getKeys(false);
            final String str = Arrays.asList((String[])keys.toArray(new String[0])).get(keys.size() - 1);
            editionInventory.getEditedSection().set("arrow-potion-effects." + str, (Object)null);
            if (keys.size() <= 1) {
                editionInventory.getEditedSection().set("arrow-potion-effects", (Object)null);
            }
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String str, final Object... array) {
        final String[] split = str.split(" ");
        Validate.isTrue(split.length == 3, str + " is not a valid [POTION_EFFECT] [DURATION] [AMPLIFIER]. Example: 'FAST_DIGGING 30 3' stands for Haste 3 for 30 seconds.");
        PotionEffectType potionEffectType = null;
        for (final PotionEffectType potionEffectType2 : PotionEffectType.values()) {
            if (potionEffectType2 != null && potionEffectType2.getName().equalsIgnoreCase(split[0].replace("-", "_"))) {
                potionEffectType = potionEffectType2;
                break;
            }
        }
        Validate.notNull((Object)potionEffectType, split[0] + " is not a valid potion effect.");
        final double double1 = MMOUtils.parseDouble(split[1]);
        final int n = (int)MMOUtils.parseDouble(split[2]);
        final ConfigurationSection section = editionInventory.getEditedSection().createSection("arrow-potion-effects." + potionEffectType.getName());
        section.set("duration", (Object)double1);
        section.set("amplifier", (Object)n);
        editionInventory.getEditedSection().set("arrow-potion-effects." + potionEffectType.getName(), (Object)section);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + potionEffectType.getName() + " " + n + " successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            for (final RandomPotionEffectData randomPotionEffectData : optional.get().getEffects()) {
                list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + MMOUtils.caseOnWords(randomPotionEffectData.getType().getName().toLowerCase().replace("_", " ")) + ChatColor.GRAY + " Level: " + ChatColor.GREEN + randomPotionEffectData.getAmplifier() + ChatColor.GRAY + " Duration: " + ChatColor.GREEN + randomPotionEffectData.getDuration());
            }
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add an effect.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last effect.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new PotionEffectListData(new PotionEffectData[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final ArrayList<String> list = new ArrayList<String>();
        ((PotionEffectListData)statData).getEffects().forEach(potionEffectData -> list.add(ItemStat.translate("arrow-potion-effects").replace("#", MMOItems.plugin.getLanguage().getPotionEffectName(potionEffectData.getType()) + " " + MMOUtils.intToRoman(potionEffectData.getLevel()) + "(" + this.durationFormat.format(potionEffectData.getDuration()) + "s)")));
        itemStackBuilder.getLore().insert("arrow-potion-effects", list);
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final JsonArray jsonArray = new JsonArray();
        final JsonObject jsonObject;
        final JsonArray jsonArray2;
        ((PotionEffectListData)statData).getEffects().forEach(potionEffectData -> {
            jsonObject = new JsonObject();
            jsonObject.addProperty("type", potionEffectData.getType().getName());
            jsonObject.addProperty("level", (Number)potionEffectData.getLevel());
            jsonObject.addProperty("duration", (Number)potionEffectData.getDuration());
            jsonArray2.add((JsonElement)jsonObject);
            return;
        });
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonArray.toString()));
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
            final PotionEffectListData potionEffectListData = new PotionEffectListData(new PotionEffectData[0]);
            for (final JsonElement jsonElement : (JsonArray)MythicLib.plugin.getJson().parse((String)tagAtPath.getValue(), (Class)JsonArray.class)) {
                if (!jsonElement.isJsonObject()) {
                    continue;
                }
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                potionEffectListData.add(new PotionEffectData(PotionEffectType.getByName(asJsonObject.get("type").getAsString()), asJsonObject.get("duration").getAsDouble(), asJsonObject.get("level").getAsInt()));
            }
            return potionEffectListData;
        }
        return null;
    }
}
