// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import de.tobiyas.racesandclasses.playermanagement.player.RaCPlayerManager;
import de.tobiyas.racesandclasses.eventprocessing.events.leveling.LevelDownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import de.tobiyas.racesandclasses.eventprocessing.events.leveling.LevelUpEvent;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import de.tobiyas.racesandclasses.playermanagement.player.RaCPlayer;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.Listener;

public class RacesAndClassesHook implements RPGHandler, Listener
{
    @Override
    public void refreshStats(final PlayerData playerData) {
        final RaCPlayer access$000 = ((RacePlayer)playerData.getRPG()).info;
        access$000.getManaManager().removeMaxManaBonus("MMOItems");
        access$000.getManaManager().addMaxManaBonus("MMOItems", playerData.getStats().getStat(ItemStats.MAX_MANA));
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new RacePlayer(playerData);
    }
    
    @EventHandler
    public void a(final LevelUpEvent levelUpEvent) {
        PlayerData.get((OfflinePlayer)levelUpEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @EventHandler
    public void b(final LevelDownEvent levelDownEvent) {
        PlayerData.get((OfflinePlayer)levelDownEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    public static class RacePlayer extends RPGPlayer
    {
        private final RaCPlayer info;
        
        public RacePlayer(final PlayerData playerData) {
            super(playerData);
            this.info = RaCPlayerManager.get().getPlayer(playerData.getUniqueId());
        }
        
        @Override
        public int getLevel() {
            return this.info.getCurrentLevel();
        }
        
        @Override
        public String getClassName() {
            return this.info.getclass().getDisplayName();
        }
        
        @Override
        public double getMana() {
            return this.info.getCurrentMana();
        }
        
        @Override
        public double getStamina() {
            return this.info.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double n) {
            this.info.getManaManager().fillMana(n - this.info.getManaManager().getCurrentMana());
        }
        
        @Override
        public void setStamina(final double n) {
            this.info.getPlayer().setFoodLevel((int)n);
        }
    }
}
