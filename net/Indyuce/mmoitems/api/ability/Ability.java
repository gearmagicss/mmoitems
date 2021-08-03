// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.ability;

import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.Set;
import org.apache.commons.lang.Validate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.List;

public abstract class Ability
{
    private final String name;
    private final String id;
    private final List<CastingMode> allowedModes;
    private final Map<String, Double> modifiers;
    private boolean enabled;
    protected static final Random random;
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Ability && ((Ability)o).getName().equals(this.getName());
    }
    
    public Ability(final CastingMode... a) {
        this.modifiers = new HashMap<String, Double>();
        this.enabled = true;
        this.id = this.getClass().getSimpleName().toUpperCase().replace("-", "_").replace(" ", "_").replaceAll("[^A-Z_]", "");
        this.name = this.getClass().getSimpleName().replace("_", " ");
        this.allowedModes = Arrays.asList(a);
    }
    
    public Ability(final String s, final String name, final CastingMode... a) {
        this.modifiers = new HashMap<String, Double>();
        this.enabled = true;
        Validate.notNull((Object)s, "Id cannot be null");
        Validate.notNull((Object)name, "Name cannot be null");
        this.id = s.toUpperCase().replace("-", "_").replace(" ", "_").replaceAll("[^A-Z_]", "");
        this.name = name;
        this.allowedModes = Arrays.asList(a);
    }
    
    public String getID() {
        return this.id;
    }
    
    public String getLowerCaseID() {
        return this.id.toLowerCase().replace("_", "-");
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public boolean isAllowedMode(final CastingMode castingMode) {
        return this.allowedModes.contains(castingMode);
    }
    
    public List<CastingMode> getSupportedCastingModes() {
        return this.allowedModes;
    }
    
    public double getDefaultValue(final String s) {
        return this.modifiers.get(s);
    }
    
    public Set<String> getModifiers() {
        return this.modifiers.keySet();
    }
    
    public void addModifier(final String s, final double d) {
        this.modifiers.put(s, d);
    }
    
    public void disable() {
        this.enabled = false;
    }
    
    public abstract AbilityResult whenRan(final PlayerStats.CachedStats p0, final LivingEntity p1, final AbilityData p2, final ItemAttackResult p3);
    
    public abstract void whenCast(final PlayerStats.CachedStats p0, final AbilityResult p1, final ItemAttackResult p2);
    
    static {
        random = new Random();
    }
    
    public enum CastingMode
    {
        ON_HIT(false), 
        WHEN_HIT(false), 
        LEFT_CLICK, 
        RIGHT_CLICK, 
        SHIFT_LEFT_CLICK, 
        SHIFT_RIGHT_CLICK;
        
        private final boolean message;
        
        private CastingMode() {
            this(true);
        }
        
        private CastingMode(final boolean message) {
            this.message = message;
        }
        
        public boolean displaysMessage() {
            return this.message;
        }
        
        public String getName() {
            return MMOUtils.caseOnWords(this.name().toLowerCase().replace("_", " "));
        }
        
        public String getLowerCaseId() {
            return this.name().toLowerCase().replace("_", "-");
        }
        
        public static CastingMode safeValueOf(final String anObject) {
            for (final CastingMode castingMode : values()) {
                if (castingMode.name().equals(anObject)) {
                    return castingMode;
                }
            }
            return null;
        }
    }
}
