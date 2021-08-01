// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.inventory;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityPickupItemEvent;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.player.EquipmentSlot;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.MythicLib;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class OrnamentPlayerInventory implements PlayerInventory, Listener
{
    public OrnamentPlayerInventory() {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    @Override
    public List<EquippedItem> getInventory(final Player player) {
        final ArrayList<EquippedItem> list = new ArrayList<EquippedItem>();
        for (final ItemStack itemStack : player.getInventory().getContents()) {
            final NBTItem nbtItem;
            if (itemStack != null && (nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack)).hasType() && Type.get(nbtItem.getType()).getEquipmentType() == EquipmentSlot.ANY) {
                list.add(new EquippedItem(nbtItem, EquipmentSlot.ANY));
            }
        }
        return list;
    }
    
    @EventHandler(ignoreCancelled = true)
    public void a(final EntityPickupItemEvent entityPickupItemEvent) {
        if (entityPickupItemEvent.getEntityType() == EntityType.PLAYER) {
            final NBTItem value = NBTItem.get(entityPickupItemEvent.getItem().getItemStack());
            if (value.hasType() && Type.get(value.getType()).getEquipmentType() == EquipmentSlot.ANY) {
                PlayerData.get((OfflinePlayer)entityPickupItemEvent.getEntity()).updateInventory();
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void b(final PlayerDropItemEvent playerDropItemEvent) {
        final NBTItem value = NBTItem.get(playerDropItemEvent.getItemDrop().getItemStack());
        if (value.hasType() && Type.get(value.getType()).getEquipmentType() == EquipmentSlot.ANY) {
            PlayerData.get((OfflinePlayer)playerDropItemEvent.getPlayer()).updateInventory();
        }
    }
}
