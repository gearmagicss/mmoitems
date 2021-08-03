// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.inventory;

import net.Indyuce.mmoitems.api.player.inventory.EquippedItem;
import java.util.List;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.Iterator;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PlayerInventoryHandler
{
    @NotNull
    ArrayList<PlayerInventory> registeredInventories;
    
    public PlayerInventoryHandler() {
        this.registeredInventories = new ArrayList<PlayerInventory>();
    }
    
    public void register(@NotNull final PlayerInventory e) {
        this.registeredInventories.add(e);
    }
    
    public void unregisterAll() {
        for (final PlayerInventory playerInventory : this.registeredInventories) {
            if (playerInventory instanceof Listener) {
                HandlerList.unregisterAll((Listener)playerInventory);
            }
        }
        this.registeredInventories.clear();
    }
    
    public ArrayList<PlayerInventory> getAll() {
        return new ArrayList<PlayerInventory>(this.registeredInventories);
    }
    
    @NotNull
    public List<EquippedItem> getInventory(@NotNull final Player player) {
        final ArrayList<EquippedItem> list = new ArrayList<EquippedItem>();
        final Iterator<PlayerInventory> iterator = this.registeredInventories.iterator();
        while (iterator.hasNext()) {
            list.addAll(iterator.next().getInventory(player));
        }
        return list;
    }
}
