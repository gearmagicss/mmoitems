// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import java.util.Collection;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import com.google.gson.JsonElement;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.Map;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class GemstoneData
{
    @NotNull
    private final Set<AbilityData> abilities;
    @NotNull
    private final List<PotionEffectData> effects;
    @NotNull
    private final Map<ItemStat, Double> stats;
    @NotNull
    private final String name;
    @Nullable
    Integer levelPut;
    @NotNull
    final UUID historicUUID;
    @Nullable
    final String mmoitemType;
    @Nullable
    final String mmoitemID;
    @Nullable
    String socketColor;
    
    public GemstoneData cloneGem() {
        final GemstoneData gemstoneData = new GemstoneData(this.getName(), this.getMMOItemType(), this.getMMOItemID(), this.getSocketColor(), this.getHistoricUUID());
        final Iterator<AbilityData> iterator = this.abilities.iterator();
        while (iterator.hasNext()) {
            gemstoneData.addAbility(iterator.next());
        }
        final Iterator<PotionEffectData> iterator2 = this.effects.iterator();
        while (iterator2.hasNext()) {
            gemstoneData.addPermanentEffect(iterator2.next());
        }
        for (final ItemStat itemStat : this.stats.keySet()) {
            gemstoneData.setStat(itemStat, this.stats.get(itemStat));
        }
        gemstoneData.setLevel(this.getLevel());
        return gemstoneData;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof GemstoneData && ((GemstoneData)o).getHistoricUUID().equals(this.getHistoricUUID());
    }
    
    @Nullable
    public String getMMOItemType() {
        return this.mmoitemType;
    }
    
    @Nullable
    public String getMMOItemID() {
        return this.mmoitemID;
    }
    
    @Nullable
    public String getSocketColor() {
        return this.socketColor;
    }
    
    public GemstoneData(@NotNull final JsonObject jsonObject) {
        this.abilities = new HashSet<AbilityData>();
        this.effects = new ArrayList<PotionEffectData>();
        this.stats = new HashMap<ItemStat, Double>();
        this.name = jsonObject.get("Name").getAsString();
        final JsonElement value = jsonObject.get("History");
        if (value != null) {
            final UUID uuidFromString = UUIDFromString(value.getAsString());
            if (uuidFromString != null) {
                this.historicUUID = uuidFromString;
            }
            else {
                this.historicUUID = UUID.randomUUID();
            }
            final JsonElement value2 = jsonObject.get("Type");
            final JsonElement value3 = jsonObject.get("Id");
            if (value2 != null) {
                this.mmoitemType = value2.getAsString();
            }
            else {
                this.mmoitemType = null;
            }
            if (value3 != null) {
                this.mmoitemID = value3.getAsString();
            }
            else {
                this.mmoitemID = null;
            }
            final JsonElement value4 = jsonObject.get("Level");
            if (value4 != null && value4.isJsonPrimitive()) {
                this.levelPut = value4.getAsJsonPrimitive().getAsInt();
            }
            else {
                this.levelPut = null;
            }
            final JsonElement value5 = jsonObject.get("Color");
            if (value5 != null && value5.isJsonPrimitive()) {
                this.socketColor = value5.getAsJsonPrimitive().getAsString();
            }
            else {
                this.socketColor = null;
            }
        }
        else {
            this.historicUUID = UUID.randomUUID();
            this.mmoitemID = null;
            this.mmoitemType = null;
            this.socketColor = null;
        }
    }
    
    public GemstoneData(@NotNull final LiveMMOItem liveMMOItem, @Nullable final String socketColor) {
        this.abilities = new HashSet<AbilityData>();
        this.effects = new ArrayList<PotionEffectData>();
        this.stats = new HashMap<ItemStat, Double>();
        this.name = MMOUtils.getDisplayName(liveMMOItem.getNBT().getItem());
        if (liveMMOItem.hasData(ItemStats.ABILITIES)) {
            this.abilities.addAll(((AbilityListData)liveMMOItem.getData(ItemStats.ABILITIES)).getAbilities());
        }
        if (liveMMOItem.hasData(ItemStats.PERM_EFFECTS)) {
            this.effects.addAll(((PotionEffectListData)liveMMOItem.getData(ItemStats.PERM_EFFECTS)).getEffects());
        }
        this.historicUUID = UUID.randomUUID();
        this.mmoitemID = liveMMOItem.getId();
        this.mmoitemType = liveMMOItem.getType().getId();
        this.socketColor = socketColor;
    }
    
    @Deprecated
    public GemstoneData(@NotNull final String name) {
        this.abilities = new HashSet<AbilityData>();
        this.effects = new ArrayList<PotionEffectData>();
        this.stats = new HashMap<ItemStat, Double>();
        this.name = name;
        this.mmoitemID = null;
        this.mmoitemType = null;
        this.socketColor = null;
        this.historicUUID = UUID.randomUUID();
    }
    
    public void setLevel(@Nullable final Integer levelPut) {
        this.levelPut = levelPut;
    }
    
    @Nullable
    public Integer getLevel() {
        return this.levelPut;
    }
    
    public boolean isScaling() {
        return this.levelPut != null;
    }
    
    public GemstoneData(@NotNull final String name, @Nullable final String mmoitemType, @Nullable final String mmoitemID, @Nullable final String socketColor) {
        this.abilities = new HashSet<AbilityData>();
        this.effects = new ArrayList<PotionEffectData>();
        this.stats = new HashMap<ItemStat, Double>();
        this.name = name;
        this.mmoitemID = mmoitemID;
        this.mmoitemType = mmoitemType;
        this.socketColor = socketColor;
        this.historicUUID = UUID.randomUUID();
    }
    
    public GemstoneData(@NotNull final String name, @Nullable final String mmoitemType, @Nullable final String mmoitemID, @Nullable final String socketColor, @NotNull final UUID historicUUID) {
        this.abilities = new HashSet<AbilityData>();
        this.effects = new ArrayList<PotionEffectData>();
        this.stats = new HashMap<ItemStat, Double>();
        this.name = name;
        this.mmoitemID = mmoitemID;
        this.mmoitemType = mmoitemType;
        this.socketColor = socketColor;
        this.historicUUID = historicUUID;
    }
    
    public void addAbility(@NotNull final AbilityData abilityData) {
        this.abilities.add(abilityData);
    }
    
    public void addPermanentEffect(@NotNull final PotionEffectData potionEffectData) {
        this.effects.add(potionEffectData);
    }
    
    public void setStat(@NotNull final ItemStat itemStat, final double d) {
        this.stats.put(itemStat, d);
    }
    
    @NotNull
    public String getName() {
        return this.name;
    }
    
    public void setColour(@Nullable final String socketColor) {
        this.socketColor = socketColor;
    }
    
    @NotNull
    public UUID getHistoricUUID() {
        return this.historicUUID;
    }
    
    @NotNull
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Name", this.name);
        jsonObject.addProperty("History", this.historicUUID.toString());
        if (this.mmoitemID != null) {
            jsonObject.addProperty("Id", this.mmoitemID);
        }
        if (this.mmoitemType != null) {
            jsonObject.addProperty("Type", this.mmoitemType);
        }
        if (this.levelPut != null) {
            jsonObject.addProperty("Level", (Number)this.levelPut);
        }
        jsonObject.addProperty("Color", this.socketColor);
        return jsonObject;
    }
    
    @Nullable
    public static UUID UUIDFromString(@Nullable final String name) {
        if (name == null) {
            return null;
        }
        if (name.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
            return UUID.fromString(name);
        }
        return null;
    }
}
