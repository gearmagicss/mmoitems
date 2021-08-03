// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.droptable.item;

import org.bukkit.inventory.ItemStack;
import javax.annotation.Nullable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import java.util.Random;

public abstract class DropItem
{
    private final double drop;
    private final int min;
    private final int max;
    protected static final Random random;
    
    public DropItem(final double drop, final int min, final int max) {
        this.drop = drop;
        this.min = min;
        this.max = max;
    }
    
    public DropItem(final String s) {
        final String[] split = s.split(",");
        this.drop = Double.parseDouble(split[0]) / 100.0;
        final String[] split2 = split[1].split("-");
        this.min = Integer.parseInt(split2[0]);
        this.max = ((split2.length > 1) ? Integer.parseInt(split2[1]) : this.min);
    }
    
    public boolean rollDrop() {
        return DropItem.random.nextDouble() < this.drop;
    }
    
    public int rollAmount() {
        return (this.max > this.min) ? (this.min + DropItem.random.nextInt(this.max - this.min + 1)) : this.min;
    }
    
    public ItemStack getItem(@Nullable final PlayerData playerData) {
        return this.getItem(playerData, this.rollAmount());
    }
    
    public abstract ItemStack getItem(@Nullable final PlayerData p0, final int p1);
    
    public abstract String getKey();
    
    static {
        random = new Random();
    }
}
