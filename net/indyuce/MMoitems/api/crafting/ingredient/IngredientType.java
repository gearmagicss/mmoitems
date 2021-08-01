// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient;

import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;
import java.util.function.Function;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.function.Predicate;
import net.Indyuce.mmoitems.api.crafting.LoadedCraftingObject;

public class IngredientType extends LoadedCraftingObject<Ingredient>
{
    private final Predicate<NBTItem> check;
    private final Function<NBTItem, PlayerIngredient> readIngredient;
    
    public IngredientType(final String s, final Function<MMOLineConfig, Ingredient> function, final ConditionalDisplay conditionalDisplay, final Predicate<NBTItem> check, final Function<NBTItem, PlayerIngredient> readIngredient) {
        super(s, function, conditionalDisplay);
        this.check = check;
        this.readIngredient = readIngredient;
    }
    
    public boolean check(final NBTItem nbtItem) {
        return this.check.test(nbtItem);
    }
    
    public PlayerIngredient readPlayerIngredient(final NBTItem nbtItem) {
        return this.readIngredient.apply(nbtItem);
    }
}
