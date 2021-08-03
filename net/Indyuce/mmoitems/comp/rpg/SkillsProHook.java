// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import org.bukkit.ChatColor;
import org.skills.main.SkillsPro;
import org.skills.data.managers.SkilledPlayer;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.skills.api.events.SkillLevelUpEvent;
import org.bukkit.event.Listener;

public class SkillsProHook implements RPGHandler, Listener
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
        return new SkillsPlayer(playerData);
    }
    
    public static class SkillsPlayer extends RPGPlayer
    {
        private final SkilledPlayer info;
        
        public SkillsPlayer(final PlayerData playerData) {
            super(playerData);
            this.info = (SkilledPlayer)SkillsPro.get().getPlayerDataManager().getData(playerData.getUniqueId());
        }
        
        @Override
        public int getLevel() {
            return this.info.getLevel();
        }
        
        @Override
        public String getClassName() {
            return ChatColor.stripColor(this.info.getSkill().getDisplayName());
        }
        
        @Override
        public double getMana() {
            return this.info.getEnergy();
        }
        
        @Override
        public double getStamina() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double energy) {
            this.info.setEnergy(energy);
        }
        
        @Override
        public void setStamina(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
    }
}
