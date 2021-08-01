// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api;

import com.google.gson.JsonObject;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowParticles extends BukkitRunnable
{
    private final Arrow arrow;
    private final Particle particle;
    private final int amount;
    private final float offset;
    private final float speed;
    private final Color color;
    
    public ArrowParticles(final Arrow arrow, final NBTItem nbtItem) {
        this.arrow = arrow;
        final JsonObject asJsonObject = new JsonParser().parse(nbtItem.getString("MMOITEMS_ARROW_PARTICLES")).getAsJsonObject();
        this.particle = Particle.valueOf(asJsonObject.get("Particle").getAsString());
        this.amount = asJsonObject.get("Amount").getAsInt();
        this.offset = (float)asJsonObject.get("Offset").getAsDouble();
        final boolean asBoolean = asJsonObject.get("Colored").getAsBoolean();
        this.color = (asBoolean ? Color.fromRGB(asJsonObject.get("Red").getAsInt(), asJsonObject.get("Green").getAsInt(), asJsonObject.get("Blue").getAsInt()) : null);
        this.speed = (asBoolean ? 0.0f : asJsonObject.get("Speed").getAsFloat());
        this.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
    
    public void run() {
        if (this.arrow.isDead() || this.arrow.isOnGround()) {
            this.cancel();
            return;
        }
        if (this.color != null) {
            if (this.particle == Particle.REDSTONE) {
                this.arrow.getWorld().spawnParticle(this.particle, this.arrow.getLocation().add(0.0, 0.25, 0.0), this.amount, (double)this.offset, (double)this.offset, (double)this.offset, (Object)new Particle.DustOptions(this.color, 1.0f));
            }
            else if (this.particle == Particle.SPELL_MOB || this.particle == Particle.SPELL_MOB_AMBIENT) {
                for (int i = 0; i < this.amount; ++i) {
                    this.arrow.getWorld().spawnParticle(this.particle, this.arrow.getLocation().add(0.0, 0.25, 0.0), 0, (double)(this.color.getRed() / 255.0f), (double)(this.color.getGreen() / 255.0f), (double)(this.color.getBlue() / 255.0f), 1.0);
                }
            }
        }
        else {
            this.arrow.getWorld().spawnParticle(this.particle, this.arrow.getLocation().add(0.0, 0.25, 0.0), this.amount, (double)this.offset, (double)this.offset, (double)this.offset, (double)this.speed);
        }
    }
}
