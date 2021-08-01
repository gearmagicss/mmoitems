// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.apache.commons.lang.Validate;
import java.text.DecimalFormat;
import java.util.Random;
import net.Indyuce.mmoitems.stat.data.random.UpdatableRandomStatData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;

public class NumericStatFormula implements RandomStatData, UpdatableRandomStatData
{
    private final double base;
    private final double scale;
    private final double spread;
    private final double maxSpread;
    private static final Random RANDOM;
    private static final DecimalFormat DIGIT;
    public static final NumericStatFormula ZERO;
    public static boolean useRelativeSpread;
    
    public NumericStatFormula(final Object o) {
        Validate.notNull(o, "Config must not be null");
        if (o instanceof String) {
            final String[] split = o.toString().split(" ");
            this.base = Double.parseDouble(split[0]);
            this.scale = ((split.length > 1) ? Double.parseDouble(split[1]) : 0.0);
            this.spread = ((split.length > 2) ? Double.parseDouble(split[2]) : 0.0);
            this.maxSpread = ((split.length > 3) ? Double.parseDouble(split[3]) : 0.0);
            return;
        }
        if (o instanceof Number) {
            this.base = Double.parseDouble(o.toString());
            this.scale = 0.0;
            this.spread = 0.0;
            this.maxSpread = 0.0;
            return;
        }
        if (o instanceof ConfigurationSection) {
            final ConfigurationSection configurationSection = (ConfigurationSection)o;
            this.base = configurationSection.getDouble("base");
            this.scale = configurationSection.getDouble("scale");
            this.spread = configurationSection.getDouble("spread");
            this.maxSpread = configurationSection.getDouble("max-spread", 0.3);
            Validate.isTrue(this.spread >= 0.0, "Spread must be positive");
            Validate.isTrue(this.maxSpread >= 0.0, "Max spread must be positive");
            return;
        }
        throw new IllegalArgumentException("Must specify a config section, a string or a number");
    }
    
    public NumericStatFormula(final double base, final double scale, final double spread, final double maxSpread) {
        this.base = base;
        this.scale = scale;
        this.spread = spread;
        this.maxSpread = maxSpread;
    }
    
    public double getBase() {
        return this.base;
    }
    
    public double getScale() {
        return this.scale;
    }
    
    public double getSpread() {
        return this.spread;
    }
    
    public double getMaxSpread() {
        return this.maxSpread;
    }
    
    public double calculate(final double n) {
        return this.calculate(n, NumericStatFormula.RANDOM.nextGaussian());
    }
    
    public double calculate(final double n, final double n2) {
        if (NumericStatFormula.useRelativeSpread) {
            return (this.base + this.scale * n) * (1.0 + Math.min(Math.max(n2 * this.spread, -this.maxSpread), this.maxSpread));
        }
        return this.base + this.scale * n + Math.min(Math.max(n2 * this.spread, -this.maxSpread), this.maxSpread);
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new DoubleData(this.calculate(mmoItemBuilder.getLevel()));
    }
    
    public void fillConfigurationSection(final ConfigurationSection configurationSection, final String s, final FormulaSaveOption formulaSaveOption) {
        if (s == null) {
            throw new NullPointerException("Path was empty");
        }
        if (this.scale == 0.0 && this.spread == 0.0 && this.maxSpread == 0.0) {
            configurationSection.set(s, (Object)((this.base == 0.0 && formulaSaveOption == FormulaSaveOption.DELETE_IF_ZERO) ? null : Double.valueOf(this.base)));
        }
        else {
            configurationSection.set(s + ".base", (Object)this.base);
            configurationSection.set(s + ".scale", (Object)this.scale);
            configurationSection.set(s + ".spread", (Object)this.spread);
            configurationSection.set(s + ".max-spread", (Object)this.maxSpread);
        }
    }
    
    public void fillConfigurationSection(final ConfigurationSection configurationSection, final String s) {
        this.fillConfigurationSection(configurationSection, s, FormulaSaveOption.DELETE_IF_ZERO);
    }
    
    @Override
    public String toString() {
        if (this.scale == 0.0 && this.spread == 0.0) {
            return NumericStatFormula.DIGIT.format(this.base);
        }
        if (this.scale == 0.0) {
            return "[" + NumericStatFormula.DIGIT.format(this.base * (1.0 - this.maxSpread)) + " -> " + NumericStatFormula.DIGIT.format(this.base * (1.0 + this.maxSpread)) + "] (" + NumericStatFormula.DIGIT.format(this.spread * 100.0) + "% Spread) (" + NumericStatFormula.DIGIT.format(this.base) + " Avg)";
        }
        return "{Base=" + NumericStatFormula.DIGIT.format(this.base) + ((this.scale != 0.0) ? (",Scale=" + NumericStatFormula.DIGIT.format(this.scale)) : "") + ((this.spread != 0.0) ? (",Spread=" + this.spread) : "") + ((this.maxSpread != 0.0) ? (",Max=" + this.maxSpread) : "") + "}";
    }
    
    public static void reload() {
        NumericStatFormula.useRelativeSpread = !MMOItems.plugin.getConfig().getBoolean("additive-spread-formula", false);
    }
    
    @NotNull
    @Override
    public <T extends StatData> T reroll(@NotNull final ItemStat itemStat, @NotNull final T t, final int n) {
        final double n2 = this.getBase() + this.getScale() * n;
        final double n3 = ((DoubleData)t).getValue() - n2;
        double n4 = Math.abs(n3 / this.getSpread());
        if (NumericStatFormula.useRelativeSpread) {
            n4 = Math.abs(n3 / (this.getSpread() * n2));
        }
        if (n4 > this.getMaxSpread() || n4 > 3.5) {
            double n5 = this.getSpread() * Math.min(2.0, this.getMaxSpread());
            if (n3 < 0.0) {
                n5 *= -1.0;
            }
            return (T)new DoubleData(n5 + n2);
        }
        return (T)((Mergeable)t).cloneData();
    }
    
    static {
        RANDOM = new Random();
        DIGIT = new DecimalFormat("0.####");
        ZERO = new NumericStatFormula(0.0, 0.0, 0.0, 0.0);
    }
    
    public enum FormulaSaveOption
    {
        DELETE_IF_ZERO, 
        NONE;
        
        private static /* synthetic */ FormulaSaveOption[] $values() {
            return new FormulaSaveOption[] { FormulaSaveOption.DELETE_IF_ZERO, FormulaSaveOption.NONE };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
