// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.inventory.InventoryCloseEvent;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.inventory.ItemStack;
import ru.endlesscode.rpginventory.api.InventoryAPI;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class RPGInventoryHook implements PlayerInventory, Listener
{
    public RPGInventoryHook() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    @Override
    public List<EquippedItem> getInventory(final Player player) {
        final ArrayList<EquippedItem> list = new ArrayList<EquippedItem>();
        for (final ItemStack itemStack : InventoryAPI.getPassiveItems(player)) {
            if (itemStack != null) {
                list.add(new EquippedItem(itemStack, Type.EquipmentSlot.ANY));
            }
        }
        return list;
    }
    
    @EventHandler
    public void a(final InventoryCloseEvent inventoryCloseEvent) {
        if (InventoryAPI.isRPGInventory(inventoryCloseEvent.getInventory())) {
            PlayerData.get((OfflinePlayer)inventoryCloseEvent.getPlayer()).updateInventory();
        }
    }
}
