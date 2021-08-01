// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.weapon;

import org.bukkit.Location;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import org.bukkit.entity.LivingEntity;
import net.Indyuce.mmoitems.api.player.PlayerStats;
import javax.annotation.Nullable;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.interaction.UseItem;

public class Weapon extends UseItem
{
    public Weapon(final Player player, final NBTItem nbtItem) {
        this(PlayerData.get((OfflinePlayer)player), nbtItem);
    }
    
    public Weapon(final PlayerData playerData, final NBTItem nbtItem) {
        super(playerData, nbtItem);
    }
    
    @Override
    public boolean checkItemRequirements() {
        if (this.playerData.areHandsFull()) {
            Message.HANDS_TOO_CHARGED.format(ChatColor.RED, new String[0]).send(this.getPlayer(), "two-handed");
            return false;
        }
        final boolean canUse = this.playerData.getRPG().canUse(this.getNBTItem(), true);
        final FlagPlugin flags = MMOItems.plugin.getFlags();
        final boolean b = flags == null || flags.isFlagAllowed(this.getPlayer(), FlagPlugin.CustomFlag.MI_WEAPONS);
        return canUse && b;
    }
    
    public boolean applyWeaponCosts() {
        return this.applyWeaponCosts(0.0, null);
    }
    
    public boolean applyWeaponCosts(final double n, @Nullable final PlayerData.CooldownType cooldownType) {
        if (cooldownType != null && this.getPlayerData().isOnCooldown(cooldownType)) {
            return false;
        }
        final double stat = this.getNBTItem().getStat("MANA_COST");
        if (stat > 0.0 && this.playerData.getRPG().getMana() < stat) {
            Message.NOT_ENOUGH_MANA.format(ChatColor.RED, new String[0]).send(this.getPlayer(), "not-enough-mana");
            return false;
        }
        final double stat2 = this.getNBTItem().getStat("STAMINA_COST");
        if (stat2 > 0.0 && this.playerData.getRPG().getStamina() < stat2) {
            Message.NOT_ENOUGH_STAMINA.format(ChatColor.RED, new String[0]).send(this.getPlayer(), "not-enough-stamina");
            return false;
        }
        if (stat > 0.0) {
            this.playerData.getRPG().giveMana(-stat);
        }
        if (stat2 > 0.0) {
            this.playerData.getRPG().giveStamina(-stat2);
        }
        if (cooldownType != null) {
            this.getPlayerData().applyCooldown(cooldownType, n);
        }
        return true;
    }
    
    public ItemAttackResult handleTargetedAttack(final PlayerStats.CachedStats cachedStats, final LivingEntity livingEntity, final ItemAttackResult itemAttackResult) {
        final double stat = this.getNBTItem().getStat(ItemStats.ATTACK_SPEED.getId());
        final double n = (stat == 0.0) ? 1.493 : (1.0 / stat);
        if (!this.applyWeaponCosts()) {
            return itemAttackResult.setSuccessful(false);
        }
        if (this.getMMOItem().getType().getItemSet().hasAttackEffect() && !this.getNBTItem().getBoolean("MMOITEMS_DISABLE_ATTACK_PASSIVE")) {
            this.getMMOItem().getType().getItemSet().applyAttackEffect(cachedStats, livingEntity, this, itemAttackResult);
        }
        return itemAttackResult;
    }
    
    protected Location getGround(final Location location) {
        for (int i = 0; i < 20; ++i) {
            if (location.getBlock().getType().isSolid()) {
                return location;
            }
            location.add(0.0, -1.0, 0.0);
        }
        return location;
    }
    
    public double getValue(final double n, final double n2) {
        return (n <= 0.0) ? n2 : n;
    }
}
