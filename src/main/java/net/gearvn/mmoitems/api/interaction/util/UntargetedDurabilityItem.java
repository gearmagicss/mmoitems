// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.util;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class UntargetedDurabilityItem extends DurabilityItem
{
    private final EquipmentSlot slot;
    
    public UntargetedDurabilityItem(final Player player, final NBTItem nbtItem, final EquipmentSlot slot) {
        super(player, nbtItem);
        this.slot = slot;
    }
    
    @Override
    public UntargetedDurabilityItem decreaseDurability(final int n) {
        return (UntargetedDurabilityItem)super.decreaseDurability(n);
    }
    
    public void update() {
        if (this.isBroken() && this.isLostWhenBroken()) {
            if (this.slot == EquipmentSlot.OFF_HAND) {
                this.getPlayer().getInventory().setItemInOffHand((ItemStack)null);
            }
            else {
                this.getPlayer().getInventory().setItemInMainHand((ItemStack)null);
            }
            return;
        }
        this.getNBTItem().getItem().setItemMeta(this.toItem().getItemMeta());
    }
}
