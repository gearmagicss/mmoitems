// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient;

import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class CheckedIngredient
{
    @NotNull
    private final Ingredient ingredient;
    @Nullable
    private final Set<PlayerIngredient> found;
    private final boolean isHad;
    
    public CheckedIngredient(@NotNull final Ingredient ingredient, @Nullable final Set<PlayerIngredient> found) {
        this.ingredient = ingredient;
        this.found = found;
        this.isHad = (this.getTotalAmount() >= ingredient.getAmount());
    }
    
    public boolean isHad() {
        return this.isHad;
    }
    
    public int getTotalAmount() {
        int n = 0;
        final Iterator<PlayerIngredient> iterator = this.found.iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getAmount();
        }
        return n;
    }
    
    public void takeAway() {
        this.reduceItem(this.ingredient.getAmount());
    }
    
    public void reduceItem(int b) {
        final Iterator<PlayerIngredient> iterator = this.found.iterator();
        while (iterator.hasNext() && b > 0) {
            final ItemStack item = iterator.next().getItem();
            if (item.getAmount() <= 0) {
                iterator.remove();
            }
            else {
                final int min = Math.min(item.getAmount(), b);
                b -= min;
                item.setAmount(item.getAmount() - min);
            }
        }
    }
    
    @NotNull
    public Ingredient getIngredient() {
        return this.ingredient;
    }
    
    @Nullable
    public Set<PlayerIngredient> getFound() {
        return this.found;
    }
    
    @NotNull
    public String format() {
        return this.ingredient.formatDisplay(this.isHad() ? this.ingredient.getDisplay().getPositive() : this.ingredient.getDisplay().getNegative());
    }
}
