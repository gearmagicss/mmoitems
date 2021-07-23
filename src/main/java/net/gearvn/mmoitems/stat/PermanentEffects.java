// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.util.Map;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import com.google.gson.JsonObject;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.PotionEffectListData;
import net.Indyuce.mmoitems.stat.data.PotionEffectData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.Optional;
import java.util.List;
import java.util.Set;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Collection;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.potion.PotionEffectType;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectListData;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class PermanentEffects extends ItemStat
{
    public PermanentEffects() {
        super("PERM_EFFECTS", Material.POTION, "Permanent Effects", new String[] { "The potion effects your", "item grants to the holder." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        final ConfigurationSection configurationSection = (ConfigurationSection)o;
        final RandomPotionEffectListData randomPotionEffectListData = new RandomPotionEffectListData(new RandomPotionEffectData[0]);
        for (final String str : configurationSection.getKeys(false)) {
            final PotionEffectType byName = PotionEffectType.getByName(str.toUpperCase().replace("-", "_").replace(" ", "_"));
            Validate.notNull((Object)byName, "Could not find potion effect type named '" + str + "'");
            randomPotionEffectListData.add(new RandomPotionEffectData(byName, new NumericStatFormula(configurationSection.get(str))));
        }
        return randomPotionEffectListData;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.PERM_EFFECTS, new Object[0]).enable("Write in the chat the permanent potion effect you want to add.", ChatColor.AQUA + "Format: {Effect Name} {Amplifier Numeric Formula}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("perm-effects")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("perm-effects").getKeys(false);
            final String str = new ArrayList<String>(keys).get(keys.size() - 1);
            editionInventory.getEditedSection().set("perm-effects." + str, (Object)null);
            if (keys.size() <= 1) {
                editionInventory.getEditedSection().set("perm-effects", (Object)null);
            }
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String[] split = s.split(" ");
        Validate.isTrue(split.length >= 2, "Use this format: {Effect Name} {Effect Amplifier Numeric Formula}. Example: 'speed 1 0.3' stands for Speed 1, plus 0.3 level per item level (rounded up to lower int)");
        final PotionEffectType byName = PotionEffectType.getByName(split[0].replace("-", "_"));
        Validate.notNull((Object)byName, split[0] + " is not a valid potion effect. All potion effects can be found here: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html");
        final NumericStatFormula obj = new NumericStatFormula(s.substring(s.indexOf(" ") + 1));
        obj.fillConfigurationSection(editionInventory.getEditedSection(), "perm-effects." + byName.getName());
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + ChatColor.GOLD + byName.getName() + " " + obj + ChatColor.GRAY + " successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            for (final RandomPotionEffectData randomPotionEffectData : optional.get().getEffects()) {
                list.add(ChatColor.GRAY + "* " + ChatColor.GREEN + MMOUtils.caseOnWords(randomPotionEffectData.getType().getName().replace("_", " ").toLowerCase()) + " " + randomPotionEffectData.getAmplifier().toString());
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
        ((PotionEffectListData)statData).getEffects().forEach(potionEffectData -> list.add(ItemStat.translate("perm-effect").replace("#", MMOItems.plugin.getLanguage().getPotionEffectName(potionEffectData.getType()) + " " + MMOUtils.intToRoman(potionEffectData.getLevel()))));
        itemStackBuilder.getLore().insert("perm-effects", list);
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final JsonObject jsonObject = new JsonObject();
        for (final PotionEffectData potionEffectData : ((PotionEffectListData)statData).getEffects()) {
            jsonObject.addProperty(potionEffectData.getType().getName(), (Number)potionEffectData.getLevel());
        }
        list.add(new ItemTag(this.getNBTPath(), (Object)jsonObject.toString()));
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
            try {
                final PotionEffectListData potionEffectListData = new PotionEffectListData(new PotionEffectData[0]);
                new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject().entrySet().forEach(entry -> potionEffectListData.add(new PotionEffectData(PotionEffectType.getByName((String)entry.getKey()), ((JsonElement)entry.getValue()).getAsInt())));
                return potionEffectListData;
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
}
