// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ArrowParticlesData implements StatData, RandomStatData
{
    private final Particle particle;
    private final int amount;
    private final int red;
    private final int green;
    private final int blue;
    private final double speed;
    private final double offset;
    private final boolean colored;
    
    public ArrowParticlesData(final Particle particle, final int amount, final double offset, final double speed) {
        this.particle = particle;
        this.amount = amount;
        this.offset = offset;
        this.speed = speed;
        this.colored = false;
        this.red = 0;
        this.blue = 0;
        this.green = 0;
    }
    
    public ArrowParticlesData(final Particle particle, final int amount, final double offset, final int red, final int green, final int blue) {
        this.particle = particle;
        this.amount = amount;
        this.offset = offset;
        this.speed = 0.0;
        this.colored = true;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public Particle getParticle() {
        return this.particle;
    }
    
    public boolean isColored() {
        return this.colored;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public double getOffset() {
        return this.offset;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public int getRed() {
        return this.red;
    }
    
    public int getGreen() {
        return this.green;
    }
    
    public int getBlue() {
        return this.blue;
    }
    
    @Override
    public String toString() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Particle", this.particle.name());
        jsonObject.addProperty("Amount", (Number)this.amount);
        jsonObject.addProperty("Offset", (Number)this.offset);
        jsonObject.addProperty("Colored", Boolean.valueOf(this.colored));
        if (this.colored) {
            jsonObject.addProperty("Red", (Number)this.red);
            jsonObject.addProperty("Green", (Number)this.green);
            jsonObject.addProperty("Blue", (Number)this.blue);
        }
        else {
            jsonObject.addProperty("Speed", (Number)this.speed);
        }
        return jsonObject.toString();
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
