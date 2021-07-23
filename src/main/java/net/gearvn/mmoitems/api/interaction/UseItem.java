// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import net.Indyuce.mmoitems.api.interaction.weapon.Weapon;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Staff;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Lute;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Whip;
import net.Indyuce.mmoitems.api.interaction.weapon.Gauntlet;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Crossbow;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.Musket;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.stat.data.CommandData;
import java.util.function.Consumer;
import net.Indyuce.mmoitems.stat.data.CommandListData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.Random;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;

public class UseItem
{
    protected final Player player;
    protected final PlayerData playerData;
    protected final VolatileMMOItem mmoitem;
    protected static final Random RANDOM;
    
    public UseItem(final Player player, final NBTItem nbtItem) {
        this(PlayerData.get((OfflinePlayer)player), nbtItem);
    }
    
    public UseItem(final PlayerData playerData, final NBTItem nbtItem) {
        this.player = playerData.getPlayer();
        this.playerData = playerData;
        this.mmoitem = new VolatileMMOItem(nbtItem);
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public PlayerData getPlayerData() {
        return this.playerData;
    }
    
    public VolatileMMOItem getMMOItem() {
        return this.mmoitem;
    }
    
    public NBTItem getNBTItem() {
        return this.mmoitem.getNBT();
    }
    
    public ItemStack getItem() {
        return this.mmoitem.getNBT().getItem();
    }
    
    public boolean applyItemCosts() {
        return this.playerData.getRPG().canUse(this.mmoitem.getNBT(), true);
    }
    
    public void executeCommands() {
        if (MMOItems.plugin.getFlags().isFlagAllowed(this.player, FlagPlugin.CustomFlag.MI_COMMANDS) && this.mmoitem.hasData(ItemStats.COMMANDS)) {
            ((CommandListData)this.mmoitem.getData(ItemStats.COMMANDS)).getCommands().forEach(this::scheduleCommandExecution);
        }
    }
    
    private void scheduleCommandExecution(final CommandData commandData) {
        final String parse = MMOItems.plugin.getPlaceholderParser().parse((OfflinePlayer)this.player, commandData.getCommand());
        if (!commandData.hasDelay()) {
            this.dispatchCommand(parse, commandData.isConsoleCommand(), commandData.hasOpPerms());
        }
        else {
            Bukkit.getScheduler().runTaskLater((Plugin)MMOItems.plugin, () -> this.dispatchCommand(parse, commandData.isConsoleCommand(), commandData.hasOpPerms()), (long)commandData.getDelay() * 20L);
        }
    }
    
    private void dispatchCommand(final String s, final boolean b, final boolean b2) {
        if (b) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), s);
            return;
        }
        if (b2 && !this.player.isOp()) {
            this.player.setOp(true);
            try {
                Bukkit.dispatchCommand((CommandSender)this.player, s);
            }
            catch (Exception ex) {}
            this.player.setOp(false);
        }
        else {
            Bukkit.dispatchCommand((CommandSender)this.player, s);
        }
    }
    
    public static UseItem getItem(final Player player, final NBTItem nbtItem, final String s) {
        return getItem(player, nbtItem, Type.get(s));
    }
    
    public static UseItem getItem(@NotNull final Player player, @NotNull final NBTItem nbtItem, @NotNull final Type type) {
        if (type.corresponds(Type.CONSUMABLE)) {
            return new Consumable(player, nbtItem);
        }
        if (type.corresponds(Type.SKIN)) {
            return new ItemSkin(player, nbtItem);
        }
        if (type.corresponds(Type.GEM_STONE)) {
            return new GemStone(player, nbtItem);
        }
        if (type.corresponds(Type.MUSKET)) {
            return new Musket(player, nbtItem);
        }
        if (type.corresponds(Type.CROSSBOW)) {
            return new Crossbow(player, nbtItem);
        }
        if (type.corresponds(Type.GAUNTLET)) {
            return new Gauntlet(player, nbtItem);
        }
        if (type.corresponds(Type.WHIP)) {
            return new Whip(player, nbtItem);
        }
        if (type.corresponds(Type.LUTE)) {
            return new Lute(player, nbtItem);
        }
        if (type.corresponds(Type.STAFF)) {
            return new Staff(player, nbtItem);
        }
        return type.isWeapon() ? new Weapon(player, nbtItem) : new UseItem(player, nbtItem);
    }
    
    static {
        RANDOM = new Random();
    }
}
