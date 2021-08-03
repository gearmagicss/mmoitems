// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import java.util.Arrays;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import java.util.Iterator;
import java.util.logging.Level;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.ItemTier;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.Material;
import java.util.Set;
import java.util.Collection;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import java.util.Optional;
import org.bukkit.block.data.BlockData;
import net.Indyuce.mmoitems.api.util.MushroomState;
import net.Indyuce.mmoitems.MMOItems;
import java.util.HashMap;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import java.util.Map;
import java.util.List;

public class BlockManager implements Reloadable
{
    private static final List<Integer> downIds;
    private static final List<Integer> eastIds;
    private static final List<Integer> northIds;
    private static final List<Integer> southIds;
    private static final List<Integer> upIds;
    private static final List<Integer> westIds;
    private final Map<Integer, CustomBlock> customBlocks;
    private final Map<Integer, CustomBlock> mushroomStateValue;
    
    public BlockManager() {
        this.customBlocks = new HashMap<Integer, CustomBlock>();
        this.mushroomStateValue = new HashMap<Integer, CustomBlock>();
        this.reload();
    }
    
    public void register(final CustomBlock customBlock) {
        this.customBlocks.put(customBlock.getId(), customBlock);
        this.mushroomStateValue.put(customBlock.getState().getUniqueId(), customBlock);
        if (customBlock.hasGenTemplate()) {
            MMOItems.plugin.getWorldGen().assign(customBlock, customBlock.getGenTemplate());
        }
    }
    
    public CustomBlock getBlock(final int i) {
        return (i > 0 && i < 161 && i != 54) ? this.customBlocks.get(i) : null;
    }
    
    public CustomBlock getBlock(final MushroomState mushroomState) {
        return this.mushroomStateValue.get(mushroomState.getUniqueId());
    }
    
    public Optional<CustomBlock> getFromBlock(final BlockData blockData) {
        if (!this.isMushroomBlock(blockData.getMaterial()) || !(blockData instanceof MultipleFacing)) {
            return Optional.empty();
        }
        final MultipleFacing multipleFacing = (MultipleFacing)blockData;
        final MushroomState mushroomState = new MushroomState(blockData.getMaterial(), multipleFacing.hasFace(BlockFace.UP), multipleFacing.hasFace(BlockFace.DOWN), multipleFacing.hasFace(BlockFace.WEST), multipleFacing.hasFace(BlockFace.EAST), multipleFacing.hasFace(BlockFace.SOUTH), multipleFacing.hasFace(BlockFace.NORTH));
        return this.isVanilla(mushroomState) ? Optional.empty() : Optional.of(this.getBlock(mushroomState));
    }
    
    public Collection<CustomBlock> getAll() {
        return this.customBlocks.values();
    }
    
    public Set<Integer> getBlockIds() {
        return this.customBlocks.keySet();
    }
    
    public boolean isVanilla(final MushroomState mushroomState) {
        return !this.mushroomStateValue.containsKey(mushroomState.getUniqueId());
    }
    
    public boolean isMushroomBlock(final Material material) {
        return material == Material.BROWN_MUSHROOM_BLOCK || material == Material.MUSHROOM_STEM || material == Material.RED_MUSHROOM_BLOCK;
    }
    
    @Override
    public void reload() {
        this.customBlocks.clear();
        this.mushroomStateValue.clear();
        final Iterator<MMOItemTemplate> iterator = MMOItems.plugin.getTemplates().getTemplates(Type.BLOCK).iterator();
        while (iterator.hasNext()) {
            final MMOItem build = iterator.next().newBuilder(0, null).build();
            final int i = build.hasData(ItemStats.BLOCK_ID) ? ((int)((DoubleData)build.getData(ItemStats.BLOCK_ID)).getValue()) : 0;
            if (i > 0 && i < 161 && i != 54) {
                try {
                    this.register(new CustomBlock(new MushroomState(this.getType(i), BlockManager.upIds.contains(i), BlockManager.downIds.contains(i), BlockManager.westIds.contains(i), BlockManager.eastIds.contains(i), BlockManager.southIds.contains(i), BlockManager.northIds.contains(i)), build));
                }
                catch (IllegalArgumentException ex) {
                    MMOItems.plugin.getLogger().log(Level.WARNING, "Could not load custom block '" + i + "': " + ex.getMessage());
                }
            }
        }
    }
    
    private Material getType(final int n) {
        return (n < 54) ? Material.BROWN_MUSHROOM_BLOCK : ((n > 99) ? Material.MUSHROOM_STEM : Material.RED_MUSHROOM_BLOCK);
    }
    
    static {
        downIds = Arrays.asList(23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160);
        eastIds = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160);
        northIds = Arrays.asList(4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21, 22, 31, 32, 33, 34, 35, 36, 37, 38, 47, 48, 49, 50, 51, 52, 53, 55, 56, 57, 58, 63, 64, 65, 66, 67, 68, 77, 78, 79, 80, 81, 82, 83, 84, 93, 94, 95, 96, 97, 98, 99, 107, 108, 109, 110, 111, 112, 113, 114, 123, 124, 125, 126, 127, 128, 129, 138, 139, 140, 141, 142, 143, 144, 145, 154, 155, 156, 157, 158, 159, 160);
        southIds = Arrays.asList(2, 3, 6, 7, 8, 9, 13, 14, 15, 19, 20, 21, 22, 27, 28, 29, 30, 35, 36, 37, 38, 43, 44, 45, 46, 51, 52, 53, 55, 56, 57, 58, 61, 62, 65, 66, 67, 68, 73, 74, 75, 76, 81, 82, 83, 84, 89, 90, 91, 92, 97, 98, 99, 103, 104, 105, 106, 111, 112, 113, 114, 119, 120, 121, 122, 127, 128, 129, 134, 135, 136, 137, 142, 143, 144, 145, 150, 151, 152, 153, 158, 159, 160);
        upIds = Arrays.asList(8, 9, 12, 15, 18, 21, 22, 25, 26, 29, 30, 33, 34, 37, 38, 41, 42, 45, 46, 49, 50, 53, 57, 58, 60, 62, 64, 67, 68, 71, 72, 75, 76, 79, 80, 83, 84, 87, 88, 91, 92, 95, 96, 99, 101, 102, 105, 106, 109, 110, 113, 114, 117, 118, 121, 122, 125, 126, 128, 129, 132, 133, 136, 137, 140, 141, 144, 145, 148, 149, 152, 153, 156, 157, 160);
        westIds = Arrays.asList(1, 3, 5, 7, 9, 11, 12, 14, 15, 17, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 56, 58, 59, 60, 61, 62, 63, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 100, 102, 104, 106, 108, 110, 112, 114, 116, 118, 120, 122, 124, 126, 129, 131, 133, 135, 137, 139, 141, 143, 145, 147, 149, 151, 153, 155, 157, 159);
    }
}
