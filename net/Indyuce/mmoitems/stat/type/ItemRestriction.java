// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;

public interface ItemRestriction
{
    boolean canUse(final RPGPlayer p0, final NBTItem p1, final boolean p2);
    
    default boolean isDynamic() {
        return false;
    }
}
