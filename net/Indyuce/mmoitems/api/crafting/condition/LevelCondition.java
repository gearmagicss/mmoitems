// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class LevelCondition extends Condition
{
    private final int level;
    
    public LevelCondition(final MMOLineConfig mmoLineConfig) {
        super("level");
        mmoLineConfig.validate(new String[] { "level" });
        this.level = mmoLineConfig.getInt("level");
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return playerData.getRPG().getLevel() >= this.level;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#level#", "" + this.level);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
    }
}
