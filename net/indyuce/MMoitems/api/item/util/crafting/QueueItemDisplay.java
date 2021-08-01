// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util.crafting;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.utils.adventure.text.Component;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemFlag;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.Indyuce.mmoitems.api.crafting.CraftingStatus;
import org.bukkit.Material;
import net.Indyuce.mmoitems.api.item.util.ConfigItem;

public class QueueItemDisplay extends ConfigItem
{
    private static final long[] ms;
    private static final String[] chars;
    
    public QueueItemDisplay() {
        super("QUEUE_ITEM_DISPLAY", Material.BARRIER, "&6&lQueue&f #name#", new String[] { "{ready}&7&oThis item was successfully crafted.", "{queue}&7&oThis item is in the crafting queue.", "{queue}", "{queue}&7Time Left: &c#left#", "", "{ready}&eClick to claim!", "{queue}&eClick to cancel." });
    }
    
    public ItemBuilder newBuilder(final CraftingStatus.CraftingQueue.CraftingInfo craftingInfo, final int n) {
        return new ItemBuilder(craftingInfo, n);
    }
    
    private String formatDelay(long n) {
        final StringBuilder sb = new StringBuilder();
        for (int n2 = 0, n3 = QueueItemDisplay.ms.length - 1; n3 >= 0 && n2 < 2; --n3) {
            if (n >= QueueItemDisplay.ms[n3]) {
                sb.append(n / QueueItemDisplay.ms[n3]).append(QueueItemDisplay.chars[n3]).append(" ");
                n %= QueueItemDisplay.ms[n3];
                ++n2;
            }
        }
        return (sb.length() == 0) ? "1s" : sb.toString();
    }
    
    static {
        ms = new long[] { 1000L, 60000L, 3600000L, 86400000L };
        chars = new String[] { "s", "m", "h", "d" };
    }
    
    public class ItemBuilder
    {
        private final CraftingStatus.CraftingQueue.CraftingInfo crafting;
        private final int position;
        private final String name;
        private final List<String> lore;
        
        public ItemBuilder(final CraftingStatus.CraftingQueue.CraftingInfo crafting, final int position) {
            this.name = QueueItemDisplay.this.getName();
            this.lore = new ArrayList<String>(QueueItemDisplay.this.getLore());
            this.crafting = crafting;
            this.position = position;
        }
        
        public ItemStack build() {
            final HashMap<Object, String> hashMap = new HashMap<Object, String>();
            final Iterator<String> iterator = this.lore.iterator();
            while (iterator.hasNext()) {
                final String s2 = iterator.next();
                if (s2.startsWith("{queue}")) {
                    if (this.crafting.isReady()) {
                        iterator.remove();
                        continue;
                    }
                    hashMap.put(s2, s2.replace("{queue}", ""));
                }
                if (s2.startsWith("{ready}")) {
                    if (!this.crafting.isReady()) {
                        iterator.remove();
                    }
                    else {
                        hashMap.put(s2, s2.replace("{ready}", ""));
                    }
                }
            }
            for (final String s3 : hashMap.keySet()) {
                this.lore.set(this.lore.indexOf(s3), hashMap.get(s3).replace("#left#", QueueItemDisplay.this.formatDelay(this.crafting.getLeft())));
            }
            final ItemStack preview = this.crafting.getRecipe().getOutput().getPreview();
            preview.setAmount(this.position);
            final ItemMeta itemMeta = preview.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            preview.setItemMeta(itemMeta);
            final NBTItem value = NBTItem.get(preview);
            value.setDisplayNameComponent(LegacyComponent.parse(this.name.replace("#name#", itemMeta.getDisplayName())));
            final ArrayList<Component> loreComponents = new ArrayList<Component>();
            this.lore.forEach(s -> loreComponents.add(LegacyComponent.parse(s)));
            value.setLoreComponents((List)loreComponents);
            return value.addTag(new ItemTag[] { new ItemTag("queueId", (Object)this.crafting.getUniqueId().toString()) }).toItem();
        }
    }
}
