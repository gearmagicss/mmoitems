// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.api.event.PlayerDataEvent;

public class ConsumableConsumedEvent extends PlayerDataEvent
{
    @NotNull
    private final VolatileMMOItem mmoitem;
    @NotNull
    private final Consumable useItem;
    @Nullable
    private boolean consumed;
    private static final HandlerList handlers;
    
    public ConsumableConsumedEvent(@NotNull final PlayerData playerData, @NotNull final VolatileMMOItem mmoitem, @NotNull final Consumable useItem) {
        super(playerData);
        this.consumed = true;
        this.mmoitem = mmoitem;
        this.useItem = useItem;
    }
    
    @Deprecated
    public ConsumableConsumedEvent(@NotNull final VolatileMMOItem mmoitem, @NotNull final Player player, @NotNull final Consumable useItem) {
        super(PlayerData.get((OfflinePlayer)player));
        this.consumed = true;
        this.mmoitem = mmoitem;
        this.useItem = useItem;
    }
    
    @NotNull
    public VolatileMMOItem getMMOItem() {
        return this.mmoitem;
    }
    
    @NotNull
    public Consumable getUseItem() {
        return this.useItem;
    }
    
    @Deprecated
    public Boolean isConsume() {
        return this.consumed;
    }
    
    public boolean isConsumed() {
        return this.consumed;
    }
    
    @Deprecated
    public void setConsume(@Nullable final Boolean b) {
        this.consumed = b;
    }
    
    public void setConsumed(final boolean consumed) {
        this.consumed = consumed;
    }
    
    @NotNull
    public HandlerList getHandlers() {
        return ConsumableConsumedEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
