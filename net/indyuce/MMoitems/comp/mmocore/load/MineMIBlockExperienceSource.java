// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import java.util.Optional;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.event.block.BlockBreakEvent;
import net.Indyuce.mmocore.manager.profession.ExperienceManager;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmocore.api.experience.source.type.SpecificExperienceSource;

public class MineMIBlockExperienceSource extends SpecificExperienceSource<Integer>
{
    private final int id;
    private final boolean silkTouch;
    private final boolean playerPlaced;
    
    public MineMIBlockExperienceSource(final Profession profession, final MMOLineConfig mmoLineConfig) {
        super(profession, mmoLineConfig);
        mmoLineConfig.validate(new String[] { "id" });
        this.id = mmoLineConfig.getInt("id", 1);
        this.silkTouch = mmoLineConfig.getBoolean("silk-touch", true);
        this.playerPlaced = mmoLineConfig.getBoolean("player-placed", false);
    }
    
    public ExperienceManager<MineMIBlockExperienceSource> newManager() {
        return new ExperienceManager<MineMIBlockExperienceSource>() {
            @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
            public void a(final BlockBreakEvent blockBreakEvent) {
                if (blockBreakEvent.getPlayer().getGameMode() != GameMode.SURVIVAL) {
                    return;
                }
                final PlayerData value = PlayerData.get((OfflinePlayer)blockBreakEvent.getPlayer());
                final Optional<CustomBlock> fromBlock = MMOItems.plugin.getCustomBlocks().getFromBlock(blockBreakEvent.getBlock().getBlockData());
                if (!fromBlock.isPresent()) {
                    return;
                }
                for (final MineMIBlockExperienceSource mineMIBlockExperienceSource : this.getSources()) {
                    if (!mineMIBlockExperienceSource.silkTouch || !MineMIBlockExperienceSource.this.hasSilkTouch(blockBreakEvent.getPlayer().getInventory().getItemInMainHand())) {
                        if (!mineMIBlockExperienceSource.playerPlaced && blockBreakEvent.getBlock().hasMetadata("player_placed")) {
                            continue;
                        }
                        if (!mineMIBlockExperienceSource.matches(value, fromBlock.get().getId())) {
                            continue;
                        }
                        mineMIBlockExperienceSource.giveExperience(value, 1, blockBreakEvent.getBlock().getLocation());
                    }
                }
            }
        };
    }
    
    private boolean hasSilkTouch(final ItemStack itemStack) {
        return itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH);
    }
    
    public boolean matches(final PlayerData playerData, final Integer n) {
        return this.id == n && this.hasRightClass(playerData);
    }
}
