// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data.random;

import net.Indyuce.mmoitems.stat.data.AbilityData;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import java.util.Set;
import java.util.Iterator;
import net.Indyuce.mmoitems.MMOItems;
import org.apache.commons.lang.Validate;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import java.util.Map;
import net.Indyuce.mmoitems.api.ability.Ability;

public class RandomAbilityData
{
    private final Ability ability;
    private final Ability.CastingMode castMode;
    private final Map<String, NumericStatFormula> modifiers;
    
    public RandomAbilityData(final ConfigurationSection configurationSection) {
        this.modifiers = new HashMap<String, NumericStatFormula>();
        Validate.isTrue(configurationSection.contains("type") && configurationSection.contains("mode"), "Ability is missing type or mode");
        final String replace = configurationSection.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getAbilities().hasAbility(replace), "Could not find ability called '" + replace + "'");
        this.ability = MMOItems.plugin.getAbilities().getAbility(replace);
        this.castMode = Ability.CastingMode.valueOf(configurationSection.getString("mode").toUpperCase().replace("-", "_").replace(" ", "_"));
        Validate.isTrue(this.ability.isAllowedMode(this.castMode), "Ability " + this.ability.getID() + " does not support cast mode " + this.castMode.name());
        for (final String s : configurationSection.getKeys(false)) {
            if (!s.equalsIgnoreCase("mode") && !s.equalsIgnoreCase("type") && this.ability.getModifiers().contains(s)) {
                this.modifiers.put(s, new NumericStatFormula(configurationSection.get(s)));
            }
        }
    }
    
    public RandomAbilityData(final Ability ability, final Ability.CastingMode castMode) {
        this.modifiers = new HashMap<String, NumericStatFormula>();
        this.ability = ability;
        this.castMode = castMode;
    }
    
    public Ability getAbility() {
        return this.ability;
    }
    
    public Ability.CastingMode getCastingMode() {
        return this.castMode;
    }
    
    public Set<String> getModifiers() {
        return this.modifiers.keySet();
    }
    
    public void setModifier(final String s, final NumericStatFormula numericStatFormula) {
        this.modifiers.put(s, numericStatFormula);
    }
    
    public boolean hasModifier(final String s) {
        return this.modifiers.containsKey(s);
    }
    
    public NumericStatFormula getModifier(final String s) {
        return this.modifiers.get(s);
    }
    
    public AbilityData randomize(final MMOItemBuilder mmoItemBuilder) {
        final AbilityData abilityData = new AbilityData(this.ability, this.castMode);
        this.modifiers.forEach((s, numericStatFormula) -> abilityData.setModifier(s, numericStatFormula.calculate(mmoItemBuilder.getLevel())));
        return abilityData;
    }
}
