// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient.inventory;

import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.NBTItem;
import javax.annotation.Nullable;
import org.bukkit.Material;

public class VanillaPlayerIngredient extends PlayerIngredient
{
    private final Material material;
    @Nullable
    private final String displayName;
    
    public VanillaPlayerIngredient(final NBTItem nbtItem) {
        super(nbtItem);
        this.material = nbtItem.getItem().getType();
        final ItemMeta itemMeta = nbtItem.getItem().getItemMeta();
        this.displayName = (itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : null);
    }
    
    public Material getType() {
        return this.material;
    }
    
    @Nullable
    public String getDisplayName() {
        return this.displayName;
    }
}
