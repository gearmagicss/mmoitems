// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Collection;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.crafting.Layout;
import java.util.Map;

public class LayoutManager implements Reloadable
{
    private final Map<String, Layout> layouts;
    
    public LayoutManager() {
        this.layouts = new HashMap<String, Layout>();
    }
    
    @Override
    public void reload() {
        this.layouts.clear();
        for (final File file : new File(MMOItems.plugin.getDataFolder() + "/layouts").listFiles()) {
            try {
                final Layout layout = new Layout(file.getName().substring(0, file.getName().length() - 4), (FileConfiguration)YamlConfiguration.loadConfiguration(file));
                this.layouts.put(layout.getId(), layout);
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load layout '" + file.getName() + "': " + ex.getMessage());
            }
        }
    }
    
    public boolean hasLayout(final String s) {
        return this.layouts.containsKey(s);
    }
    
    public Collection<Layout> getLayouts() {
        return this.layouts.values();
    }
    
    public Layout getLayout(final String key) {
        return this.layouts.getOrDefault(key, this.layouts.get("default"));
    }
}
