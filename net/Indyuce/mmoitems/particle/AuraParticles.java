// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class AuraParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float radius;
    private final float r_speed;
    private final float y_offset;
    private final float y_speed;
    private final int amount;
    private double j;
    
    public AuraParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.radius = (float)particleData.getModifier("radius");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.y_speed = (float)particleData.getModifier("y-speed");
        this.y_offset = (float)particleData.getModifier("y-offset");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        for (int i = 0; i < this.amount; ++i) {
            final double n = this.j + 6.283185307179586 * i / this.amount;
            this.particle.display(location.clone().add(Math.cos(n) * this.radius, Math.sin(this.j * this.y_speed * 3.0) * this.y_offset + this.height, Math.sin(n) * this.radius), this.speed);
        }
        this.j += 0.06544984694978735 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586 / this.y_speed) ? (6.283185307179586 / this.y_speed) : 0.0);
    }
}
