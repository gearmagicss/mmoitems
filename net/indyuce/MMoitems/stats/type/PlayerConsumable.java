// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.type;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;

@FunctionalInterface
public interface PlayerConsumable
{
    void onConsume(@NotNull final VolatileMMOItem p0, @NotNull final Player p1);
}
