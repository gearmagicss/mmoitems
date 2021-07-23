// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.edition;

import net.Indyuce.mmoitems.gui.PluginInventory;

public interface Edition
{
    boolean processInput(final String p0);
    
    PluginInventory getInventory();
    
    void enable(final String... p0);
    
    boolean shouldGoBack();
}
