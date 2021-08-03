// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore;

import net.Indyuce.mmocore.api.event.PlayerDataLoadEvent;
import net.Indyuce.mmocore.api.event.PlayerChangeClassEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmocore.api.event.PlayerLevelUpEvent;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Iterator;
import net.Indyuce.mmoitems.comp.mmocore.stat.Required_Profession;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.comp.mmocore.stat.Required_Attribute;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmocore.MMOCore;
import org.bukkit.event.Listener;
import net.Indyuce.mmoitems.comp.rpg.RPGHandler;

public class MMOCoreHook implements RPGHandler, Listener
{
    public MMOCoreHook() {
        final Iterator<PlayerAttribute> iterator = MMOCore.plugin.attributeManager.getAll().iterator();
        while (iterator.hasNext()) {
            MMOItems.plugin.getStats().register(new Required_Attribute(iterator.next()));
        }
        final Iterator<Profession> iterator2 = MMOCore.plugin.professionManager.getAll().iterator();
        while (iterator2.hasNext()) {
            MMOItems.plugin.getStats().register(new Required_Profession(iterator2.next()));
        }
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new MMOCoreRPGPlayer(playerData);
    }
    
    @EventHandler
    public void updateInventoryOnLevelUp(final PlayerLevelUpEvent playerLevelUpEvent) {
        PlayerData.get((OfflinePlayer)playerLevelUpEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @EventHandler
    public void updateInventoryOnClassChange(final PlayerChangeClassEvent playerChangeClassEvent) {
        PlayerData.get((OfflinePlayer)playerChangeClassEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    @EventHandler
    public void updateInventoryOnPlayerDataLoad(final PlayerDataLoadEvent playerDataLoadEvent) {
        PlayerData.get((OfflinePlayer)playerDataLoadEvent.getPlayer()).getInventory().scheduleUpdate();
    }
    
    public static class MMOCoreRPGPlayer extends RPGPlayer
    {
        private final net.Indyuce.mmocore.api.player.PlayerData data;
        
        public MMOCoreRPGPlayer(final PlayerData playerData) {
            super(playerData);
            this.data = net.Indyuce.mmocore.api.player.PlayerData.get(playerData.getUniqueId());
        }
        
        public net.Indyuce.mmocore.api.player.PlayerData getData() {
            return this.data;
        }
        
        @Override
        public int getLevel() {
            return this.data.getLevel();
        }
        
        @Override
        public String getClassName() {
            return this.data.getProfess().getName();
        }
        
        @Override
        public double getMana() {
            return this.data.getMana();
        }
        
        @Override
        public double getStamina() {
            return this.data.getStamina();
        }
        
        @Override
        public void setMana(final double mana) {
            this.data.setMana(mana);
        }
        
        @Override
        public void setStamina(final double stamina) {
            this.data.setStamina(stamina);
        }
        
        @Override
        public void giveMana(final double n) {
            this.data.giveMana(n);
        }
        
        @Override
        public void giveStamina(final double n) {
            this.data.giveStamina(n);
        }
    }
}
