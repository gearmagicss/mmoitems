// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class PotionEffectListData implements StatData, Mergeable
{
    private final List<PotionEffectData> effects;
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof PotionEffectListData)) {
            return false;
        }
        if (((PotionEffectListData)o).getEffects().size() != this.getEffects().size()) {
            return false;
        }
        for (final PotionEffectData potionEffectData : ((PotionEffectListData)o).getEffects()) {
            if (potionEffectData == null) {
                continue;
            }
            boolean b = true;
            final Iterator<PotionEffectData> iterator2 = this.getEffects().iterator();
            while (iterator2.hasNext()) {
                if (potionEffectData.equals(iterator2.next())) {
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
    
    public PotionEffectListData(final PotionEffectData... array) {
        this.effects = new ArrayList<PotionEffectData>();
        this.add(array);
    }
    
    public void add(final PotionEffectData... a) {
        this.effects.addAll(Arrays.asList(a));
    }
    
    public List<PotionEffectData> getEffects() {
        return this.effects;
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof PotionEffectListData, "Cannot merge two different stat data types");
        this.effects.addAll(((PotionEffectListData)statData).effects);
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new PotionEffectListData((PotionEffectData[])this.getEffects().toArray(new PotionEffectData[0]));
    }
    
    @Override
    public boolean isClear() {
        return this.getEffects().size() == 0;
    }
}
