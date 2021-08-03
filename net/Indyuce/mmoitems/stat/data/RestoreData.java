// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class RestoreData implements StatData, Mergeable
{
    private double health;
    private double food;
    private double saturation;
    
    public RestoreData(final double health, final double food, final double saturation) {
        this.health = health;
        this.food = food;
        this.saturation = saturation;
    }
    
    public double getHealth() {
        return this.health;
    }
    
    public double getFood() {
        return this.food;
    }
    
    public double getSaturation() {
        return this.saturation;
    }
    
    public void setHealth(final double health) {
        this.health = health;
    }
    
    public void setFood(final double food) {
        this.food = food;
    }
    
    public void setSaturation(final double saturation) {
        this.saturation = saturation;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof RestoreData && ((RestoreData)o).getFood() == this.getFood() && ((RestoreData)o).getHealth() == this.getHealth() && ((RestoreData)o).getSaturation() == this.getSaturation();
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof RestoreData, "Cannot merge two different stat data types");
        this.health += ((RestoreData)statData).health;
        this.food += ((RestoreData)statData).food;
        this.saturation += ((RestoreData)statData).saturation;
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new RestoreData(this.getHealth(), this.getFood(), this.getSaturation());
    }
    
    @Override
    public boolean isClear() {
        return this.getFood() == 0.0 && this.getHealth() == 0.0 && this.getSaturation() == 0.0;
    }
}
