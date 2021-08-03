// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.droptable.item;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.player.PlayerData;

public class BlockDropItem extends DropItem
{
    private final int blockId;
    
    public BlockDropItem(final int blockId, final String s) {
        super(s);
        this.blockId = blockId;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    @Override
    public ItemStack getItem(final PlayerData playerData, final int n) {
        return MMOItems.plugin.getCustomBlocks().getBlock(this.blockId).getItem();
    }
    
    @Override
    public String getKey() {
        return "block." + this.getBlockId();
    }
}
