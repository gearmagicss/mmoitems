// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.material;

import org.bukkit.Material;

public class FurnaceAndDispenser extends DirectionalContainer
{
    @Deprecated
    public FurnaceAndDispenser(final int type) {
        super(type);
    }
    
    public FurnaceAndDispenser(final Material type) {
        super(type);
    }
    
    @Deprecated
    public FurnaceAndDispenser(final int type, final byte data) {
        super(type, data);
    }
    
    @Deprecated
    public FurnaceAndDispenser(final Material type, final byte data) {
        super(type, data);
    }
    
    @Override
    public FurnaceAndDispenser clone() {
        return (FurnaceAndDispenser)super.clone();
    }
}
