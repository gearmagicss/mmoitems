// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ItemSet;
import java.util.Map;

public class SetManager implements Reloadable
{
    private final Map<String, ItemSet> itemSets;
    
    public SetManager() {
        this.itemSets = new HashMap<String, ItemSet>();
        this.reload();
    }
    
    @Override
    public void reload() {
        this.itemSets.clear();
        final ConfigFile configFile = new ConfigFile("item-sets");
        for (final String str : configFile.getConfig().getKeys(false)) {
            try {
                this.itemSets.put(str, new ItemSet(configFile.getConfig().getConfigurationSection(str)));
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load item set '" + str + "': " + ex.getMessage());
            }
        }
    }
    
    public void register(final ItemSet set) {
        this.itemSets.put(set.getId(), set);
    }
    
    public boolean has(final String s) {
        return this.itemSets.containsKey(s);
    }
    
    public Collection<ItemSet> getAll() {
        return this.itemSets.values();
    }
    
    public ItemSet get(final String key) {
        return this.itemSets.getOrDefault(key, null);
    }
}
