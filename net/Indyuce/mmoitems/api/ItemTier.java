// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.Bukkit;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Random;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.comp.itemglow.TierColor;
import net.Indyuce.mmoitems.api.droptable.DropTable;

public class ItemTier
{
    private final String name;
    private final String id;
    private final UnidentificationInfo unidentificationInfo;
    private final DropTable deconstruct;
    private final TierColor color;
    private final boolean hint;
    private final double chance;
    private final NumericStatFormula capacity;
    private static final Random RANDOM;
    private static final boolean GLOW;
    
    public ItemTier(final ConfigurationSection configurationSection) {
        this.id = configurationSection.getName().toUpperCase().replace("-", "_");
        this.name = MythicLib.plugin.parseColors(configurationSection.getString("name"));
        this.deconstruct = (configurationSection.contains("deconstruct-item") ? new DropTable(configurationSection.getConfigurationSection("deconstruct-item")) : null);
        this.unidentificationInfo = new UnidentificationInfo(configurationSection.getConfigurationSection("unidentification"));
        try {
            this.hint = (configurationSection.contains("item-glow") && configurationSection.getBoolean("item-glow.hint"));
            this.color = (configurationSection.contains("item-glow") ? new TierColor(configurationSection.getString("item-glow.color"), ItemTier.GLOW) : null);
        }
        catch (NoClassDefFoundError | IllegalAccessException | NoSuchFieldException | SecurityException noClassDefFoundError) {
            final Object o;
            throw new IllegalArgumentException("Could not load tier color: " + ((Throwable)o).getMessage());
        }
        this.chance = configurationSection.getDouble("generation.chance");
        this.capacity = (configurationSection.contains("generation.capacity") ? new NumericStatFormula(configurationSection.getConfigurationSection("generation.capacity")) : null);
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasDropTable() {
        return this.deconstruct != null;
    }
    
    public DropTable getDropTable() {
        return this.deconstruct;
    }
    
    public boolean hasColor() {
        return this.color != null;
    }
    
    public TierColor getColor() {
        return this.color;
    }
    
    public boolean isHintEnabled() {
        return this.hint;
    }
    
    public double getGenerationChance() {
        return this.chance;
    }
    
    public boolean hasCapacity() {
        return this.capacity != null;
    }
    
    public NumericStatFormula getModifierCapacity() {
        return this.capacity;
    }
    
    public UnidentificationInfo getUnidentificationInfo() {
        return this.unidentificationInfo;
    }
    
    public List<ItemStack> getDeconstructedLoot(final PlayerData playerData) {
        return this.hasDropTable() ? this.deconstruct.read(playerData, false) : new ArrayList<ItemStack>();
    }
    
    private String color(final String s) {
        return MythicLib.plugin.parseColors(s);
    }
    
    static {
        RANDOM = new Random();
        GLOW = (Bukkit.getPluginManager().getPlugin("GlowAPI") != null);
    }
    
    public class UnidentificationInfo
    {
        private final String name;
        private final String prefix;
        private final int range;
        
        public UnidentificationInfo(final ItemTier itemTier, final ConfigurationSection configurationSection) {
            this(itemTier, itemTier.color(configurationSection.getString("name")), itemTier.color(configurationSection.getString("prefix")), configurationSection.getInt("range"));
        }
        
        public UnidentificationInfo(final String name, final String prefix, final int range) {
            this.name = name;
            this.prefix = prefix;
            this.range = range;
        }
        
        public String getPrefix() {
            return this.prefix;
        }
        
        public String getDisplayName() {
            return this.name;
        }
        
        public int[] calculateRange(final int n) {
            final int n2 = (int)Math.max(1.0, n - this.range * ItemTier.RANDOM.nextDouble());
            return new int[] { n2, n2 + this.range };
        }
    }
}
