// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient.inventory;

import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.NBTItem;

public class MMOItemPlayerIngredient extends PlayerIngredient
{
    private final String type;
    private final String id;
    private final int upgradeLevel;
    
    public MMOItemPlayerIngredient(final NBTItem nbtItem) {
        super(nbtItem);
        this.type = nbtItem.getString("MMOITEMS_ITEM_TYPE");
        this.id = nbtItem.getString("MMOITEMS_ITEM_ID");
        final String string = nbtItem.getString("MMOITEMS_UPGRADE");
        this.upgradeLevel = (string.isEmpty() ? 0 : new JsonParser().parse(string).getAsJsonObject().get("Level").getAsInt());
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getUpgradeLevel() {
        return this.upgradeLevel;
    }
}
