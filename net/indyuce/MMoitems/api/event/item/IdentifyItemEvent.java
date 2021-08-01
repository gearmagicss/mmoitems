// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class IdentifyItemEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem consumable;
    private final NBTItem unidentified;
    
    public IdentifyItemEvent(final PlayerData playerData, final VolatileMMOItem consumable, final NBTItem unidentified) {
        super(playerData);
        this.consumable = consumable;
        this.unidentified = unidentified;
    }
    
    public VolatileMMOItem getConsumable() {
        return this.consumable;
    }
    
    public NBTItem getUnidentifiedItem() {
        return this.unidentified;
    }
    
    public HandlerList getHandlers() {
        return IdentifyItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return IdentifyItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
