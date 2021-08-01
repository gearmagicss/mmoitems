// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class FoodCondition extends Condition
{
    private final int amount;
    
    public FoodCondition(final MMOLineConfig mmoLineConfig) {
        super("food");
        mmoLineConfig.validate(new String[] { "amount" });
        this.amount = mmoLineConfig.getInt("amount");
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return playerData.isOnline() && playerData.getPlayer().getFoodLevel() >= this.amount;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#food#", "" + this.amount);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        playerData.getPlayer().setFoodLevel(Math.max(0, playerData.getPlayer().getFoodLevel() - this.amount));
    }
}
