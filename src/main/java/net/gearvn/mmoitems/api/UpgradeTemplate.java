// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.Objects;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.Enchants;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.jetbrains.annotations.Nullable;
import java.util.Set;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.type.Upgradable;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class UpgradeTemplate
{
    @NotNull
    private final String id;
    @NotNull
    private final Map<ItemStat, UpgradeInfo> perStatUpgradeInfos;
    
    public UpgradeTemplate(@NotNull final ConfigurationSection configurationSection) {
        this.perStatUpgradeInfos = new HashMap<ItemStat, UpgradeInfo>();
        Validate.notNull((Object)configurationSection, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "You must specify a config section.", new String[0]));
        this.id = configurationSection.getName().toLowerCase().replace("_", "-").replace(" ", "-");
        final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
        friendlyFeedbackProvider.activatePrefix(true, "Upgrade Template $i&o" + configurationSection.getName());
        for (final String s : configurationSection.getKeys(false)) {
            final String replace = s.toUpperCase().replace("-", "_");
            final ItemStat value = MMOItems.plugin.getStats().get(replace);
            if (value == null) {
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Stat '$r{0}$b' $fnot found$b.", new String[] { replace });
            }
            else if (!(value instanceof Upgradable)) {
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Stat $r{0}$b is $fnot upgradeable$b.", new String[] { value.getId() });
            }
            else if (!(value.getClearStatData() instanceof Mergeable)) {
                friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Stat Data used by $r{0}$b is $fnot mergeable$b, and thus it cannot be upgradeable. Contact the dev of this ItemStat.", new String[] { value.getId() });
            }
            else {
                try {
                    this.perStatUpgradeInfos.put(value, ((Upgradable)value).loadUpgradeInfo(configurationSection.get(s)));
                }
                catch (IllegalArgumentException ex) {
                    friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, ex.getMessage(), new String[0]);
                }
            }
        }
        friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
    }
    
    @NotNull
    public String getId() {
        return this.id;
    }
    
    @NotNull
    public Set<ItemStat> getKeys() {
        return this.perStatUpgradeInfos.keySet();
    }
    
    @Nullable
    public UpgradeInfo getUpgradeInfo(@NotNull final ItemStat itemStat) {
        return this.perStatUpgradeInfos.get(itemStat);
    }
    
    public void upgrade(@NotNull final MMOItem mmoItem) {
        this.upgradeTo(mmoItem, mmoItem.getUpgradeLevel() + 1);
    }
    
    public void upgradeTo(@NotNull final MMOItem item, final int level) {
        Enchants.separateEnchantments(item);
        UpgradeData upgradeData;
        if (item.hasData(ItemStats.UPGRADE)) {
            upgradeData = (UpgradeData)item.getData(ItemStats.UPGRADE);
        }
        else {
            upgradeData = new UpgradeData(null, null, false, false, 0, 100.0);
        }
        upgradeData.setLevel(level);
        item.setData(ItemStats.UPGRADE, upgradeData);
        for (final ItemStat itemStat : this.perStatUpgradeInfos.keySet()) {
            ((Upgradable)itemStat).preprocess(item);
            final StatHistory from = StatHistory.from(item, itemStat);
            ((Upgradable)itemStat).midprocess(item);
            item.setData(itemStat, from.recalculate(level));
            ((Upgradable)itemStat).postprocess(item);
        }
    }
    
    public static boolean isDisplayingUpgrades() {
        return MMOItems.plugin.getConfig().getBoolean("item-upgrading.display-stat-changes", false);
    }
    
    @NotNull
    public static String getUpgradeChangeSuffix(@NotNull final String s, final boolean b) {
        final String s2 = Objects.requireNonNull(MMOItems.plugin.getConfig().getString("item-upgrading.stat-change-suffix", " &8(<p>#stat#&8)"));
        final String replacement = Objects.requireNonNull(MMOItems.plugin.getConfig().getString("item-upgrading.stat-change-positive", "&a"));
        final String replacement2 = Objects.requireNonNull(MMOItems.plugin.getConfig().getString("item-upgrading.stat-change-negative", "&c"));
        if (b) {
            return s2.replace("<p>", replacement2).replace("#stat#", s);
        }
        return s2.replace("<p>", replacement).replace("#stat#", s);
    }
}
