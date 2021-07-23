// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.flags;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.entity.Player;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flags;
import org.bukkit.Location;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.flags.Flag;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import com.sk89q.worldguard.protection.flags.StateFlag;
import java.util.Map;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.WorldGuard;

public class WorldGuardFlags implements FlagPlugin
{
    private final WorldGuard worldguard;
    private final WorldGuardPlugin worldguardPlugin;
    private final Map<String, StateFlag> flags;
    
    public WorldGuardFlags() {
        this.flags = new HashMap<String, StateFlag>();
        this.worldguard = WorldGuard.getInstance();
        this.worldguardPlugin = (WorldGuardPlugin)MMOItems.plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        final FlagRegistry flagRegistry = this.worldguard.getFlagRegistry();
        for (final CustomFlag customFlag : CustomFlag.values()) {
            final StateFlag stateFlag = new StateFlag(customFlag.getPath(), true);
            try {
                flagRegistry.register((Flag)stateFlag);
                this.flags.put(customFlag.getPath(), stateFlag);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public boolean isPvpAllowed(final Location location) {
        return this.getApplicableRegion(location).queryState((RegionAssociable)null, new StateFlag[] { Flags.PVP }) != StateFlag.State.DENY;
    }
    
    @Override
    public boolean isFlagAllowed(final Location location, final CustomFlag customFlag) {
        return this.getApplicableRegion(location).queryValue((RegionAssociable)null, (Flag)this.flags.get(customFlag.getPath())) != StateFlag.State.DENY;
    }
    
    @Override
    public boolean isFlagAllowed(final Player player, final CustomFlag customFlag) {
        return this.getApplicableRegion(player.getLocation()).queryValue((RegionAssociable)this.worldguardPlugin.wrapPlayer(player), (Flag)this.flags.get(customFlag.getPath())) != StateFlag.State.DENY;
    }
    
    private ApplicableRegionSet getApplicableRegion(final Location location) {
        return this.worldguard.getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location));
    }
}
