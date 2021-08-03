// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.data.SkullTextureData;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Damageable;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class ItemSkin extends UseItem
{
    public ItemSkin(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem);
    }
    
    public ApplyResult applyOntoItem(final NBTItem nbtItem, final Type type) {
        if (type == Type.SKIN) {
            return new ApplyResult(ResultType.NONE);
        }
        if (MMOItems.plugin.getConfig().getBoolean("locked-skins") && nbtItem.getBoolean("MMOITEMS_HAS_SKIN")) {
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
            Message.SKIN_REJECTED.format(ChatColor.RED, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)this.player);
            return new ApplyResult(ResultType.NONE);
        }
        int n = 0;
        if (this.getMMOItem().hasData(ItemStats.COMPATIBLE_TYPES)) {
            final Iterator<String> iterator = ((StringListData)this.getMMOItem().getData(ItemStats.COMPATIBLE_TYPES)).getList().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equalsIgnoreCase(type.getId())) {
                    n = 1;
                    break;
                }
            }
            if (n == 0) {
                this.player.playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
                Message.SKIN_INCOMPATIBLE.format(ChatColor.RED, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)this.player);
                return new ApplyResult(ResultType.NONE);
            }
        }
        if (this.getMMOItem().hasData(ItemStats.COMPATIBLE_IDS)) {
            final Iterator<String> iterator2 = ((StringListData)this.getMMOItem().getData(ItemStats.COMPATIBLE_IDS)).getList().iterator();
            while (iterator2.hasNext()) {
                if (iterator2.next().equalsIgnoreCase(nbtItem.getString("MMOITEMS_ITEM_ID"))) {
                    n = 1;
                    break;
                }
            }
            if (n == 0) {
                this.player.playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
                Message.SKIN_INCOMPATIBLE.format(ChatColor.RED, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)this.player);
                return new ApplyResult(ResultType.NONE);
            }
        }
        final double stat = this.getNBTItem().getStat(ItemStats.SUCCESS_RATE.getId());
        if (stat != 0.0 && ItemSkin.RANDOM.nextDouble() < 1.0 - stat / 100.0) {
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            Message.SKIN_BROKE.format(ChatColor.RED, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)this.player);
            return new ApplyResult(ResultType.FAILURE);
        }
        nbtItem.addTag(new ItemTag[] { new ItemTag("MMOITEMS_HAS_SKIN", (Object)true) });
        nbtItem.addTag(new ItemTag[] { new ItemTag("MMOITEMS_SKIN_ID", (Object)this.getNBTItem().getString("MMOITEMS_ITEM_ID")) });
        if (this.getNBTItem().getInteger("CustomModelData") != 0) {
            nbtItem.addTag(new ItemTag[] { new ItemTag("CustomModelData", (Object)this.getNBTItem().getInteger("CustomModelData")) });
        }
        if (!this.getNBTItem().getString("MMOITEMS_ITEM_PARTICLES").isEmpty()) {
            nbtItem.addTag(new ItemTag[] { new ItemTag("MMOITEMS_ITEM_PARTICLES", (Object)this.getNBTItem().getString("MMOITEMS_ITEM_PARTICLES")) });
        }
        final ItemStack item = nbtItem.toItem();
        if (item.getType() != this.getNBTItem().getItem().getType()) {
            item.setType(this.getNBTItem().getItem().getType());
        }
        final ItemMeta itemMeta = item.getItemMeta();
        final ItemMeta itemMeta2 = this.getNBTItem().getItem().getItemMeta();
        if (itemMeta2.isUnbreakable()) {
            itemMeta.setUnbreakable(true);
            if (itemMeta instanceof Damageable && itemMeta2 instanceof Damageable) {
                ((Damageable)itemMeta).setDamage(((Damageable)itemMeta2).getDamage());
            }
        }
        if (itemMeta2 instanceof LeatherArmorMeta && itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)itemMeta).setColor(((LeatherArmorMeta)itemMeta2).getColor());
        }
        if (this.getMMOItem().hasData(ItemStats.SKULL_TEXTURE) && item.getType() == VersionMaterial.PLAYER_HEAD.toMaterial() && this.getNBTItem().getItem().getType() == VersionMaterial.PLAYER_HEAD.toMaterial()) {
            try {
                final Field declaredField = ((LeatherArmorMeta)itemMeta).getClass().getDeclaredField("profile");
                declaredField.setAccessible(true);
                declaredField.set(itemMeta, ((SkullTextureData)this.getMMOItem().getData(ItemStats.SKULL_TEXTURE)).getGameProfile());
            }
            catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                MMOItems.plugin.getLogger().warning("Could not read skull texture");
            }
        }
        item.setItemMeta(itemMeta);
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        Message.SKIN_APPLIED.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(nbtItem.getItem())).send((CommandSender)this.player);
        return new ApplyResult(item);
    }
    
    public static class ApplyResult
    {
        private final ResultType type;
        private final ItemStack result;
        
        public ApplyResult(final ResultType resultType) {
            this(null, resultType);
        }
        
        public ApplyResult(final ItemStack itemStack) {
            this(itemStack, ResultType.SUCCESS);
        }
        
        public ApplyResult(final ItemStack result, final ResultType type) {
            this.type = type;
            this.result = result;
        }
        
        public ResultType getType() {
            return this.type;
        }
        
        public ItemStack getResult() {
            return this.result;
        }
    }
    
    public enum ResultType
    {
        FAILURE, 
        NONE, 
        SUCCESS;
    }
}
