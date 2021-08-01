// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.api.Type;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.ReforgeOptions;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class MMOItemReforgeFinishEvent extends Event implements Cancellable
{
    @NotNull
    final MMOItemReforger reforger;
    @NotNull
    final ReforgeOptions options;
    @NotNull
    ItemStack finishedItem;
    private static final HandlerList handlers;
    boolean cancelled;
    
    @NotNull
    public MMOItemReforger getReforger() {
        return this.reforger;
    }
    
    @NotNull
    public ReforgeOptions getOptions() {
        return this.options;
    }
    
    @NotNull
    public ItemStack getFinishedItem() {
        return this.finishedItem;
    }
    
    public void setFinishedItem(@NotNull final ItemStack finishedItem) {
        this.finishedItem = finishedItem;
    }
    
    public MMOItemReforgeFinishEvent(@NotNull final ItemStack finishedItem, @NotNull final MMOItemReforger reforger, @NotNull final ReforgeOptions options) {
        this.finishedItem = finishedItem;
        this.reforger = reforger;
        this.options = options;
    }
    
    @Nullable
    public Player getPlayer() {
        if (this.getReforger().getPlayer() == null) {
            return null;
        }
        return this.getReforger().getPlayer().getPlayer();
    }
    
    @NotNull
    public Type getType() {
        return this.getReforger().getTemplate().getType();
    }
    
    @NotNull
    public String getTypeName() {
        return this.getReforger().getTemplate().getType().getId();
    }
    
    @NotNull
    public String getID() {
        return this.getReforger().getTemplate().getId();
    }
    
    @NotNull
    public LiveMMOItem getOldMMOItem() {
        return this.getReforger().getOldMMOItem();
    }
    
    @NotNull
    public MMOItem getNewMMOItem() {
        return this.getReforger().getFreshMMOItem();
    }
    
    @NotNull
    public HandlerList getHandlers() {
        return MMOItemReforgeFinishEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return MMOItemReforgeFinishEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    static {
        handlers = new HandlerList();
    }
}
