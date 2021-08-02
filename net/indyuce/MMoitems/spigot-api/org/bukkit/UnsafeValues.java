// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import org.bukkit.inventory.ItemStack;
import java.util.List;

@Deprecated
public interface UnsafeValues
{
    Material getMaterialFromInternalName(final String p0);
    
    List<String> tabCompleteInternalMaterialName(final String p0, final List<String> p1);
    
    ItemStack modifyItemStack(final ItemStack p0, final String p1);
    
    Statistic getStatisticFromInternalName(final String p0);
    
    Achievement getAchievementFromInternalName(final String p0);
    
    List<String> tabCompleteInternalStatisticOrAchievementName(final String p0, final List<String> p1);
}
