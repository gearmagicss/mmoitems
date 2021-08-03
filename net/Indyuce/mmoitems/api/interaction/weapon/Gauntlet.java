// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;
import org.bukkit.Particle;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.entity.LivingEntity;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Gauntlet extends Weapon
{
    public Gauntlet(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem);
    }
    
    public void specialAttack(final LivingEntity livingEntity) {
        if (!MMOItems.plugin.getConfig().getBoolean("item-ability.gauntlet.enabled")) {
            return;
        }
        if (!this.applyWeaponCosts(MMOItems.plugin.getConfig().getDouble("item-ability.gauntlet.cooldown"), PlayerData.CooldownType.SPECIAL_ATTACK)) {
            return;
        }
        livingEntity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, livingEntity.getLocation().add(0.0, 1.0, 0.0), 0);
        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 0.0f);
        livingEntity.removePotionEffect(PotionEffectType.BLINDNESS);
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
        livingEntity.setVelocity(this.getPlayer().getEyeLocation().getDirection().setY(0).normalize().setY(0.8));
        livingEntity.setVelocity(this.getPlayer().getEyeLocation().getDirection().setY(0).normalize().multiply(2).setY(0.3));
    }
}
