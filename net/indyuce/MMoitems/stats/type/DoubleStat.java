// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackMessage;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import io.lumine.mythic.lib.api.util.ui.PlusMinusPercent;
import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import java.util.Optional;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.regex.Pattern;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import java.util.Objects;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.api.util.StatFormat;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.UpgradeTemplate;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import java.text.DecimalFormat;

public class DoubleStat extends ItemStat implements Upgradable, Previewable
{
    private static final DecimalFormat digit;
    
    public DoubleStat(final String s, final Material material, final String s2, final String[] array) {
        super(s, material, s2, array, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
    }
    
    public DoubleStat(final String s, final Material material, final String s2, final String[] array, final String[] array2, final Material... array3) {
        super(s, material, s2, array, array2, array3);
    }
    
    public boolean handleNegativeStats() {
        return true;
    }
    
    public double multiplyWhenDisplaying() {
        return 1.0;
    }
    
    public boolean moreIsBetter() {
        return true;
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        if (o instanceof Number) {
            return new NumericStatFormula(Double.parseDouble(o.toString()), 0.0, 0.0, 0.0);
        }
        if (o instanceof ConfigurationSection) {
            return new NumericStatFormula(o);
        }
        throw new IllegalArgumentException("Must specify a number or a config section");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final double value = ((DoubleData)statData).getValue();
        if (value < 0.0 && !this.handleNegativeStats()) {
            return;
        }
        double n = 0.0;
        if (UpgradeTemplate.isDisplayingUpgrades() && itemStackBuilder.getMMOItem().getUpgradeLevel() != 0) {
            final StatHistory statHistory = itemStackBuilder.getMMOItem().getStatHistory(this);
            if (statHistory != null) {
                n = value - ((DoubleData)statHistory.recalculateUnupgraded()).getValue();
            }
        }
        if (value != 0.0 || n != 0.0) {
            MMOItems.plugin.getLanguage().getStatFormat(this.getPath());
            if (n != 0.0) {
                itemStackBuilder.getLore().insert(this.getPath(), formatPath(MMOItems.plugin.getLanguage().getStatFormat(this.getPath()), this.moreIsBetter(), value * this.multiplyWhenDisplaying()) + MythicLib.plugin.parseColors(UpgradeTemplate.getUpgradeChangeSuffix(this.plus(n * this.multiplyWhenDisplaying()) + new StatFormat("##").format(n * this.multiplyWhenDisplaying()), !this.isGood(n * this.multiplyWhenDisplaying()))));
            }
            else {
                itemStackBuilder.getLore().insert(this.getPath(), formatPath(MMOItems.plugin.getLanguage().getStatFormat(this.getPath()), this.moreIsBetter(), value * this.multiplyWhenDisplaying()));
            }
        }
        if (((DoubleData)statData).getValue() != 0.0) {
            itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        }
    }
    
    @NotNull
    public static String formatPath(@NotNull final String s, final boolean b, final double n) {
        return s.replace("<plus>#", getColorPrefix(n < 0.0 && b) + ((n > 0.0) ? "+" : "") + new StatFormat("##").format(n)).replace("#", getColorPrefix(n < 0.0 && b) + new StatFormat("##").format(n)).replace("<plus>", (n > 0.0) ? "+" : "");
    }
    
    @NotNull
    public static String formatPath(@NotNull final String s, final boolean b, final double number, final double number2) {
        return s.replace("<plus>", "").replace("#", getColorPrefix(number < 0.0 && b) + ((number > 0.0) ? "+" : "") + new StatFormat("##").format(number) + MMOItems.plugin.getConfig().getString("stats-displaying.range-dash", "\u2393") + getColorPrefix(number2 < 0.0 && b) + ((number < 0.0 && number2 > 0.0) ? "+" : "") + new StatFormat("##").format(number2));
    }
    
    @Override
    public void whenPreviewed(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData, @NotNull final RandomStatData randomStatData) {
        Validate.isTrue(statData instanceof DoubleData, "Current Data is not Double Data");
        Validate.isTrue(randomStatData instanceof NumericStatFormula, "Template Data is not Numeric Stat Formula");
        double calculate = ((NumericStatFormula)randomStatData).calculate(0.0, -2.5);
        final double calculate2 = ((NumericStatFormula)randomStatData).calculate(0.0, 2.5);
        if (calculate2 < 0.0 && !this.handleNegativeStats()) {
            return;
        }
        if (calculate < 0.0 && !this.handleNegativeStats()) {
            calculate = 0.0;
        }
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        if (calculate != 0.0 || calculate2 != 0.0) {
            String s;
            if (SilentNumbers.round(calculate, 2) == SilentNumbers.round(calculate2, 2)) {
                s = formatPath(MMOItems.plugin.getLanguage().getStatFormat(this.getPath()), this.moreIsBetter(), calculate2 * this.multiplyWhenDisplaying());
            }
            else {
                s = formatPath(MMOItems.plugin.getLanguage().getStatFormat(this.getPath()), this.moreIsBetter(), calculate * this.multiplyWhenDisplaying(), calculate2 * this.multiplyWhenDisplaying());
            }
            itemStackBuilder.getLore().insert(this.getPath(), s);
        }
    }
    
    @NotNull
    public static String getColorPrefix(final boolean b) {
        return Objects.requireNonNull(MMOItems.plugin.getConfig().getString("stats-displaying.color-" + (b ? "negative" : "positive"), ""));
    }
    
    @NotNull
    String plus(final double n) {
        if (n >= 0.0) {
            return "+";
        }
        return "";
    }
    
    public boolean isGood(final double n) {
        return this.moreIsBetter() ? (n >= 0.0) : (n <= 0.0);
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((DoubleData)statData).getValue()));
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE));
        }
        final DoubleData doubleData = (DoubleData)this.getLoadedNBT(list);
        if (doubleData != null) {
            readMMOItem.setData(this, doubleData);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            return new DoubleData(SilentNumbers.round((double)tagAtPath.getValue(), 4));
        }
        return null;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed " + this.getName() + ChatColor.GRAY + ".");
            return;
        }
        new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the numeric value you want.", "Second Format: {Base} {Scaling Value} {Spread} {Max Spread}", "Third Format: {Min Value} -> {Max Value}");
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        double d;
        double n;
        double truncation;
        double truncation2;
        if (s.contains("->")) {
            final String[] split = s.replace(" ", "").split(Pattern.quote("->"));
            Validate.isTrue(split.length > 1, "You must specif two (both min and max) values");
            final double double1 = Double.parseDouble(split[0]);
            final double double2 = Double.parseDouble(split[1]);
            Validate.isTrue(double2 > double1, "Max value must be greater than min value");
            d = MMOUtils.truncation((double1 == -double2) ? ((double2 - double1) * 0.05) : ((double1 + double2) / 2.0), 3);
            n = 0.0;
            truncation = MMOUtils.truncation((double2 - double1) / (2.0 * d), 3);
            truncation2 = MMOUtils.truncation(0.8 * truncation, 3);
        }
        else {
            final String[] split2 = s.split(" ");
            d = MMOUtils.parseDouble(split2[0]);
            n = ((split2.length > 1) ? MMOUtils.parseDouble(split2[1]) : 0.0);
            truncation2 = ((split2.length > 2) ? MMOUtils.parseDouble(split2[2]) : 0.0);
            truncation = ((split2.length > 3) ? MMOUtils.parseDouble(split2[3]) : 0.0);
        }
        if (n == 0.0 && truncation2 == 0.0 && truncation == 0.0) {
            editionInventory.getEditedSection().set(this.getPath(), (Object)d);
        }
        else {
            editionInventory.getEditedSection().set(this.getPath() + ".base", (Object)d);
            editionInventory.getEditedSection().set(this.getPath() + ".scale", (Object)((n == 0.0) ? null : Double.valueOf(n)));
            editionInventory.getEditedSection().set(this.getPath() + ".spread", (Object)((truncation2 == 0.0) ? null : Double.valueOf(truncation2)));
            editionInventory.getEditedSection().set(this.getPath() + ".max-spread", (Object)((truncation == 0.0) ? null : Double.valueOf(truncation)));
        }
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " successfully changed to {" + d + " - " + n + " - " + truncation2 + " - " + truncation + "}");
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
        if (optional.isPresent()) {
            final NumericStatFormula numericStatFormula = optional.get();
            list.add(ChatColor.GRAY + "Base Value: " + ChatColor.GREEN + DoubleStat.digit.format(numericStatFormula.getBase()) + ((numericStatFormula.getScale() != 0.0) ? (ChatColor.GRAY + " (+" + ChatColor.GREEN + DoubleStat.digit.format(numericStatFormula.getScale()) + ChatColor.GRAY + ")") : ""));
            if (numericStatFormula.getSpread() > 0.0) {
                list.add(ChatColor.GRAY + "Spread: " + ChatColor.GREEN + DoubleStat.digit.format(numericStatFormula.getSpread() * 100.0) + "%" + ChatColor.GRAY + " (Max: " + ChatColor.GREEN + DoubleStat.digit.format(numericStatFormula.getMaxSpread() * 100.0) + "%" + ChatColor.GRAY + ")");
            }
        }
        else {
            list.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + "---");
        }
        list.add("");
        list.add(ChatColor.YELLOW + "\u25ba" + " Left click to change this value.");
        list.add(ChatColor.YELLOW + "\u25ba" + " Right click to remove this value.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new DoubleData(0.0);
    }
    
    @NotNull
    @Override
    public UpgradeInfo loadUpgradeInfo(@Nullable final Object o) {
        return DoubleUpgradeInfo.GetFrom(o);
    }
    
    @NotNull
    @Override
    public StatData apply(@NotNull final StatData statData, @NotNull final UpgradeInfo upgradeInfo, final int n) {
        int i = n;
        if (statData instanceof DoubleData && upgradeInfo instanceof DoubleUpgradeInfo) {
            double value = ((DoubleData)statData).getValue();
            if (i > 0) {
                while (i > 0) {
                    value = ((DoubleUpgradeInfo)upgradeInfo).getPMP().apply(value);
                    --i;
                }
            }
            else if (i < 0) {
                while (i < 0) {
                    value = ((DoubleUpgradeInfo)upgradeInfo).getPMP().reverse(value);
                    ++i;
                }
            }
            ((DoubleData)statData).setValue(value);
        }
        return statData;
    }
    
    static {
        digit = new DecimalFormat("0.####");
    }
    
    public static class DoubleUpgradeInfo implements UpgradeInfo
    {
        @NotNull
        PlusMinusPercent pmp;
        
        @NotNull
        public static DoubleUpgradeInfo GetFrom(@Nullable final Object o) {
            Validate.notNull(o, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Upgrade operation must not be null", new String[0]));
            String str = o.toString();
            if (str.isEmpty()) {
                throw new IllegalArgumentException(FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Upgrade operation is empty", new String[0]));
            }
            final char char1 = str.charAt(0);
            if (char1 == 's') {
                str = str.substring(1);
            }
            else if (char1 != '+' && char1 != '-' && char1 != 'n') {
                str = '+' + str;
            }
            final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
            final PlusMinusPercent fromString = PlusMinusPercent.getFromString(str, friendlyFeedbackProvider);
            if (fromString == null) {
                throw new IllegalArgumentException(((FriendlyFeedbackMessage)friendlyFeedbackProvider.getFeedbackOf(FriendlyFeedbackCategory.ERROR).get(0)).forConsole(friendlyFeedbackProvider.getPalette()));
            }
            return new DoubleUpgradeInfo(fromString);
        }
        
        public DoubleUpgradeInfo(@NotNull final PlusMinusPercent pmp) {
            this.pmp = pmp;
        }
        
        @NotNull
        public PlusMinusPercent getPMP() {
            return this.pmp;
        }
    }
}
