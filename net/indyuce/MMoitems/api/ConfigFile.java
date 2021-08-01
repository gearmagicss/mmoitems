// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import net.Indyuce.mmoitems.api.item.ItemReference;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigFile
{
    private final Plugin plugin;
    private final String path;
    private final String name;
    private final FileConfiguration config;
    
    public ConfigFile(final String s) {
        this((Plugin)MMOItems.plugin, "", s);
    }
    
    public ConfigFile(final Plugin plugin, final String s) {
        this(plugin, "", s);
    }
    
    public ConfigFile(final String s, final String s2) {
        this((Plugin)MMOItems.plugin, s, s2);
    }
    
    public ConfigFile(final Plugin plugin, final String s, final String s2) {
        this.plugin = plugin;
        this.path = s;
        this.name = s2;
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + s, s2 + ".yml"));
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public void save() {
        try {
            this.config.save(new File(this.plugin.getDataFolder() + this.path, this.name + ".yml"));
        }
        catch (IOException ex) {
            MMOItems.plugin.getLogger().log(Level.SEVERE, "Could not save " + this.name + ".yml: " + ex.getMessage());
        }
    }
    
    public void setup() {
        try {
            if (!new File(this.plugin.getDataFolder() + this.path).exists()) {
                new File(this.plugin.getDataFolder() + this.path).mkdir();
            }
            if (!new File(this.plugin.getDataFolder() + this.path, this.name + ".yml").exists()) {
                new File(this.plugin.getDataFolder() + this.path, this.name + ".yml").createNewFile();
            }
        }
        catch (IOException ex) {
            MMOItems.plugin.getLogger().log(Level.SEVERE, "Could not generate " + this.name + ".yml: " + ex.getMessage());
        }
    }
    
    public void registerTemplateEdition(final ItemReference itemReference) {
        this.save();
        MMOItems.plugin.getTemplates().requestTemplateUpdate(itemReference.getType(), itemReference.getId());
    }
}
