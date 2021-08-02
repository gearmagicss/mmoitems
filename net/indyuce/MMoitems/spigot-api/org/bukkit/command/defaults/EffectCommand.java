// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command.defaults;

import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.potion.PotionEffect;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;
import com.google.common.collect.ImmutableList;
import java.util.List;

@Deprecated
public class EffectCommand extends VanillaCommand
{
    private static final List<String> effects;
    
    static {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        PotionEffectType[] values;
        for (int length = (values = PotionEffectType.values()).length, i = 0; i < length; ++i) {
            final PotionEffectType type = values[i];
            if (type != null) {
                builder.add(type.getName());
            }
        }
        effects = builder.build();
    }
    
    public EffectCommand() {
        super("effect");
        this.description = "Adds/Removes effects on players";
        this.usageMessage = "/effect <player> <effect|clear> [seconds] [amplifier]";
        this.setPermission("bukkit.command.effect");
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        final Player player = sender.getServer().getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + String.format("Player, %s, not found", args[0]));
            return true;
        }
        if ("clear".equalsIgnoreCase(args[1])) {
            for (final PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            sender.sendMessage(String.format("Took all effects from %s", args[0]));
            return true;
        }
        PotionEffectType effect2 = PotionEffectType.getByName(args[1]);
        if (effect2 == null) {
            effect2 = PotionEffectType.getById(this.getInteger(sender, args[1], 0));
        }
        if (effect2 == null) {
            sender.sendMessage(ChatColor.RED + String.format("Effect, %s, not found", args[1]));
            return true;
        }
        int duration = 600;
        int duration_temp = 30;
        int amplification = 0;
        if (args.length >= 3) {
            duration_temp = this.getInteger(sender, args[2], 0, 1000000);
            if (effect2.isInstant()) {
                duration = duration_temp;
            }
            else {
                duration = duration_temp * 20;
            }
        }
        else if (effect2.isInstant()) {
            duration = 1;
        }
        if (args.length >= 4) {
            amplification = this.getInteger(sender, args[3], 0, 255);
        }
        if (duration_temp == 0) {
            if (!player.hasPotionEffect(effect2)) {
                sender.sendMessage(String.format("Couldn't take %s from %s as they do not have the effect", effect2.getName(), args[0]));
                return true;
            }
            player.removePotionEffect(effect2);
            Command.broadcastCommandMessage(sender, String.format("Took %s from %s", effect2.getName(), args[0]));
        }
        else {
            final PotionEffect applyEffect = new PotionEffect(effect2, duration, amplification);
            player.addPotionEffect(applyEffect, true);
            Command.broadcastCommandMessage(sender, String.format("Given %s (ID %d) * %d to %s for %d seconds", effect2.getName(), effect2.getId(), amplification, args[0], duration_temp));
        }
        return true;
    }
    
    @Override
    public List<String> tabComplete(final CommandSender sender, final String commandLabel, final String[] args) {
        if (args.length == 1) {
            return super.tabComplete(sender, commandLabel, args);
        }
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], EffectCommand.effects, new ArrayList<String>(EffectCommand.effects.size()));
        }
        return (List<String>)ImmutableList.of();
    }
}
