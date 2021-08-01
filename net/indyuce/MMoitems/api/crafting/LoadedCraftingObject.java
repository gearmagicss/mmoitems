// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import io.lumine.mythic.lib.api.MMOLineConfig;
import java.util.function.Function;

public class LoadedCraftingObject<C>
{
    private final String id;
    private final Function<MMOLineConfig, C> function;
    private ConditionalDisplay display;
    
    public LoadedCraftingObject(final String id, final Function<MMOLineConfig, C> function, final ConditionalDisplay display) {
        this.id = id;
        this.function = function;
        this.display = display;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setDisplay(final ConditionalDisplay display) {
        this.display = display;
    }
    
    public boolean hasDisplay() {
        return this.display != null;
    }
    
    public ConditionalDisplay getDisplay() {
        return this.display;
    }
    
    public C load(final MMOLineConfig mmoLineConfig) {
        return this.function.apply(mmoLineConfig);
    }
}
