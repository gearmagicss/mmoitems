// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class DoubleRingsParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float radius;
    private final float r_speed;
    private final float y_offset;
    private double j;
    
    public DoubleRingsParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.radius = (float)particleData.getModifier("radius");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.y_offset = (float)particleData.getModifier("y-offset");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        for (double n = 0.0; n < 2.0; ++n) {
            final double n2 = this.j + n * 3.141592653589793;
            this.particle.display(location.clone().add(this.radius * Math.cos(n2), this.height + Math.sin(this.j) * this.y_offset, this.radius * Math.sin(n2)), this.speed);
        }
        this.j += 0.19634954084936207 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586) ? 6.283185307179586 : 0.0);
    }
}
