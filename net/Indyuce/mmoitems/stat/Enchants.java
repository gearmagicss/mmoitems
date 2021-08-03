// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackMessage;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.plugin.Plugin;
import io.lumine.mythicenchants.MythicEnchants;
import org.bukkit.NamespacedKey;
import java.util.HashMap;
import io.lumine.mythic.lib.api.util.ui.PlusMinusPercent;
import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.ArrayList;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.Optional;
import java.util.List;
import org.bukkit.enchantments.Enchantment;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
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
import net.Indyuce.mmoitems.stat.data.random.RandomEnchantListData;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.Upgradable;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Enchants extends ItemStat implements Upgradable
{
    public Enchants() {
        super("ENCHANTS", Material.ENCHANTED_BOOK, "Enchantments", new String[] { "The item enchants." }, new String[] { "all" }, new Material[0]);
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        Validate.isTrue(o instanceof ConfigurationSection, "Must specify a config section");
        return new RandomEnchantListData((ConfigurationSection)o);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_ALL) {
            new StatEdition(editionInventory, ItemStats.ENCHANTS, new Object[0]).enable("Write in the chat the enchant you want to add.", ChatColor.AQUA + "Format: {Enchant Name} {Enchant Level Numeric Formula}");
        }
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF && editionInventory.getEditedSection().contains("enchants")) {
            final Set keys = editionInventory.getEditedSection().getConfigurationSection("enchants").getKeys(false);
            final String str = Arrays.asList((String[])keys.toArray(new String[0])).get(keys.size() - 1);
            editionInventory.getEditedSection().set("enchants." + str, (Object)null);
            if (keys.size() <= 1) {
                editionInventory.getEditedSection().set("enchants", (Object)null);
            }
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase().replace("_", " ") + ".");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final String[] split = s.split(" ");
        Validate.isTrue(split.length >= 2, "Use this format: {Enchant Name} {Enchant Level Numeric Formula}. Example: 'sharpness 5 0.3' stands for Sharpness 5, plus 0.3 level per item level (rounded up to lower integer)");
        final Enchantment enchant = getEnchant(split[0]);
        Validate.notNull((Object)enchant, split[0] + " is not a valid enchantment! All enchants can be found here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html");
        final NumericStatFormula numericStatFormula = new NumericStatFormula(s.substring(s.indexOf(" ") + 1));
        numericStatFormula.fillConfigurationSection(editionInventory.getEditedSection(), "enchants." + enchant.getKey().getKey());
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + enchant.getKey().getKey() + " " + numericStatFormula.toString() + " successfully added.");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            list.add(ChatColor.GRAY + "Current Value:");
            final RandomEnchantListData randomEnchantListData;
            optional.get().getEnchants().forEach(enchantment -> list.add(ChatColor.GRAY + "* " + MMOUtils.caseOnWords(enchantment.getKey().getKey().replace("_", " ")) + " " + randomEnchantListData.getLevel(enchantment).toString()));
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.RED + "None");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Click to add an enchant.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove the last enchant.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new EnchantListData();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final EnchantListData enchantListData = new EnchantListData();
        for (final Enchantment enchantment : readMMOItem.getNBT().getItem().getItemMeta().getEnchants().keySet()) {
            enchantListData.addEnchant(enchantment, readMMOItem.getNBT().getItem().getItemMeta().getEnchantLevel(enchantment));
        }
        readMMOItem.setData(ItemStats.ENCHANTS, enchantListData);
        if (ItemTag.getTagAtPath("HSTRY_" + this.getId(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING) == null && (!readMMOItem.getNBT().getBoolean(ItemStats.DISABLE_ENCHANTING.getNBTPath()) || !readMMOItem.getNBT().getBoolean(ItemStats.DISABLE_REPAIRING.getNBTPath()))) {
            StatHistory.from(readMMOItem, ItemStats.ENCHANTS);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            final ArrayList stringListFromTag = ItemTag.getStringListFromTag(tagAtPath);
            final EnchantListData enchantListData = new EnchantListData();
            final Iterator<String> iterator = stringListFromTag.iterator();
            while (iterator.hasNext()) {
                final String[] split = iterator.next().split(" ");
                if (split.length >= 2) {
                    final String s = split[0];
                    final String s2 = split[1];
                    Enchantment enchant = null;
                    try {
                        enchant = getEnchant(s);
                    }
                    catch (Exception ex) {}
                    final Integer integerParse = SilentNumbers.IntegerParse(s2);
                    if (enchant == null || integerParse == null) {
                        continue;
                    }
                    enchantListData.addEnchant(enchant, integerParse);
                }
            }
            return enchantListData;
        }
        return null;
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final EnchantListData enchantListData = (EnchantListData)statData;
        for (final Enchantment enchantment : enchantListData.getEnchants()) {
            if (enchantListData.getLevel(enchantment) != 0) {
                itemStackBuilder.getMeta().addEnchant(enchantment, enchantListData.getLevel(enchantment), true);
            }
        }
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        final ArrayList<String> list2 = new ArrayList<String>();
        for (final Enchantment enchantment : ((EnchantListData)statData).getEnchants()) {
            list2.add(enchantment.getKey().getKey() + " " + ((EnchantListData)statData).getLevel(enchantment));
        }
        list.add(ItemTag.fromStringList(this.getNBTPath(), (List)list2));
        return list;
    }
    
    @NotNull
    @Override
    public UpgradeInfo loadUpgradeInfo(@Nullable final Object o) {
        return EnchantUpgradeInfo.GetFrom(o);
    }
    
    @NotNull
    @Override
    public StatData apply(@NotNull final StatData statData, @NotNull final UpgradeInfo upgradeInfo, final int n) {
        if (statData instanceof EnchantListData && upgradeInfo instanceof EnchantUpgradeInfo) {
            final EnchantListData enchantListData = (EnchantListData)statData;
            final EnchantUpgradeInfo enchantUpgradeInfo = (EnchantUpgradeInfo)upgradeInfo;
            for (final Enchantment enchantment : enchantUpgradeInfo.getAffectedEnchantments()) {
                int i = n;
                double n2 = enchantListData.getLevel(enchantment);
                final PlusMinusPercent pmp = enchantUpgradeInfo.getPMP(enchantment);
                if (pmp == null) {
                    continue;
                }
                if (i > 0) {
                    while (i > 0) {
                        n2 = pmp.apply(n2);
                        --i;
                    }
                }
                else if (i < 0) {
                    while (i < 0) {
                        n2 = pmp.reverse(n2);
                        ++i;
                    }
                }
                enchantListData.addEnchant(enchantment, SilentNumbers.floor(n2));
            }
            return enchantListData;
        }
        return statData;
    }
    
    public static void separateEnchantments(@NotNull final MMOItem mmoItem) {
        if (mmoItem.hasData(ItemStats.DISABLE_REPAIRING) && mmoItem.hasData(ItemStats.DISABLE_ENCHANTING)) {
            return;
        }
        final boolean boolean1 = MMOItems.plugin.getConfig().getBoolean("stat-merging.additive-enchantments", false);
        if (mmoItem.hasData(ItemStats.ENCHANTS)) {
            final EnchantListData enchantListData = (EnchantListData)mmoItem.getData(ItemStats.ENCHANTS);
            final StatHistory from = StatHistory.from(mmoItem, ItemStats.ENCHANTS);
            final EnchantListData enchantListData2 = (EnchantListData)from.recalculate(mmoItem.getUpgradeLevel());
            final HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
            for (final Enchantment enchantment : Enchantment.values()) {
                final int level = enchantListData.getLevel(enchantment);
                final int level2 = enchantListData2.getLevel(enchantment);
                if (boolean1) {
                    final int j = level - level2;
                    if (j != 0) {
                        hashMap.put(enchantment, j);
                    }
                }
                else if (level > level2) {
                    hashMap.put(enchantment, level);
                }
            }
            if (hashMap.size() > 0) {
                final EnchantListData enchantListData3 = new EnchantListData();
                for (final Enchantment key : hashMap.keySet()) {
                    enchantListData3.addEnchant(key, hashMap.get(key));
                }
                from.registerExternalData(enchantListData3);
            }
        }
    }
    
    public static Enchantment getEnchant(String replace) {
        replace = replace.toLowerCase().replace("-", "_");
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(replace));
        if (enchantment == null) {
            if (MMOItems.plugin.getMythicEnchantsSupport() != null) {
                final Enchantment byKey = Enchantment.getByKey(new NamespacedKey((Plugin)MythicEnchants.inst(), replace));
                if (byKey != null) {
                    return byKey;
                }
            }
            enchantment = Enchantment.getByName(replace);
        }
        return enchantment;
    }
    
    public static class EnchantUpgradeInfo implements UpgradeInfo
    {
        @NotNull
        HashMap<Enchantment, PlusMinusPercent> perEnchantmentOperations;
        
        @NotNull
        public static EnchantUpgradeInfo GetFrom(@Nullable final Object o) {
            Validate.notNull(o, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Upgrade operation list must not be null", new String[0]));
            if (!(o instanceof List)) {
                throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Expected a list of strings instead of $i{0}", new String[] { o.toString() }));
            }
            final ArrayList<String> list = new ArrayList<String>();
            int n = 0;
            final StringBuilder sb = new StringBuilder();
            for (final String next : (List)o) {
                if (next instanceof String) {
                    list.add(next);
                }
                else {
                    n = 1;
                    sb.append(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), " Invalid list entry $i{0}$b;", new String[] { o.toString() }));
                }
            }
            if (n != 0) {
                throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Could not read enchantment list:", new String[0]) + sb.toString());
            }
            if (list.isEmpty()) {
                throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Upgrade operation list is empty", new String[0]));
            }
            final EnchantUpgradeInfo enchantUpgradeInfo = new EnchantUpgradeInfo();
            for (final String s : list) {
                final String[] split = s.split(" ");
                if (split.length >= 2) {
                    final String s2 = split[0];
                    String str = split[1];
                    final char char1 = str.charAt(0);
                    if (char1 == 's') {
                        str = str.substring(1);
                    }
                    else if (char1 != '+' && char1 != '-' && char1 != 'n') {
                        str = '+' + str;
                    }
                    final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
                    final PlusMinusPercent fromString = PlusMinusPercent.getFromString(str, friendlyFeedbackProvider);
                    Enchantment enchant = null;
                    try {
                        enchant = Enchants.getEnchant(s2);
                    }
                    catch (Exception ex) {}
                    if (fromString == null) {
                        sb.append(' ').append(((FriendlyFeedbackMessage)friendlyFeedbackProvider.getFeedbackOf(FriendlyFeedbackCategory.ERROR).get(0)).forConsole(friendlyFeedbackProvider.getPalette()));
                        n = 1;
                    }
                    if (enchant == null) {
                        sb.append(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), " Invalid Enchantment $i{0}$b.", new String[] { s2 }));
                        n = 1;
                    }
                    if (fromString == null || enchant == null) {
                        continue;
                    }
                    enchantUpgradeInfo.addEnchantmentOperation(enchant, fromString);
                }
                else {
                    n = 1;
                    sb.append(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), " Invalid list entry $i{0}$b. List entries are of the format 'esharpness +1$b'.", new String[] { s }));
                }
            }
            if (n != 0) {
                throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Could not read enchantment list:", new String[0]) + sb.toString());
            }
            return enchantUpgradeInfo;
        }
        
        public EnchantUpgradeInfo() {
            this.perEnchantmentOperations = new HashMap<Enchantment, PlusMinusPercent>();
        }
        
        @Nullable
        public PlusMinusPercent getPMP(@NotNull final Enchantment key) {
            return this.perEnchantmentOperations.get(key);
        }
        
        public void addEnchantmentOperation(@NotNull final Enchantment key, @NotNull final PlusMinusPercent value) {
            this.perEnchantmentOperations.put(key, value);
        }
        
        @NotNull
        public Set<Enchantment> getAffectedEnchantments() {
            return this.perEnchantmentOperations.keySet();
        }
    }
}
