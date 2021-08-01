// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import net.Indyuce.mmoitems.api.crafting.recipe.UpgradingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CraftingRecipe;
import net.Indyuce.mmoitems.api.crafting.recipe.CheckedRecipe;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.IngredientInventory;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import java.util.LinkedHashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import net.Indyuce.mmoitems.api.crafting.recipe.Recipe;
import java.util.Map;
import org.bukkit.Sound;
import io.lumine.mythic.lib.api.util.PostLoadObject;

public class CraftingStation extends PostLoadObject
{
    private final String id;
    private final String name;
    private final Layout layout;
    private final Sound sound;
    private final StationItemOptions itemOptions;
    private final int maxQueueSize;
    private final Map<String, Recipe> recipes;
    private CraftingStation parent;
    
    public CraftingStation(final String str, final FileConfiguration fileConfiguration) {
        super((ConfigurationSection)fileConfiguration);
        this.recipes = new LinkedHashMap<String, Recipe>();
        this.id = str.toLowerCase().replace("_", "-").replace(" ", "-");
        this.name = MythicLib.plugin.parseColors(fileConfiguration.getString("name", "Station"));
        this.layout = MMOItems.plugin.getLayouts().getLayout(fileConfiguration.getString("layout", "default"));
        this.sound = Sound.valueOf(fileConfiguration.getString("sound", "ENTITY_EXPERIENCE_ORB_PICKUP").toUpperCase());
        for (final String s : fileConfiguration.getConfigurationSection("recipes").getKeys(false)) {
            try {
                this.registerRecipe(this.loadRecipe(fileConfiguration.getConfigurationSection("recipes." + s)));
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.INFO, "An issue occurred registering recipe '" + s + "' from crafting station '" + str + "': " + ex.getMessage());
            }
        }
        this.itemOptions = new StationItemOptions(fileConfiguration.getConfigurationSection("items"));
        this.maxQueueSize = Math.max(1, Math.min(fileConfiguration.getInt("max-queue-size"), 64));
    }
    
    public CraftingStation(final String s, final String s2, final Layout layout, final Sound sound, final StationItemOptions itemOptions, final int maxQueueSize, final CraftingStation parent) {
        super((ConfigurationSection)null);
        this.recipes = new LinkedHashMap<String, Recipe>();
        Validate.notNull((Object)s, "Crafting station ID must not be null");
        Validate.notNull((Object)s2, "Crafting station name must not be null");
        Validate.notNull((Object)sound, "Crafting station sound must not be null");
        this.id = s.toLowerCase().replace("_", "-").replace(" ", "-");
        this.name = MythicLib.plugin.parseColors(s2);
        this.layout = layout;
        this.sound = sound;
        this.itemOptions = itemOptions;
        this.maxQueueSize = maxQueueSize;
        this.parent = parent;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Layout getLayout() {
        return this.layout;
    }
    
    public Sound getSound() {
        return this.sound;
    }
    
    public CraftingStation getParent() {
        return this.parent;
    }
    
    public Collection<Recipe> getRecipes() {
        if (this.parent == null) {
            return this.recipes.values();
        }
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<Recipe>(this.recipes.values());
        for (CraftingStation craftingStation = this.parent; craftingStation != null; craftingStation = craftingStation.getParent()) {
            list.addAll(craftingStation.getRecipes());
        }
        return (Collection<Recipe>)list;
    }
    
    public boolean hasRecipe(final String s) {
        return this.recipes.containsKey(s);
    }
    
    public Recipe getRecipe(final String s) {
        return this.recipes.get(s);
    }
    
    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }
    
    public List<CheckedRecipe> getAvailableRecipes(final PlayerData playerData, final IngredientInventory ingredientInventory) {
        final ArrayList<CheckedRecipe> list = new ArrayList<CheckedRecipe>();
        final Iterator<Recipe> iterator = this.getRecipes().iterator();
        while (iterator.hasNext()) {
            final CheckedRecipe evaluateRecipe = iterator.next().evaluateRecipe(playerData, ingredientInventory);
            if ((evaluateRecipe.areConditionsMet() || !evaluateRecipe.getRecipe().hasOption(Recipe.RecipeOption.HIDE_WHEN_LOCKED)) && (evaluateRecipe.allIngredientsHad() || !evaluateRecipe.getRecipe().hasOption(Recipe.RecipeOption.HIDE_WHEN_NO_INGREDIENTS))) {
                list.add(evaluateRecipe);
            }
        }
        return list;
    }
    
    public StationItemOptions getItemOptions() {
        return this.itemOptions;
    }
    
    public void registerRecipe(final Recipe recipe) {
        this.recipes.put(recipe.getId(), recipe);
    }
    
    public int getMaxPage() {
        return Math.max(1, (int)Math.ceil(this.recipes.size() / (double)this.getLayout().getRecipeSlots().size()));
    }
    
    protected void whenPostLoaded(final ConfigurationSection configurationSection) {
        if (configurationSection.contains("parent")) {
            final String replace = configurationSection.getString("parent").toLowerCase().replace(" ", "-").replace("_", "-");
            Validate.isTrue(!replace.equals(this.id), "Station cannot use itself as parent");
            Validate.isTrue(MMOItems.plugin.getCrafting().hasStation(replace), "Could not find parent station with ID '" + replace + "'");
            this.parent = MMOItems.plugin.getCrafting().getStation(replace);
        }
    }
    
    private Recipe loadRecipe(final ConfigurationSection configurationSection) {
        return configurationSection.contains("output") ? new CraftingRecipe(configurationSection) : new UpgradingRecipe(configurationSection);
    }
}
