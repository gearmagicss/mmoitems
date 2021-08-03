// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import java.util.Optional;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.OfflinePlayer;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.block.BlockCookEvent;
import net.Indyuce.mmocore.manager.profession.ExperienceManager;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmocore.api.experience.Profession;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmocore.api.experience.source.type.SpecificExperienceSource;

public class SmeltMMOItemExperienceSource extends SpecificExperienceSource<NBTItem>
{
    private final String type;
    private final String id;
    
    public SmeltMMOItemExperienceSource(final Profession profession, final MMOLineConfig mmoLineConfig) {
        super(profession, mmoLineConfig);
        mmoLineConfig.validate(new String[] { "type", "id" });
        this.type = mmoLineConfig.getString("type").replace("-", "_").replace(" ", "_").toUpperCase();
        this.id = mmoLineConfig.getString("id").replace("-", "_").replace(" ", "_").toUpperCase();
    }
    
    public ExperienceManager<SmeltMMOItemExperienceSource> newManager() {
        return new ExperienceManager<SmeltMMOItemExperienceSource>() {
            @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
            public void a(final BlockCookEvent blockCookEvent) {
                final Optional access$000 = SmeltMMOItemExperienceSource.this.getNearbyPlayer(blockCookEvent.getBlock().getLocation());
                if (!access$000.isPresent()) {
                    return;
                }
                final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(blockCookEvent.getResult());
                if (!nbtItem.hasType()) {
                    return;
                }
                final PlayerData value = PlayerData.get((OfflinePlayer)access$000.get());
                for (final SmeltMMOItemExperienceSource smeltMMOItemExperienceSource : this.getSources()) {
                    if (smeltMMOItemExperienceSource.matches(value, nbtItem)) {
                        smeltMMOItemExperienceSource.giveExperience(value, 1, blockCookEvent.getBlock().getLocation().add(0.5, 1.0, 0.5));
                    }
                }
            }
        };
    }
    
    private Optional<Player> getNearbyPlayer(final Location location) {
        return (Optional<Player>)location.getWorld().getPlayers().stream().filter(player -> player.getLocation().distanceSquared(location) < 100.0).findAny();
    }
    
    public boolean matches(final PlayerData playerData, final NBTItem nbtItem) {
        return nbtItem.getString("MMOITEMS_ITEM_TYPE").equals(this.type) && nbtItem.getString("MMOITEMS_ITEM_ID").equals(this.id) && this.hasRightClass(playerData);
    }
}
