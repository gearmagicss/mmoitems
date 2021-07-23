// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.droptable.item;

import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.Material;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.util.RandomAmount;
import net.Indyuce.mmoitems.api.Type;

public class MMOItemDropItem extends DropItem
{
    private final Type type;
    private final String id;
    private final double unidentification;
    
    public MMOItemDropItem(final Type type, final String s, final double n, final double n2, final RandomAmount randomAmount) {
        this(type, s, n, n2, randomAmount.getMin(), randomAmount.getMax());
    }
    
    public MMOItemDropItem(final Type type, final String id, final double n, final double unidentification, final int n2, final int n3) {
        super(n, n2, n3);
        this.type = type;
        this.id = id;
        this.unidentification = unidentification;
    }
    
    public MMOItemDropItem(final Type type, final String id, final String s) {
        super(s);
        this.type = type;
        this.id = id;
        this.unidentification = Double.parseDouble(s.split(",")[2]) / 100.0;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean rollIdentification() {
        return MMOItemDropItem.random.nextDouble() < this.unidentification;
    }
    
    @Override
    public ItemStack getItem(final PlayerData playerData, final int amount) {
        if (!MMOItems.plugin.getTemplates().hasTemplate(this.type, this.id)) {
            return null;
        }
        ItemStack build = (playerData == null) ? MMOItems.plugin.getItem(this.type, this.id) : MMOItems.plugin.getItem(this.type, this.id, playerData);
        if (build == null || build.getType() == Material.AIR) {
            return null;
        }
        if (this.rollIdentification()) {
            build = this.type.getUnidentifiedTemplate().newBuilder(NBTItem.get(build)).build();
        }
        build.setAmount(amount);
        return build;
    }
    
    @Override
    public String getKey() {
        return this.type.getId() + "." + this.id;
    }
}
