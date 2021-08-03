// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.droptable;

import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.droptable.item.BlockDropItem;
import net.Indyuce.mmoitems.api.droptable.item.MMOItemDropItem;
import net.Indyuce.mmoitems.api.droptable.item.DropItem;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nullable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Iterator;
import java.util.Collection;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class DropTable
{
    private static final Random random;
    private final List<String> subtablesList;
    private final Map<String, Subtable> subtables;
    
    public DropTable(final ConfigurationSection configurationSection) {
        this.subtablesList = new ArrayList<String>();
        this.subtables = new HashMap<String, Subtable>();
        Validate.notNull((Object)configurationSection, "Could not read the config");
        for (final String s : configurationSection.getKeys(false)) {
            try {
                for (int i = 0; i < configurationSection.getInt(s + ".coef"); ++i) {
                    this.subtablesList.add(s);
                }
                this.subtables.put(s, new Subtable(configurationSection.getConfigurationSection(s)));
            }
            catch (IllegalArgumentException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not read subtable '" + s + "' from drop table '" + configurationSection.getName() + "': " + ex.getMessage());
            }
        }
        Validate.notEmpty((Collection)this.subtablesList, "Your droptable must contain at least one subtable");
    }
    
    public String getRandomSubtable() {
        return this.subtablesList.get(DropTable.random.nextInt(this.subtablesList.size()));
    }
    
    public List<ItemStack> read(@Nullable final PlayerData playerData, final boolean b) {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (final DropItem dropItem : this.getSubtable(this.getRandomSubtable()).getDropItems(b)) {
            if (dropItem.rollDrop()) {
                final ItemStack item = dropItem.getItem(playerData);
                if (item == null) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Couldn't read the subtable item " + dropItem.getKey());
                }
                else {
                    list.add(item);
                }
            }
        }
        return list;
    }
    
    public Collection<Subtable> getSubtables() {
        return this.subtables.values();
    }
    
    public Subtable getSubtable(final String s) {
        return this.subtables.get(s);
    }
    
    static {
        random = new Random();
    }
    
    public static class Subtable
    {
        private final List<DropItem> items;
        private final boolean disableSilkTouch;
        
        public Subtable(final ConfigurationSection configurationSection) {
            this.items = new ArrayList<DropItem>();
            Validate.notNull((Object)configurationSection, "Could not read subtable config");
            Validate.isTrue(configurationSection.contains("coef"), "Could not read subtable coefficient.");
            Validate.isTrue(configurationSection.contains("items") || configurationSection.contains("blocks"), "Could not find item/block list");
            if (configurationSection.contains("items")) {
                for (final String s : configurationSection.getConfigurationSection("items").getKeys(false)) {
                    final Type orThrow = MMOItems.plugin.getTypes().getOrThrow(s.toUpperCase().replace("-", "_"));
                    for (final String str : configurationSection.getConfigurationSection("items." + s).getKeys(false)) {
                        this.items.add(new MMOItemDropItem(orThrow, str, configurationSection.getString("items." + s + "." + str)));
                    }
                }
            }
            if (configurationSection.contains("blocks")) {
                for (final String s2 : configurationSection.getConfigurationSection("blocks").getKeys(false)) {
                    final int int1 = Integer.parseInt(s2);
                    Validate.isTrue(int1 > 0 && int1 != 54 && int1 <= 160, int1 + " is not a valid block ID");
                    this.items.add(new BlockDropItem(int1, configurationSection.getString("blocks." + s2)));
                }
            }
            this.disableSilkTouch = configurationSection.getBoolean("disable-silk-touch");
        }
        
        public List<DropItem> getDropItems(final boolean b) {
            return (b && this.disableSilkTouch) ? new ArrayList<DropItem>() : this.items;
        }
    }
}
