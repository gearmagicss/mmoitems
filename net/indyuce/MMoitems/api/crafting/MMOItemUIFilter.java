// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import io.lumine.mythic.lib.api.crafting.uimanager.UIFilterManager;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import io.lumine.mythic.utils.items.ItemFactory;
import org.bukkit.Material;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.jetbrains.annotations.Nullable;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.crafting.uifilters.UIFilter;

public class MMOItemUIFilter implements UIFilter
{
    ArrayList<String> validDataments;
    private static boolean reg;
    static MMOItemUIFilter global;
    
    @NotNull
    public String getIdentifier() {
        return "m";
    }
    
    public boolean matches(@NotNull final ItemStack itemStack, @NotNull String upperCase, @NotNull String anObject, @Nullable final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        upperCase = upperCase.replace(" ", "_").replace("-", "_").toUpperCase();
        anObject = anObject.replace(" ", "_").replace("-", "_").toUpperCase();
        if (!this.isValid(upperCase, anObject, friendlyFeedbackProvider)) {
            return false;
        }
        if (this.cancelMatch(itemStack, friendlyFeedbackProvider)) {
            return false;
        }
        final NBTItem value = NBTItem.get(itemStack);
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(value);
        if (template == null) {
            FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.FAILURE, "Item $r{0}$b is $fnot a MMOItem$b. ", new String[] { SilentNumbers.getItemName(itemStack) });
            return false;
        }
        String s = "";
        if (anObject.contains("{")) {
            s = anObject.substring(anObject.indexOf(123) + 1);
            anObject = anObject.substring(0, anObject.indexOf(123));
            if (s.endsWith("}")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        if (!template.getType().getId().equals(upperCase) || !template.getId().equals(anObject)) {
            FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.FAILURE, "MMOItem $r{0} {1}$b is not the expected $r{2} {3}$b. $fNo Match. ", new String[] { template.getType().getId(), template.getId(), upperCase, anObject });
            return false;
        }
        if (!s.isEmpty()) {
            final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(value);
            final QuickNumberRange rangeFromBracketsTab = SilentNumbers.rangeFromBracketsTab(s, "level");
            if (rangeFromBracketsTab != null) {
                int upgradeLevel = 0;
                if (volatileMMOItem.hasData(ItemStats.UPGRADE)) {
                    upgradeLevel = volatileMMOItem.getUpgradeLevel();
                }
                if (!rangeFromBracketsTab.inRange((double)upgradeLevel)) {
                    FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.FAILURE, "MMOItem $r{0} {1}$b is of level $u{2}$b though $r{3}$b was expected. $fNo Match. ", new String[] { template.getType().getId(), template.getId(), String.valueOf(upgradeLevel), rangeFromBracketsTab.toString() });
                    return false;
                }
            }
        }
        FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.SUCCESS, "Detected $r{0} {1} $sSuccessfully. ", new String[] { template.getType().getId(), template.getId() });
        return true;
    }
    
    public boolean isValid(@NotNull String upperCase, @NotNull String s, @Nullable final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        if (MMOItemUIFilter.reg) {
            return true;
        }
        upperCase = upperCase.replace(" ", "_").replace("-", "_").toUpperCase();
        s = s.replace(" ", "_").replace("-", "_").toUpperCase();
        final Type value = MMOItems.plugin.getTypes().get(upperCase);
        if (value == null) {
            FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.ERROR, "$fInvalid MMOItem Type $r{0}$f. ", new String[] { upperCase });
            return false;
        }
        if (s.contains("{") && s.contains("}")) {
            s = s.substring(0, s.indexOf(123));
        }
        if (MMOItems.plugin.getMMOItem(value, s) == null) {
            FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.ERROR, "$fInvalid MMOItem $r{0} {1}$f: $bNo such MMOItem for Type $e{0}$b. ", new String[] { upperCase, s });
            return false;
        }
        FriendlyFeedbackProvider.log(friendlyFeedbackProvider, FriendlyFeedbackCategory.SUCCESS, "Valid MMOItem $r{0} {1}$b. $snice. ", new String[] { upperCase, s });
        return true;
    }
    
    @NotNull
    public ArrayList<String> tabCompleteArgument(@NotNull final String s) {
        return (ArrayList<String>)SilentNumbers.smartFilter((ArrayList)MMOItems.plugin.getTypes().getAllTypeNames(), s, true);
    }
    
    @NotNull
    public ArrayList<String> tabCompleteData(@NotNull final String s, @NotNull String substring) {
        final Type value = MMOItems.plugin.getTypes().get(s);
        if (value != null) {
            String substring2 = "";
            String substring3 = "";
            if (substring.contains("{")) {
                final String substring4 = substring.substring(substring.indexOf(123) + 1);
                substring = substring.substring(0, substring.indexOf(123));
                int n = 0;
                if (substring4.contains(",")) {
                    n = substring4.lastIndexOf(44) + 1;
                }
                substring3 = substring4.substring(n);
                substring2 = substring4.substring(0, n);
            }
            final ArrayList smartFilter;
            final ArrayList list = smartFilter = SilentNumbers.smartFilter((ArrayList)MMOItems.plugin.getTemplates().getTemplateNames(value), substring, (boolean)(1 != 0));
            if (!substring3.isEmpty()) {
                ArrayList<String> smartFilter2 = new ArrayList<String>();
                if (substring3.contains("=")) {
                    final String lowerCase = substring3.substring(0, substring3.indexOf(61)).toLowerCase();
                    switch (lowerCase) {
                        case "level": {
                            SilentNumbers.addAll((ArrayList)smartFilter2, (Object[])new String[] { "level=1..", "level=2..4", "level=..6" });
                            break;
                        }
                        default: {
                            smartFilter2.add(substring3);
                            break;
                        }
                    }
                }
                else {
                    smartFilter2 = (ArrayList<String>)SilentNumbers.smartFilter((ArrayList)this.getValidDataments(), substring3, true);
                }
                for (final String str : list) {
                    final Iterator<String> iterator2 = smartFilter2.iterator();
                    while (iterator2.hasNext()) {
                        smartFilter.add(str + "{" + substring2 + iterator2.next());
                    }
                }
            }
            return (ArrayList<String>)smartFilter;
        }
        return (ArrayList<String>)SilentNumbers.toArrayList((Object[])new String[] { "Type_not_found,_check_your_spelling" });
    }
    
    @NotNull
    public ArrayList<String> getValidDataments() {
        if (this.validDataments != null) {
            return this.validDataments;
        }
        return this.validDataments = (ArrayList<String>)SilentNumbers.toArrayList((Object[])new String[] { "level" });
    }
    
    public boolean fullyDefinesItem() {
        return true;
    }
    
    @Nullable
    public ItemStack getItemStack(@NotNull String upperCase, @NotNull String upperCase2, @Nullable final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        if (!this.isValid(upperCase, upperCase2, friendlyFeedbackProvider)) {
            return null;
        }
        upperCase = upperCase.replace(" ", "_").replace("-", "_").toUpperCase();
        upperCase2 = upperCase2.replace(" ", "_").replace("-", "_").toUpperCase();
        return MMOItems.plugin.getItem(upperCase, upperCase2);
    }
    
    @NotNull
    public ItemStack getDisplayStack(@NotNull String upperCase, @NotNull String str, @Nullable final FriendlyFeedbackProvider friendlyFeedbackProvider) {
        if (!this.isValid(upperCase, str, friendlyFeedbackProvider)) {
            return ItemFactory.of(Material.STRUCTURE_VOID).name("§cInvalid MMOItem §e" + upperCase + " " + str).build();
        }
        upperCase = upperCase.replace(" ", "_").replace("-", "_").toUpperCase();
        String s = "";
        if (str.contains("{")) {
            s = str.substring(str.indexOf(123) + 1);
            str = str.substring(0, str.indexOf(123));
            if (s.endsWith("}")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        str = str.replace(" ", "_").replace("-", "_").toUpperCase();
        final MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(upperCase), str);
        if (!s.isEmpty() && mmoItem.hasUpgradeTemplate()) {
            final QuickNumberRange rangeFromBracketsTab = SilentNumbers.rangeFromBracketsTab(s, "level");
            if (rangeFromBracketsTab != null) {
                final UpgradeData clone = ((UpgradeData)mmoItem.getData(ItemStats.UPGRADE)).clone();
                clone.setLevel(SilentNumbers.floor(rangeFromBracketsTab.getAsDouble(0.0)));
                mmoItem.setData(ItemStats.UPGRADE, clone);
            }
        }
        return mmoItem.newBuilder().buildNBT(true).toItem();
    }
    
    @NotNull
    public ArrayList<String> getDescription(@NotNull String upperCase, @NotNull String upperCase2) {
        if (!this.isValid(upperCase, upperCase2, null)) {
            return (ArrayList<String>)SilentNumbers.toArrayList((Object[])new String[] { "This MMOItem is $finvalid$b." });
        }
        upperCase = upperCase.replace(" ", "_").replace("-", "_").toUpperCase();
        upperCase2 = upperCase2.replace(" ", "_").replace("-", "_").toUpperCase();
        return (ArrayList<String>)SilentNumbers.toArrayList((Object[])new String[] { SilentNumbers.getItemName(MMOItems.plugin.getItem(upperCase, upperCase2)) });
    }
    
    public boolean determinateGeneration() {
        return false;
    }
    
    public boolean partialDeterminateGeneration(@NotNull final String s, @NotNull final String s2) {
        return !this.isValid(s, s2, null);
    }
    
    @NotNull
    public String getSourcePlugin() {
        return "MMOItems";
    }
    
    @NotNull
    public String getFilterName() {
        return "MMOItem";
    }
    
    @NotNull
    public String exampleArgument() {
        return "CONSUMABLE";
    }
    
    @NotNull
    public String exampleData() {
        return "MANGO";
    }
    
    public static void register() {
        UIFilterManager.registerUIFilter((UIFilter)(MMOItemUIFilter.global = new MMOItemUIFilter()));
        VanillaMMOItemCountermatch.enable();
        MMOItemUIFilter.reg = false;
    }
    
    @NotNull
    public static MMOItemUIFilter get() {
        return MMOItemUIFilter.global;
    }
    
    static {
        MMOItemUIFilter.reg = true;
    }
}
