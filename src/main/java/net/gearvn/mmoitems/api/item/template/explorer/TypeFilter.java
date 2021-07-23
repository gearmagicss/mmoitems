// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template.explorer;

import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.function.Predicate;

public class TypeFilter implements Predicate<MMOItemTemplate>
{
    private final Type type;
    
    public TypeFilter(final Type type) {
        this.type = type;
    }
    
    @Override
    public boolean test(final MMOItemTemplate mmoItemTemplate) {
        return mmoItemTemplate.getType().equals(this.type);
    }
}
