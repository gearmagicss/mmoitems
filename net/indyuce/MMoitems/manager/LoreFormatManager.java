// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Collection;
import java.util.logging.Level;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoreFormatManager implements Reloadable
{
    private final Map<String, List<String>> formats;
    
    public LoreFormatManager() {
        this.formats = new HashMap<String, List<String>>();
    }
    
    @Override
    public void reload() {
        this.formats.clear();
        for (final File file : new File(MMOItems.plugin.getDataFolder() + "/language/lore-formats").listFiles()) {
            try {
                final YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(file);
                Validate.isTrue(loadConfiguration.isList("lore-format"), "Invalid lore-format! (" + file.getName() + ")");
                this.formats.put(file.getName().substring(0, file.getName().length() - 4), loadConfiguration.getStringList("lore-format"));
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load layout '" + file.getName() + "': " + ex.getMessage());
            }
        }
    }
    
    public boolean hasFormat(final String s) {
        return this.formats.containsKey(s);
    }
    
    public Collection<List<String>> getFormats() {
        return this.formats.values();
    }
    
    public List<String> getFormat(final String s) {
        if (!this.hasFormat(s)) {
            return MMOItems.plugin.getLanguage().getDefaultLoreFormat();
        }
        return this.formats.get(s);
    }
}
