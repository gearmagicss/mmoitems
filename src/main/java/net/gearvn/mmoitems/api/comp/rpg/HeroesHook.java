// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import com.herocraftonline.heroes.api.events.ClassChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.OfflinePlayer;
import com.herocraftonline.heroes.api.events.HeroChangeLevelEvent;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import com.herocraftonline.heroes.characters.Hero;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.player.PlayerData;
import com.herocraftonline.heroes.api.SkillUseInfo;
import io.lumine.mythic.lib.api.AttackResult;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Set;
import io.lumine.mythic.lib.api.RegisteredAttack;
import com.herocraftonline.heroes.Heroes;
import org.bukkit.entity.Entity;
import io.lumine.mythic.lib.MythicLib;
import java.util.HashMap;
import io.lumine.mythic.lib.api.DamageType;
import com.herocraftonline.heroes.characters.skill.SkillType;
import java.util.Map;
import io.lumine.mythic.lib.api.DamageHandler;
import org.bukkit.event.Listener;

public class HeroesHook implements RPGHandler, Listener, DamageHandler
{
    private final Map<SkillType, DamageType> damages;
    
    public HeroesHook() {
        this.damages = new HashMap<SkillType, DamageType>();
        MythicLib.plugin.getDamage().registerHandler((DamageHandler)this);
        this.damages.put(SkillType.ABILITY_PROPERTY_PHYSICAL, DamageType.PHYSICAL);
        this.damages.put(SkillType.ABILITY_PROPERTY_MAGICAL, DamageType.MAGIC);
        this.damages.put(SkillType.ABILITY_PROPERTY_PROJECTILE, DamageType.PROJECTILE);
    }
    
    public boolean hasDamage(final Entity entity) {
        return Heroes.getInstance().getDamageManager().isSpellTarget(entity);
    }
    
    public RegisteredAttack getDamage(final Entity entity) {
        final SkillUseInfo spellTargetInfo = Heroes.getInstance().getDamageManager().getSpellTargetInfo(entity);
        return new RegisteredAttack(new AttackResult(true, 0.0, (Set)spellTargetInfo.getSkill().getTypes().stream().filter(this.damages::containsKey).map(this.damages::get).collect(Collectors.toSet())), spellTargetInfo.getCharacter().getEntity());
    }
    
    @Override
    public void refreshStats(final PlayerData playerData) {
        final Hero access$000 = ((HeroesPlayer)playerData.getRPG()).hero;
        access$000.removeMaxMana("MMOItems");
        access$000.addMaxMana("MMOItems", (int)playerData.getStats().getStat(ItemStats.MAX_MANA));
    }
    
    @Override
    public RPGPlayer getInfo(final PlayerData playerData) {
        return new HeroesPlayer(playerData);
    }
    
    @EventHandler
    public void a(final HeroChangeLevelEvent heroChangeLevelEvent) {
        PlayerData.get((OfflinePlayer)heroChangeLevelEvent.getHero().getPlayer()).getInventory().scheduleUpdate();
    }
    
    @EventHandler
    public void b(final ClassChangeEvent classChangeEvent) {
        PlayerData.get((OfflinePlayer)classChangeEvent.getHero().getPlayer()).getInventory().scheduleUpdate();
    }
    
    public static class HeroesPlayer extends RPGPlayer
    {
        private final Hero hero;
        
        public HeroesPlayer(final PlayerData playerData) {
            super(playerData);
            this.hero = Heroes.getInstance().getCharacterManager().getHero(this.getPlayer());
        }
        
        @Override
        public int getLevel() {
            return this.hero.getHeroLevel();
        }
        
        @Override
        public String getClassName() {
            return this.hero.getHeroClass().getName();
        }
        
        @Override
        public double getMana() {
            return this.hero.getMana();
        }
        
        @Override
        public double getStamina() {
            return this.hero.getStamina();
        }
        
        @Override
        public void setMana(final double n) {
            this.hero.setMana((int)n);
        }
        
        @Override
        public void setStamina(final double n) {
            this.hero.setStamina((int)n);
        }
    }
}
