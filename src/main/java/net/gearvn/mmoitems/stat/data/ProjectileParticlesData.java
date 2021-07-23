// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.google.gson.JsonObject;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class ProjectileParticlesData implements StatData, RandomStatData
{
    private final Particle particle;
    private final int red;
    private final int green;
    private final int blue;
    private final boolean colored;
    
    public ProjectileParticlesData(final Particle particle) {
        this.particle = particle;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.colored = false;
    }
    
    public ProjectileParticlesData(final Particle particle, final int red, final int green, final int blue) {
        this.particle = particle;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.colored = true;
    }
    
    public Particle getParticle() {
        return this.particle;
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
    
    public static boolean isColorable(final Particle particle) {
        return particle == Particle.REDSTONE || particle == Particle.SPELL_MOB || particle == Particle.SPELL_MOB_AMBIENT || particle == Particle.NOTE;
    }
    
    @Override
    public String toString() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Particle", this.particle.name());
        if (this.colored) {
            jsonObject.addProperty("Red", (Number)this.red);
            jsonObject.addProperty("Green", (Number)this.green);
            jsonObject.addProperty("Blue", (Number)this.blue);
        }
        return jsonObject.toString();
    }
    
    public static void shootParticle(final Player player, final Particle particle, final Location location, final double n, final double n2, final double n3) {
        if (isColorable(particle)) {
            switch (particle) {
                case REDSTONE: {
                    player.spawnParticle(Particle.REDSTONE, location, 25, (Object)new Particle.DustOptions(Color.fromRGB((int)n, (int)n2, (int)n3), 1.0f));
                    break;
                }
                case NOTE: {
                    player.spawnParticle(Particle.NOTE, location, 0, n / 24.0, 0.0, 0.0, 1.0);
                    break;
                }
                default: {
                    player.spawnParticle(particle, location, 0, n / 255.0, n2 / 255.0, n3 / 255.0, 1.0);
                    break;
                }
            }
        }
        else if (particle == Particle.ITEM_CRACK || particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST || particle == Particle.FALLING_DUST) {
            if (particle == Particle.ITEM_CRACK) {
                player.spawnParticle(particle, location, 0, (Object)new ItemStack(Material.STONE));
            }
            else {
                player.spawnParticle(particle, location, 0, (Object)Material.STONE.createBlockData());
            }
        }
        else {
            player.spawnParticle(particle, location, 0);
        }
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return this;
    }
}
