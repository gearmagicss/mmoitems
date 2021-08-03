// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.RequiredLevelData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;

public class RandomRequiredLevelData extends NumericStatFormula
{
    public RandomRequiredLevelData(final Object o) {
        super(o);
    }
    
    public RandomRequiredLevelData(final double n, final double n2, final double n3, final double n4) {
        super(n, n2, n3, n4);
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new RequiredLevelData(this.calculate(mmoItemBuilder.getLevel()));
    }
}
