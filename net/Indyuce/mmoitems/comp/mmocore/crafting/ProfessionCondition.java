// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.crafting;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmocore.MMOCore;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;

public class ProfessionCondition extends Condition
{
    private final Profession profession;
    private final int level;
    
    public ProfessionCondition(final MMOLineConfig mmoLineConfig) {
        super("profession");
        mmoLineConfig.validate(new String[] { "profession", "level" });
        this.level = mmoLineConfig.getInt("level");
        final String replace = mmoLineConfig.getString("profession").toLowerCase().replace("_", "-");
        Validate.isTrue(MMOCore.plugin.professionManager.has(replace), "Could not find profession " + replace);
        this.profession = MMOCore.plugin.professionManager.get(replace);
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#level#", "" + this.level).replace("#profession#", this.profession.getName());
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return net.Indyuce.mmocore.api.player.PlayerData.get(playerData.getUniqueId()).getCollectionSkills().getLevel(this.profession) >= this.level;
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
    }
}
