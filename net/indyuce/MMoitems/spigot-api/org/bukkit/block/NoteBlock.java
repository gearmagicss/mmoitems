// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.block;

import org.bukkit.Instrument;
import org.bukkit.Note;

public interface NoteBlock extends BlockState
{
    Note getNote();
    
    @Deprecated
    byte getRawNote();
    
    void setNote(final Note p0);
    
    @Deprecated
    void setRawNote(final byte p0);
    
    boolean play();
    
    @Deprecated
    boolean play(final byte p0, final byte p1);
    
    boolean play(final Instrument p0, final Note p1);
}
