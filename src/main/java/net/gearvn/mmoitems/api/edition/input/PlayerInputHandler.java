// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition.input;

import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.edition.Edition;

public abstract class PlayerInputHandler
{
    private final Edition edition;
    
    public PlayerInputHandler(final Edition edition) {
        this.edition = edition;
    }
    
    public Player getPlayer() {
        return this.edition.getInventory().getPlayer();
    }
    
    protected void registerInput(final String s) {
        if (!this.edition.processInput(s)) {
            return;
        }
        if (this.edition.shouldGoBack()) {
            this.edition.getInventory().open();
        }
        this.close();
    }
    
    public abstract void close();
}
