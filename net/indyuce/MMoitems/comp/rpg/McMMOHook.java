// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import com.gmail.nossr50.api.ExperienceAPI;
import net.Indyuce.mmoitems.stat.type.DisableStat;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelDownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.event.Listener;

public class McMMOHook implements RPGHandler, Listener
{
    public static final ItemStat disableMcMMORepair;
    
    @EventHandler(ignoreCancelled = true)
    public void a(final McMMOPlayerLevelUpEvent mcMMOPlayerLevelUpEvent) {
        PlayerData.get((OfflinePlayer)mcMMOPlayerLevelUpEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @EventHandler(ignoreCancelled = true)
    public void b(final McMMOPlayerLevelDownEvent mcMMOPlayerLevelDownEvent) {
        PlayerData.get((OfflinePlayer)mcMMOPlayerLevelDownEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new McMMOPlayer(playerData);
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    static {
        disableMcMMORepair = new DisableStat("MCMMO_REPAIR", Material.IRON_BLOCK, "Disable McMMO Repair", new String[] { "Players can't repair this with McMMO." });
    }
    
    public static class McMMOPlayer extends RPGPlayer
    {
        public McMMOPlayer(final PlayerData playerData) {
            super(playerData);
        }
        
        @Override
        public int getLevel() {
            try {
                return ExperienceAPI.getPowerLevel(this.getPlayer());
            }
            catch (McMMOPlayerNotFoundException ex) {
                return 0;
            }
        }
        
        @Override
        public String getClassName() {
            return "";
        }
        
        @Override
        public double getMana() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public double getStamina() {
            return 0.0;
        }
        
        @Override
        public void setMana(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
        
        @Override
        public void setStamina(final double n) {
        }
    }
}
