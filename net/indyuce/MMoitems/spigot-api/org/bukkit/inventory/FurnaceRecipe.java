// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.inventory;

import org.bukkit.material.MaterialData;
import org.bukkit.Material;

public class FurnaceRecipe implements Recipe
{
    private ItemStack output;
    private ItemStack ingredient;
    private float experience;
    
    public FurnaceRecipe(final ItemStack result, final Material source) {
        this(result, source, 0, 0.0f);
    }
    
    public FurnaceRecipe(final ItemStack result, final MaterialData source) {
        this(result, source.getItemType(), source.getData(), 0.0f);
    }
    
    public FurnaceRecipe(final ItemStack result, final MaterialData source, final float experience) {
        this(result, source.getItemType(), source.getData(), experience);
    }
    
    @Deprecated
    public FurnaceRecipe(final ItemStack result, final Material source, final int data) {
        this(result, source, data, 0.0f);
    }
    
    @Deprecated
    public FurnaceRecipe(final ItemStack result, final Material source, final int data, final float experience) {
        this.output = new ItemStack(result);
        this.ingredient = new ItemStack(source, 1, (short)data);
        this.experience = experience;
    }
    
    public FurnaceRecipe setInput(final MaterialData input) {
        return this.setInput(input.getItemType(), input.getData());
    }
    
    public FurnaceRecipe setInput(final Material input) {
        return this.setInput(input, 0);
    }
    
    @Deprecated
    public FurnaceRecipe setInput(final Material input, final int data) {
        this.ingredient = new ItemStack(input, 1, (short)data);
        return this;
    }
    
    public ItemStack getInput() {
        return this.ingredient.clone();
    }
    
    @Override
    public ItemStack getResult() {
        return this.output.clone();
    }
    
    public void setExperience(final float experience) {
        this.experience = experience;
    }
    
    public float getExperience() {
        return this.experience;
    }
}
