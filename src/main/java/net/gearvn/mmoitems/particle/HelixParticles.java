// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class HelixParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float radius;
    private final float r_speed;
    private final float y_speed;
    private final int amount;
    private double j;
    
    public HelixParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.radius = (float)particleData.getModifier("radius");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.y_speed = (float)particleData.getModifier("y-speed");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        for (double n = 0.0; n < this.amount; ++n) {
            final double n2 = this.j + n * 3.141592653589793 * 2.0 / this.amount;
            this.particle.display(location.clone().add(Math.cos(n2) * Math.cos(this.j * this.y_speed) * this.radius, 1.0 + Math.sin(this.j * this.y_speed) * this.height, Math.sin(n2) * Math.cos(this.j * this.y_speed) * this.radius), this.speed);
        }
        this.j += 0.1308996938995747 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586 / this.y_speed) ? (6.283185307179586 / this.y_speed) : 0.0);
    }
}
