// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import java.util.Iterator;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.entity.Entity;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.util.NoClipItem;
import com.google.gson.JsonElement;
import io.lumine.mythic.lib.MythicLib;
import com.google.gson.JsonArray;
import org.bukkit.Material;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Item_Throw extends Ability implements Listener
{
    public Item_Throw() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("damage", 6.0);
        this.addModifier("force", 1.0);
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new VectorAbilityResult(abilityData, cachedStats.getPlayer(), livingEntity);
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final ItemStack clone = cachedStats.getPlayer().getInventory().getItemInMainHand().clone();
        final NBTItem value = NBTItem.get(clone);
        if (clone.getType() == Material.AIR || !value.hasType()) {
            itemAttackResult.setSuccessful(false);
            return;
        }
        boolean b = false;
        for (final JsonElement jsonElement : (JsonArray)MythicLib.plugin.getJson().parse(value.getString("MMOITEMS_ABILITY"), (Class)JsonArray.class)) {
            if (!jsonElement.isJsonObject()) {
                continue;
            }
            if (jsonElement.getAsJsonObject().get("Id").getAsString().equalsIgnoreCase(this.getID())) {
                b = true;
                break;
            }
        }
        if (!b) {
            return;
        }
        final NoClipItem noClipItem = new NoClipItem(cachedStats.getPlayer().getLocation().add(0.0, 1.2, 0.0), clone);
        noClipItem.getEntity().setVelocity(((VectorAbilityResult)abilityResult).getTarget().multiply(1.5 * abilityResult.getModifier("force")));
        cachedStats.getPlayer().getWorld().playSound(cachedStats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 0.0f);
        new BukkitRunnable() {
            double ti = 0.0;
            
            public void run() {
                ++this.ti;
                if (this.ti > 20.0 || noClipItem.getEntity().isDead()) {
                    noClipItem.close();
                    this.cancel();
                }
                noClipItem.getEntity().getWorld().spawnParticle(Particle.CRIT, noClipItem.getEntity().getLocation(), 0);
                for (final Entity entity : noClipItem.getEntity().getNearbyEntities(1.0, 1.0, 1.0)) {
                    if (MMOUtils.canDamage(cachedStats.getPlayer(), entity)) {
                        new AttackResult(abilityResult.getModifier("damage"), new DamageType[] { DamageType.SKILL, DamageType.PHYSICAL, DamageType.PROJECTILE }).damage(cachedStats.getPlayer(), (LivingEntity)entity);
                        noClipItem.close();
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
