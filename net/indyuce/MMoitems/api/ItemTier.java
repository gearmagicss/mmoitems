// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import org.bukkit.Bukkit;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Random;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import net.Indyuce.mmoitems.comp.itemglow.TierColor;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.api.droptable.DropTable;
import org.jetbrains.annotations.NotNull;

public class ItemTier
{
    @NotNull
    private final String name;
    @NotNull
    private final String id;
    @NotNull
    private final UnidentificationInfo unidentificationInfo;
    @Nullable
    private final DropTable deconstruct;
    @Nullable
    private TierColor color;
    private boolean hint;
    private final double chance;
    @Nullable
    private final NumericStatFormula capacity;
    @NotNull
    private static final Random RANDOM;
    private static final boolean GLOW;
    
    public ItemTier(@NotNull final ConfigurationSection configurationSection) {
        this.color = null;
        this.hint = false;
        this.id = configurationSection.getName().toUpperCase().replace("-", "_");
        this.name = MythicLib.plugin.parseColors(configurationSection.getString("name"));
        this.deconstruct = (configurationSection.contains("deconstruct-item") ? new DropTable(configurationSection.getConfigurationSection("deconstruct-item")) : null);
        final ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection("unidentification");
        if (configurationSection2 == null) {
            this.unidentificationInfo = this.getDefaultUnident();
        }
        else {
            this.unidentificationInfo = new UnidentificationInfo(configurationSection2);
        }
        try {
            final ConfigurationSection configurationSection3 = configurationSection.getConfigurationSection("item-glow");
            if (configurationSection3 != null) {
                this.hint = configurationSection3.getBoolean("hint");
                this.color = new TierColor(configurationSection.getString("color", "WHITE"), ItemTier.GLOW);
            }
        }
        catch (NoClassDefFoundError | IllegalAccessException | NoSuchFieldException | SecurityException noClassDefFoundError) {
            final SecurityException ex2;
            final SecurityException ex = ex2;
            this.hint = false;
            this.color = null;
            MMOItems.print(null, "Could not load glow color for tier $r{0}$b;$f {1}", "Tier Hints", this.id, ex.getMessage());
        }
        this.chance = configurationSection.getDouble("generation.chance");
        this.capacity = (configurationSection.contains("generation.capacity") ? new NumericStatFormula(configurationSection.getConfigurationSection("generation.capacity")) : null);
    }
    
    @NotNull
    public String getId() {
        return this.id;
    }
    
    @NotNull
    public String getName() {
        return this.name;
    }
    
    public boolean hasDropTable() {
        return this.deconstruct != null;
    }
    
    @Nullable
    public DropTable getDropTable() {
        return this.deconstruct;
    }
    
    public boolean hasColor() {
        return this.color != null;
    }
    
    @Nullable
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
    
    @Nullable
    public NumericStatFormula getModifierCapacity() {
        return this.capacity;
    }
    
    @NotNull
    public UnidentificationInfo getUnidentificationInfo() {
        return this.unidentificationInfo;
    }
    
    public List<ItemStack> getDeconstructedLoot(@NotNull final PlayerData playerData) {
        return this.hasDropTable() ? this.deconstruct.read(playerData, false) : new ArrayList<ItemStack>();
    }
    
    @NotNull
    private UnidentificationInfo getDefaultUnident() {
        return new UnidentificationInfo("Unidentified Item", "Unknown", 0);
    }
    
    private String color(@Nullable final String s) {
        return MythicLib.plugin.parseColors(s);
    }
    
    static {
        RANDOM = new Random();
        GLOW = (Bukkit.getPluginManager().getPlugin("GlowAPI") != null);
    }
    
    public class UnidentificationInfo
    {
        @NotNull
        private final String unidentificationName;
        @NotNull
        private final String prefix;
        private final int range;
        public static final String UNIDENT_NAME = "Unidentified Item";
        public static final String UNIDENT_PREFIX = "Unknown";
        
        public UnidentificationInfo(@NotNull final ItemTier itemTier, final ConfigurationSection configurationSection) {
            this(itemTier, itemTier.color(configurationSection.getString("name", "Unidentified Item")), itemTier.color(configurationSection.getString("prefix", "Unknown")), configurationSection.getInt("range"));
        }
        
        public UnidentificationInfo(@NotNull final String unidentificationName, final String prefix, final int range) {
            this.unidentificationName = unidentificationName;
            this.prefix = prefix;
            this.range = range;
        }
        
        @NotNull
        public String getPrefix() {
            return this.prefix;
        }
        
        @NotNull
        public String getDisplayName() {
            return this.unidentificationName;
        }
        
        public int[] calculateRange(final int n) {
            final int n2 = (int)Math.max(1.0, n - this.range * ItemTier.RANDOM.nextDouble());
            return new int[] { n2, n2 + this.range };
        }
    }
}
