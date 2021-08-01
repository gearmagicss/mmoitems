// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.mmoitem;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.Type;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.item.NBTItem;

public abstract class ReadMMOItem extends MMOItem
{
    @NotNull
    private final NBTItem item;
    
    public ReadMMOItem(@NotNull final NBTItem item) {
        super(Type.get(item.getType()), item.getString("MMOITEMS_ITEM_ID"));
        this.item = item;
    }
    
    @Override
    public int getDamage() {
        if (this.hasData(ItemStats.MAX_DURABILITY)) {
            final DurabilityItem durabilityItem = new DurabilityItem(null, this.getNBT());
            return durabilityItem.getMaxDurability() - durabilityItem.getDurability();
        }
        final ItemStack item = this.getNBT().getItem();
        if (item.hasItemMeta()) {
            final ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta instanceof Damageable) {
                return ((Damageable)itemMeta).getDamage();
            }
        }
        return 0;
    }
    
    @NotNull
    public NBTItem getNBT() {
        return this.item;
    }
}
