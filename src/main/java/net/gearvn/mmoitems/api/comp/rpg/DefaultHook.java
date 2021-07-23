// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.event.Listener;

public class DefaultHook implements RPGHandler, Listener
{
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    @EventHandler
    public void a(final PlayerLevelChangeEvent playerLevelChangeEvent) {
        PlayerData.get((OfflinePlayer)playerLevelChangeEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new DefaultRPGPlayer(playerData);
    }
    
    public static class DefaultRPGPlayer extends RPGPlayer
    {
        public DefaultRPGPlayer(final PlayerData playerData) {
            super(playerData);
        }
        
        @Override
        public int getLevel() {
            return this.getPlayer().getLevel();
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
