// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class ItemDropEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final DropCause cause;
    private final List<ItemStack> drops;
    private final LivingEntity player;
    private final Block block;
    private final Entity entity;
    private final String mythicMobName;
    
    public ItemDropEvent(final LivingEntity livingEntity, final List<ItemStack> list, final DropCause dropCause, final Block block) {
        this(livingEntity, list, dropCause, block, null, null);
    }
    
    public ItemDropEvent(final LivingEntity livingEntity, final List<ItemStack> list, final DropCause dropCause, final Entity entity) {
        this(livingEntity, list, dropCause, null, entity, null);
    }
    
    public ItemDropEvent(final LivingEntity livingEntity, final List<ItemStack> list, final DropCause dropCause, final String s) {
        this(livingEntity, list, dropCause, null, null, s);
    }
    
    public ItemDropEvent(final LivingEntity player, final List<ItemStack> drops, final DropCause cause, final Block block, final Entity entity, final String mythicMobName) {
        this.cancelled = false;
        this.player = player;
        this.cause = cause;
        this.drops = drops;
        this.block = block;
        this.entity = entity;
        this.mythicMobName = mythicMobName;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public List<ItemStack> getDrops() {
        return this.drops;
    }
    
    public DropCause getCause() {
        return this.cause;
    }
    
    public LivingEntity getWhoDropped() {
        return this.player;
    }
    
    public Block getMinedBlock() {
        return this.block;
    }
    
    public Entity getKilledEntity() {
        return this.entity;
    }
    
    public String getKilledMythicMobName() {
        return this.mythicMobName;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public HandlerList getHandlers() {
        return ItemDropEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ItemDropEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum DropCause
    {
        BLOCK, 
        MONSTER, 
        MYTHIC_MOB;
    }
}
