// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class CustomDurabilityRepair extends Event implements Cancellable
{
    @NotNull
    private final DurabilityItem sourceItem;
    private final int durabilityIncrease;
    private boolean cancelled;
    private static final HandlerList handlers;
    
    public CustomDurabilityRepair(@NotNull final DurabilityItem sourceItem, final int durabilityIncrease) {
        this.sourceItem = sourceItem;
        this.durabilityIncrease = durabilityIncrease;
    }
    
    public boolean hasPlayer() {
        return this.sourceItem.getPlayer() != null;
    }
    
    public Player getPlayer() {
        return this.sourceItem.getPlayer();
    }
    
    public int getDurabilityIncrease() {
        return this.durabilityIncrease;
    }
    
    @NotNull
    public DurabilityItem getSourceItem() {
        return this.sourceItem;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @NotNull
    public HandlerList getHandlers() {
        return CustomDurabilityRepair.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
