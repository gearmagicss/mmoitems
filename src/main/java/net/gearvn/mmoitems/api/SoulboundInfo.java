// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class SoulboundInfo
{
    private final List<ItemStack> items;
    private final Location loc;
    private final Player player;
    private static final Map<UUID, SoulboundInfo> INFO;
    
    public SoulboundInfo(final Player player) {
        this.items = new ArrayList<ItemStack>();
        this.player = player;
        this.loc = player.getLocation().clone();
    }
    
    public void add(final ItemStack itemStack) {
        this.items.add(itemStack);
    }
    
    public boolean hasItems() {
        return !this.items.isEmpty();
    }
    
    public void setup() {
        SoulboundInfo.INFO.put(this.player.getUniqueId(), this);
    }
    
    public void giveItems() {
        final Iterator<ItemStack> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            final Iterator<ItemStack> iterator2 = this.player.getInventory().addItem(new ItemStack[] { iterator.next() }).values().iterator();
            while (iterator2.hasNext()) {
                this.player.getWorld().dropItem(this.player.getLocation(), (ItemStack)iterator2.next());
            }
        }
    }
    
    public void dropItems() {
        this.items.forEach(itemStack -> this.loc.getWorld().dropItem(this.loc, itemStack));
    }
    
    public static void read(final Player player) {
        if (SoulboundInfo.INFO.containsKey(player.getUniqueId())) {
            SoulboundInfo.INFO.get(player.getUniqueId()).giveItems();
            SoulboundInfo.INFO.remove(player.getUniqueId());
        }
    }
    
    public static Collection<SoulboundInfo> getAbandonnedInfo() {
        return SoulboundInfo.INFO.values();
    }
    
    static {
        INFO = new HashMap<UUID, SoulboundInfo>();
    }
}
