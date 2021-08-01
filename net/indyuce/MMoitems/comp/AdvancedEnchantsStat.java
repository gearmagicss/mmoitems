// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.advancedplugins.ae.enchanthandler.enchantments.AdvancedEnchantment;
import net.advancedplugins.ae.enchanthandler.enchantments.AEnchants;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class AdvancedEnchantsStat extends StringListStat
{
    @NotNull
    public static final String AE_TAG = "ae_enchantment";
    
    public AdvancedEnchantsStat() {
        super("ADVANCED_ENCHANTS", VersionMaterial.EXPERIENCE_BOTTLE.toMaterial(), "Advanced Enchants", new String[] { "The AEnchants of this. Format:", "§e[internal_name] [level]" }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final HashMap<String, Integer> list = this.readList(((StringListData)statData).getList());
        final ArrayList<String> list2 = new ArrayList<String>();
        final ArrayList<String> list3 = new ArrayList<String>();
        for (final String key : list.keySet()) {
            final Integer n = list.get(key);
            if (key != null && n != null) {
                if (key.isEmpty()) {
                    continue;
                }
                if (!AEnchants.getEnchants().contains(key.toLowerCase())) {
                    continue;
                }
                final AdvancedEnchantment advancedEnchantment = new AdvancedEnchantment(key);
                if (!advancedEnchantment.getLevelList().contains(n)) {
                    continue;
                }
                if (list3.contains(key.toLowerCase())) {
                    continue;
                }
                list3.add(key.toLowerCase());
                list2.add(advancedEnchantment.getDisplay((int)n) + " " + SilentNumbers.toRomanNumerals((int)n));
                itemStackBuilder.addItemTag(this.tagForAEnchantment(key, n));
            }
        }
        itemStackBuilder.getLore().insert(this.getPath(), list2);
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final List<String> list = editionInventory.getEditedSection().contains(this.getPath()) ? editionInventory.getEditedSection().getStringList(this.getPath()) : new ArrayList<String>();
        final AEListEnchantment entry = this.readEntry(s);
        Validate.notNull((Object)entry, FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Incorrect format, use $e[internal_name] [level]$b. For the list of enchantments, use $r/ae list$b. ", new String[0]));
        Validate.isTrue(AEnchants.getEnchants().contains(entry.getName().toLowerCase()), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Adv Enchantment '$u{0}$b' doesnt exist. For the list of enchantments, use $r/ae list$b. ", new String[] { entry.getName() }));
        final AdvancedEnchantment advancedEnchantment = new AdvancedEnchantment(entry.getName());
        Validate.isTrue(advancedEnchantment.getLevelList().contains(entry.getLevel()), FriendlyFeedbackProvider.quickForPlayer((FriendlyFeedbackPalette)FFPMMOItems.get(), "Adv Enchantment '$u{0}$b' cannot be level $u{1}$b. It only admits levels $e{2}$b. ", new String[] { entry.getName(), String.valueOf(entry.getLevel()), SilentNumbers.collapseList(SilentNumbers.transcribeList(advancedEnchantment.getLevelList(), String::valueOf), "$b,$e ") }));
        for (int i = 0; i < list.size(); ++i) {
            final AEListEnchantment entry2 = this.readEntry(list.get(i));
            if (entry2 != null) {
                if (entry2.getName().toLowerCase().equals(entry.getName().toLowerCase())) {
                    list.remove(i);
                    --i;
                }
            }
        }
        list.add(s);
        editionInventory.getEditedSection().set(this.getPath(), (Object)list);
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + this.getName() + " Stat successfully added.");
    }
    
    ItemTag tagForAEnchantment(@NotNull final String str, final int i) {
        return new ItemTag("ae_enchantment;" + str, (Object)i);
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        for (final String s : readMMOItem.getNBT().getTags()) {
            if (s == null) {
                continue;
            }
            if (!s.startsWith("ae_enchantment")) {
                continue;
            }
            final ItemTag tagAtPath = ItemTag.getTagAtPath(s, readMMOItem.getNBT(), SupportedNBTTagValues.DOUBLE);
            if (tagAtPath == null) {
                continue;
            }
            list.add(new ItemTag(s, (Object)SilentNumbers.round((double)tagAtPath.getValue())));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ArrayList<String> list2 = new ArrayList<String>();
        for (final ItemTag itemTag : list) {
            if (itemTag == null) {
                continue;
            }
            if (!(itemTag.getValue() instanceof Integer)) {
                continue;
            }
            final int index = itemTag.getPath().indexOf("ae_enchantment;");
            if (index < 0) {
                continue;
            }
            list2.add(itemTag.getPath().substring("ae_enchantment".length() + 1 + index) + " " + SilentNumbers.removeDecimalZeros(String.valueOf((int)itemTag.getValue())));
        }
        if (list2.size() == 0) {
            return super.getLoadedNBT(list);
        }
        return new StringListData(list2);
    }
    
    @NotNull
    HashMap<String, Integer> readList(@NotNull final List<String> list) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            final AEListEnchantment entry = this.readEntry(iterator.next());
            if (entry == null) {
                continue;
            }
            hashMap.put(entry.getName(), entry.getLevel());
        }
        return hashMap;
    }
    
    @Nullable
    AEListEnchantment readEntry(@Nullable final String s) {
        if (s == null || !s.contains(" ")) {
            return null;
        }
        final int lastIndex = s.lastIndexOf(32);
        final Integer integerParse = SilentNumbers.IntegerParse(s.substring(lastIndex + 1));
        if (integerParse == null) {
            return null;
        }
        return new AEListEnchantment(s.substring(0, lastIndex), integerParse);
    }
    
    private static class AEListEnchantment
    {
        @NotNull
        final String name;
        final int level;
        
        AEListEnchantment(@NotNull final String name, final int level) {
            this.name = name;
            this.level = level;
        }
        
        @NotNull
        public String getName() {
            return this.name;
        }
        
        public int getLevel() {
            return this.level;
        }
        
        @Override
        public String toString() {
            return this.getName() + " " + this.getLevel();
        }
    }
}
