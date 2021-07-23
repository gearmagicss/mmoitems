// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class RepairItemEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem consumable;
    private final NBTItem target;
    private int repaired;
    private double repairedPercent;
    
    public RepairItemEvent(final PlayerData playerData, final VolatileMMOItem consumable, final NBTItem target, final int repaired) {
        super(playerData);
        this.consumable = consumable;
        this.target = target;
        this.repaired = repaired;
    }
    
    public RepairItemEvent(final PlayerData playerData, final VolatileMMOItem consumable, final NBTItem target, final double repairedPercent) {
        super(playerData);
        this.consumable = consumable;
        this.target = target;
        this.repairedPercent = repairedPercent;
    }
    
    public VolatileMMOItem getConsumable() {
        return this.consumable;
    }
    
    public NBTItem getTargetItem() {
        return this.target;
    }
    
    public int getRepaired() {
        return this.repaired;
    }
    
    public double getRepairedPercent() {
        return this.repairedPercent;
    }
    
    public void setRepaired(final int repaired) {
        this.repaired = repaired;
    }
    
    public HandlerList getHandlers() {
        return RepairItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return RepairItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
