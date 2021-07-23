// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.util.identify;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.OutputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import net.Indyuce.mmoitems.api.ItemTier;
import org.bukkit.inventory.ItemFlag;
import net.Indyuce.mmoitems.api.item.util.DynamicLore;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import java.util.HashMap;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.VolatileMMOItem;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.Arrays;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.util.ConfigItem;

public class UnidentifiedItem extends ConfigItem
{
    public UnidentifiedItem(final Type type) {
        super("unidentified", type.getItem().getType());
        this.setName("#prefix#Unidentified " + type.getName());
        this.setLore(Arrays.asList("&7This item is unidentified. I must", "&7find a way to identify it!", "{tier}", "{tier}&8Item Info:", "{range}&8- &7Lvl Range: &e#range#", "{tier}&8- &7Item Tier: #prefix##tier#"));
    }
    
    public ItemBuilder newBuilder(final NBTItem nbtItem) {
        return new ItemBuilder(nbtItem);
    }
    
    public class ItemBuilder
    {
        private final int amount;
        private final NBTItem item;
        private String name;
        private final List<String> lore;
        
        public ItemBuilder(final NBTItem item) {
            this.name = UnidentifiedItem.this.getName();
            this.lore = new ArrayList<String>(UnidentifiedItem.this.getLore());
            this.amount = item.getItem().getAmount();
            this.item = item;
        }
        
        public ItemStack build() {
            final VolatileMMOItem volatileMMOItem = new VolatileMMOItem(this.item);
            final ItemTier tier = MMOItems.plugin.getTiers().findTier(volatileMMOItem);
            final int n = volatileMMOItem.hasData(ItemStats.REQUIRED_LEVEL) ? ((int)((DoubleData)volatileMMOItem.getData(ItemStats.REQUIRED_LEVEL)).getValue()) : -1;
            final HashMap<Object, String> hashMap = new HashMap<Object, String>();
            if (tier != null) {
                hashMap.put("prefix", tier.getUnidentificationInfo().getPrefix());
                hashMap.put("tier", tier.getUnidentificationInfo().getDisplayName());
                if (n > -1) {
                    final int[] calculateRange = tier.getUnidentificationInfo().calculateRange(n);
                    hashMap.put("range", calculateRange[0] + "-" + calculateRange[1]);
                }
            }
            else {
                this.name = this.name.replace("#prefix#", "");
            }
            final Object o;
            final int n2;
            this.lore.removeIf(s -> (s.startsWith("{tier}") && o == null) || (s.startsWith("{range}") && (o == null || n2 < 0)));
            for (final String str : hashMap.keySet()) {
                this.name = this.name.replace("#" + str + "#", hashMap.get(str));
            }
            for (int i = 0; i < this.lore.size(); ++i) {
                String replace = this.lore.get(i);
                for (final String str2 : hashMap.keySet()) {
                    replace = replace.replace("#" + str2 + "#", hashMap.get(str2));
                }
                this.lore.set(i, MythicLib.plugin.parseColors(replace.replace("{range}", "").replace("{tier}", "")));
            }
            this.item.getItem().setAmount(1);
            final ItemStack item = MythicLib.plugin.getVersion().getWrapper().copyTexture(this.item).addTag(new ItemTag[] { new ItemTag("MMOITEMS_UNIDENTIFIED_ITEM", (Object)this.serialize(new DynamicLore(this.item).build())) }).toItem();
            item.setAmount(this.amount);
            final ItemMeta itemMeta = item.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.setUnbreakable(true);
            itemMeta.setDisplayName(MythicLib.plugin.parseColors(this.name));
            itemMeta.setLore((List)this.lore);
            if (UnidentifiedItem.this.customModelData != null) {
                itemMeta.setCustomModelData(UnidentifiedItem.this.customModelData);
            }
            item.setItemMeta(itemMeta);
            if (UnidentifiedItem.this.material != null && UnidentifiedItem.this.material.isItem()) {
                item.setType(UnidentifiedItem.this.material);
            }
            return item;
        }
        
        private String serialize(final ItemStack itemStack) {
            try {
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream((OutputStream)byteArrayOutputStream);
                bukkitObjectOutputStream.writeObject((Object)itemStack);
                bukkitObjectOutputStream.close();
                return Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
