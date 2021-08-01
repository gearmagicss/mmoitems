// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class VortexParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float radius;
    private final float r_speed;
    private final float y_speed;
    private final int amount;
    private double j;
    
    public VortexParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.radius = (float)particleData.getModifier("radius");
        this.y_speed = (float)particleData.getModifier("y-speed");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        final double n = this.j / 3.141592653589793 / 2.0;
        for (int i = 0; i < this.amount; ++i) {
            final double n2 = this.j + 6.283185307179586 * i / this.amount;
            this.particle.display(location.clone().add(Math.cos(n2) * this.radius * (1.0 - n * this.y_speed), n * this.y_speed * this.height, Math.sin(n2) * this.radius * (1.0 - n * this.y_speed)), this.speed);
        }
        this.j += 0.1308996938995747 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586 / this.y_speed) ? (6.283185307179586 / this.y_speed) : 0.0);
    }
}
