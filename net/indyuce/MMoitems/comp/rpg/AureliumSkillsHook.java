// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import org.bukkit.Bukkit;
import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.data.PlayerDataLoadEvent;
import net.Indyuce.mmoitems.api.player.EmptyRPGPlayer;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import com.archyx.aureliumskills.api.event.SkillLevelUpEvent;
import org.bukkit.event.Listener;

public class AureliumSkillsHook implements RPGHandler, Listener
{
    @EventHandler
    public void a(final SkillLevelUpEvent skillLevelUpEvent) {
        final Player player = skillLevelUpEvent.getPlayer();
        if (((OfflinePlayer)player).isOnline()) {
            PlayerData.get((OfflinePlayer)player).getInventory().scheduleUpdate();
        }
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new EmptyRPGPlayer(playerData);
    }
    
    @EventHandler
    public void a(final PlayerDataLoadEvent playerDataLoadEvent) {
        final PlayerData value = PlayerData.get((OfflinePlayer)playerDataLoadEvent.getPlayerData().getPlayer());
        value.setRPGPlayer(new AureliumSkillsPlayer(value, playerDataLoadEvent.getPlayerData()));
    }
    
    public static class SkillsPlayer extends RPGPlayer
    {
        private final com.archyx.aureliumskills.data.PlayerData info;
        
        public SkillsPlayer(final PlayerData playerData) {
            super(playerData);
            this.info = ((AureliumSkills)Bukkit.getPluginManager().getPlugin("AureliumSkills")).getPlayerManager().getPlayerData(playerData.getUniqueId());
        }
        
        @Override
        public int getLevel() {
            return this.info.getPowerLevel();
        }
        
        @Override
        public String getClassName() {
            return "";
        }
        
        @Override
        public double getMana() {
            return this.info.getMana();
        }
        
        @Override
        public double getStamina() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double mana) {
            this.info.setMana(mana);
        }
        
        @Override
        public void setStamina(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
    }
    
    public static class AureliumSkillsPlayer extends RPGPlayer
    {
        private final com.archyx.aureliumskills.data.PlayerData info;
        
        public AureliumSkillsPlayer(final PlayerData playerData, final com.archyx.aureliumskills.data.PlayerData info) {
            super(playerData);
            this.info = info;
        }
        
        @Override
        public int getLevel() {
            return this.info.getPowerLevel();
        }
        
        @Override
        public String getClassName() {
            return "";
        }
        
        @Override
        public double getMana() {
            return this.info.getMana();
        }
        
        @Override
        public double getStamina() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double mana) {
            this.info.setMana(mana);
        }
        
        @Override
        public void setStamina(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
    }
}
