// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.UpgradeData;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class UpgradeItemEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem consumable;
    private final MMOItem target;
    private final UpgradeData consumableData;
    private final UpgradeData targetData;
    
    public UpgradeItemEvent(final PlayerData playerData, final VolatileMMOItem consumable, final MMOItem target, final UpgradeData consumableData, final UpgradeData targetData) {
        super(playerData);
        this.consumable = consumable;
        this.target = target;
        this.consumableData = consumableData;
        this.targetData = targetData;
    }
    
    public VolatileMMOItem getConsumable() {
        return this.consumable;
    }
    
    public MMOItem getTargetItem() {
        return this.target;
    }
    
    public UpgradeData getConsumableData() {
        return this.consumableData;
    }
    
    public UpgradeData getTargetData() {
        return this.targetData;
    }
    
    public HandlerList getHandlers() {
        return UpgradeItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return UpgradeItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
