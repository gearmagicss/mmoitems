// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.Collection;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import net.Indyuce.mmoitems.stat.Enchants;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.stat.type.NameData;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.stat.data.random.UpdatableRandomStatData;
import java.util.UUID;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import org.bukkit.inventory.meta.ItemMeta;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import io.lumine.mythic.lib.api.util.Ref;
import org.apache.commons.lang.Validate;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import java.util.logging.Level;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import java.util.HashMap;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import java.util.ArrayList;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.item.NBTItem;

public class MMOItemReforger
{
    static int autoSoulboundLevel;
    static int defaultItemLevel;
    static boolean keepTiersWhenReroll;
    @NotNull
    private final NBTItem nbtItem;
    private final int amount;
    @Nullable
    private MMOItem mmoItem;
    private final Map<ItemStat, StatHistory> itemDataHistory;
    @Nullable
    String cachedName;
    @NotNull
    ArrayList<String> cachedLore;
    @Nullable
    EnchantListData cachedEnchantments;
    @Nullable
    GemSocketsData cachedGemStones;
    @Nullable
    UpgradeData cachedUpgradeLevel;
    @Nullable
    StatData cachedSoulbound;
    @NotNull
    final String miID;
    @NotNull
    final Type miTypeName;
    @Nullable
    DoubleData cachedDurability;
    @Nullable
    Double cachedDur;
    @NotNull
    ArrayList<MMOItem> destroyedGems;
    
    public static void reload() {
        MMOItemReforger.autoSoulboundLevel = MMOItems.plugin.getConfig().getInt("soulbound.auto-bind.level", 1);
        MMOItemReforger.defaultItemLevel = MMOItems.plugin.getConfig().getInt("item-revision.default-item-level", -32767);
        MMOItemReforger.keepTiersWhenReroll = MMOItems.plugin.getConfig().getBoolean("item-revision.keep-tiers");
    }
    
    public MMOItemReforger(@NotNull final NBTItem nbtItem) {
        this.itemDataHistory = new HashMap<ItemStat, StatHistory>();
        this.cachedLore = new ArrayList<String>();
        this.cachedDurability = null;
        this.cachedDur = null;
        this.destroyedGems = new ArrayList<MMOItem>();
        this.nbtItem = nbtItem;
        this.amount = nbtItem.getItem().getAmount();
        final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(nbtItem);
        this.miTypeName = volatileMMOItem.getType();
        this.miID = volatileMMOItem.getId();
        if (volatileMMOItem.hasData(ItemStats.DURABILITY)) {
            this.cachedDurability = (DoubleData)volatileMMOItem.getData(ItemStats.DURABILITY);
        }
        else if (nbtItem.getItem().getItemMeta() instanceof Damageable) {
            this.cachedDur = ((Damageable)nbtItem.getItem().getItemMeta()).getDamage() / (double)nbtItem.getItem().getType().getMaxDurability();
        }
    }
    
    public void applySoulbound(@NotNull final Player player) {
        this.applySoulbound(player, MMOItemReforger.autoSoulboundLevel);
    }
    
    public void applySoulbound(@NotNull final Player player, final int n) {
        this.loadLiveMMOItem();
        this.mmoItem.setData(ItemStats.SOULBOUND, new SoulboundData(player.getUniqueId(), player.getName(), n));
    }
    
    public void update(@Nullable final Player player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player == null) {
            this.update((RPGPlayer)null, reforgeOptions);
        }
        else {
            final PlayerData value = PlayerData.get((OfflinePlayer)player);
            if (value == null) {
                this.update((RPGPlayer)null, reforgeOptions);
            }
            else {
                this.update(value.getRPG(), reforgeOptions);
            }
        }
    }
    
    public void update(@Nullable final RPGPlayer rpgPlayer, @NotNull final ReforgeOptions reforgeOptions) {
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(this.miTypeName, this.miID);
        final ItemMeta itemMeta = this.nbtItem.getItem().getItemMeta();
        if (template == null) {
            MMOItems.print(null, "Could not find template for $r{0} {1}$b. ", "MMOItems Reforger", this.miTypeName.toString(), this.miID);
            this.mmoItem = null;
            return;
        }
        Validate.isTrue(itemMeta != null, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Invalid item meta prevented $f{0}$b from updating.", new String[] { template.getType().toString() + " " + template.getId() }));
        this.loadLiveMMOItem();
        if (reforgeOptions.isRegenerate()) {
            this.restorePreRNGStats(this.extractStatDataHistory(), template, this.regenerate(rpgPlayer, template), true);
            return;
        }
        if (reforgeOptions.shouldKeepName()) {
            this.keepName(itemMeta);
        }
        if (reforgeOptions.shouldKeepLore() && this.mmoItem.hasData(ItemStats.LORE)) {
            this.keepLore(reforgeOptions.getKeepCase());
        }
        EnchantListData enchantListData = null;
        if (reforgeOptions.shouldKeepEnchantments()) {
            final Ref ref = new Ref();
            this.keepEnchantments((Ref<EnchantListData>)ref);
            enchantListData = (EnchantListData)ref.getValue();
        }
        if (reforgeOptions.shouldKeepUpgrades() && this.mmoItem.hasData(ItemStats.UPGRADE)) {
            this.keepUpgrades();
        }
        if (reforgeOptions.shouldKeepGemStones() || reforgeOptions.shouldKeepExternalSH()) {
            this.cacheFullHistory(!reforgeOptions.shouldKeepGemStones(), !reforgeOptions.shouldKeepExternalSH());
        }
        if (reforgeOptions.shouldKeepSoulbind() && this.mmoItem.hasData(ItemStats.SOULBOUND)) {
            this.keepSoulbound();
        }
        this.restorePreRNGStats(this.extractStatDataHistory(), template, this.regenerate(rpgPlayer, template), false);
        if (reforgeOptions.shouldKeepEnchantments() && enchantListData != null) {
            enchantListData.identifyTrueOriginalEnchantments(this.mmoItem, this.cachedEnchantments);
        }
    }
    
    @NotNull
    HashMap<ItemStat, StatHistory> extractStatDataHistory() {
        final HashMap<ItemStat, StatHistory> hashMap = new HashMap<ItemStat, StatHistory>();
        for (final ItemStat itemStat : this.mmoItem.getStats()) {
            if (!(itemStat.getClearStatData() instanceof Mergeable)) {
                continue;
            }
            final StatHistory from = StatHistory.from(this.mmoItem, itemStat);
            from.clearGemstones();
            from.clearExternalData();
            hashMap.put(from.getItemStat(), from.clone(this.mmoItem));
        }
        return hashMap;
    }
    
    void restorePreRNGStats(@NotNull final HashMap<? extends ItemStat, ? extends StatHistory> hashMap, @NotNull final MMOItemTemplate mmoItemTemplate, final int n, final boolean b) {
        if (!b) {
            for (final ItemStat itemStat : this.mmoItem.getStats()) {
                if (this.mmoItem.getData(itemStat) instanceof Mergeable) {
                    StatHistory.from(this.mmoItem, itemStat).clearModifiersBonus();
                }
            }
        }
        this.mmoItem.getUpgradeLevel();
        for (final ItemStat key : hashMap.keySet()) {
            final StatHistory statHistory = (StatHistory)hashMap.get(key);
            if (statHistory == null) {
                continue;
            }
            final StatData shouldRerollRegardless = this.shouldRerollRegardless(key, mmoItemTemplate.getBaseItemData().get(key), statHistory.getOriginalData(), n);
            final StatHistory from = StatHistory.from(this.mmoItem, key);
            if (shouldRerollRegardless != null) {
                from.setOriginalData(shouldRerollRegardless);
            }
            if (b) {
                statHistory.clearModifiersBonus();
            }
            else {
                from.clearModifiersBonus();
                for (final UUID uuid : statHistory.getAllModifiers()) {
                    from.registerModifierBonus(uuid, statHistory.getModifiersBonus(uuid));
                }
            }
            final StatHistory statHistory2 = this.itemDataHistory.get(key);
            if (statHistory2 != null) {
                from.assimilate(statHistory2);
            }
            this.itemDataHistory.put(key, from);
            this.mmoItem.setStatHistory(key, from);
        }
    }
    
    @Nullable
    StatData shouldRerollRegardless(@NotNull final ItemStat itemStat, @NotNull final RandomStatData randomStatData, @NotNull final StatData statData, final int n) {
        if (!(randomStatData instanceof UpdatableRandomStatData)) {
            return null;
        }
        if (ItemStats.LORE.equals(itemStat) || ItemStats.NAME.equals(itemStat) || ItemStats.GEM_SOCKETS.equals(itemStat)) {
            return null;
        }
        return ((UpdatableRandomStatData)randomStatData).reroll(itemStat, statData, n);
    }
    
    void regenerate(@Nullable final RPGPlayer rpgPlayer) {
        this.loadVolatileMMOItem();
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(this.mmoItem.getType(), this.mmoItem.getId());
        final ItemMeta itemMeta = this.nbtItem.getItem().getItemMeta();
        if (template == null) {
            MMOItems.print(null, "Could not find template for $r{0} {1}$b. ", "MMOItems Reforger", this.mmoItem.getType().toString(), this.mmoItem.getId());
            this.mmoItem = null;
            return;
        }
        Validate.isTrue(itemMeta != null, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Invalid item meta prevented $f{0}$b from updating.", new String[] { template.getType().toString() + " " + template.getId() }));
        if (rpgPlayer != null) {
            this.mmoItem = template.newBuilder(rpgPlayer).build();
        }
        else {
            this.mmoItem = template.newBuilder(this.mmoItem.hasData(ItemStats.ITEM_LEVEL) ? ((int)((DoubleData)this.mmoItem.getData(ItemStats.ITEM_LEVEL)).getValue()) : 0, null).build();
        }
    }
    
    int regenerate(@Nullable final RPGPlayer rpgPlayer, @NotNull final MMOItemTemplate mmoItemTemplate) {
        int n;
        if (rpgPlayer == null) {
            final int defaultItemLevel = MMOItemReforger.defaultItemLevel;
            n = ((defaultItemLevel == -32767) ? (this.mmoItem.hasData(ItemStats.ITEM_LEVEL) ? ((int)((DoubleData)this.mmoItem.getData(ItemStats.ITEM_LEVEL)).getValue()) : 0) : defaultItemLevel);
            this.mmoItem = mmoItemTemplate.newBuilder(n, (MMOItemReforger.keepTiersWhenReroll && this.mmoItem.hasData(ItemStats.TIER)) ? MMOItems.plugin.getTiers().get(this.mmoItem.getData(ItemStats.TIER).toString()) : null).build();
        }
        else {
            n = (this.mmoItem.hasData(ItemStats.ITEM_LEVEL) ? ((int)((DoubleData)this.mmoItem.getData(ItemStats.ITEM_LEVEL)).getValue()) : 0);
            this.mmoItem = mmoItemTemplate.newBuilder(rpgPlayer).build();
        }
        return n;
    }
    
    public void reforge(@Nullable final Player player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player == null) {
            this.reforge((RPGPlayer)null, reforgeOptions);
        }
        else {
            final PlayerData value = PlayerData.get((OfflinePlayer)player);
            if (value == null) {
                this.reforge((RPGPlayer)null, reforgeOptions);
            }
            else {
                this.reforge(value.getRPG(), reforgeOptions);
            }
        }
    }
    
    public void reforge(@Nullable final RPGPlayer rpgPlayer, @NotNull final ReforgeOptions reforgeOptions) {
        final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(this.miTypeName, this.miID);
        final ItemMeta itemMeta = this.nbtItem.getItem().getItemMeta();
        if (template == null) {
            MMOItems.print(null, "Could not find template for $r{0} {1}$b. ", "MMOItems Reforger", this.miTypeName.toString(), this.miID);
            this.mmoItem = null;
            return;
        }
        Validate.isTrue(itemMeta != null, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "Invalid item meta prevented $f{0}$b from updating.", new String[] { template.getType().toString() + " " + template.getId() }));
        if (reforgeOptions.isRegenerate()) {
            this.regenerate(rpgPlayer, template);
            return;
        }
        this.loadLiveMMOItem();
        if (reforgeOptions.shouldKeepName()) {
            this.keepName(itemMeta);
        }
        if (reforgeOptions.shouldKeepLore() && this.mmoItem.hasData(ItemStats.LORE)) {
            this.keepLore(reforgeOptions.getKeepCase());
        }
        EnchantListData enchantListData = null;
        if (reforgeOptions.shouldKeepEnchantments()) {
            final Ref ref = new Ref();
            this.keepEnchantments((Ref<EnchantListData>)ref);
            enchantListData = (EnchantListData)ref.getValue();
        }
        if (reforgeOptions.shouldKeepUpgrades() && this.mmoItem.hasData(ItemStats.UPGRADE)) {
            this.keepUpgrades();
        }
        if (reforgeOptions.shouldKeepGemStones() || reforgeOptions.shouldKeepExternalSH()) {
            this.cacheFullHistory(!reforgeOptions.shouldKeepGemStones(), !reforgeOptions.shouldKeepExternalSH());
        }
        if (reforgeOptions.shouldKeepSoulbind() && this.mmoItem.hasData(ItemStats.SOULBOUND)) {
            this.keepSoulbound();
        }
        this.regenerate(rpgPlayer, template);
        if (reforgeOptions.shouldKeepEnchantments() && enchantListData != null) {
            enchantListData.identifyTrueOriginalEnchantments(this.mmoItem, this.cachedEnchantments);
        }
    }
    
    void keepName(@NotNull final ItemMeta itemMeta) {
        if (this.mmoItem.hasData(ItemStats.NAME)) {
            this.cachedName = ((NameData)this.mmoItem.getData(ItemStats.NAME)).getMainName();
        }
        else if (itemMeta.hasDisplayName()) {
            this.cachedName = itemMeta.getDisplayName();
        }
    }
    
    void keepLore(@NotNull final String s) {
        if (!this.mmoItem.hasData(ItemStats.LORE)) {
            return;
        }
        this.cachedLore = this.extractLore(((StringListData)this.mmoItem.getData(ItemStats.LORE)).getList(), s);
    }
    
    @NotNull
    ArrayList<String> extractLore(@NotNull final List<String> list, @NotNull final String prefix) {
        final ArrayList<String> list2 = new ArrayList<String>();
        for (final String e : list) {
            if (e.startsWith(prefix)) {
                list2.add(e);
            }
        }
        return list2;
    }
    
    void keepEnchantments(@NotNull final Ref<EnchantListData> ref) {
        this.cachedEnchantments = new EnchantListData();
        if (!this.mmoItem.hasData(ItemStats.ENCHANTS)) {
            this.mmoItem.setData(ItemStats.ENCHANTS, new EnchantListData());
        }
        Enchants.separateEnchantments(this.mmoItem);
        final StatHistory from = StatHistory.from(this.mmoItem, ItemStats.ENCHANTS);
        ref.setValue((Object)((Mergeable)from.getOriginalData()).cloneData());
        for (final StatData statData : from.getExternalData()) {
            if (statData instanceof EnchantListData) {
                ((Mergeable)statData).merge(this.cachedEnchantments);
                for (final Enchantment enchantment : ((EnchantListData)statData).getEnchants()) {
                    this.cachedEnchantments.addEnchant(enchantment, ((EnchantListData)statData).getLevel(enchantment));
                }
            }
        }
        from.getExternalData().clear();
    }
    
    void keepUpgrades() {
        this.cachedUpgradeLevel = (UpgradeData)this.mmoItem.getData(ItemStats.UPGRADE);
    }
    
    void cacheFullHistory(final boolean b, final boolean b2) {
        if (this.mmoItem.hasData(ItemStats.GEM_SOCKETS) && !b) {
            this.cachedGemStones = (GemSocketsData)this.mmoItem.getData(ItemStats.GEM_SOCKETS);
        }
        final Iterator<StatHistory> iterator = this.mmoItem.getStatHistories().iterator();
        while (iterator.hasNext()) {
            final StatHistory clone = iterator.next().clone(this.mmoItem);
            if (b2) {
                clone.clearExternalData();
            }
            clone.clearModifiersBonus();
            this.itemDataHistory.put(clone.getItemStat(), clone);
        }
    }
    
    void keepSoulbound() {
        this.cachedSoulbound = this.mmoItem.getData(ItemStats.SOULBOUND);
    }
    
    public boolean hasChanges() {
        return this.mmoItem != null;
    }
    
    public ItemStack toStack() {
        final MMOItem clone = this.mmoItem.clone();
        if (this.cachedUpgradeLevel != null && clone.hasData(ItemStats.UPGRADE)) {
            final UpgradeData upgradeData = (UpgradeData)clone.getData(ItemStats.UPGRADE);
            final UpgradeData upgradeData2 = new UpgradeData(upgradeData.getReference(), upgradeData.getTemplateName(), upgradeData.isWorkbench(), upgradeData.isDestroy(), upgradeData.getMax(), upgradeData.getSuccess());
            upgradeData2.setLevel(Math.min(this.cachedUpgradeLevel.getLevel(), upgradeData.getMaxUpgrades()));
            clone.setData(ItemStats.UPGRADE, upgradeData2);
        }
        final int upgradeLevel = this.mmoItem.getUpgradeLevel();
        for (final ItemStat itemStat : this.itemDataHistory.keySet()) {
            final StatHistory statHistory = this.itemDataHistory.get(itemStat);
            if (statHistory == null) {
                continue;
            }
            final StatHistory from = StatHistory.from(clone, itemStat);
            statHistory.clearModifiersBonus();
            from.getExternalData().clear();
            from.assimilate(statHistory);
            clone.setData(from.getItemStat(), from.recalculate(false, upgradeLevel));
        }
        if (this.cachedSoulbound != null) {
            clone.setData(ItemStats.SOULBOUND, this.cachedSoulbound);
        }
        if (this.cachedEnchantments != null) {
            final StatHistory from2 = StatHistory.from(clone, ItemStats.ENCHANTS);
            from2.registerExternalData(this.cachedEnchantments.cloneData());
            clone.setData(ItemStats.ENCHANTS, from2.recalculate(this.mmoItem.getUpgradeLevel()));
        }
        if (this.cachedGemStones != null) {
            final ArrayList<GemstoneData> list = new ArrayList<GemstoneData>();
            if (clone.hasData(ItemStats.GEM_SOCKETS)) {
                final GemSocketsData gemSocketsData = (GemSocketsData)clone.getData(ItemStats.GEM_SOCKETS);
                final ArrayList<GemstoneData> list2 = new ArrayList<GemstoneData>();
                final ArrayList list3 = new ArrayList<String>(gemSocketsData.getEmptySlots());
                for (final GemstoneData e : new ArrayList<GemstoneData>(this.cachedGemStones.getGemstones())) {
                    if (list3.size() <= 0) {
                        list.add(e);
                    }
                    else {
                        String s = e.getSocketColor();
                        if (s == null) {
                            s = GemSocketsData.getUncoloredGemSlot();
                        }
                        String s2 = null;
                        for (final String anObject : list3) {
                            if (anObject.equals(GemSocketsData.getUncoloredGemSlot()) || s.equals(anObject)) {
                                s2 = anObject;
                            }
                        }
                        if (s2 != null) {
                            list3.remove(s2);
                            e.setColour(s2);
                            list2.add(e);
                        }
                        else {
                            list.add(e);
                        }
                    }
                }
                final GemSocketsData originalData = new GemSocketsData((List<String>)list3);
                for (final GemstoneData gemstoneData : list2) {
                    if (gemstoneData == null) {
                        continue;
                    }
                    originalData.add(gemstoneData);
                }
                final StatHistory from3 = StatHistory.from(clone, ItemStats.GEM_SOCKETS);
                from3.setOriginalData(originalData);
                clone.setData(ItemStats.GEM_SOCKETS, from3.recalculate(upgradeLevel));
            }
            else {
                list.addAll(this.cachedGemStones.getGemstones());
            }
            if (ReforgeOptions.dropRestoredGems) {
                final Iterator<GemstoneData> iterator5 = list.iterator();
                while (iterator5.hasNext()) {
                    final MMOItem gemstone = clone.extractGemstone(iterator5.next());
                    if (gemstone != null) {
                        this.destroyedGems.add(gemstone);
                    }
                }
            }
        }
        if (!this.cachedLore.isEmpty()) {
            if (clone.hasData(ItemStats.LORE)) {
                this.cachedLore.addAll(new ArrayList<String>(((StringListData)clone.getData(ItemStats.LORE)).getList()));
            }
            clone.setData(ItemStats.LORE, new StringListData(this.cachedLore));
        }
        if (this.cachedName != null) {
            final StatHistory from4 = StatHistory.from(clone, ItemStats.NAME);
            ((NameData)from4.getOriginalData()).setString(this.cachedName);
            clone.setData(ItemStats.NAME, from4.recalculate(clone.getUpgradeLevel()));
        }
        if (clone.hasUpgradeTemplate()) {
            clone.getUpgradeTemplate().upgradeTo(clone, clone.getUpgradeLevel());
        }
        if (this.cachedDurability != null) {
            this.mmoItem.setData(ItemStats.DURABILITY, this.cachedDurability);
        }
        final ItemStack build = clone.newBuilder().build();
        build.setAmount(this.amount);
        if (this.cachedDur != null && build.getItemMeta() instanceof Damageable) {
            ((Damageable)build.getItemMeta()).setDamage(SilentNumbers.floor(this.cachedDur * build.getType().getMaxDurability()));
        }
        return build;
    }
    
    private void loadLiveMMOItem() {
        if (this.mmoItem != null && this.mmoItem instanceof LiveMMOItem) {
            return;
        }
        this.mmoItem = new LiveMMOItem(this.nbtItem);
    }
    
    private void loadVolatileMMOItem() {
        if (this.mmoItem != null) {
            return;
        }
        this.mmoItem = new VolatileMMOItem(this.nbtItem);
    }
    
    @NotNull
    public ArrayList<MMOItem> getDestroyedGems() {
        return this.destroyedGems;
    }
    
    static {
        MMOItemReforger.autoSoulboundLevel = 1;
        MMOItemReforger.defaultItemLevel = -32767;
        MMOItemReforger.keepTiersWhenReroll = true;
    }
}
