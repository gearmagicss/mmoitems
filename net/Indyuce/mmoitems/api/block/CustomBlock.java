// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.block;

import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.util.MushroomState;

public class CustomBlock
{
    private final int id;
    private final MushroomState state;
    private final MMOItem mmoitem;
    private final WorldGenTemplate template;
    private final int minExp;
    private final int maxExp;
    private final int requiredPower;
    
    public CustomBlock(final MushroomState state, final MMOItem mmoitem) {
        this.mmoitem = mmoitem;
        this.id = (mmoitem.hasData(ItemStats.BLOCK_ID) ? ((int)((DoubleData)mmoitem.getData(ItemStats.BLOCK_ID)).getValue()) : 0);
        this.state = state;
        this.minExp = (mmoitem.hasData(ItemStats.MIN_XP) ? ((int)((DoubleData)mmoitem.getData(ItemStats.MIN_XP)).getValue()) : 0);
        this.maxExp = (mmoitem.hasData(ItemStats.MAX_XP) ? ((int)((DoubleData)mmoitem.getData(ItemStats.MAX_XP)).getValue()) : 0);
        this.requiredPower = (mmoitem.hasData(ItemStats.REQUIRED_POWER) ? ((int)((DoubleData)mmoitem.getData(ItemStats.REQUIRED_POWER)).getValue()) : 0);
        this.template = (mmoitem.hasData(ItemStats.GEN_TEMPLATE) ? MMOItems.plugin.getWorldGen().getOrThrow(mmoitem.getData(ItemStats.GEN_TEMPLATE).toString()) : null);
    }
    
    public int getId() {
        return this.id;
    }
    
    public MushroomState getState() {
        return this.state;
    }
    
    public boolean hasGenTemplate() {
        return this.template != null;
    }
    
    public WorldGenTemplate getGenTemplate() {
        return this.template;
    }
    
    public int getMinExpDrop() {
        return this.minExp;
    }
    
    public int getMaxExpDrop() {
        return this.maxExp;
    }
    
    public int getRequiredPower() {
        return this.requiredPower;
    }
    
    public ItemStack getItem() {
        return this.mmoitem.newBuilder().build();
    }
}
