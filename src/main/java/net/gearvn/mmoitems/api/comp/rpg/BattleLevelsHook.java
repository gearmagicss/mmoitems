// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import me.robin.battlelevels.api.BattleLevelsAPI;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import me.robin.battlelevels.events.PlayerLevelUpEvent;
import org.bukkit.event.Listener;

public class BattleLevelsHook implements RPGHandler, Listener
{
    @EventHandler
    public void a(final PlayerLevelUpEvent playerLevelUpEvent) {
        PlayerData.get((OfflinePlayer)playerLevelUpEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new BattleLevelsPlayer(playerData);
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    public static class BattleLevelsPlayer extends RPGPlayer
    {
        public BattleLevelsPlayer(final PlayerData playerData) {
            super(playerData);
        }
        
        @Override
        public int getLevel() {
            return BattleLevelsAPI.getLevel(this.getPlayer().getUniqueId());
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
