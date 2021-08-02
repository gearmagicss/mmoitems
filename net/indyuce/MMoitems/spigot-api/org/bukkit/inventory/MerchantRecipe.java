// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import java.util.Iterator;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

public class MerchantRecipe implements Recipe
{
    private ItemStack result;
    private List<ItemStack> ingredients;
    private int uses;
    private int maxUses;
    private boolean experienceReward;
    
    public MerchantRecipe(final ItemStack result, final int maxUses) {
        this(result, 0, maxUses, false);
    }
    
    public MerchantRecipe(final ItemStack result, final int uses, final int maxUses, final boolean experienceReward) {
        this.ingredients = new ArrayList<ItemStack>();
        this.result = result;
        this.uses = uses;
        this.maxUses = maxUses;
        this.experienceReward = experienceReward;
    }
    
    @Override
    public ItemStack getResult() {
        return this.result;
    }
    
    public void addIngredient(final ItemStack item) {
        Preconditions.checkState(this.ingredients.size() < 2, (Object)"Merchant can only have 2 ingredients");
        this.ingredients.add(item.clone());
    }
    
    public void removeIngredient(final int index) {
        this.ingredients.remove(index);
    }
    
    public void setIngredients(final List<ItemStack> ingredients) {
        this.ingredients = new ArrayList<ItemStack>();
        for (final ItemStack item : ingredients) {
            this.ingredients.add(item.clone());
        }
    }
    
    public List<ItemStack> getIngredients() {
        final List<ItemStack> copy = new ArrayList<ItemStack>();
        for (final ItemStack item : this.ingredients) {
            copy.add(item.clone());
        }
        return copy;
    }
    
    public int getUses() {
        return this.uses;
    }
    
    public void setUses(final int uses) {
        this.uses = uses;
    }
    
    public int getMaxUses() {
        return this.maxUses;
    }
    
    public void setMaxUses(final int maxUses) {
        this.maxUses = maxUses;
    }
    
    public boolean hasExperienceReward() {
        return this.experienceReward;
    }
    
    public void setExperienceReward(final boolean flag) {
        this.experienceReward = flag;
    }
}
