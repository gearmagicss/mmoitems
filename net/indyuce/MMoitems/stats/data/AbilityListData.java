// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class AbilityListData implements StatData, Mergeable
{
    @NotNull
    private final Set<AbilityData> abilities;
    
    public AbilityListData(@NotNull final AbilityData... array) {
        this.abilities = new LinkedHashSet<AbilityData>();
        this.add(array);
    }
    
    public AbilityListData(@NotNull final Set<AbilityData> set) {
        this.abilities = new LinkedHashSet<AbilityData>();
        this.add(set);
    }
    
    public void add(@NotNull final AbilityData... a) {
        this.abilities.addAll(Arrays.asList(a));
    }
    
    public void add(@NotNull final Set<AbilityData> set) {
        this.abilities.addAll(set);
    }
    
    @NotNull
    public Set<AbilityData> getAbilities() {
        return this.abilities;
    }
    
    @Override
    public void merge(@NotNull final StatData statData) {
        Validate.isTrue(statData instanceof AbilityListData, "Cannot merge two different stat data types");
        this.abilities.addAll(((AbilityListData)statData).abilities);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AbilityListData)) {
            return false;
        }
        if (this.getAbilities().size() != ((AbilityListData)o).getAbilities().size()) {
            return false;
        }
        for (final AbilityData abilityData : ((AbilityListData)o).getAbilities()) {
            if (abilityData == null) {
                continue;
            }
            boolean b = true;
            final Iterator<AbilityData> iterator2 = this.getAbilities().iterator();
            while (iterator2.hasNext()) {
                if (abilityData.equals(iterator2.next())) {
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
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new AbilityListData(this.getAbilities());
    }
    
    @Override
    public boolean isClear() {
        return this.getAbilities().size() == 0;
    }
}
