// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.player;

import java.text.DecimalFormat;
import net.Indyuce.mmoitems.stat.data.AbilityData;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.jetbrains.annotations.NotNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class RPGPlayer
{
    private final PlayerData playerData;
    private final Player player;
    
    @Deprecated
    public RPGPlayer(final Player player) {
        this(PlayerData.get((OfflinePlayer)player));
    }
    
    public RPGPlayer(@NotNull final PlayerData playerData) {
        this.player = playerData.getPlayer();
        this.playerData = playerData;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public Player getPlayer() {
        return this.playerData.getPlayer();
    }
    
    public abstract int getLevel();
    
    public abstract String getClassName();
    
    public abstract double getMana();
    
    public abstract double getStamina();
    
    public abstract void setMana(final double p0);
    
    public abstract void setStamina(final double p0);
    
    public void giveMana(final double n) {
        this.setMana(this.getMana() + n);
    }
    
    public void giveStamina(final double n) {
        this.setStamina(this.getStamina() + n);
    }
    
    public boolean canUse(final NBTItem nbtItem, final boolean b) {
        return this.canUse(nbtItem, b, false);
    }
    
    public boolean canUse(final NBTItem nbtItem, final boolean b, final boolean b2) {
        if (nbtItem.hasTag("MMOITEMS_UNIDENTIFIED_ITEM")) {
            if (b) {
                Message.UNIDENTIFIED_ITEM.format(ChatColor.RED, new String[0]).send(this.player.getPlayer(), "cant-use-item");
                this.player.getPlayer().playSound(this.player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
            }
            return false;
        }
        for (final ItemRestriction itemRestriction : MMOItems.plugin.getStats().getItemRestrictionStats()) {
            if ((!itemRestriction.isDynamic() || !b2) && !itemRestriction.canUse(this, nbtItem, b)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canCast(final AbilityData abilityData) {
        if (this.playerData.hasCooldownInfo(abilityData.getAbility())) {
            final CooldownInformation cooldownInfo = this.playerData.getCooldownInfo(abilityData.getAbility());
            if (!cooldownInfo.hasCooledDown()) {
                if (abilityData.getCastingMode().displaysMessage()) {
                    final StringBuilder sb = new StringBuilder(ChatColor.YELLOW + "");
                    final double n = (cooldownInfo.getInitialCooldown() - cooldownInfo.getRemaining()) / cooldownInfo.getInitialCooldown() * 10.0;
                    final String string = MMOItems.plugin.getConfig().getString("cooldown-progress-bar-char");
                    for (int i = 0; i < 10; ++i) {
                        sb.append((n >= i) ? ChatColor.GREEN : ChatColor.WHITE).append(string);
                    }
                    Message.SPELL_ON_COOLDOWN.format(ChatColor.RED, "#left#", "" + new DecimalFormat("0.#").format(cooldownInfo.getRemaining()), "#progress#", sb.toString(), "#s#", (cooldownInfo.getRemaining() >= 2.0) ? "s" : "").send(this.player, "ability-cooldown");
                }
                return false;
            }
        }
        if (MMOItems.plugin.getConfig().getBoolean("permissions.abilities") && !this.player.hasPermission("mmoitems.ability." + abilityData.getAbility().getLowerCaseID()) && !this.player.hasPermission("mmoitems.bypass.ability")) {
            return false;
        }
        if (abilityData.hasModifier("mana") && this.getMana() < abilityData.getModifier("mana")) {
            Message.NOT_ENOUGH_MANA.format(ChatColor.RED, new String[0]).send(this.player, "not-enough-mana");
            return false;
        }
        if (abilityData.hasModifier("stamina") && this.getStamina() < abilityData.getModifier("stamina")) {
            Message.NOT_ENOUGH_STAMINA.format(ChatColor.RED, new String[0]).send(this.player, "not-enough-stamina");
            return false;
        }
        return true;
    }
}
