// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.inventory;

import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import java.util.List;
import org.bukkit.entity.Player;

public interface PlayerInventory
{
    List<EquippedItem> getInventory(final Player p0);
}
