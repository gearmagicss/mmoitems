// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import com.google.gson.JsonElement;
import java.util.function.Consumer;
import java.util.Objects;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.UUID;
import net.Indyuce.mmoitems.MMOItems;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class GemSocketsData implements StatData, Mergeable, RandomStatData
{
    @NotNull
    private final Set<GemstoneData> gems;
    @NotNull
    private final List<String> emptySlots;
    
    public GemSocketsData(@NotNull final List<String> emptySlots) {
        this.gems = new HashSet<GemstoneData>();
        this.emptySlots = emptySlots;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof GemSocketsData)) {
            return false;
        }
        if (((GemSocketsData)o).getEmptySlots().size() != this.getEmptySlots().size()) {
            return false;
        }
        if (((GemSocketsData)o).getGemstones().size() != this.getGemstones().size()) {
            return false;
        }
        if (!SilentNumbers.hasAll((List)((GemSocketsData)o).getEmptySlots(), (List)this.getEmptySlots())) {
            return false;
        }
        for (final GemstoneData gemstoneData : ((GemSocketsData)o).getGemstones()) {
            if (gemstoneData == null) {
                continue;
            }
            boolean b = true;
            final Iterator<GemstoneData> iterator2 = this.getGemstones().iterator();
            while (iterator2.hasNext()) {
                if (gemstoneData.equals(iterator2.next())) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canReceive(@NotNull final String s) {
        return this.getEmptySocket(s) != null;
    }
    
    @Nullable
    public String getEmptySocket(@NotNull final String s) {
        for (final String anObject : this.emptySlots) {
            if (s.equals("") || anObject.equals(getUncoloredGemSlot()) || s.equals(anObject)) {
                return anObject;
            }
        }
        return null;
    }
    
    @NotNull
    public static String getUncoloredGemSlot() {
        final String string = MMOItems.plugin.getConfig().getString("gem-sockets.uncolored");
        return (string == null) ? "Uncolored" : string;
    }
    
    public void add(final GemstoneData gemstoneData) {
        this.gems.add(gemstoneData);
    }
    
    public void apply(final String s, final GemstoneData gemstoneData) {
        this.emptySlots.remove(this.getEmptySocket(s));
        this.gems.add(gemstoneData);
    }
    
    public void addEmptySlot(@NotNull final String s) {
        this.emptySlots.add(s);
    }
    
    @NotNull
    public List<String> getEmptySlots() {
        return this.emptySlots;
    }
    
    @NotNull
    public Set<GemstoneData> getGemstones() {
        return this.gems;
    }
    
    public void removeGem(@NotNull final UUID obj) {
        Object o = null;
        for (final GemstoneData gemstoneData : this.getGemstones()) {
            if (gemstoneData.getHistoricUUID().equals(obj)) {
                o = gemstoneData;
                break;
            }
        }
        this.gems.remove(o);
    }
    
    public static boolean removeGemFrom(@NotNull final GemSocketsData gemSocketsData, @NotNull final UUID obj, @Nullable final String s) {
        boolean b = false;
        final Iterator<GemstoneData> iterator = gemSocketsData.getGemstones().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getHistoricUUID().equals(obj)) {
                if (s != null) {
                    gemSocketsData.addEmptySlot(s);
                }
                b = true;
                break;
            }
        }
        if (b) {
            gemSocketsData.removeGem(obj);
        }
        return b;
    }
    
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();
        final List<String> emptySlots = this.getEmptySlots();
        final JsonArray obj = jsonArray;
        Objects.requireNonNull(obj);
        emptySlots.forEach(obj::add);
        jsonObject.add("EmptySlots", (JsonElement)jsonArray);
        final JsonArray jsonArray2 = new JsonArray();
        this.gems.forEach(gemstoneData -> jsonArray2.add((JsonElement)gemstoneData.toJson()));
        jsonObject.add("Gemstones", (JsonElement)jsonArray2);
        return jsonObject;
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof GemSocketsData, "Cannot merge two different stat data types");
        this.emptySlots.addAll(((GemSocketsData)statData).emptySlots);
        this.gems.addAll(((GemSocketsData)statData).getGemstones());
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        final GemSocketsData gemSocketsData = new GemSocketsData(new ArrayList<String>(this.emptySlots));
        final Iterator<GemstoneData> iterator = this.getGemstones().iterator();
        while (iterator.hasNext()) {
            gemSocketsData.add(iterator.next().cloneGem());
        }
        return gemSocketsData;
    }
    
    @Override
    public boolean isClear() {
        return this.getGemstones().size() == 0 && this.getEmptySlots().size() == 0;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new GemSocketsData(new ArrayList<String>(this.emptySlots));
    }
    
    @Override
    public String toString() {
        return "Empty:§b " + this.getEmptySlots().size() + "§7, Gems:§b " + this.getGemstones().size();
    }
}
