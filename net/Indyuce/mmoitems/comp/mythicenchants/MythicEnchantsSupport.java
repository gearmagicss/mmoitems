// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicenchants;

import io.lumine.mythicenchants.enchants.MythicEnchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythicenchants.util.MythicEnchantsHelper;
import io.lumine.xikage.mythicmobs.adapters.AbstractPlayer;

public class MythicEnchantsSupport
{
    public void reparseWeapon(final AbstractPlayer abstractPlayer) {
        MythicEnchantsHelper.reparseWeapon(abstractPlayer);
    }
    
    public boolean handleEnchant(final ItemStack itemStack, final Enchantment enchantment, final int n) {
        if (enchantment instanceof MythicEnchant) {
            ((MythicEnchant)enchantment).applyToItem(itemStack, n);
            return true;
        }
        return false;
    }
}
