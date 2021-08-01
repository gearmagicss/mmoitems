// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import org.bukkit.plugin.Plugin;
import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Particle;
import io.lumine.mythic.lib.UtilityMethods;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.Block;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Tool extends UseItem
{
    public Tool(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem);
    }
    
    @Override
    public boolean checkItemRequirements() {
        return MMOItems.plugin.getFlags().isFlagAllowed(this.player, FlagPlugin.CustomFlag.MI_TOOLS) && this.playerData.getRPG().canUse(this.getNBTItem(), true);
    }
    
    public boolean miningEffects(final Block block) {
        boolean b = false;
        if (this.getNBTItem().getBoolean("MMOITEMS_AUTOSMELT") && (block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE)) {
            UtilityMethods.dropItemNaturally(block.getLocation(), new ItemStack(Material.valueOf(block.getType().name().replace("_ORE", "_INGOT"))));
            block.getWorld().spawnParticle(Particle.CLOUD, block.getLocation().add(0.5, 0.5, 0.5), 0);
            block.setType(Material.AIR);
            b = true;
        }
        if (this.getNBTItem().getBoolean("MMOITEMS_BOUNCING_CRACK")) {
            new BukkitRunnable() {
                final Vector v = Tool.this.player.getEyeLocation().getDirection().multiply(0.5);
                final Location loc = block.getLocation().clone().add(0.5, 0.5, 0.5);
                int j = 0;
                
                public void run() {
                    ++this.j;
                    if (this.j > 10) {
                        this.cancel();
                    }
                    this.loc.add(this.v);
                    final Block block = this.loc.getBlock();
                    if (block.getType() == Material.AIR || MMOItems.plugin.getLanguage().isBlacklisted(block.getType())) {
                        return;
                    }
                    block.breakNaturally(Tool.this.getItem());
                    this.loc.getWorld().playSound(this.loc, Sound.BLOCK_GRAVEL_BREAK, 1.0f, 1.0f);
                }
            }.runTaskTimer((Plugin)MMOItems.plugin, 0L, 1L);
        }
        return b;
    }
}
