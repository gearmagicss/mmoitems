// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import java.util.Set;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import com.google.gson.JsonElement;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.Map;
import net.Indyuce.mmoitems.api.ability.Ability;

public class AbilityData
{
    private final Ability ability;
    private final Ability.CastingMode castMode;
    private final Map<String, Double> modifiers;
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof AbilityData)) {
            return false;
        }
        if (((AbilityData)o).getCastingMode() != this.getCastingMode()) {
            return false;
        }
        if (!((AbilityData)o).getAbility().equals(this.getAbility())) {
            return false;
        }
        for (final String s : ((AbilityData)o).getModifiers()) {
            if (((AbilityData)o).getModifier(s) != this.getModifier(s)) {
                return false;
            }
        }
        return true;
    }
    
    public AbilityData(final JsonObject jsonObject) {
        this.modifiers = new HashMap<String, Double>();
        this.ability = MMOItems.plugin.getAbilities().getAbility(jsonObject.get("Id").getAsString());
        this.castMode = Ability.CastingMode.valueOf(jsonObject.get("CastMode").getAsString());
        jsonObject.getAsJsonObject("Modifiers").entrySet().forEach(entry -> this.setModifier(entry.getKey(), ((JsonElement)entry.getValue()).getAsDouble()));
    }
    
    public AbilityData(final ConfigurationSection configurationSection) {
        this.modifiers = new HashMap<String, Double>();
        Validate.isTrue(configurationSection.contains("type") && configurationSection.contains("mode"), "Ability is missing type or mode");
        final String replace = configurationSection.getString("type").toUpperCase().replace("-", "_").replace(" ", "_");
        Validate.isTrue(MMOItems.plugin.getAbilities().hasAbility(replace), "Could not find ability called '" + replace + "'");
        this.ability = MMOItems.plugin.getAbilities().getAbility(replace);
        this.castMode = Ability.CastingMode.valueOf(configurationSection.getString("mode").toUpperCase().replace("-", "_").replace(" ", "_"));
        Validate.isTrue(this.ability.isAllowedMode(this.castMode), "Ability " + this.ability.getID() + " does not support cast mode " + this.castMode.name());
        for (final String s : configurationSection.getKeys(false)) {
            if (!s.equalsIgnoreCase("mode") && !s.equalsIgnoreCase("type") && this.ability.getModifiers().contains(s)) {
                this.modifiers.put(s, configurationSection.getDouble(s));
            }
        }
    }
    
    public AbilityData(final Ability ability, final Ability.CastingMode castMode) {
        this.modifiers = new HashMap<String, Double>();
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
    
    public void setModifier(final String s, final double d) {
        this.modifiers.put(s, d);
    }
    
    public boolean hasModifier(final String s) {
        return this.modifiers.containsKey(s);
    }
    
    public double getModifier(final String s) {
        return this.modifiers.containsKey(s) ? this.modifiers.get(s) : this.ability.getDefaultValue(s);
    }
    
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", this.ability.getID());
        jsonObject.addProperty("CastMode", this.castMode.name());
        final JsonObject jsonObject2 = new JsonObject();
        this.modifiers.keySet().forEach(s -> jsonObject2.addProperty(s, (Number)this.getModifier(s)));
        jsonObject.add("Modifiers", (JsonElement)jsonObject2);
        return jsonObject;
    }
}
