// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import net.Indyuce.mmoitems.stat.data.StringListData;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackCategory;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import com.google.gson.JsonParser;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.lumine.mythic.lib.api.item.ItemTag;
import com.google.gson.JsonObject;
import net.Indyuce.mmoitems.stat.data.type.UpgradeInfo;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import java.util.List;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.Collection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.jetbrains.annotations.NotNull;

public class StatHistory
{
    @NotNull
    private final ItemStat itemStat;
    @NotNull
    MMOItem parent;
    @NotNull
    StatData originalData;
    @NotNull
    public HashMap<UUID, StatData> perModifierBonus;
    @NotNull
    public HashMap<UUID, StatData> perGemstoneData;
    @NotNull
    ArrayList<StatData> perExternalData;
    static final String enc_Stat = "Stat";
    static final String enc_OGS = "OGStory";
    static final String enc_GSS = "Gemstory";
    static final String enc_EXS = "Exstory";
    static final String enc_MOD = "Mod";
    
    @NotNull
    public ItemStat getItemStat() {
        return this.itemStat;
    }
    
    public boolean isClear() {
        return (!(this.getOriginalData() instanceof EnchantListData) || ((EnchantListData)this.getOriginalData()).getEnchants().size() == 0) && this.getAllGemstones().size() <= 0 && this.getExternalData().size() <= 0 && this.getAllModifiers().size() <= 0 && ((((Mergeable)this.getOriginalData()).isClear() && (!this.isUpgradeable() || this.getMMOItem().getUpgradeLevel() == 0)) || this.getOriginalData().equals(this.getMMOItem().getData(this.getItemStat())));
    }
    
    @NotNull
    public MMOItem getMMOItem() {
        return this.parent;
    }
    
    @NotNull
    public StatData getOriginalData() {
        if (this.originalData == null) {
            this.setOriginalData(this.getItemStat().getClearStatData());
            MMOItems.print(null, "Stat History for $e{0}$b in $u{1} {2}$b had null original data.", null, this.getItemStat().getId(), this.getMMOItem().getType().toString(), this.getMMOItem().getId());
        }
        return this.originalData;
    }
    
    public void setOriginalData(@NotNull final StatData originalData) {
        this.originalData = originalData;
    }
    
    @Contract("null -> null")
    @Nullable
    public StatData getModifiersBonus(@Nullable final UUID key) {
        if (key == null) {
            return null;
        }
        return this.perModifierBonus.get(key);
    }
    
    public void registerModifierBonus(@NotNull final UUID key, @NotNull final StatData value) {
        this.perModifierBonus.put(key, value);
    }
    
    public void removeModifierBonus(@NotNull final UUID key) {
        this.perModifierBonus.remove(key);
    }
    
    @NotNull
    public ArrayList<UUID> getAllModifiers() {
        return new ArrayList<UUID>(this.perModifierBonus.keySet());
    }
    
    public void clearModifiersBonus() {
        this.perModifierBonus.clear();
    }
    
    @Contract("null -> null")
    @Nullable
    public StatData getGemstoneData(@Nullable final UUID key) {
        if (key == null) {
            return null;
        }
        return this.perGemstoneData.get(key);
    }
    
    public void removeGemData(@NotNull final UUID key) {
        this.perGemstoneData.remove(key);
    }
    
    @NotNull
    public ArrayList<UUID> getAllGemstones() {
        return new ArrayList<UUID>(this.perGemstoneData.keySet());
    }
    
    public void registerGemstoneData(@NotNull final UUID key, @NotNull final StatData value) {
        this.perGemstoneData.put(key, value);
    }
    
    public void clearGemstones() {
        this.perGemstoneData.clear();
    }
    
    @NotNull
    public ArrayList<StatData> getExternalData() {
        return this.perExternalData;
    }
    
    public void consolidateEXSH() {
        final StatData clearStatData = this.getItemStat().getClearStatData();
        for (final StatData statData : this.getExternalData()) {
            if (statData == null) {
                continue;
            }
            ((Mergeable)clearStatData).merge(statData);
        }
        this.getExternalData().clear();
        this.registerExternalData(clearStatData);
    }
    
    public void registerExternalData(@NotNull final StatData e) {
        this.perExternalData.add(e);
    }
    
    public void clearExternalData() {
        this.perExternalData.clear();
    }
    
    @NotNull
    public static StatHistory from(@NotNull final MMOItem mmoItem, @NotNull final ItemStat itemStat) {
        return from(mmoItem, itemStat, false);
    }
    
    @NotNull
    public static StatHistory from(@NotNull final MMOItem mmoItem, @NotNull final ItemStat itemStat, final boolean b) {
        if (!b) {
            final StatHistory statHistory = mmoItem.getStatHistory(itemStat);
            if (statHistory != null) {
                return statHistory;
            }
        }
        Validate.isTrue(itemStat.getClearStatData() instanceof Mergeable, "Non-Mergeable stat data wont have a Stat History; they cannot be modified dynamically in the first place.");
        final StatData data = mmoItem.getData(itemStat);
        StatData statData;
        if (data == null) {
            statData = itemStat.getClearStatData();
            mmoItem.setData(itemStat, statData);
        }
        else {
            statData = ((Mergeable)data).cloneData();
        }
        final StatHistory statHistory2 = new StatHistory(mmoItem, itemStat, statData);
        mmoItem.setStatHistory(itemStat, statHistory2);
        return statHistory2;
    }
    
    public StatHistory(@NotNull final MMOItem parent, @NotNull final ItemStat itemStat, @NotNull final StatData originalData) {
        this.perModifierBonus = new HashMap<UUID, StatData>();
        this.perGemstoneData = new HashMap<UUID, StatData>();
        this.perExternalData = new ArrayList<StatData>();
        this.itemStat = itemStat;
        this.originalData = originalData;
        this.parent = parent;
    }
    
    public void purgeGemstones() {
        final ArrayList<UUID> list = new ArrayList<UUID>();
        GemSocketsData gemSocketsData = (GemSocketsData)this.getMMOItem().getData(ItemStats.GEM_SOCKETS);
        if (gemSocketsData == null) {
            gemSocketsData = new GemSocketsData(new ArrayList<String>());
        }
        for (final UUID e : this.perGemstoneData.keySet()) {
            boolean b = false;
            for (final GemstoneData gemstoneData : gemSocketsData.getGemstones()) {
                if (gemstoneData != null && e.equals(gemstoneData.getHistoricUUID())) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                list.add(e);
            }
        }
        final Iterator<UUID> iterator3 = list.iterator();
        while (iterator3.hasNext()) {
            this.removeGemData(iterator3.next());
        }
    }
    
    public boolean isUpgradeable() {
        return this.getMMOItem().hasUpgradeTemplate() && this.getMMOItem().getUpgradeTemplate().getUpgradeInfo(this.getItemStat()) != null;
    }
    
    @NotNull
    public StatData recalculate(final int n) {
        return this.recalculate(true, n);
    }
    
    @NotNull
    public StatData recalculate(final boolean b, final int n) {
        if (b) {
            this.purgeGemstones();
        }
        if (n != 0 && this.getItemStat() instanceof Upgradable && this.getMMOItem().hasUpgradeTemplate()) {
            return this.recalculateUpgradeable(n);
        }
        return this.recalculateMergeable();
    }
    
    @NotNull
    public StatData recalculateUnupgraded() {
        return this.recalculateUnupgraded(true);
    }
    
    @NotNull
    public StatData recalculateUnupgraded(final boolean b) {
        if (b) {
            this.purgeGemstones();
        }
        return this.recalculateMergeable();
    }
    
    private StatData recalculateUpgradeable(final int n) {
        final UpgradeInfo upgradeInfo = this.getMMOItem().getUpgradeTemplate().getUpgradeInfo(this.getItemStat());
        if (upgradeInfo == null) {
            return this.recalculateMergeable();
        }
        final StatData cloneData = ((Mergeable)this.originalData).cloneData();
        final Iterator<UUID> iterator = this.perModifierBonus.keySet().iterator();
        while (iterator.hasNext()) {
            ((Mergeable)cloneData).merge(((Mergeable)this.getModifiersBonus(iterator.next())).cloneData());
        }
        final StatData apply = ((Upgradable)this.getItemStat()).apply(cloneData, upgradeInfo, n);
        for (final UUID obj : this.perGemstoneData.keySet()) {
            int intValue = 0;
            for (final GemstoneData gemstoneData : this.getMMOItem().getGemStones()) {
                if (gemstoneData == null) {
                    continue;
                }
                if (!gemstoneData.getHistoricUUID().equals(obj)) {
                    continue;
                }
                if (gemstoneData.isScaling()) {
                    intValue = gemstoneData.getLevel();
                }
                else {
                    intValue = n;
                }
            }
            ((Mergeable)apply).merge(((Mergeable)((Upgradable)this.getItemStat()).apply(((Mergeable)this.getGemstoneData(obj)).cloneData(), upgradeInfo, n - intValue)).cloneData());
        }
        final Iterator<StatData> iterator4 = this.getExternalData().iterator();
        while (iterator4.hasNext()) {
            ((Mergeable)apply).merge(((Mergeable)iterator4.next()).cloneData());
        }
        return apply;
    }
    
    private StatData recalculateMergeable() {
        final StatData cloneData = ((Mergeable)this.getOriginalData()).cloneData();
        final Iterator<StatData> iterator = this.perModifierBonus.values().iterator();
        while (iterator.hasNext()) {
            ((Mergeable)cloneData).merge(((Mergeable)iterator.next()).cloneData());
        }
        final Iterator<StatData> iterator2 = this.perGemstoneData.values().iterator();
        while (iterator2.hasNext()) {
            ((Mergeable)cloneData).merge(((Mergeable)iterator2.next()).cloneData());
        }
        final Iterator<StatData> iterator3 = this.getExternalData().iterator();
        while (iterator3.hasNext()) {
            ((Mergeable)cloneData).merge(((Mergeable)iterator3.next()).cloneData());
        }
        return cloneData;
    }
    
    @NotNull
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Stat", this.getItemStat().getId());
        if (!((Mergeable)this.getOriginalData()).isClear()) {
            jsonObject.add("OGStory", (JsonElement)ItemTag.compressTags((ArrayList)this.getItemStat().getAppliedNBT(this.getOriginalData())));
        }
        final JsonArray jsonArray = new JsonArray();
        for (final UUID uuid : this.getAllGemstones()) {
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.add(uuid.toString(), (JsonElement)ItemTag.compressTags((ArrayList)this.getItemStat().getAppliedNBT(this.getGemstoneData(uuid))));
            jsonArray.add((JsonElement)jsonObject2);
        }
        if (jsonArray.size() > 0) {
            jsonObject.add("Gemstory", (JsonElement)jsonArray);
        }
        final JsonArray jsonArray2 = new JsonArray();
        for (final StatData statData : this.getExternalData()) {
            if (((Mergeable)statData).isClear()) {
                continue;
            }
            jsonArray2.add((JsonElement)ItemTag.compressTags((ArrayList)this.getItemStat().getAppliedNBT(statData)));
        }
        if (jsonArray2.size() > 0) {
            jsonObject.add("Exstory", (JsonElement)jsonArray2);
        }
        final JsonArray jsonArray3 = new JsonArray();
        for (final UUID uuid2 : this.getAllModifiers()) {
            final JsonObject jsonObject3 = new JsonObject();
            jsonObject3.add(uuid2.toString(), (JsonElement)ItemTag.compressTags((ArrayList)this.getItemStat().getAppliedNBT(this.getModifiersBonus(uuid2))));
            jsonArray3.add((JsonElement)jsonObject3);
        }
        if (jsonArray3.size() > 0) {
            jsonObject.add("Mod", (JsonElement)jsonArray3);
        }
        return jsonObject;
    }
    
    @NotNull
    public String toNBTString() {
        return this.toJson().toString();
    }
    
    @Nullable
    public static StatHistory fromJson(@NotNull final MMOItem mmoItem, @NotNull final JsonObject jsonObject) {
        JsonElement value = null;
        JsonElement value2 = null;
        JsonElement value3 = null;
        JsonElement value4 = null;
        if (!jsonObject.has("Stat")) {
            return null;
        }
        final JsonElement value5 = jsonObject.get("Stat");
        if (jsonObject.has("OGStory")) {
            value = jsonObject.get("OGStory");
        }
        if (jsonObject.has("Gemstory")) {
            value2 = jsonObject.get("Gemstory");
        }
        if (jsonObject.has("Exstory")) {
            value3 = jsonObject.get("Exstory");
        }
        if (jsonObject.has("Mod")) {
            value4 = jsonObject.get("Mod");
        }
        if (!value5.isJsonPrimitive()) {
            return null;
        }
        if (value != null && !value.isJsonArray()) {
            return null;
        }
        if (value2 != null && !value2.isJsonArray()) {
            return null;
        }
        if (value3 != null && !value3.isJsonArray()) {
            return null;
        }
        if (value4 != null && !value4.isJsonArray()) {
            return null;
        }
        final ItemStat value6 = MMOItems.plugin.getStats().get(value5.getAsJsonPrimitive().getAsString());
        if (value6 == null) {
            return null;
        }
        StatData statData;
        if (value != null) {
            statData = value6.getLoadedNBT(ItemTag.decompressTags(value.getAsJsonArray()));
        }
        else {
            statData = value6.getClearStatData();
        }
        if (statData == null) {
            return null;
        }
        final StatHistory statHistory = new StatHistory(mmoItem, value6, statData);
        if (value2 != null) {
            for (final JsonElement jsonElement : value2.getAsJsonArray()) {
                if (jsonElement.isJsonObject()) {
                    for (final Map.Entry<String, V> entry : jsonElement.getAsJsonObject().entrySet()) {
                        final UUID uuidFromString = MMOUtils.UUIDFromString(entry.getKey());
                        final JsonElement jsonElement2 = (JsonElement)entry.getValue();
                        if (jsonElement2.isJsonArray() && uuidFromString != null) {
                            final StatData loadedNBT = value6.getLoadedNBT(ItemTag.decompressTags(jsonElement2.getAsJsonArray()));
                            if (loadedNBT == null) {
                                continue;
                            }
                            statHistory.registerGemstoneData(uuidFromString, loadedNBT);
                        }
                    }
                }
            }
        }
        if (value3 != null) {
            for (final JsonElement jsonElement3 : value3.getAsJsonArray()) {
                if (jsonElement3.isJsonArray()) {
                    final StatData loadedNBT2 = value6.getLoadedNBT(ItemTag.decompressTags(jsonElement3.getAsJsonArray()));
                    if (loadedNBT2 == null) {
                        continue;
                    }
                    statHistory.registerExternalData(loadedNBT2);
                }
            }
        }
        if (value4 != null) {
            for (final JsonElement jsonElement4 : value4.getAsJsonArray()) {
                if (jsonElement4.isJsonObject()) {
                    for (final Map.Entry<String, V> entry2 : jsonElement4.getAsJsonObject().entrySet()) {
                        final UUID uuidFromString2 = MMOUtils.UUIDFromString(entry2.getKey());
                        final JsonElement jsonElement5 = (JsonElement)entry2.getValue();
                        if (jsonElement5.isJsonArray() && uuidFromString2 != null) {
                            final StatData loadedNBT3 = value6.getLoadedNBT(ItemTag.decompressTags(jsonElement5.getAsJsonArray()));
                            if (loadedNBT3 == null) {
                                continue;
                            }
                            statHistory.registerModifierBonus(uuidFromString2, loadedNBT3);
                        }
                    }
                }
            }
        }
        return statHistory;
    }
    
    @Nullable
    public static StatHistory fromNBTString(@NotNull final MMOItem mmoItem, @NotNull final String s) {
        try {
            return fromJson(mmoItem, new JsonParser().parse(s).getAsJsonObject());
        }
        catch (Throwable t) {
            final FriendlyFeedbackProvider friendlyFeedbackProvider = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get());
            friendlyFeedbackProvider.activatePrefix(true, "Stat History");
            friendlyFeedbackProvider.log(FriendlyFeedbackCategory.ERROR, "Could not get stat history: $f{0}$b at $f{1}", new String[] { t.getMessage(), t.getStackTrace()[0].toString() });
            friendlyFeedbackProvider.sendTo(FriendlyFeedbackCategory.ERROR, MMOItems.getConsole());
            return null;
        }
    }
    
    public void assimilate(@NotNull final StatHistory statHistory) {
        if (statHistory.getItemStat().getNBTPath().equals(this.getItemStat().getNBTPath())) {
            for (final UUID uuid : statHistory.getAllGemstones()) {
                this.registerGemstoneData(uuid, statHistory.getGemstoneData(uuid));
            }
            final Iterator<StatData> iterator2 = statHistory.getExternalData().iterator();
            while (iterator2.hasNext()) {
                this.registerExternalData(iterator2.next());
            }
            for (final UUID uuid2 : statHistory.getAllModifiers()) {
                this.registerModifierBonus(uuid2, statHistory.getModifiersBonus(uuid2));
            }
        }
    }
    
    public StatHistory clone(@NotNull final MMOItem mmoItem) {
        final StatHistory statHistory = new StatHistory(mmoItem, this.getItemStat(), ((Mergeable)this.getOriginalData()).cloneData());
        for (final UUID uuid : this.getAllGemstones()) {
            if (uuid == null) {
                continue;
            }
            final StatData gemstoneData = this.getGemstoneData(uuid);
            if (!(gemstoneData instanceof Mergeable)) {
                continue;
            }
            statHistory.registerGemstoneData(uuid, ((Mergeable)gemstoneData).cloneData());
        }
        for (final StatData statData : this.getExternalData()) {
            if (!(statData instanceof Mergeable)) {
                continue;
            }
            statHistory.registerExternalData(((Mergeable)statData).cloneData());
        }
        for (final UUID uuid2 : this.getAllModifiers()) {
            if (uuid2 == null) {
                continue;
            }
            final StatData modifiersBonus = this.getModifiersBonus(uuid2);
            if (!(modifiersBonus instanceof Mergeable)) {
                continue;
            }
            statHistory.registerModifierBonus(uuid2, ((Mergeable)modifiersBonus).cloneData());
        }
        return statHistory;
    }
    
    public void log() {
        MMOItems.print(null, "§6SH of §e" + this.getItemStat().getId() + "§7, §b" + this.getMMOItem().getType() + " " + this.getMMOItem().getId(), null, new String[0]);
        if (this.getOriginalData() instanceof StringListData) {
            MMOItems.print(null, "§a++ Original", null, new String[0]);
            final Iterator<String> iterator = ((StringListData)this.getOriginalData()).getList().iterator();
            while (iterator.hasNext()) {
                MMOItems.print(null, "§a ++§7 " + iterator.next(), null, new String[0]);
            }
            MMOItems.print(null, "§e++ Gemstones", null, new String[0]);
            final Iterator<UUID> iterator2 = this.getAllGemstones().iterator();
            while (iterator2.hasNext()) {
                final StatData gemstoneData = this.getGemstoneData(iterator2.next());
                if (!(gemstoneData instanceof StringListData)) {
                    continue;
                }
                final Iterator<String> iterator3 = ((StringListData)gemstoneData).getList().iterator();
                while (iterator3.hasNext()) {
                    MMOItems.print(null, "§e ++§7 " + iterator3.next(), null, new String[0]);
                }
            }
            MMOItems.print(null, "§c++ ExSH", null, new String[0]);
            for (final StatData statData : this.getExternalData()) {
                if (!(statData instanceof StringListData)) {
                    continue;
                }
                final Iterator<String> iterator5 = ((StringListData)statData).getList().iterator();
                while (iterator5.hasNext()) {
                    MMOItems.print(null, "§e ++§7 " + iterator5.next(), null, new String[0]);
                }
            }
            MMOItems.print(null, "§d++ Modifiers", null, new String[0]);
            final Iterator<UUID> iterator6 = this.getAllModifiers().iterator();
            while (iterator6.hasNext()) {
                final StatData modifiersBonus = this.getModifiersBonus(iterator6.next());
                if (!(modifiersBonus instanceof StringListData)) {
                    continue;
                }
                final Iterator<String> iterator7 = ((StringListData)modifiersBonus).getList().iterator();
                while (iterator7.hasNext()) {
                    MMOItems.print(null, "§d ++§7 " + iterator7.next(), null, new String[0]);
                }
            }
        }
        else {
            MMOItems.print(null, "§a-- Original", null, new String[0]);
            MMOItems.print(null, "§a ++§7 " + this.getOriginalData(), null, new String[0]);
            MMOItems.print(null, "§e-- Gemstones", null, new String[0]);
            final Iterator<UUID> iterator8 = this.getAllGemstones().iterator();
            while (iterator8.hasNext()) {
                final StatData gemstoneData2 = this.getGemstoneData(iterator8.next());
                if (gemstoneData2 == null) {
                    continue;
                }
                MMOItems.print(null, "§e ++§7 " + gemstoneData2, null, new String[0]);
            }
            MMOItems.print(null, "§c-- ExSH", null, new String[0]);
            for (final StatData obj : this.getExternalData()) {
                if (obj == null) {
                    continue;
                }
                MMOItems.print(null, "§e ++§7 " + obj, null, new String[0]);
            }
            MMOItems.print(null, "§d-- Modifiers", null, new String[0]);
            final Iterator<UUID> iterator10 = this.getAllModifiers().iterator();
            while (iterator10.hasNext()) {
                final StatData modifiersBonus2 = this.getModifiersBonus(iterator10.next());
                if (modifiersBonus2 == null) {
                    continue;
                }
                MMOItems.print(null, "§d ++§7 " + modifiersBonus2, null, new String[0]);
            }
        }
    }
}
