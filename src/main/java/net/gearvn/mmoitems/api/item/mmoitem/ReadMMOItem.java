// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.mmoitem;

import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;

public abstract class ReadMMOItem extends MMOItem
{
    private final NBTItem item;
    
    public ReadMMOItem(final NBTItem item) {
        super(Type.get(item.getType()), item.getString("MMOITEMS_ITEM_ID"));
        this.item = item;
    }
    
    public NBTItem getNBT() {
        return this.item;
    }
}
