// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle.api;

import net.Indyuce.mmoitems.particle.AuraParticles;
import net.Indyuce.mmoitems.particle.HelixParticles;
import net.Indyuce.mmoitems.particle.DoubleRingsParticles;
import net.Indyuce.mmoitems.particle.GalaxyParticles;
import net.Indyuce.mmoitems.particle.VortexParticles;
import net.Indyuce.mmoitems.particle.FirefliesParticles;
import net.Indyuce.mmoitems.particle.OffsetParticles;
import java.util.Set;
import net.Indyuce.mmoitems.MMOUtils;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.util.StringValue;
import java.util.Map;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import java.util.function.BiFunction;

public enum ParticleType
{
    OFFSET((BiFunction<ParticleData, PlayerData, ParticleRunnable>)OffsetParticles::new, false, 5L, "Some particles randomly spawning around your body.", new StringValue[] { new StringValue("amount", 5.0), new StringValue("vertical-offset", 0.5), new StringValue("horizontal-offset", 0.3), new StringValue("speed", 0.0), new StringValue("height", 1.0) }), 
    FIREFLIES((BiFunction<ParticleData, PlayerData, ParticleRunnable>)FirefliesParticles::new, true, 1L, "Particles dashing around you at the same height.", new StringValue[] { new StringValue("amount", 3.0), new StringValue("speed", 0.0), new StringValue("rotation-speed", 1.0), new StringValue("radius", 1.3), new StringValue("height", 1.0) }), 
    VORTEX((BiFunction<ParticleData, PlayerData, ParticleRunnable>)VortexParticles::new, true, 1L, "Particles flying around you in a cone shape.", new StringValue[] { new StringValue("radius", 1.5), new StringValue("height", 2.4), new StringValue("speed", 0.0), new StringValue("y-speed", 1.0), new StringValue("rotation-speed", 1.0), new StringValue("amount", 3.0) }), 
    GALAXY((BiFunction<ParticleData, PlayerData, ParticleRunnable>)GalaxyParticles::new, true, 1L, "Particles flying around you in spiral arms.", new StringValue[] { new StringValue("height", 1.0), new StringValue("speed", 1.0), new StringValue("y-coord", 0.0), new StringValue("rotation-speed", 1.0), new StringValue("amount", 6.0) }), 
    DOUBLE_RINGS((BiFunction<ParticleData, PlayerData, ParticleRunnable>)DoubleRingsParticles::new, true, 1L, "Particles drawing two rings around you.", new StringValue[] { new StringValue("radius", 0.8), new StringValue("y-offset", 0.4), new StringValue("height", 1.0), new StringValue("speed", 0.0), new StringValue("rotation-speed", 1.0) }), 
    HELIX((BiFunction<ParticleData, PlayerData, ParticleRunnable>)HelixParticles::new, true, 1L, "Particles drawing a sphere around you.", new StringValue[] { new StringValue("radius", 0.8), new StringValue("height", 0.6), new StringValue("rotation-speed", 1.0), new StringValue("y-speed", 1.0), new StringValue("amount", 4.0), new StringValue("speed", 0.0) }), 
    AURA((BiFunction<ParticleData, PlayerData, ParticleRunnable>)AuraParticles::new, true, 1L, "Particles dashing around you (height can differ).", new StringValue[] { new StringValue("amount", 3.0), new StringValue("speed", 0.0), new StringValue("rotation-speed", 1.0), new StringValue("y-speed", 1.0), new StringValue("y-offset", 0.7), new StringValue("radius", 1.3), new StringValue("height", 1.0) });
    
    private final BiFunction<ParticleData, PlayerData, ParticleRunnable> func;
    private final boolean override;
    private final long period;
    private final String lore;
    private final Map<String, Double> modifiers;
    
    private ParticleType(final BiFunction<ParticleData, PlayerData, ParticleRunnable> func, final boolean override, final long period, final String lore, final StringValue[] array) {
        this.modifiers = new HashMap<String, Double>();
        this.func = func;
        this.override = override;
        this.period = period;
        this.lore = lore;
        for (final StringValue stringValue : array) {
            this.modifiers.put(stringValue.getName(), stringValue.getValue());
        }
    }
    
    public String getDefaultName() {
        return MMOUtils.caseOnWords(this.name().toLowerCase().replace("_", " "));
    }
    
    public double getModifier(final String s) {
        return this.modifiers.get(s);
    }
    
    public Set<String> getModifiers() {
        return this.modifiers.keySet();
    }
    
    public String getDescription() {
        return this.lore;
    }
    
    public boolean hasPriority() {
        return this.override;
    }
    
    public long getTime() {
        return this.period;
    }
    
    public ParticleRunnable newRunnable(final ParticleData particleData, final PlayerData playerData) {
        return this.func.apply(particleData, playerData);
    }
}
