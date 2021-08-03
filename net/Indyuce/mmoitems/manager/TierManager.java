// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.ItemTier;
import java.util.Map;

public class TierManager implements Reloadable
{
    private final Map<String, ItemTier> tiers;
    
    public TierManager() {
        this.tiers = new HashMap<String, ItemTier>();
        this.reload();
    }
    
    @Override
    public void reload() {
        this.tiers.clear();
        final ConfigFile configFile = new ConfigFile("item-tiers");
        for (final String str : configFile.getConfig().getKeys(false)) {
            try {
                this.register(new ItemTier(configFile.getConfig().getConfigurationSection(str)));
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load item tier '" + str + "': " + ex.getMessage());
            }
        }
    }
    
    public void register(final ItemTier itemTier) {
        this.tiers.put(itemTier.getId(), itemTier);
    }
    
    public boolean has(final String s) {
        return this.tiers.containsKey(s);
    }
    
    public ItemTier getOrThrow(final String str) {
        Validate.isTrue(this.tiers.containsKey(str), "Could not find tier with ID '" + str + "'");
        return this.tiers.get(str);
    }
    
    public ItemTier get(final String s) {
        return this.tiers.get(s);
    }
    
    public Collection<ItemTier> getAll() {
        return this.tiers.values();
    }
    
    public ItemTier findTier(final MMOItem mmoItem) {
        try {
            return mmoItem.hasData(ItemStats.TIER) ? this.get(mmoItem.getData(ItemStats.TIER).toString()) : null;
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
