// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.particle;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.particle.api.ParticleRunnable;

public class OffsetParticles extends ParticleRunnable
{
    private final float speed;
    private final float h_offset;
    private final float v_offset;
    private final float height;
    private final int amount;
    
    public OffsetParticles(final ParticleData particleData, final PlayerData playerData) {
        super(particleData, playerData);
        this.speed = (float)particleData.getModifier("speed");
        this.height = (float)particleData.getModifier("height");
        this.h_offset = (float)particleData.getModifier("horizontal-offset");
        this.v_offset = (float)particleData.getModifier("vertical-offset");
        this.amount = (int)particleData.getModifier("amount");
    }
    
    @Override
    public void createParticles() {
        this.particle.display(this.player.getPlayer().getLocation().add(0.0, (double)this.height, 0.0), this.amount, this.h_offset, this.v_offset, this.h_offset, this.speed);
    }
}
