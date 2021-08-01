// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.util.Iterator;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeFinishEvent;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.MMOItemReforgeEvent;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;
import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class MMOItemReforger
{
    @NotNull
    final ItemStack stack;
    @Nullable
    ItemStack result;
    @NotNull
    final NBTItem nbtItem;
    @NotNull
    final ItemMeta meta;
    @Nullable
    RPGPlayer player;
    @Nullable
    LiveMMOItem oldMMOItem;
    @Nullable
    private MMOItemTemplate template;
    @Nullable
    private MMOItem freshMMOItem;
    @Nullable
    Boolean canUpdate;
    @Nullable
    Boolean shouldUpdate;
    @NotNull
    final ArrayList<ItemStack> reforgingOutput;
    int generationItemLevel;
    public static int autoSoulbindLevel;
    public static int defaultItemLevel;
    public static boolean keepTiersWhenReroll;
    
    public MMOItemReforger(@NotNull final ItemStack stack) {
        this.reforgingOutput = new ArrayList<ItemStack>();
        this.stack = stack;
        this.nbtItem = NBTItem.get(stack);
        Validate.isTrue(stack.getItemMeta() != null, "ItemStack has no ItemMeta, cannot be reforged.");
        this.meta = stack.getItemMeta();
    }
    
    public MMOItemReforger(@NotNull final NBTItem nbtItem) {
        this.reforgingOutput = new ArrayList<ItemStack>();
        this.nbtItem = nbtItem;
        this.stack = nbtItem.getItem();
        Validate.isTrue(this.stack.getItemMeta() != null, "ItemStack has no ItemMeta, cannot be reforged.");
        this.meta = this.stack.getItemMeta();
    }
    
    public MMOItemReforger(@NotNull final ItemStack stack, @NotNull final NBTItem nbtItem) {
        this.reforgingOutput = new ArrayList<ItemStack>();
        this.stack = stack;
        this.nbtItem = nbtItem;
        Validate.isTrue(stack.getItemMeta() != null, "ItemStack has no ItemMeta, cannot be reforged.");
        this.meta = stack.getItemMeta();
    }
    
    @NotNull
    public ItemStack getStack() {
        return this.stack;
    }
    
    @Nullable
    public ItemStack getResult() {
        return this.result;
    }
    
    public void setResult(@Nullable final ItemStack result) {
        this.result = result;
    }
    
    @NotNull
    public NBTItem getNBTItem() {
        return this.nbtItem;
    }
    
    @NotNull
    public ItemMeta getMeta() {
        return this.meta;
    }
    
    @Nullable
    public RPGPlayer getPlayer() {
        return this.player;
    }
    
    public void setPlayer(@Nullable final RPGPlayer player) {
        this.player = player;
    }
    
    public void setPlayer(@Nullable final Player player) {
        if (player == null) {
            this.player = null;
            return;
        }
        this.player = PlayerData.get((OfflinePlayer)player).getRPG();
    }
    
    @NotNull
    public LiveMMOItem getOldMMOItem() {
        return this.oldMMOItem;
    }
    
    @NotNull
    public MMOItemTemplate getTemplate() {
        return this.template;
    }
    
    @NotNull
    public MMOItem getFreshMMOItem() {
        return this.freshMMOItem;
    }
    
    public void setFreshMMOItem(@NotNull final MMOItem freshMMOItem) {
        this.freshMMOItem = freshMMOItem;
    }
    
    public boolean canReforge() {
        if (this.canUpdate != null) {
            return this.canUpdate;
        }
        if (!this.getNBTItem().hasType()) {
            final Boolean value = false;
            this.canUpdate = value;
            return value;
        }
        this.template = MMOItems.plugin.getTemplates().getTemplate(this.getNBTItem());
        if (this.template == null) {
            final Boolean value2 = false;
            this.canUpdate = value2;
            return value2;
        }
        final Boolean value3 = true;
        this.canUpdate = value3;
        return value3;
    }
    
    public boolean shouldReforge(@Nullable final String str) {
        if (this.shouldUpdate != null) {
            return this.shouldUpdate;
        }
        if (!this.canReforge()) {
            final Boolean value = false;
            this.shouldUpdate = value;
            return value;
        }
        if (str != null && MMOItems.plugin.getConfig().getBoolean("item-revision.disable-on." + str)) {
            final Boolean value2 = false;
            this.shouldUpdate = value2;
            return value2;
        }
        if (this.getTemplate().getRevisionId() > (this.getNBTItem().hasTag(ItemStats.REVISION_ID.getNBTPath()) ? this.getNBTItem().getInteger(ItemStats.REVISION_ID.getNBTPath()) : 1)) {
            final Boolean value3 = true;
            this.shouldUpdate = value3;
            return value3;
        }
        if (1 > (this.nbtItem.hasTag(ItemStats.INTERNAL_REVISION_ID.getNBTPath()) ? this.nbtItem.getInteger(ItemStats.INTERNAL_REVISION_ID.getNBTPath()) : 1)) {
            final Boolean value4 = true;
            this.shouldUpdate = value4;
            return value4;
        }
        final Boolean value5 = false;
        this.shouldUpdate = value5;
        return value5;
    }
    
    public void addReforgingOutput(@Nullable final ItemStack e) {
        if (SilentNumbers.isAir(e)) {
            return;
        }
        if (!e.getType().isItem()) {
            return;
        }
        this.reforgingOutput.add(e);
    }
    
    public void clearReforgingOutput() {
        this.reforgingOutput.clear();
    }
    
    @NotNull
    public ArrayList<ItemStack> getReforgingOutput() {
        return this.reforgingOutput;
    }
    
    public int getGenerationItemLevel() {
        return this.generationItemLevel;
    }
    
    public boolean reforge(@NotNull final ReforgeOptions reforgeOptions) {
        if (!this.canReforge()) {
            throw new IllegalArgumentException("Unreforgable Item " + SilentNumbers.getItemName(this.getStack()));
        }
        this.oldMMOItem = new LiveMMOItem(this.getNBTItem());
        if (reforgeOptions.isBlacklisted(this.getOldMMOItem().getId())) {
            return false;
        }
        final int defaultItemLevel = MMOItemReforger.defaultItemLevel;
        this.generationItemLevel = ((defaultItemLevel == -32767) ? (this.getOldMMOItem().hasData(ItemStats.ITEM_LEVEL) ? ((int)((DoubleData)this.getOldMMOItem().getData(ItemStats.ITEM_LEVEL)).getValue()) : 0) : defaultItemLevel);
        this.setFreshMMOItem(this.getTemplate().newBuilder(this.generationItemLevel, (MMOItemReforger.keepTiersWhenReroll && this.getOldMMOItem().hasData(ItemStats.TIER)) ? MMOItems.plugin.getTiers().get(this.getOldMMOItem().getData(ItemStats.TIER).toString()) : null).build());
        final MMOItemReforgeEvent mmoItemReforgeEvent = new MMOItemReforgeEvent(this, reforgeOptions);
        Bukkit.getPluginManager().callEvent((Event)mmoItemReforgeEvent);
        if (mmoItemReforgeEvent.isCancelled()) {
            return false;
        }
        for (final StatHistory statHistory : this.getFreshMMOItem().getStatHistories()) {
            this.getFreshMMOItem().setData(statHistory.getItemStat(), statHistory.recalculate(this.getFreshMMOItem().getUpgradeLevel()));
        }
        if (this.getFreshMMOItem().hasUpgradeTemplate()) {
            final Iterator<ItemStat> iterator2 = this.getFreshMMOItem().getUpgradeTemplate().getKeys().iterator();
            while (iterator2.hasNext()) {
                final StatHistory from = StatHistory.from(this.getFreshMMOItem(), iterator2.next());
                this.getFreshMMOItem().setData(from.getItemStat(), from.recalculate(this.getFreshMMOItem().getUpgradeLevel()));
            }
        }
        this.result = this.getFreshMMOItem().newBuilder().build();
        final MMOItemReforgeFinishEvent mmoItemReforgeFinishEvent = new MMOItemReforgeFinishEvent(this.result, this, reforgeOptions);
        Bukkit.getPluginManager().callEvent((Event)mmoItemReforgeFinishEvent);
        this.setResult(mmoItemReforgeFinishEvent.getFinishedItem());
        return !mmoItemReforgeFinishEvent.isCancelled();
    }
    
    public static void reload() {
        MMOItemReforger.autoSoulbindLevel = MMOItems.plugin.getConfig().getInt("soulbound.auto-bind.level", 1);
        MMOItemReforger.defaultItemLevel = MMOItems.plugin.getConfig().getInt("item-revision.default-item-level", -32767);
        MMOItemReforger.keepTiersWhenReroll = MMOItems.plugin.getConfig().getBoolean("item-revision.keep-tiers");
    }
    
    @Deprecated
    public void update(@Nullable final Player player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.reforge(reforgeOptions);
    }
    
    @Deprecated
    public void update(@Nullable final RPGPlayer player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.reforge(reforgeOptions);
    }
    
    @Deprecated
    void regenerate(@Nullable final RPGPlayer player) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.reforge(new ReforgeOptions(new boolean[] { false, false, false, false, false, false, false, true }));
    }
    
    @Deprecated
    int regenerate(@Nullable final RPGPlayer player, @NotNull final MMOItemTemplate template) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.canUpdate = true;
        this.template = template;
        this.reforge(new ReforgeOptions(new boolean[] { false, false, false, false, false, false, false, true }));
        return 0;
    }
    
    @Deprecated
    public void reforge(@Nullable final Player player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.reforge(reforgeOptions);
    }
    
    @Deprecated
    public void reforge(@Nullable final RPGPlayer player, @NotNull final ReforgeOptions reforgeOptions) {
        if (player != null) {
            this.setPlayer(player);
        }
        this.reforge(reforgeOptions);
    }
    
    @Deprecated
    public ItemStack toStack() {
        return this.getResult();
    }
    
    @Deprecated
    public boolean hasChanges() {
        return this.getResult() != null;
    }
    
    @Deprecated
    @NotNull
    public ArrayList<MMOItem> getDestroyedGems() {
        return new ArrayList<MMOItem>();
    }
    
    static {
        MMOItemReforger.autoSoulbindLevel = 1;
        MMOItemReforger.defaultItemLevel = -32767;
        MMOItemReforger.keepTiersWhenReroll = true;
    }
}
