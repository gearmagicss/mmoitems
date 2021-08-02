// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class SignChangeEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final Player player;
    private final String[] lines;
    
    static {
        handlers = new HandlerList();
    }
    
    public SignChangeEvent(final Block theBlock, final Player thePlayer, final String[] theLines) {
        super(theBlock);
        this.cancel = false;
        this.player = thePlayer;
        this.lines = theLines;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public String[] getLines() {
        return this.lines;
    }
    
    public String getLine(final int index) throws IndexOutOfBoundsException {
        return this.lines[index];
    }
    
    public void setLine(final int index, final String line) throws IndexOutOfBoundsException {
        this.lines[index] = line;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return SignChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return SignChangeEvent.handlers;
    }
}
