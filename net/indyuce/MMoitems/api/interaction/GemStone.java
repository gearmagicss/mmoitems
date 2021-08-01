// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import org.jetbrains.annotations.Nullable;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.GemUpgradeScaling;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import java.util.UUID;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import net.Indyuce.mmoitems.stat.Enchants;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.ApplyGemStoneEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.api.Type;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class GemStone extends UseItem
{
    public GemStone(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem);
    }
    
    @NotNull
    public ApplyResult applyOntoItem(@NotNull final NBTItem nbtItem, @NotNull final Type type) {
        return this.applyOntoItem(new LiveMMOItem(nbtItem), type, MMOUtils.getDisplayName(nbtItem.getItem()), true, false);
    }
    
    @NotNull
    public ApplyResult applyOntoItem(@NotNull final MMOItem mmoItem, @NotNull final Type type, @NotNull final String s, final boolean b, final boolean b2) {
        if (!mmoItem.hasData(ItemStats.GEM_SOCKETS)) {
            return new ApplyResult(ResultType.NONE);
        }
        final String string = this.getNBTItem().getString(ItemStats.GEM_COLOR.getNBTPath());
        final String emptySocket = ((GemSocketsData)mmoItem.getData(ItemStats.GEM_SOCKETS)).getEmptySocket(string);
        if (emptySocket == null) {
            return new ApplyResult(ResultType.NONE);
        }
        final String string2 = this.getNBTItem().getString(ItemStats.ITEM_TYPE_RESTRICTION.getNBTPath());
        if (!string2.equals("") && (!type.isWeapon() || !string2.contains("WEAPON")) && !string2.contains(type.getItemSet().name()) && !string2.contains(type.getId())) {
            return new ApplyResult(ResultType.NONE);
        }
        final double stat = this.getNBTItem().getStat(ItemStats.SUCCESS_RATE.getId());
        if (stat != 0.0 && GemStone.RANDOM.nextDouble() > stat / 100.0) {
            if (!b2) {
                this.player.playSound(this.player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                Message.GEM_STONE_BROKE.format(ChatColor.RED, "#gem#", MMOUtils.getDisplayName(this.getItem()), "#item#", s).send((CommandSender)this.player);
            }
            return new ApplyResult(ResultType.FAILURE);
        }
        final ApplyGemStoneEvent applyGemStoneEvent = new ApplyGemStoneEvent(this.playerData, this.mmoitem, mmoItem);
        Bukkit.getPluginManager().callEvent((Event)applyGemStoneEvent);
        if (applyGemStoneEvent.isCancelled()) {
            return new ApplyResult(ResultType.NONE);
        }
        Enchants.separateEnchantments(mmoItem);
        final LiveMMOItem liveMMOItem = new LiveMMOItem(this.getNBTItem());
        final GemstoneData gemstoneData = new GemstoneData(liveMMOItem, emptySocket);
        final StatHistory from = StatHistory.from(mmoItem, ItemStats.GEM_SOCKETS);
        if (((GemSocketsData)from.getOriginalData()).getEmptySocket(string) != null) {
            ((GemSocketsData)from.getOriginalData()).apply(string, gemstoneData);
        }
        else {
            boolean b3 = false;
            final Iterator<UUID> iterator = from.getAllModifiers().iterator();
            while (iterator.hasNext()) {
                final GemSocketsData gemSocketsData = (GemSocketsData)from.getModifiersBonus(iterator.next());
                if (gemSocketsData != null && gemSocketsData.getEmptySocket(string) != null) {
                    b3 = true;
                    gemSocketsData.apply(string, gemstoneData);
                }
            }
            if (!b3) {
                for (final GemSocketsData gemSocketsData2 : from.getExternalData()) {
                    if (gemSocketsData2 == null) {
                        continue;
                    }
                    if (gemSocketsData2.getEmptySocket(string) != null) {
                        gemSocketsData2.apply(string, gemstoneData);
                        break;
                    }
                }
            }
        }
        mmoItem.setData(ItemStats.GEM_SOCKETS, from.recalculate(mmoItem.getUpgradeLevel()));
        Integer level = null;
        String s2 = GemUpgradeScaling.defaultValue;
        if (liveMMOItem.hasData(ItemStats.GEM_UPGRADE_SCALING)) {
            s2 = liveMMOItem.getData(ItemStats.GEM_UPGRADE_SCALING).toString();
        }
        final String s3 = s2;
        switch (s3) {
            case "HISTORIC": {
                level = 0;
                break;
            }
            case "SUBSEQUENT": {
                level = mmoItem.getUpgradeLevel();
                break;
            }
        }
        gemstoneData.setLevel(level);
        for (final ItemStat itemStat : liveMMOItem.getStats()) {
            if (!(itemStat instanceof GemStoneStat)) {
                final StatData data = liveMMOItem.getData(itemStat);
                if (!(data instanceof Mergeable)) {
                    continue;
                }
                mmoItem.mergeData(itemStat, data, gemstoneData.getHistoricUUID());
            }
        }
        if (!b2) {
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
            Message.GEM_STONE_APPLIED.format(ChatColor.YELLOW, "#gem#", MMOUtils.getDisplayName(this.getItem()), "#item#", s).send((CommandSender)this.player);
        }
        if (b) {
            return new ApplyResult(mmoItem.newBuilder().build());
        }
        return new ApplyResult(mmoItem, ResultType.SUCCESS);
    }
    
    public static class ApplyResult
    {
        @NotNull
        private final ResultType type;
        @Nullable
        private final ItemStack result;
        @Nullable
        private final MMOItem resultAsMMOItem;
        
        public ApplyResult(@NotNull final ResultType resultType) {
            this((ItemStack)null, resultType);
        }
        
        public ApplyResult(@Nullable final ItemStack itemStack) {
            this(itemStack, ResultType.SUCCESS);
        }
        
        public ApplyResult(@Nullable final ItemStack result, @NotNull final ResultType type) {
            this.type = type;
            this.result = result;
            this.resultAsMMOItem = null;
        }
        
        public ApplyResult(@Nullable final MMOItem resultAsMMOItem, @NotNull final ResultType type) {
            this.type = type;
            this.result = null;
            this.resultAsMMOItem = resultAsMMOItem;
        }
        
        @NotNull
        public ResultType getType() {
            return this.type;
        }
        
        @Nullable
        public ItemStack getResult() {
            return this.result;
        }
        
        @Nullable
        public MMOItem getResultAsMMOItem() {
            return this.resultAsMMOItem;
        }
    }
    
    public enum ResultType
    {
        FAILURE, 
        NONE, 
        SUCCESS;
        
        private static /* synthetic */ ResultType[] $values() {
            return new ResultType[] { ResultType.FAILURE, ResultType.NONE, ResultType.SUCCESS };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
