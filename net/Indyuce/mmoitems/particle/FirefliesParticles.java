// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class FirefliesParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float radius;
    private final float r_speed;
    private final int amount;
    private double j;
    
    public FirefliesParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.radius = (float)particleData.getModifier("radius");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        for (int i = 0; i < this.amount; ++i) {
            final double n = this.j + 6.283185307179586 * i / this.amount;
            this.particle.display(location.clone().add(Math.cos(n) * this.radius, (double)this.height, Math.sin(n) * this.radius), this.speed);
        }
        this.j += 0.06544984694978735 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586) ? 6.283185307179586 : 0.0);
    }
}
