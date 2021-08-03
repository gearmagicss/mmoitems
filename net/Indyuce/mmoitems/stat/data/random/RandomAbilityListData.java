// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.AbilityListData;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Collection;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class RandomAbilityListData implements RandomStatData
{
    private final Set<RandomAbilityData> abilities;
    
    public RandomAbilityListData(final RandomAbilityData... array) {
        this.abilities = new LinkedHashSet<RandomAbilityData>();
        this.add(array);
    }
    
    public void add(final RandomAbilityData... a) {
        this.abilities.addAll(Arrays.asList(a));
    }
    
    public Set<RandomAbilityData> getAbilities() {
        return this.abilities;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        final AbilityListData abilityListData = new AbilityListData(new AbilityData[0]);
        this.abilities.forEach(randomAbilityData -> abilityListData.add(randomAbilityData.randomize(mmoItemBuilder)));
        return abilityListData;
    }
}
