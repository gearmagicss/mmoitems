// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.jetbrains.annotations.Nullable;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
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
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData;
import java.util.Optional;
import java.util.List;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
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
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import java.text.DecimalFormat;
import net.Indyuce.mmoitems.stat.type.PlayerConsumable;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Effects extends ItemStat implements PlayerConsumable
{
    private final DecimalFormat durationFormat;
    
    public Effects() {
        super("EFFECTS", Material.POTION, "Effects", new String[] { "The potion effects your", "consumable item grants." }, new String[] { "consumable" }, new Material[0]);
        this.durationFormat = new DecimalFormat("0.#");
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Must specify a config section", new String[0]));
        return new RandomPotionEffectListData((ConfigurationSection)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.EFFECTS, new Object[0]).enable("Write in the chat the permanent potion effect you want to add.", ChatColor.AQUA + "Format: {Potion Effect Name}|{Duration Numeric Formula}|{Amplifier Numeric Formula}", ChatColor.DARK_RED + "Note: " + ChatColor.RED + "The '|' lines are literal.");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("effects")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("effects").getKeys(false);
            final String str = Arrays.asList((String[])keys.toArray(new String[0])).get(keys.size() - 1);
            editionInventory.getEditedSection().set("effects." + str, (Object)null);
            if (keys.size() <= 1) {
                editionInventory.getEditedSection().set("effects", (Object)null);
            }
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() + ChatColor.GRAY + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String[] split = s.split("\\|");
        Validate.isTrue(split.length > 1, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Use this format: $e{Potion Effect Name}|{Duration Numeric Formula}|{Amplifier Numeric Formula}$b.", new String[0]));
        final PotionEffectType byName = PotionEffectType.getByName(split[0].replace("-", "_").replace(" ", "_").toUpperCase());
        Validate.notNull((Object)byName, split[0] + FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), " is not a valid potion effect. All potion effects can be found here:$e https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html", new String[0]));
        final NumericStatFormula numericStatFormula = new NumericStatFormula(split[1]);
        final NumericStatFormula obj = (split.length > 2) ? new NumericStatFormula(split[2]) : new NumericStatFormula(1.0, 0.0, 0.0, 0.0);
        numericStatFormula.fillConfigurationSection(editionInventory.getEditedSection(), "effects." + byName.getName() + ".duration");
        obj.fillConfigurationSection(editionInventory.getEditedSection(), "effects." + byName.getName() + ".amplifier");
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + byName.getName() + " " + obj + " successfully added.");
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
        String string;
        final List<String> list2;
        final String s;
        final CharSequence target;
        final StringBuilder sb;
        ((PotionEffectListData)statData).getEffects().forEach(potionEffectData -> {
            ItemStat.translate("effect");
            new StringBuilder().append(MMOItems.plugin.getLanguage().getPotionEffectName(potionEffectData.getType()));
            if (potionEffectData.getLevel() < 2) {
                string = "";
            }
            else {
                string = " " + MMOUtils.intToRoman(potionEffectData.getLevel());
            }
            list2.add(s.replace(target, sb.append(string).toString()).replace("#d", this.durationFormat.format(potionEffectData.getDuration())));
            return;
        });
        itemStackBuilder.getLore().insert("effects", list);
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final JsonArray jsonArray = new JsonArray();
        for (final PotionEffectData potionEffectData : ((PotionEffectListData)statData).getEffects()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Type", potionEffectData.getType().getName());
            jsonObject.addProperty("Duration", (Number)potionEffectData.getDuration());
            jsonObject.addProperty("Level", (Number)potionEffectData.getLevel());
            jsonArray.add((JsonElement)jsonObject);
        }
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
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
            try {
                final PotionEffectListData potionEffectListData = new PotionEffectListData(new PotionEffectData[0]);
                for (final JsonElement jsonElement : new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonArray()) {
                    if (jsonElement.isJsonObject()) {
                        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                        potionEffectListData.add(new PotionEffectData(PotionEffectType.getByName(asJsonObject.get("Type").getAsString()), asJsonObject.get("Duration").getAsDouble(), asJsonObject.get("Level").getAsInt()));
                    }
                }
                return potionEffectListData;
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    @Override
    public void onConsume(@NotNull final VolatileMMOItem volatileMMOItem, @NotNull final Player player) {
        if (!volatileMMOItem.hasData(ItemStats.EFFECTS)) {
            return;
        }
        for (final PotionEffectData potionEffectData : ((PotionEffectListData)volatileMMOItem.getData(ItemStats.EFFECTS)).getEffects()) {
            if (potionEffectData != null) {
                player.removePotionEffect(potionEffectData.getType());
                player.addPotionEffect(potionEffectData.toEffect());
            }
        }
    }
}
