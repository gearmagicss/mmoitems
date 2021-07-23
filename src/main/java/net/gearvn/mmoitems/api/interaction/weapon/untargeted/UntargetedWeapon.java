// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon.untargeted;

import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;

public abstract class UntargetedWeapon extends Weapon
{
    protected final WeaponType weaponType;
    
    public UntargetedWeapon(final Player player, final NBTItem nbtItem, final WeaponType weaponType) {
        super(player, nbtItem);
        this.weaponType = weaponType;
    }
    
    public abstract void untargetedAttack(final EquipmentSlot p0);
    
    public WeaponType getWeaponType() {
        return this.weaponType;
    }
    
    public enum WeaponType
    {
        RIGHT_CLICK, 
        LEFT_CLICK;
        
        public boolean corresponds(final Action action) {
            return (this == WeaponType.RIGHT_CLICK && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) || (this == WeaponType.LEFT_CLICK && (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK));
        }
    }
}
