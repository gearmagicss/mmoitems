// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.eco;

import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;

public class MoneyCondition extends Condition
{
    private final double amount;
    
    public MoneyCondition(final MMOLineConfig mmoLineConfig) {
        super("money");
        mmoLineConfig.validate(new String[] { "amount" });
        this.amount = mmoLineConfig.getDouble("amount");
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        return playerData.isOnline() && MMOItems.plugin.getVault().getEconomy().has((OfflinePlayer)playerData.getPlayer(), this.amount);
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#money#", "" + this.amount);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
        if (!playerData.isOnline()) {
            return;
        }
        MMOItems.plugin.getVault().getEconomy().withdrawPlayer((OfflinePlayer)playerData.getPlayer(), this.amount);
    }
}
