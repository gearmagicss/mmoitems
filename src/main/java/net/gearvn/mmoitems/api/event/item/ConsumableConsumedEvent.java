// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class ConsumableConsumedEvent extends Event implements Cancellable
{
    @NotNull
    final VolatileMMOItem mmoitem;
    @NotNull
    final Player player;
    @NotNull
    final Consumable useItem;
    boolean cancelled;
    @Nullable
    Boolean consume;
    private static final HandlerList handlers;
    
    @NotNull
    public VolatileMMOItem getMMOItem() {
        return this.mmoitem;
    }
    
    @NotNull
    public Player getPlayer() {
        return this.player;
    }
    
    @NotNull
    public Consumable getUseItem() {
        return this.useItem;
    }
    
    @Nullable
    public Boolean isConsume() {
        return this.consume;
    }
    
    public void setConsume(@Nullable final Boolean consume) {
        this.consume = consume;
    }
    
    public ConsumableConsumedEvent(@NotNull final VolatileMMOItem mmoitem, @NotNull final Player player, @NotNull final Consumable useItem) {
        this.cancelled = false;
        this.consume = null;
        this.mmoitem = mmoitem;
        this.player = player;
        this.useItem = useItem;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @NotNull
    public HandlerList getHandlers() {
        return ConsumableConsumedEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
