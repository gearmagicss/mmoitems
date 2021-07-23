// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Arrays;
import io.lumine.mythic.lib.api.MMOLineConfig;
import java.util.List;

public class ClassCondition extends Condition
{
    private final List<String> classes;
    
    public ClassCondition(final MMOLineConfig mmoLineConfig) {
        super("class");
        mmoLineConfig.validate(new String[] { "list" });
        this.classes = Arrays.asList(mmoLineConfig.getString("list").split(","));
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return this.classes.contains(playerData.getRPG().getClassName());
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#class#", String.join(", ", this.classes));
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
    }
}
