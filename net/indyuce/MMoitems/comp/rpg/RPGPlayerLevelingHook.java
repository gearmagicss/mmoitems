// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import me.baks.rpl.api.API;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;

public class RPGPlayerLevelingHook implements RPGHandler
{
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new RPGPlayerLevelingPlayer(playerData);
    }
    
    public static class RPGPlayerLevelingPlayer extends RPGPlayer
    {
        public RPGPlayerLevelingPlayer(final PlayerData playerData) {
            super(playerData);
        }
        
        @Override
        public int getLevel() {
            return new API().getPlayerLevel(this.getPlayer());
        }
        
        @Override
        public String getClassName() {
            return "";
        }
        
        @Override
        public double getMana() {
            return new API().getMana(this.getPlayer());
        }
        
        @Override
        public double getStamina() {
            return new API().getPower(this.getPlayer());
        }
        
        @Override
        public void setMana(final double n) {
            new API().setMana(this.getPlayer(), (int)n);
        }
        
        @Override
        public void setStamina(final double n) {
            new API().setPower(this.getPlayer(), (int)n);
        }
    }
}
