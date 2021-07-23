// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs.crafting;

import java.util.Collection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Optional;
import org.apache.commons.lang.Validate;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.mythic.lib.api.MMOLineConfig;
import io.lumine.xikage.mythicmobs.skills.Skill;
import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;

public class MythicMobsSkillTrigger extends Trigger
{
    private final Skill skill;
    
    public MythicMobsSkillTrigger(final MMOLineConfig mmoLineConfig) {
        super("mmskill");
        mmoLineConfig.validate(new String[] { "id" });
        final String string = mmoLineConfig.getString("id");
        final Optional skill = MythicMobs.inst().getSkillManager().getSkill(string);
        Validate.isTrue(skill.isPresent(), "Could not find MM skill " + string);
        this.skill = skill.get();
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        final ArrayList<Player> list = new ArrayList<Player>();
        list.add(playerData.getPlayer());
        MythicMobs.inst().getAPIHelper().castSkill((Entity)playerData.getPlayer(), this.skill.getInternalName(), (Entity)playerData.getPlayer(), playerData.getPlayer().getEyeLocation(), (Collection)list, (Collection)null, 1.0f);
    }
}
