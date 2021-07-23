// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template.explorer;

import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.function.Predicate;

public class IDFilter implements Predicate<MMOItemTemplate>
{
    private final String id;
    
    public IDFilter(final String id) {
        this.id = id;
    }
    
    @Override
    public boolean test(final MMOItemTemplate mmoItemTemplate) {
        return mmoItemTemplate.getId().equalsIgnoreCase(this.id);
    }
}
