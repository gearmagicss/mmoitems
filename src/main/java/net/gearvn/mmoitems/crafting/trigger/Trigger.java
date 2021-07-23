// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.trigger;

import net.Indyuce.mmoitems.api.player.PlayerData;

public abstract class Trigger
{
    private final String id;
    
    public Trigger(final String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public abstract void whenCrafting(final PlayerData p0);
}
