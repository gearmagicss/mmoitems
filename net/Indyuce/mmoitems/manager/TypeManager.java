// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import net.Indyuce.mmoitems.stat.type.ItemStat;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.ConfigFile;
import java.util.LinkedHashMap;
import net.Indyuce.mmoitems.api.Type;
import java.util.Map;

public class TypeManager implements Reloadable
{
    private final Map<String, Type> map;
    
    public TypeManager() {
        this.map = new LinkedHashMap<String, Type>();
    }
    
    @Override
    public void reload() {
        this.map.clear();
        this.registerAll(Type.ACCESSORY, Type.ARMOR, Type.BLOCK, Type.BOW, Type.CATALYST, Type.CONSUMABLE, Type.CROSSBOW, Type.DAGGER, Type.GAUNTLET, Type.GEM_STONE, Type.SKIN, Type.HAMMER, Type.LUTE, Type.MISCELLANEOUS, Type.MUSKET, Type.OFF_CATALYST, Type.ORNAMENT, Type.SPEAR, Type.STAFF, Type.SWORD, Type.TOOL, Type.WHIP);
        ConfigManager.DefaultFile.ITEM_TYPES.checkFile();
        final ConfigFile configFile = new ConfigFile("item-types");
        for (final String str : configFile.getConfig().getKeys(false)) {
            if (!this.map.containsKey(str)) {
                try {
                    this.register(new Type(this, configFile.getConfig().getConfigurationSection(str)));
                }
                catch (IllegalArgumentException ex) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not register type '" + str + "': " + ex.getMessage());
                }
            }
        }
        final Iterator<Type> iterator2 = this.map.values().iterator();
        while (iterator2.hasNext()) {
            final Type type = iterator2.next();
            try {
                type.load(configFile.getConfig().getConfigurationSection(type.getId()));
            }
            catch (IllegalArgumentException ex2) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not register type '" + type.getId() + "': " + ex2.getMessage());
                iterator2.remove();
                continue;
            }
            type.getAvailableStats().clear();
            MMOItems.plugin.getStats().getAll().stream().filter(itemStat -> itemStat.isCompatible(type)).forEach(itemStat2 -> type.getAvailableStats().add(itemStat2));
        }
    }
    
    public void register(final Type type) {
        this.map.put(type.getId(), type);
    }
    
    public void registerAll(final Type... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.register(array[i]);
        }
    }
    
    @Nullable
    public Type get(@Nullable final String s) {
        if (s == null) {
            return null;
        }
        return this.map.get(s);
    }
    
    public Type getOrThrow(final String str) {
        Validate.isTrue(this.map.containsKey(str), "Could not find item type with ID '" + str + "'");
        return this.map.get(str);
    }
    
    public boolean has(final String s) {
        return this.map.containsKey(s);
    }
    
    public Collection<Type> getAll() {
        return this.map.values();
    }
    
    public ArrayList<String> getAllTypeNames() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<Type> iterator = this.getAll().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getId());
        }
        return list;
    }
}
