// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.template.explorer;

import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import java.util.function.Predicate;

public class ClassFilter implements Predicate<MMOItemTemplate>
{
    private final String name;
    
    public ClassFilter(final RPGPlayer rpgPlayer) {
        this(rpgPlayer.getClassName());
    }
    
    public ClassFilter(final String name) {
        this.name = name;
    }
    
    @Override
    public boolean test(final MMOItemTemplate mmoItemTemplate) {
        if (!mmoItemTemplate.getBaseItemData().containsKey(ItemStats.REQUIRED_CLASS)) {
            return true;
        }
        final Iterator<String> iterator = mmoItemTemplate.getBaseItemData().get(ItemStats.REQUIRED_CLASS).getList().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equalsIgnoreCase(this.name)) {
                return true;
            }
        }
        return false;
    }
}
