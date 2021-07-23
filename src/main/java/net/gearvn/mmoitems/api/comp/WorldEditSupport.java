// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp;

import com.sk89q.worldedit.world.block.BlockType;
import net.Indyuce.mmoitems.api.util.MushroomState;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.Indyuce.mmoitems.MMOItems;
import com.sk89q.worldedit.extension.input.ParserContext;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.internal.registry.InputParser;
import com.sk89q.worldedit.WorldEdit;

public class WorldEditSupport
{
    public WorldEditSupport() {
        WorldEdit.getInstance().getBlockFactory().register((InputParser)new WECustomBlockInputParser());
    }
    
    public static class WECustomBlockInputParser extends InputParser<BaseBlock>
    {
        public WECustomBlockInputParser() {
            super(WorldEdit.getInstance());
        }
        
        public BaseBlock parseFromInput(String lowerCase, final ParserContext parserContext) {
            lowerCase = lowerCase.toLowerCase();
            if (!lowerCase.startsWith("mmoitems-")) {
                return null;
            }
            int int1;
            try {
                int1 = Integer.parseInt(lowerCase.split("-")[1]);
            }
            catch (NumberFormatException ex) {
                return null;
            }
            final CustomBlock block = MMOItems.plugin.getCustomBlocks().getBlock(int1);
            if (block == null) {
                return null;
            }
            final MushroomState state = block.getState();
            BlockType blockType = null;
            switch (state.getType()) {
                case MUSHROOM_STEM: {
                    blockType = BlockTypes.MUSHROOM_STEM;
                    break;
                }
                case BROWN_MUSHROOM_BLOCK: {
                    blockType = BlockTypes.BROWN_MUSHROOM_BLOCK;
                    break;
                }
                case RED_MUSHROOM_BLOCK: {
                    blockType = BlockTypes.RED_MUSHROOM_BLOCK;
                    break;
                }
                default: {
                    return null;
                }
            }
            return blockType.getDefaultState().with((Property)blockType.getPropertyMap().get("up"), (Object)state.getSide("up")).with((Property)blockType.getPropertyMap().get("down"), (Object)state.getSide("down")).with((Property)blockType.getPropertyMap().get("north"), (Object)state.getSide("north")).with((Property)blockType.getPropertyMap().get("south"), (Object)state.getSide("south")).with((Property)blockType.getPropertyMap().get("east"), (Object)state.getSide("east")).with((Property)blockType.getPropertyMap().get("west"), (Object)state.getSide("west")).toBaseBlock();
        }
    }
}
