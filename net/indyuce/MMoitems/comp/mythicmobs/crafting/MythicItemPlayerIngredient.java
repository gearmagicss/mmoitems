// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs.crafting;

import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;

@Deprecated
public class MythicItemPlayerIngredient extends PlayerIngredient
{
    private final String type;
    private final String id;
    
    public MythicItemPlayerIngredient(final NBTItem nbtItem) {
        super(nbtItem);
        this.type = nbtItem.getString("MYTHIC_TYPE").toLowerCase();
        this.id = null;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
}
