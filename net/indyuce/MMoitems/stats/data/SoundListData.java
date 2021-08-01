// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.jetbrains.annotations.NotNull;
import java.util.function.BiConsumer;
import java.util.Objects;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.CustomSound;
import java.util.Map;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class SoundListData implements StatData, Mergeable, RandomStatData
{
    private final Map<CustomSound, SoundData> sounds;
    
    public SoundListData() {
        this(new HashMap<CustomSound, SoundData>());
    }
    
    public SoundListData(final Map<CustomSound, SoundData> sounds) {
        this.sounds = sounds;
    }
    
    public Set<CustomSound> getCustomSounds() {
        return this.sounds.keySet();
    }
    
    public Map<CustomSound, SoundData> mapData() {
        return this.sounds;
    }
    
    @Nullable
    public SoundData get(final CustomSound key) {
        return this.sounds.getOrDefault(key, null);
    }
    
    public void set(final CustomSound customSound, final SoundData soundData) {
        this.sounds.put(customSound, soundData);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SoundListData)) {
            return false;
        }
        if (((SoundListData)o).getCustomSounds().size() != this.getCustomSounds().size()) {
            return false;
        }
        for (final CustomSound customSound : ((SoundListData)o).getCustomSounds()) {
            if (customSound == null) {
                continue;
            }
            boolean b = true;
            final Iterator<CustomSound> iterator2 = this.getCustomSounds().iterator();
            while (iterator2.hasNext()) {
                if (customSound.equals(iterator2.next())) {
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
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof SoundListData, "Cannot merge two different stat data types");
        final Map<CustomSound, SoundData> sounds = ((SoundListData)statData).sounds;
        final Map<CustomSound, SoundData> sounds2 = this.sounds;
        Objects.requireNonNull(sounds2);
        sounds.forEach(sounds2::put);
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new SoundListData(this.mapData());
    }
    
    @Override
    public boolean isClear() {
        return this.mapData().size() == 0;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new SoundListData(new HashMap<CustomSound, SoundData>(this.sounds));
    }
}
