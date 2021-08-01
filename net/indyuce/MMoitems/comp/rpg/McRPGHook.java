// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import us.eunoians.mcrpg.players.PlayerManager;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.api.player.PlayerData;
import us.eunoians.mcrpg.api.events.mcrpg.McRPGPlayerLevelChangeEvent;
import org.bukkit.event.Listener;

public class McRPGHook implements RPGHandler, Listener
{
    @EventHandler
    public void a(final McRPGPlayerLevelChangeEvent mcRPGPlayerLevelChangeEvent) {
        PlayerData.get(mcRPGPlayerLevelChangeEvent.getMcRPGPlayer().getOfflineMcRPGPlayer()).getInventory().scheduleUpdate();
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new McRPGPlayer(playerData);
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    public static class McRPGPlayer extends RPGPlayer
    {
        public McRPGPlayer(final PlayerData playerData) {
            super(playerData);
        }
        
        @Override
        public int getLevel() {
            try {
                return PlayerManager.getPlayer(this.getPlayer().getUniqueId()).getPowerLevel();
            }
            catch (Exception ex) {
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
