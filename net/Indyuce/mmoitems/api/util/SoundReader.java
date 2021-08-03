// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Sound;

public class SoundReader
{
    private final Sound sound;
    private final String soundKey;
    
    public SoundReader(final String s, final Sound sound) {
        if (s.isEmpty()) {
            this.sound = sound;
            this.soundKey = "";
            return;
        }
        Sound value;
        String s2;
        try {
            value = Sound.valueOf(s);
            s2 = "";
        }
        catch (Exception ex) {
            value = null;
            s2 = s;
        }
        this.sound = value;
        this.soundKey = s2.toLowerCase();
    }
    
    public Sound getSound() {
        return this.sound;
    }
    
    public String getSoundKey() {
        return this.soundKey;
    }
    
    public void play(final Player player) {
        this.play(player, 1.0f, 1.0f);
    }
    
    public void play(final Player player, final float n, final float n2) {
        if (this.soundKey.isEmpty()) {
            player.playSound(player.getLocation(), this.sound, n, n2);
        }
        else {
            player.playSound(player.getLocation(), this.soundKey, n, n2);
        }
    }
    
    public void play(final Location location) {
        this.play(location, 1.0f, 1.0f);
    }
    
    public void play(final Location location, final float n, final float n2) {
        if (this.soundKey.isEmpty()) {
            location.getWorld().playSound(location, this.sound, n, n2);
        }
        else {
            location.getWorld().playSound(location, this.soundKey, n, n2);
        }
    }
}
