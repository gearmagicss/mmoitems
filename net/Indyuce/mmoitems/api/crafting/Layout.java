// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class Layout
{
    private final String id;
    private final List<Integer> recipeSlots;
    private final List<Integer> queueSlots;
    private final int size;
    private final int recipePreviousSlot;
    private final int recipeNextSlot;
    private final int queuePreviousSlot;
    private final int queueNextSlot;
    
    public Layout(final String id, final FileConfiguration fileConfiguration) {
        this.id = id;
        this.size = fileConfiguration.getInt("slots");
        final ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection("layout");
        this.recipeSlots = (List<Integer>)configurationSection.getIntegerList("recipe-slots");
        this.queueSlots = (List<Integer>)configurationSection.getIntegerList("queue-slots");
        this.recipePreviousSlot = configurationSection.getInt("recipe-previous-slot", 18);
        this.recipeNextSlot = configurationSection.getInt("recipe-next-slot", 26);
        this.queuePreviousSlot = configurationSection.getInt("queue-previous-slot", 37);
        this.queueNextSlot = configurationSection.getInt("queue-next-slot", 43);
    }
    
    public String getId() {
        return this.id;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public List<Integer> getRecipeSlots() {
        return this.recipeSlots;
    }
    
    public List<Integer> getQueueSlots() {
        return this.queueSlots;
    }
    
    public int getRecipePreviousSlot() {
        return this.recipePreviousSlot;
    }
    
    public int getRecipeNextSlot() {
        return this.recipeNextSlot;
    }
    
    public int getQueuePreviousSlot() {
        return this.queuePreviousSlot;
    }
    
    public int getQueueNextSlot() {
        return this.queueNextSlot;
    }
}
