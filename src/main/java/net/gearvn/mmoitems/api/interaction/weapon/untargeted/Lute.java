// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import io.lumine.mythic.lib.api.DamageType;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.ProjectileParticlesData;
import org.bukkit.Particle;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonObject;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.util.SoundReader;
import io.lumine.mythic.lib.version.VersionSound;
import net.Indyuce.mmoitems.stat.LuteAttackEffectStat;
import org.bukkit.util.Vector;
import net.Indyuce.mmoitems.api.interaction.util.UntargetedDurabilityItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.listener.ItemUse;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Lute extends UntargetedWeapon
{
    public Lute(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem, WeaponType.RIGHT_CLICK);
    }
    
    @Override
    public void untargetedAttack(final EquipmentSlot equipmentSlot) {
        if (!ItemUse.eitherHandSuccess(this.getPlayer(), this.getNBTItem(), equipmentSlot)) {
            return;
        }
        final PlayerStats.CachedStats temporary = this.getPlayerData().getStats().newTemporary();
        if (!this.applyWeaponCosts(1.0 / this.getValue(temporary.getStat(ItemStats.ATTACK_SPEED), MMOItems.plugin.getConfig().getDouble("default.attack-speed")), PlayerData.CooldownType.ATTACK)) {
            return;
        }
        final UntargetedDurabilityItem untargetedDurabilityItem = new UntargetedDurabilityItem(this.getPlayer(), this.getNBTItem(), equipmentSlot);
        if (untargetedDurabilityItem.isBroken()) {
            return;
        }
        if (untargetedDurabilityItem.isValid()) {
            untargetedDurabilityItem.decreaseDurability(1).update();
        }
        final double value = this.getValue(temporary.getStat(ItemStats.ATTACK_DAMAGE), 1.0);
        final double value2 = this.getValue(this.getNBTItem().getStat(ItemStats.RANGE.getId()), MMOItems.plugin.getConfig().getDouble("default.range"));
        final Vector vector = new Vector(0.0, -0.003 * this.getNBTItem().getStat(ItemStats.NOTE_WEIGHT.getId()), 0.0);
        final LuteAttackEffectStat.LuteAttackEffect value3 = LuteAttackEffectStat.LuteAttackEffect.get(this.getNBTItem());
        final SoundReader soundReader = new SoundReader(this.getNBTItem().getString("MMOITEMS_LUTE_ATTACK_SOUND"), VersionSound.BLOCK_NOTE_BLOCK_BELL.toSound());
        if (value3 != null) {
            value3.getAttack().handle(temporary, this.getNBTItem(), value, value2, vector, soundReader);
            return;
        }
        new BukkitRunnable() {
            final Vector vec = Lute.this.getPlayer().getEyeLocation().getDirection().multiply(0.4);
            final Location loc = Lute.this.getPlayer().getEyeLocation();
            int ti = 0;
            
            public void run() {
                if (this.ti++ > value2) {
                    this.cancel();
                }
                if (Lute.this.getNBTItem().hasTag("MMOITEMS_PROJECTILE_PARTICLES")) {
                    final JsonObject jsonObject = (JsonObject)MythicLib.plugin.getJson().parse(Lute.this.getNBTItem().getString("MMOITEMS_PROJECTILE_PARTICLES"), (Class)JsonObject.class);
                    final Particle value = Particle.valueOf(jsonObject.get("Particle").getAsString());
                    if (ProjectileParticlesData.isColorable(value)) {
                        ProjectileParticlesData.shootParticle(Lute.this.player, value, this.loc, Double.parseDouble(String.valueOf(jsonObject.get("Red"))), Double.parseDouble(String.valueOf(jsonObject.get("Green"))), Double.parseDouble(String.valueOf(jsonObject.get("Blue"))));
                    }
                    else {
                        ProjectileParticlesData.shootParticle(Lute.this.player, value, this.loc, 0.0, 0.0, 0.0);
                    }
                }
                else {
                    this.loc.getWorld().spawnParticle(Particle.NOTE, this.loc, 0, 1.0, 0.0, 0.0, 1.0);
                }
                soundReader.play(this.loc, 2.0f, (float)(0.5 + this.ti / value2));
                final List<Entity> nearbyChunkEntities = MMOUtils.getNearbyChunkEntities(this.loc);
                for (int i = 0; i < 3; ++i) {
                    this.loc.add(this.vec.add(vector));
                    if (this.loc.getBlock().getType().isSolid()) {
                        this.cancel();
                        break;
                    }
                    for (final Entity entity : nearbyChunkEntities) {
                        if (MMOUtils.canDamage(Lute.this.getPlayer(), this.loc, entity)) {
                            new ItemAttackResult(value, new DamageType[] { DamageType.WEAPON, DamageType.PROJECTILE, DamageType.MAGIC }).applyEffectsAndDamage(temporary, Lute.this.getNBTItem(), (LivingEntity)entity);
                            this.cancel();
                            return;
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
