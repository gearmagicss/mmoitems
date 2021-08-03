// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class RequiredLevelData extends DoubleData
{
    public RequiredLevelData(final double n) {
        super(n);
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof RequiredLevelData, "Cannot merge two different stat data types");
        if (MMOItems.plugin.getConfig().getBoolean("stat-merging.additive-levels", false)) {
            this.setValue(((RequiredLevelData)statData).getValue() + this.getValue());
        }
        else {
            this.setValue(Math.max(((RequiredLevelData)statData).getValue(), this.getValue()));
        }
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new RequiredLevelData(this.getValue());
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
}
