// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore.load;

import org.bukkit.Location;
import net.Indyuce.mmocore.api.block.BlockInfo;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.block.Block;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmocore.api.block.BlockType;

public class MMOItemsBlockType implements BlockType
{
    private final int id;
    
    public MMOItemsBlockType(final MMOLineConfig mmoLineConfig) {
        mmoLineConfig.validate(new String[] { "id" });
        this.id = mmoLineConfig.getInt("id");
    }
    
    public MMOItemsBlockType(final CustomBlock customBlock) {
        this.id = customBlock.getId();
    }
    
    public int getBlockId() {
        return this.id;
    }
    
    public static boolean matches(final Block block) {
        return MMOItems.plugin.getCustomBlocks().isMushroomBlock(block.getType());
    }
    
    public void place(final BlockInfo.RegeneratingBlock regeneratingBlock) {
        final Location location = regeneratingBlock.getLocation();
        final CustomBlock block = MMOItems.plugin.getCustomBlocks().getBlock(this.id);
        location.getBlock().setType(block.getState().getType());
        location.getBlock().setBlockData(block.getState().getBlockData());
    }
    
    public void regenerate(final BlockInfo.RegeneratingBlock regeneratingBlock) {
        this.place(regeneratingBlock);
    }
    
    public String generateKey() {
        return "mmoitems-custom-block-" + this.id;
    }
    
    public boolean breakRestrictions(final Block block) {
        return true;
    }
}
