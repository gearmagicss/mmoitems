// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.completion;

import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.MMOItems;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class UpdateItemCompletion implements TabCompleter
{
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String s3, final String[] array) {
        if (!commandSender.hasPermission("mmoitems.update")) {
            return null;
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        if (array.length == 1) {
            final Iterator<Type> iterator = MMOItems.plugin.getTypes().getAll().iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().getId());
            }
        }
        if (array.length == 2 && Type.isValid(array[0])) {
            Type.get(array[0]).getConfigFile().getConfig().getKeys(false).forEach(s -> list.add(s.toUpperCase()));
        }
        return (List<String>)(array[array.length - 1].isEmpty() ? list : list.stream().filter(s2 -> s2.toLowerCase().startsWith(array[array.length - 1].toLowerCase())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
    }
}
