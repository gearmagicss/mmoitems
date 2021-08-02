// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.Note;
import org.bukkit.Instrument;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class NotePlayEvent extends BlockEvent implements Cancellable
{
    private static HandlerList handlers;
    private Instrument instrument;
    private Note note;
    private boolean cancelled;
    
    static {
        NotePlayEvent.handlers = new HandlerList();
    }
    
    public NotePlayEvent(final Block block, final Instrument instrument, final Note note) {
        super(block);
        this.cancelled = false;
        this.instrument = instrument;
        this.note = note;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Instrument getInstrument() {
        return this.instrument;
    }
    
    public Note getNote() {
        return this.note;
    }
    
    public void setInstrument(final Instrument instrument) {
        if (instrument != null) {
            this.instrument = instrument;
        }
    }
    
    public void setNote(final Note note) {
        if (note != null) {
            this.note = note;
        }
    }
    
    @Override
    public HandlerList getHandlers() {
        return NotePlayEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return NotePlayEvent.handlers;
    }
}
