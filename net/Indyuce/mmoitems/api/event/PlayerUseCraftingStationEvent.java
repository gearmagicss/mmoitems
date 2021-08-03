// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.event;

import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.HandlerList;
import net.Indyuce.mmoitems.api.crafting.CraftingStation;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.Recipe;

public class PlayerUseCraftingStationEvent extends PlayerDataEvent
{
    private final Recipe recipe;
    private final CheckedRecipe recipeInfo;
    private final CraftingStation station;
    private final StationAction action;
    private static final HandlerList handlers;
    
    public PlayerUseCraftingStationEvent(final PlayerData playerData, final CraftingStation craftingStation, final CheckedRecipe checkedRecipe, final StationAction stationAction) {
        this(playerData, craftingStation, checkedRecipe, checkedRecipe.getRecipe(), stationAction);
    }
    
    public PlayerUseCraftingStationEvent(final PlayerData playerData, final CraftingStation craftingStation, final Recipe recipe, final StationAction stationAction) {
        this(playerData, craftingStation, null, recipe, stationAction);
    }
    
    private PlayerUseCraftingStationEvent(final PlayerData playerData, final CraftingStation station, final CheckedRecipe recipeInfo, final Recipe recipe, final StationAction action) {
        super(playerData);
        this.recipeInfo = recipeInfo;
        this.recipe = recipe;
        this.station = station;
        this.action = action;
    }
    
    public CraftingStation getStation() {
        return this.station;
    }
    
    public CheckedRecipe getRecipeInfo() {
        Validate.notNull((Object)this.recipeInfo, "No recipe info is provided when a player claims an item in the crafting queue");
        return this.recipeInfo;
    }
    
    public boolean hasRecipeInfo() {
        return this.recipeInfo != null;
    }
    
    public Recipe getRecipe() {
        return this.recipe;
    }
    
    public StationAction getInteraction() {
        return this.action;
    }
    
    @Deprecated
    public boolean isInstant() {
        return this.recipe instanceof CraftingRecipe && ((CraftingRecipe)this.recipe).isInstant();
    }
    
    public HandlerList getHandlers() {
        return PlayerUseCraftingStationEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerUseCraftingStationEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum StationAction
    {
        INTERACT_WITH_RECIPE, 
        INSTANT_RECIPE, 
        CRAFTING_QUEUE, 
        CANCEL_QUEUE;
    }
}
