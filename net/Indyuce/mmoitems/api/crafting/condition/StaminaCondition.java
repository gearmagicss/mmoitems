// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class StaminaCondition extends Condition
{
    private final double amount;
    
    public StaminaCondition(final MMOLineConfig mmoLineConfig) {
        super("stamina");
        mmoLineConfig.validate(new String[] { "amount" });
        this.amount = mmoLineConfig.getDouble("amount");
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return playerData.getRPG().getStamina() >= this.amount;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#stamina#", "" + this.amount);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        playerData.getRPG().giveStamina(-this.amount);
    }
}
