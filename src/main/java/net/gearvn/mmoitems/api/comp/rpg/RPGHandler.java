// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.rpg;

import net.Indyuce.mmoitems.comp.mmocore.MMOCoreHook;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;

public interface RPGHandler
{
    RPGPlayer getInfo(final PlayerData p0);
    
    void refreshStats(final PlayerData p0);
    
    public enum PluginEnum
    {
        MMOCORE("MMOCore", (Class<? extends RPGHandler>)MMOCoreHook.class), 
        HEROES("Heroes", (Class<? extends RPGHandler>)HeroesHook.class), 
        SKILLAPI("SkillAPI", (Class<? extends RPGHandler>)SkillAPIHook.class), 
        RPGPLAYERLEVELING("RPGPlayerLeveling", (Class<? extends RPGHandler>)RPGPlayerLevelingHook.class), 
        RACESANDCLASSES("RacesAndClasses", (Class<? extends RPGHandler>)RacesAndClassesHook.class), 
        BATTLELEVELS("BattleLevels", (Class<? extends RPGHandler>)BattleLevelsHook.class), 
        MCMMO("mcMMO", (Class<? extends RPGHandler>)McMMOHook.class), 
        MCRPG("McRPG", (Class<? extends RPGHandler>)McRPGHook.class), 
        SKILLS("Skills", (Class<? extends RPGHandler>)SkillsHook.class), 
        SKILLSPRO("SkillsPro", (Class<? extends RPGHandler>)SkillsProHook.class);
        
        private final Class<? extends RPGHandler> pluginClass;
        private final String name;
        
        private PluginEnum(final String name2, final Class<? extends RPGHandler> pluginClass) {
            this.pluginClass = pluginClass;
            this.name = name2;
        }
        
        public RPGHandler load() {
            try {
                return (RPGHandler)this.pluginClass.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                final Object o;
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not initialize RPG plugin compatibility with " + this.name + ": " + ((Throwable)o).getMessage());
                return new DefaultHook();
            }
        }
        
        public String getName() {
            return this.name;
        }
    }
}
