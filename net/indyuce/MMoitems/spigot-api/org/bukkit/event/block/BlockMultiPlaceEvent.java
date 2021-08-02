// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.block;

import java.util.Collection;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import java.util.List;

public class BlockMultiPlaceEvent extends BlockPlaceEvent
{
    private final List<BlockState> states;
    
    public BlockMultiPlaceEvent(final List<BlockState> states, final Block clicked, final ItemStack itemInHand, final Player thePlayer, final boolean canBuild) {
        super(states.get(0).getBlock(), states.get(0), clicked, itemInHand, thePlayer, canBuild);
        this.states = (List<BlockState>)ImmutableList.copyOf((Collection<?>)states);
    }
    
    public List<BlockState> getReplacedBlockStates() {
        return this.states;
    }
}
