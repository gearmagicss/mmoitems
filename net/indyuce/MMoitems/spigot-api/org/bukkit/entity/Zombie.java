// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

public interface Zombie extends Monster
{
    boolean isBaby();
    
    void setBaby(final boolean p0);
    
    boolean isVillager();
    
    @Deprecated
    void setVillager(final boolean p0);
    
    void setVillagerProfession(final Villager.Profession p0);
    
    Villager.Profession getVillagerProfession();
}
