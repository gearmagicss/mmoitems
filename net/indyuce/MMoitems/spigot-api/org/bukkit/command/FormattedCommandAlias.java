// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.command;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.ArrayList;

public class FormattedCommandAlias extends Command
{
    private final String[] formatStrings;
    
    public FormattedCommandAlias(final String alias, final String[] formatStrings) {
        super(alias);
        this.formatStrings = formatStrings;
    }
    
    @Override
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        boolean result = false;
        final ArrayList<String> commands = new ArrayList<String>();
        String[] formatStrings;
        for (int length = (formatStrings = this.formatStrings).length, i = 0; i < length; ++i) {
            final String formatString = formatStrings[i];
            try {
                commands.add(this.buildCommand(formatString, args));
            }
            catch (Throwable throwable) {
                if (throwable instanceof IllegalArgumentException) {
                    sender.sendMessage(throwable.getMessage());
                }
                else {
                    sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
                }
                return false;
            }
        }
        for (final String command : commands) {
            result |= Bukkit.dispatchCommand(sender, command);
        }
        return result;
    }
    
    private String buildCommand(String formatString, final String[] args) {
        for (int index = formatString.indexOf("$"); index != -1; index = formatString.indexOf("$", index)) {
            final int start = index;
            if (index > 0 && formatString.charAt(start - 1) == '\\') {
                formatString = String.valueOf(formatString.substring(0, start - 1)) + formatString.substring(start);
            }
            else {
                boolean required = false;
                if (formatString.charAt(index + 1) == '$') {
                    required = true;
                    ++index;
                }
                int argStart;
                for (argStart = ++index; index < formatString.length() && inRange(formatString.charAt(index) - '0', 0, 9); ++index) {}
                if (argStart == index) {
                    throw new IllegalArgumentException("Invalid replacement token");
                }
                int position = Integer.valueOf(formatString.substring(argStart, index));
                if (position == 0) {
                    throw new IllegalArgumentException("Invalid replacement token");
                }
                --position;
                boolean rest = false;
                if (index < formatString.length() && formatString.charAt(index) == '-') {
                    rest = true;
                    ++index;
                }
                final int end = index;
                if (required && position >= args.length) {
                    throw new IllegalArgumentException("Missing required argument " + (position + 1));
                }
                final StringBuilder replacement = new StringBuilder();
                if (rest && position < args.length) {
                    for (int i = position; i < args.length; ++i) {
                        if (i != position) {
                            replacement.append(' ');
                        }
                        replacement.append(args[i]);
                    }
                }
                else if (position < args.length) {
                    replacement.append(args[position]);
                }
                formatString = String.valueOf(formatString.substring(0, start)) + replacement.toString() + formatString.substring(end);
                index = start + replacement.length();
            }
        }
        return formatString;
    }
    
    private static boolean inRange(final int i, final int j, final int k) {
        return i >= j && i <= k;
    }
}
