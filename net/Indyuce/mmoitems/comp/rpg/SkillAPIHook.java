// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.AttackResult;
import io.lumine.mythic.lib.api.DamageType;
import com.sucy.skill.api.event.SkillDamageEvent;
import org.bukkit.entity.Entity;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.MythicLib;
import java.util.HashMap;
import io.lumine.mythic.lib.api.RegisteredAttack;
import java.util.Map;
import io.lumine.mythic.lib.api.DamageHandler;
import org.bukkit.event.Listener;

public class SkillAPIHook implements RPGHandler, Listener, DamageHandler
{
    private final Map<Integer, RegisteredAttack> damageInfo;
    
    public SkillAPIHook() {
        this.damageInfo = new HashMap<Integer, RegisteredAttack>();
        MythicLib.plugin.getDamage().registerHandler((DamageHandler)this);
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new SkillAPIPlayer(playerData);
    }
    
    public RegisteredAttack getDamage(final Entity entity) {
        return this.damageInfo.get(entity.getEntityId());
    }
    
    public boolean hasDamage(final Entity entity) {
        return this.damageInfo.containsKey(entity.getEntityId());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void a(final SkillDamageEvent skillDamageEvent) {
        this.damageInfo.put(skillDamageEvent.getTarget().getEntityId(), new RegisteredAttack(new AttackResult(skillDamageEvent.getDamage(), new DamageType[] { DamageType.SKILL }), skillDamageEvent.getDamager()));
        if (skillDamageEvent.getDamager() instanceof Player) {
            skillDamageEvent.setDamage(skillDamageEvent.getDamage() * (1.0 + PlayerData.get((OfflinePlayer)skillDamageEvent.getDamager()).getStats().getStat(ItemStats.MAGIC_DAMAGE) / 100.0));
        }
        if (skillDamageEvent.getTarget() instanceof Player) {
            skillDamageEvent.setDamage(skillDamageEvent.getDamage() * (1.0 - PlayerData.get((OfflinePlayer)skillDamageEvent.getTarget()).getStats().getStat(ItemStats.MAGIC_DAMAGE_REDUCTION) / 100.0));
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void c(final EntityDamageByEntityEvent entityDamageByEntityEvent) {
        this.damageInfo.remove(entityDamageByEntityEvent.getEntity().getEntityId());
    }
    
    @EventHandler
    public void b(final PlayerLevelUpEvent playerLevelUpEvent) {
        PlayerData.get((OfflinePlayer)playerLevelUpEvent.getPlayerData().getPlayer()).getInventory().scheduleUpdate();
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
    }
    
    public static class SkillAPIPlayer extends RPGPlayer
    {
        private final com.sucy.skill.api.player.PlayerData rpgdata;
        
        public SkillAPIPlayer(final PlayerData playerData) {
            super(playerData);
            this.rpgdata = SkillAPI.getPlayerData((OfflinePlayer)playerData.getPlayer());
        }
        
        @Override
        public int getLevel() {
            return this.rpgdata.hasClass() ? this.rpgdata.getMainClass().getLevel() : 0;
        }
        
        @Override
        public String getClassName() {
            return this.rpgdata.hasClass() ? this.rpgdata.getMainClass().getData().getName() : "";
        }
        
        @Override
        public double getMana() {
            return this.rpgdata.hasClass() ? this.rpgdata.getMana() : 0.0;
        }
        
        @Override
        public double getStamina() {
            return this.getPlayer().getFoodLevel();
        }
        
        @Override
        public void setMana(final double mana) {
            if (this.rpgdata.hasClass()) {
                this.rpgdata.setMana(mana);
            }
        }
        
        @Override
        public void setStamina(final double n) {
            this.getPlayer().setFoodLevel((int)n);
        }
    }
}
