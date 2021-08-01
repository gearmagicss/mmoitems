// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle.api;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ParticleRunnable extends BukkitRunnable
{
    protected final ParticleData particle;
    protected final PlayerData player;
    
    public ParticleRunnable(final ParticleData particle, final PlayerData player) {
        this.particle = particle;
        this.player = player;
    }
    
    public void run() {
        if (!this.player.isOnline()) {
            return;
        }
        this.createParticles();
    }
    
    public abstract void createParticles();
}
