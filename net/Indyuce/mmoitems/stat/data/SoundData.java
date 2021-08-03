// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import org.bukkit.configuration.ConfigurationSection;

public class SoundData
{
    private final String sound;
    private final double volume;
    private final double pitch;
    
    public SoundData(final String sound, final double volume, final double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    public SoundData(final Object o) {
        if (o instanceof String) {
            this.sound = o.toString();
            this.volume = 1.0;
            this.pitch = 1.0;
        }
        else {
            if (!(o instanceof ConfigurationSection)) {
                throw new IllegalArgumentException("You must provide a string or config section");
            }
            final ConfigurationSection configurationSection = (ConfigurationSection)o;
            this.sound = configurationSection.getString("sound");
            this.volume = configurationSection.getDouble("volume");
            this.pitch = configurationSection.getDouble("pitch");
        }
    }
    
    public String getSound() {
        return this.sound;
    }
    
    public double getVolume() {
        return this.volume;
    }
    
    public double getPitch() {
        return this.pitch;
    }
}
