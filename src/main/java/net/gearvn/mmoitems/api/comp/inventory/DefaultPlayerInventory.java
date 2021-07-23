// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.inventory;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.Type;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import java.util.List;
import org.bukkit.entity.Player;

public class DefaultPlayerInventory implements PlayerInventory
{
    @Override
    public List<EquippedItem> getInventory(final Player player) {
        final ArrayList<EquippedItem> list = new ArrayList<EquippedItem>();
        list.add(new EquippedItem(player.getEquipment().getItemInMainHand(), Type.EquipmentSlot.MAIN_HAND));
        list.add(new EquippedItem(player.getEquipment().getItemInOffHand(), Type.EquipmentSlot.OFF_HAND));
        final ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int length = armorContents.length, i = 0; i < length; ++i) {
            list.add(new EquippedItem(armorContents[i], Type.EquipmentSlot.ARMOR));
        }
        return list;
    }
}
