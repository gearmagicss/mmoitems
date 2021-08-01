// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player.inventory;

import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;

public class EquippedItem
{
    private final NBTItem item;
    private final EquipmentSlot slot;
    
    public EquippedItem(final ItemStack itemStack, final EquipmentSlot equipmentSlot) {
        this(MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack), equipmentSlot);
    }
    
    public EquippedItem(final NBTItem item, final EquipmentSlot slot) {
        this.item = item;
        this.slot = slot;
    }
    
    public NBTItem getItem() {
        return this.item;
    }
    
    public EquipmentSlot getSlot() {
        return this.slot;
    }
    
    public boolean matches(@NotNull final Type type) {
        if (this.slot == EquipmentSlot.ANY) {
            return true;
        }
        if (type.getEquipmentType() == EquipmentSlot.BOTH_HANDS) {
            return this.slot.isHand();
        }
        return this.slot == type.getEquipmentType();
    }
}
