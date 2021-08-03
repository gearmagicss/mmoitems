// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player.inventory;

import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;

public class EquippedItem
{
    private final NBTItem item;
    private final Type.EquipmentSlot slot;
    
    public EquippedItem(final ItemStack itemStack, final Type.EquipmentSlot equipmentSlot) {
        this(MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack), equipmentSlot);
    }
    
    public EquippedItem(final NBTItem item, final Type.EquipmentSlot slot) {
        this.item = item;
        this.slot = slot;
    }
    
    public NBTItem getItem() {
        return this.item;
    }
    
    public Type.EquipmentSlot getSlot() {
        return this.slot;
    }
    
    public boolean matches(@NotNull final Type type) {
        return this.matches(type, null);
    }
    
    public boolean matches(@NotNull final Type type, @Nullable final Type.EquipmentSlot equipmentSlot) {
        Type.EquipmentSlot equipmentSlot2 = type.getEquipmentType();
        if (this.slot == Type.EquipmentSlot.ANY) {
            return true;
        }
        if (equipmentSlot2 == Type.EquipmentSlot.EITHER_HAND) {
            if (this.slot == Type.EquipmentSlot.MAIN_HAND) {
                return true;
            }
            if (equipmentSlot != null) {
                return equipmentSlot != Type.EquipmentSlot.MAIN_HAND && equipmentSlot != Type.EquipmentSlot.EITHER_HAND && this.slot.isHand();
            }
            equipmentSlot2 = Type.EquipmentSlot.OFF_HAND;
        }
        return (equipmentSlot2 == Type.EquipmentSlot.BOTH_HANDS && this.slot.isHand()) || (this.slot == Type.EquipmentSlot.BOTH_HANDS && equipmentSlot2.isHand()) || this.slot == equipmentSlot2;
    }
}
