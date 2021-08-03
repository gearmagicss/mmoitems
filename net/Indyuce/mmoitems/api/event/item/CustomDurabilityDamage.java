// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event.item;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class CustomDurabilityDamage extends Event implements Cancellable
{
    @NotNull
    DurabilityItem sourceItem;
    int durabilityDecrease;
    boolean cancelled;
    private static final HandlerList handlers;
    
    public CustomDurabilityDamage(@NotNull final DurabilityItem sourceItem, final int durabilityDecrease) {
        this.cancelled = false;
        this.sourceItem = sourceItem;
        this.durabilityDecrease = durabilityDecrease;
    }
    
    public int getDurabilityDecrease() {
        return this.durabilityDecrease;
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
        return CustomDurabilityDamage.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
