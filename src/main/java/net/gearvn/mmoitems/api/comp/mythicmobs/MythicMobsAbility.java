// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs;

import net.Indyuce.mmoitems.stat.data.AbilityData;
import org.bukkit.entity.LivingEntity;
import java.util.Collection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import java.util.Iterator;
import java.util.Optional;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import io.lumine.xikage.mythicmobs.skills.Skill;
import net.Indyuce.mmoitems.api.ability.Ability;

public class MythicMobsAbility extends Ability
{
    private final Skill skill;
    private final boolean selfOnly;
    
    public MythicMobsAbility(final String s, final FileConfiguration fileConfiguration) {
        super(s, fileConfiguration.getString("name"), new CastingMode[] { CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK });
        final String string = fileConfiguration.getString("mythicmobs-skill-id");
        Validate.notNull((Object)string, "Could not find MM skill name");
        final Optional skill = MythicMobs.inst().getSkillManager().getSkill(string);
        Validate.isTrue(skill.isPresent(), "Could not find MM skill " + string);
        this.skill = skill.get();
        this.selfOnly = fileConfiguration.getBoolean("self-only");
        this.addModifier("cooldown", 10.0);
        this.addModifier("mana", 0.0);
        this.addModifier("stamina", 0.0);
        for (final String s2 : fileConfiguration.getKeys(false)) {
            if (!s2.equals("name") && !s2.equals("mythicmobs-skill-id") && !s2.equals("self-only")) {
                this.addModifier(s2.toLowerCase().replace("_", "-").replace(" ", "-"), fileConfiguration.getInt(s2));
            }
        }
    }
    
    public String getInternalName() {
        return this.skill.getInternalName();
    }
    
    @Override
    public void whenCast(final PlayerStats.CachedStats cachedStats, final AbilityResult abilityResult, final ItemAttackResult itemAttackResult) {
        final LivingEntity target = ((MythicMobsAbilityResult)abilityResult).getTarget();
        final ArrayList<Player> list = new ArrayList<Player>();
        list.add((Player)((target == null || this.selfOnly) ? cachedStats.getPlayer() : target));
        cachedStats.getData().getAbilityData().cacheModifiers(this, abilityResult.getAbility());
        if (!MythicMobs.inst().getAPIHelper().castSkill((Entity)cachedStats.getPlayer(), this.skill.getInternalName(), (Entity)cachedStats.getPlayer(), cachedStats.getPlayer().getEyeLocation(), (Collection)list, (Collection)null, 1.0f)) {
            itemAttackResult.setSuccessful(false);
        }
    }
    
    @Override
    public AbilityResult whenRan(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final AbilityData abilityData, final ItemAttackResult itemAttackResult) {
        return new MythicMobsAbilityResult(abilityData, livingEntity);
    }
    
    public static class MythicMobsAbilityResult extends AbilityResult
    {
        private final LivingEntity target;
        
        public MythicMobsAbilityResult(final AbilityData abilityData, final LivingEntity target) {
            super(abilityData);
            this.target = target;
        }
        
        public LivingEntity getTarget() {
            return this.target;
        }
        
        @Override
        public boolean isSuccessful() {
            return true;
        }
    }
}
