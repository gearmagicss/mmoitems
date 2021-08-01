// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.Random;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import java.util.logging.Level;
import com.mojang.authlib.GameProfile;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.plugin.Plugin;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;

public class NoClipItem implements Listener
{
    private final Item item;
    
    public NoClipItem(final Location location, final ItemStack itemStack) {
        (this.item = location.getWorld().dropItem(location, this.stripItemData(itemStack))).setPickupDelay(1000000);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)MMOItems.plugin);
    }
    
    public Item getEntity() {
        return this.item;
    }
    
    public void close() {
        this.item.remove();
        EntityPortalEnterEvent.getHandlerList().unregister((Listener)this);
        InventoryPickupItemEvent.getHandlerList().unregister((Listener)this);
        EntityPickupItemEvent.getHandlerList().unregister((Listener)this);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void a(final InventoryPickupItemEvent inventoryPickupItemEvent) {
        if (inventoryPickupItemEvent.getItem().equals(this.item)) {
            inventoryPickupItemEvent.setCancelled(true);
            this.close();
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void b(final EntityPortalEnterEvent entityPortalEnterEvent) {
        if (entityPortalEnterEvent.getEntity().equals(this.item)) {
            this.close();
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void c(final EntityPickupItemEvent entityPickupItemEvent) {
        if (entityPickupItemEvent.getItem().equals(this.item)) {
            this.close();
        }
    }
    
    private ItemStack stripItemData(final ItemStack itemStack) {
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack);
        final ItemStack itemStack2 = new ItemStack(itemStack.getType());
        final ItemMeta itemMeta = itemStack2.getItemMeta();
        itemStack2.setAmount(1);
        if (itemStack.getItemMeta().hasEnchants()) {
            itemMeta.addEnchant(Enchantment.LUCK, 0, true);
            itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        if (MythicLib.plugin.getVersion().isStrictlyHigher(new int[] { 1, 13 }) && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() != 0) {
            itemMeta.setCustomModelData(Integer.valueOf(itemStack.getItemMeta().getCustomModelData()));
        }
        if (itemStack.getItemMeta() instanceof SkullMeta) {
            try {
                final Field declaredField = itemStack.getItemMeta().getClass().getDeclaredField("profile");
                declaredField.setAccessible(true);
                final GameProfile value = (GameProfile)declaredField.get(itemStack.getItemMeta());
                final Field declaredField2 = itemMeta.getClass().getDeclaredField("profile");
                declaredField2.setAccessible(true);
                declaredField2.set(itemMeta, value);
            }
            catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                MMOItems.plugin.getLogger().log(Level.WARNING, "Could not set skull texture on stripItemData method in the NoClipItem class. Please report this issue!");
            }
        }
        if (itemStack.getItemMeta() instanceof LeatherArmorMeta && itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)itemMeta).setColor(((LeatherArmorMeta)itemStack.getItemMeta()).getColor());
        }
        itemStack2.setItemMeta(itemMeta);
        final NBTItem nbtItem2 = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack2);
        nbtItem2.addTag(new ItemTag[] { new ItemTag("MMOITEMS_TIER", (Object)(nbtItem.getString("MMOITEMS_TIER").trim().isEmpty() ? null : nbtItem.getString("MMOITEMS_TIER"))) });
        nbtItem2.addTag(new ItemTag[] { new ItemTag("MMOITEMS_NO_STACK", (Object)new Random().nextInt(Integer.MAX_VALUE)) });
        nbtItem2.addTag(new ItemTag[] { new ItemTag("MMOITEMS_NO_CLIP_ITEM", (Object)true) });
        return nbtItem2.toItem();
    }
}
