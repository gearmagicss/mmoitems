// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class DeconstructItemEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem consumable;
    private final NBTItem deconstructed;
    private final List<ItemStack> loot;
    
    public DeconstructItemEvent(final PlayerData playerData, final VolatileMMOItem consumable, final NBTItem deconstructed, final List<ItemStack> loot) {
        super(playerData);
        this.consumable = consumable;
        this.deconstructed = deconstructed;
        this.loot = loot;
    }
    
    public VolatileMMOItem getConsumable() {
        return this.consumable;
    }
    
    public NBTItem getDeconstructedItem() {
        return this.deconstructed;
    }
    
    public List<ItemStack> getLoot() {
        return this.loot;
    }
    
    public HandlerList getHandlers() {
        return DeconstructItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return DeconstructItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
