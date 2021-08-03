// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.eco;

import org.bukkit.plugin.RegisteredServiceProvider;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import io.lumine.mythic.lib.api.MMOLineConfig;
import java.util.function.Function;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import java.util.logging.Level;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.economy.Economy;

public class VaultSupport
{
    private final Economy economy;
    private final Permission permissions;
    
    public VaultSupport() {
        final RegisteredServiceProvider registration = Bukkit.getServer().getServicesManager().getRegistration((Class)Economy.class);
        this.economy = ((registration != null) ? ((Economy)registration.getProvider()) : null);
        final RegisteredServiceProvider registration2 = Bukkit.getServer().getServicesManager().getRegistration((Class)Permission.class);
        this.permissions = ((registration2 != null) ? ((Permission)registration2.getProvider()) : null);
        if (this.economy == null) {
            MMOItems.plugin.getLogger().log(Level.SEVERE, "Could not load Economy Support (Vault)");
        }
        else {
            MMOItems.plugin.getCrafting().registerCondition("money", (Function<MMOLineConfig, Condition>)MoneyCondition::new, new ConditionalDisplay("&a\u2714 Requires $#money#", "&c\u2716 Requires $#money#"));
        }
        if (this.permissions == null) {
            MMOItems.plugin.getLogger().log(Level.SEVERE, "Could not load Permissions Support (Vault)");
        }
        if (this.economy != null || this.permissions != null) {
            MMOItems.plugin.getLogger().log(Level.INFO, "Hooked onto Vault");
        }
    }
    
    public Permission getPermissions() {
        return this.permissions;
    }
    
    public Economy getEconomy() {
        return this.economy;
    }
}
