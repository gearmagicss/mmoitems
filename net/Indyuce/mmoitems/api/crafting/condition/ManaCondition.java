// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class ManaCondition extends Condition
{
    private final double amount;
    
    public ManaCondition(final MMOLineConfig mmoLineConfig) {
        super("mana");
        mmoLineConfig.validate(new String[] { "amount" });
        this.amount = mmoLineConfig.getDouble("amount");
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return playerData.getRPG().getMana() >= this.amount;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#mana#", "" + this.amount);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        playerData.getRPG().giveMana(-this.amount);
    }
}
