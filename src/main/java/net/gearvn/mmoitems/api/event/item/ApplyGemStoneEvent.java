// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class ApplyGemStoneEvent extends PlayerDataEvent
{
    private static final HandlerList handlers;
    private final VolatileMMOItem gemStone;
    private final MMOItem targetItem;
    
    public ApplyGemStoneEvent(final PlayerData playerData, final VolatileMMOItem gemStone, final MMOItem targetItem) {
        super(playerData);
        this.gemStone = gemStone;
        this.targetItem = targetItem;
    }
    
    public VolatileMMOItem getGemStone() {
        return this.gemStone;
    }
    
    public MMOItem getTargetItem() {
        return this.targetItem;
    }
    
    public HandlerList getHandlers() {
        return ApplyGemStoneEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ApplyGemStoneEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
