// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import io.lumine.mythic.lib.api.item.NBTItem;

@Deprecated
public interface DynamicLoreStat
{
    @Deprecated
    String getDynamicLoreId();
    
    String calculatePlaceholder(final NBTItem p0);
}
