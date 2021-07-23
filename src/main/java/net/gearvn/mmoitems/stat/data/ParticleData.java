// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.Location;
import java.util.Set;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import com.google.gson.JsonElement;
import java.util.HashMap;
import com.google.gson.JsonObject;
import org.bukkit.Color;
import java.util.Map;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.particle.api.ParticleType;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ParticleData implements StatData, RandomStatData
{
    private final ParticleType type;
    private final Particle particle;
    private final Map<String, Double> modifiers;
    private final Color color;
    
    public ParticleData(final JsonObject jsonObject) {
        this.modifiers = new HashMap<String, Double>();
        this.particle = Particle.valueOf(jsonObject.get("Particle").getAsString());
        this.type = ParticleType.valueOf(jsonObject.get("Type").getAsString());
        if (jsonObject.has("Color")) {
            final JsonObject asJsonObject = jsonObject.getAsJsonObject("Color");
            this.color = Color.fromRGB(asJsonObject.get("Red").getAsInt(), asJsonObject.get("Green").getAsInt(), asJsonObject.get("Blue").getAsInt());
        }
        else {
            this.color = Color.fromRGB(255, 0, 0);
        }
        jsonObject.getAsJsonObject("Modifiers").entrySet().forEach(entry -> this.setModifier(entry.getKey(), ((JsonElement)entry.getValue()).getAsDouble()));
    }
    
    public ParticleData(final ConfigurationSection configurationSection) {
        this.modifiers = new HashMap<String, Double>();
        Validate.isTrue(configurationSection.contains("type") && configurationSection.contains("particle"), "Particle is missing type or selected particle.");
        this.type = ParticleType.valueOf(configurationSection.getString("type").toUpperCase().replace("-", "_").replace(" ", "_"));
        this.particle = Particle.valueOf(configurationSection.getString("particle").toUpperCase().replace("-", "_").replace(" ", "_"));
        this.color = (configurationSection.contains("color") ? Color.fromRGB(configurationSection.getInt("color.red"), configurationSection.getInt("color.green"), configurationSection.getInt("color.blue")) : Color.fromRGB(255, 0, 0));
        for (final String s : configurationSection.getKeys(false)) {
            if (!s.equalsIgnoreCase("particle") && !s.equalsIgnoreCase("type") && !s.equalsIgnoreCase("color")) {
                this.setModifier(s, configurationSection.getDouble(s));
            }
        }
    }
    
    public ParticleData(final ParticleType type, final Particle particle) {
        this.modifiers = new HashMap<String, Double>();
        this.type = type;
        this.particle = particle;
        this.color = Color.fromRGB(255, 0, 0);
    }
    
    public ParticleType getType() {
        return this.type;
    }
    
    public Particle getParticle() {
        return this.particle;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public double getModifier(final String s) {
        return this.modifiers.containsKey(s) ? this.modifiers.get(s) : this.type.getModifier(s);
    }
    
    public Set<String> getModifiers() {
        return this.modifiers.keySet();
    }
    
    public void setModifier(final String s, final double d) {
        this.modifiers.put(s, d);
    }
    
    public void display(final Location location, final float n) {
        this.display(location, 1, 0.0f, 0.0f, 0.0f, n);
    }
    
    public void display(final Location location, final int n, final float n2, final float n3, final float n4, final float n5) {
        if (this.particle == Particle.REDSTONE) {
            location.getWorld().spawnParticle(this.particle, location, n, (double)n2, (double)n3, (double)n4, (Object)new Particle.DustOptions(this.color, 1.0f));
        }
        else if (this.particle == Particle.SPELL_MOB || this.particle == Particle.SPELL_MOB_AMBIENT) {
            for (int i = 0; i < n; ++i) {
                location.getWorld().spawnParticle(this.particle, location, 0, (double)(this.color.getRed() / 255.0f), (double)(this.color.getGreen() / 255.0f), (double)(this.color.getBlue() / 255.0f), 1.0);
            }
        }
        else {
            location.getWorld().spawnParticle(this.particle, location, n, (double)n2, (double)n3, (double)n4, (double)n5);
        }
    }
    
    public ParticleRunnable start(final PlayerData playerData) {
        final ParticleRunnable runnable = this.type.newRunnable(this, playerData);
        runnable.runTaskTimer((Plugin)MMOItems.plugin, 0L, this.type.getTime());
        return runnable;
    }
    
    public JsonObject toJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Particle", this.getParticle().name());
        jsonObject.addProperty("Type", this.getType().name());
        if (isColorable(this.particle)) {
            final JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("Red", (Number)this.getColor().getRed());
            jsonObject2.addProperty("Green", (Number)this.getColor().getGreen());
            jsonObject2.addProperty("Blue", (Number)this.getColor().getBlue());
            jsonObject.add("Color", (JsonElement)jsonObject2);
        }
        final JsonObject jsonObject3 = new JsonObject();
        this.getModifiers().forEach(s -> jsonObject3.addProperty(s, (Number)this.getModifier(s)));
        jsonObject.add("Modifiers", (JsonElement)jsonObject3);
        return jsonObject;
    }
    
    public static boolean isColorable(final Particle particle) {
        return particle == Particle.REDSTONE || particle == Particle.SPELL_MOB || particle == Particle.SPELL_MOB_AMBIENT;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
