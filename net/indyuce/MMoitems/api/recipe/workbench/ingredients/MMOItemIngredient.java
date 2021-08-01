// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.recipe.workbench.ingredients;

import org.bukkit.inventory.RecipeChoice;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.Type;

public class MMOItemIngredient extends WorkbenchIngredient
{
    private final Type type;
    private final String id;
    
    public MMOItemIngredient(final Type type, final String id, final int n) {
        super(n);
        this.type = type;
        this.id = id;
    }
    
    public boolean corresponds(final ItemStack itemStack) {
        final NBTItem value = NBTItem.get(itemStack);
        return this.type.equals(Type.get(value.getType())) && value.getString("MMOITEMS_ITEM_ID").equalsIgnoreCase(this.id);
    }
    
    @Override
    public ItemStack generateItem() {
        return MMOItems.plugin.getItem(this.type, this.id);
    }
    
    @Override
    public RecipeChoice toBukkit() {
        return (RecipeChoice)new RecipeChoice.ExactChoice(this.generateItem());
    }
}
