// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.crafting;

import net.Indyuce.mmocore.api.experience.EXPSource;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.apache.commons.lang.Validate;
import net.Indyuce.mmocore.MMOCore;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;

public class ExperienceCraftingTrigger extends Trigger
{
    private final Profession profession;
    private final int amount;
    
    public ExperienceCraftingTrigger(final MMOLineConfig mmoLineConfig) {
        super("exp");
        mmoLineConfig.validate(new String[] { "profession", "amount" });
        this.amount = mmoLineConfig.getInt("amount");
        final String replace = mmoLineConfig.getString("profession").toLowerCase().replace("_", "-");
        if (!replace.equalsIgnoreCase("main")) {
            Validate.isTrue(MMOCore.plugin.professionManager.has(replace), "Could not find profession " + replace);
            this.profession = MMOCore.plugin.professionManager.get(replace);
        }
        else {
            this.profession = null;
        }
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (this.profession == null) {
            net.Indyuce.mmocore.api.player.PlayerData.get(playerData.getUniqueId()).giveExperience(this.amount, EXPSource.SOURCE);
        }
        else {
            net.Indyuce.mmocore.api.player.PlayerData.get(playerData.getUniqueId()).getCollectionSkills().giveExperience(this.profession, this.amount, EXPSource.SOURCE);
        }
    }
}
