// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.ItemTier;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.NameData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class DisplayName extends StringStat implements GemStoneStat
{
    private final String[] cleanFilter;
    
    public DisplayName() {
        super("NAME", VersionMaterial.OAK_SIGN.toMaterial(), "Display Name", new String[] { "The item display name." }, new String[] { "all" }, new Material[0]);
        this.cleanFilter = new String[] { ChatColor.BOLD.toString(), ChatColor.ITALIC.toString(), ChatColor.UNDERLINE.toString(), ChatColor.STRIKETHROUGH.toString(), ChatColor.MAGIC.toString() };
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        String s;
        if (statData instanceof NameData) {
            s = ((NameData)statData).bake();
        }
        else {
            s = statData.toString();
        }
        final ItemTier tier = MMOItems.plugin.getTiers().findTier(itemStackBuilder.getMMOItem());
        String s2 = s.replace("<tier-name>", (tier != null) ? ChatColor.stripColor(tier.getName()) : "").replace("<tier-color>", (tier != null) ? ChatColor.getLastColors(tier.getName()) : "&f");
        if (tier != null) {
            for (final String s3 : this.cleanFilter) {
                if (ChatColor.getLastColors(tier.getName()).contains(s3)) {
                    s2 = s2.replace("<tier-color-cleaned>", ChatColor.getLastColors(tier.getName().replace(s3, "")));
                }
            }
        }
        String s4 = this.cropUpgrade(s2);
        if (itemStackBuilder.getMMOItem().hasUpgradeTemplate()) {
            s4 = appendUpgradeLevel(s4, itemStackBuilder.getMMOItem().getUpgradeLevel());
        }
        itemStackBuilder.getMeta().setDisplayName(MythicLib.plugin.parseColors(s4));
        StatHistory.from(itemStackBuilder.getMMOItem(), this);
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
    }
    
    @NotNull
    String cropUpgrade(@NotNull String s) {
        final String string = MMOItems.plugin.getConfig().getString("item-upgrading.name-suffix", " &8(&e+#lvl#&8)");
        if (string == null || string.isEmpty()) {
            return s;
        }
        final String colors = MythicLib.plugin.parseColors(string);
        if (colors != null) {
            final int index = colors.indexOf("#lvl#");
            if (index < 0) {
                return s;
            }
            final String substring = colors.substring(0, index);
            final String substring2 = colors.substring(index + "#lvl#".length());
            final String replace = substring.replace("+", "-");
            final String replace2 = substring2.replace("+", "-");
            if (s.contains(substring)) {
                final int index2 = s.indexOf(substring);
                final int lastIndex = s.lastIndexOf(substring2);
                int length;
                if (lastIndex < 0) {
                    length = s.length();
                }
                else {
                    length = lastIndex + substring2.length();
                }
                s = s.substring(0, index2) + s.substring(length);
            }
            if (s.contains(replace)) {
                final int index3 = s.indexOf(replace);
                final int lastIndex2 = s.lastIndexOf(replace2);
                int length2;
                if (lastIndex2 < 0) {
                    length2 = s.length();
                }
                else {
                    length2 = lastIndex2 + replace2.length();
                }
                s = s.substring(0, index3) + s.substring(length2);
            }
        }
        return s;
    }
    
    @NotNull
    public static String appendUpgradeLevel(@NotNull final String str, final int n) {
        final String colors = MythicLib.plugin.parseColors(MMOItems.plugin.getConfig().getString("item-upgrading.name-suffix"));
        if (colors != null && n != 0) {
            return str + levelPrefix(colors, n);
        }
        return str;
    }
    
    @NotNull
    public static String levelPrefix(@NotNull String s, final int i) {
        s = s.replace("#lvl#", String.valueOf(i));
        s = s.replace("+-", "-");
        return s;
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        if (statData instanceof NameData) {
            final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
            list.add(new ItemTag(this.getNBTPath(), (Object)((NameData)statData).getMainName()));
            if (((NameData)statData).hasPrefixes()) {
                list.add(((NameData)statData).compressPrefixes(this.getNBTPath() + "_PRE"));
            }
            if (((NameData)statData).hasSuffixes()) {
                list.add(((NameData)statData).compressSuffixes(this.getNBTPath() + "_SUF"));
            }
            return list;
        }
        return new ArrayList<ItemTag>();
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        boolean b = false;
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING);
        if (tagAtPath != null) {
            final ItemTag tagAtPath2 = ItemTag.getTagAtPath(this.getNBTPath() + "_PRE", readMMOItem.getNBT(), SupportedNBTTagValues.STRING);
            final ItemTag tagAtPath3 = ItemTag.getTagAtPath(this.getNBTPath() + "_SUF", readMMOItem.getNBT(), SupportedNBTTagValues.STRING);
            list.add(tagAtPath);
            list.add(tagAtPath2);
            list.add(tagAtPath3);
            if (readMMOItem.getNBT().getItem().getItemMeta().hasDisplayName()) {
                b = true;
            }
        }
        else {
            if (!readMMOItem.getNBT().getItem().getItemMeta().hasDisplayName()) {
                return;
            }
            list.add(new ItemTag(this.getNBTPath(), (Object)this.cropUpgrade(readMMOItem.getNBT().getItem().getItemMeta().getDisplayName())));
        }
        final NameData nameData = (NameData)this.getLoadedNBT(list);
        if (nameData != null) {
            String displayName = null;
            if (b) {
                displayName = readMMOItem.getNBT().getItem().getItemMeta().getDisplayName();
                if (!displayName.equals(ChatColor.stripColor(displayName))) {
                    displayName = null;
                }
                else {
                    nameData.setString(displayName);
                }
            }
            readMMOItem.setData(this, nameData);
            if (b && displayName != null && readMMOItem.getStatHistory(this) == null) {
                final ItemTag tagAtPath4 = ItemTag.getTagAtPath("HSTRY_" + this.getId(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING);
                if (tagAtPath4 != null) {
                    final StatHistory fromNBTString = StatHistory.fromNBTString(readMMOItem, (String)tagAtPath4.getValue());
                    if (fromNBTString != null) {
                        ((NameData)fromNBTString.getOriginalData()).setString(displayName);
                        readMMOItem.setStatHistory(this, fromNBTString);
                    }
                }
            }
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            final NameData nameData = new NameData((String)tagAtPath.getValue());
            nameData.readPrefixes(ItemTag.getTagAtPath(this.getNBTPath() + "_PRE", (ArrayList)list));
            nameData.readSuffixes(ItemTag.getTagAtPath(this.getNBTPath() + "_SUF", (ArrayList)list));
            return nameData;
        }
        return null;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new NameData("");
    }
    
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return new NameData(o.toString());
    }
}
