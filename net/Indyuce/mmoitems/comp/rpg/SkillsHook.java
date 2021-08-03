// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import me.leothepro555.skilltype.ScalingType;
import me.leothepro555.skills.main.Skills;
import me.leothepro555.skills.database.managers.PlayerInfo;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import me.leothepro555.skills.events.SkillLevelUpEvent;
import org.bukkit.event.Listener;

public class SkillsHook implements RPGHandler, Listener
{
    @EventHandler
    public void a(final SkillLevelUpEvent skillLevelUpEvent) {
        final OfflinePlayer player = skillLevelUpEvent.getPlayer();
        if (player.isOnline()) {
            PlayerData.get(player).getInventory().scheduleUpdate();
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
        private final PlayerInfo info;
        
        public SkillsPlayer(final PlayerData playerData) {
            super(playerData);
            this.info = Skills.get().getPlayerDataManager().loadPlayerInfo((OfflinePlayer)playerData.getPlayer());
        }
        
        @Override
        public int getLevel() {
            return this.info.getLevel();
        }
        
        @Override
        public String getClassName() {
            return this.info.getSkill().getLanguageName().getDefault();
        }
        
        @Override
        public double getMana() {
            return this.info.getActiveStatType(ScalingType.ENERGY);
        }
        
        @Override
        public double getStamina() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double n) {
            this.info.setActiveStatType(ScalingType.ENERGY, n);
        }
        
        @Override
        public void setStamina(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
    }
}
