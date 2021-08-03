// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import org.bukkit.Location;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class GalaxyParticles extends ParticleRunnable
{
    private final float speed;
    private final float height;
    private final float r_speed;
    private final float y_coord;
    private final int amount;
    private double j;
    
    public GalaxyParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.j = 0.0;
        this.speed = (float)particleData.getModifier("speed") * 0.2f;
        this.height = (float)particleData.getModifier("height");
        this.r_speed = (float)particleData.getModifier("rotation-speed");
        this.y_coord = (float)particleData.getModifier("y-coord");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        final Location location = this.player.getPlayer().getLocation();
        for (int i = 0; i < this.amount; ++i) {
            final double n = this.j + 6.283185307179586 * i / this.amount;
            this.particle.display(location.clone().add(0.0, (double)this.height, 0.0), 0, (float)Math.cos(n), this.y_coord, (float)Math.sin(n), this.speed);
        }
        this.j += 0.1308996938995747 * this.r_speed;
        this.j -= ((this.j > 6.283185307179586) ? 6.283185307179586 : 0.0);
    }
}
