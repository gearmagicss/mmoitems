// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player.inventory;

import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;

public class EquippedPlayerItem
{
    private final VolatileMMOItem item;
    private final EquipmentSlot slot;
    
    public EquippedPlayerItem(final EquippedItem equippedItem) {
        this.item = new VolatileMMOItem(equippedItem.getItem());
        this.slot = equippedItem.getSlot();
    }
    
    public VolatileMMOItem getItem() {
        return this.item;
    }
    
    public EquipmentSlot getSlot() {
        return this.slot;
    }
}
