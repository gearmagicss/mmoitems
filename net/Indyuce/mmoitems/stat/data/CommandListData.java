// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat.data;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.data.type.Mergeable;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class CommandListData implements StatData, Mergeable, RandomStatData
{
    @NotNull
    private final Set<CommandData> commands;
    
    public CommandListData(@NotNull final Set<CommandData> commands) {
        this.commands = commands;
    }
    
    public CommandListData(final CommandData... array) {
        this(new HashSet<CommandData>());
        this.add(array);
    }
    
    public void add(final CommandData... a) {
        this.commands.addAll(Arrays.asList(a));
    }
    
    @NotNull
    public Set<CommandData> getCommands() {
        return this.commands;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CommandListData)) {
            return false;
        }
        if (((CommandListData)o).getCommands().size() != this.getCommands().size()) {
            return false;
        }
        for (final CommandData commandData : ((CommandListData)o).getCommands()) {
            if (commandData == null) {
                continue;
            }
            boolean b = true;
            final Iterator<CommandData> iterator2 = this.getCommands().iterator();
            while (iterator2.hasNext()) {
                if (commandData.equals(iterator2.next())) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void merge(final StatData statData) {
        Validate.isTrue(statData instanceof CommandListData, "Cannot merge two different stat data types");
        this.commands.addAll(((CommandListData)statData).commands);
    }
    
    @NotNull
    @Override
    public StatData cloneData() {
        return new CommandListData(this.commands);
    }
    
    @Override
    public boolean isClear() {
        return this.getCommands().size() == 0;
    }
    
    @Override
    public StatData randomize(final MMOItemBuilder mmoItemBuilder) {
        return new CommandListData(new HashSet<CommandData>(this.commands));
    }
}
