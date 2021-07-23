// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.ability;

import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import net.Indyuce.mmoitems.api.ability.Ability;

public class Hoearthquake extends Ability
{
    public Hoearthquake() {
        super(new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new SimpleAbilityResult(abilityData, ((LivingEntity)cachedStats.getPlayer()).isOnGround());
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        new BukkitRunnable() {
            final Vector vec = cachedStats.getPlayer().getEyeLocation().getDirection().setY(0);
            final Location loc = cachedStats.getPlayer().getLocation();
            int ti = 0;
            
            public void run() {
                if (this.ti++ > 20) {
                    this.cancel();
                }
                this.loc.add(this.vec);
                this.loc.getWorld().playSound(this.loc, Sound.BLOCK_GRAVEL_BREAK, 2.0f, 1.0f);
                this.loc.getWorld().spawnParticle(Particle.CLOUD, this.loc, 1, 0.5, 0.0, 0.5, 0.0);
                for (int i = -1; i < 2; ++i) {
                    for (int j = -1; j < 2; ++j) {
                        final Block block = this.loc.clone().add((double)i, -1.0, (double)j).getBlock();
                        if (block.getType() == Material.GRASS || block.getType() == Material.DIRT) {
                            final BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, cachedStats.getPlayer());
                            blockBreakEvent.setDropItems(false);
                            Bukkit.getPluginManager().callEvent((Event)blockBreakEvent);
                            if (!blockBreakEvent.isCancelled()) {
                                block.setType(Material.FARMLAND);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
    }
}
