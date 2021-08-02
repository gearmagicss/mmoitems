// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import java.util.Collection;

public interface ThrownPotion extends Projectile
{
    Collection<PotionEffect> getEffects();
    
    ItemStack getItem();
    
    void setItem(final ItemStack p0);
}
