// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player.inventory;

import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;

public class EquippedPlayerItem
{
    private final VolatileMMOItem item;
    private final Type.EquipmentSlot slot;
    
    public EquippedPlayerItem(final EquippedItem equippedItem) {
        this.item = new VolatileMMOItem(equippedItem.getItem());
        this.slot = equippedItem.getSlot();
    }
    
    public VolatileMMOItem getItem() {
        return this.item;
    }
    
    public Type.EquipmentSlot getSlot() {
        return this.slot;
    }
    
    public boolean matches(final Type type) {
        return this.slot == Type.EquipmentSlot.ANY || ((type.getEquipmentType() != Type.EquipmentSlot.BOTH_HANDS) ? ((this.slot != Type.EquipmentSlot.BOTH_HANDS) ? (this.slot == type.getEquipmentType()) : type.getEquipmentType().isHand()) : this.slot.isHand());
    }
}
