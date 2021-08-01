// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.manager;

import org.bukkit.ChatColor;
import java.util.Arrays;
import org.bukkit.enchantments.Enchantment;
import java.util.Iterator;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import org.bukkit.inventory.meta.Damageable;
import io.lumine.mythic.utils.adventure.text.format.NamedTextColor;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.utils.adventure.text.Component;
import java.util.LinkedList;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class UpdaterManager implements Listener
{
    @EventHandler
    public void updateOnClick(final InventoryClickEvent inventoryClickEvent) {
        final ItemStack currentItem = inventoryClickEvent.getCurrentItem();
        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }
        final ItemStack updated = this.getUpdated(currentItem, (Player)inventoryClickEvent.getWhoClicked());
        if (!updated.equals((Object)currentItem)) {
            inventoryClickEvent.setCurrentItem(updated);
        }
    }
    
    @EventHandler
    public void updateOnJoin(final PlayerJoinEvent playerJoinEvent) {
        final Player player = playerJoinEvent.getPlayer();
        player.getEquipment().setHelmet(this.getUpdated(player.getEquipment().getHelmet(), player));
        player.getEquipment().setChestplate(this.getUpdated(player.getEquipment().getChestplate(), player));
        player.getEquipment().setLeggings(this.getUpdated(player.getEquipment().getLeggings(), player));
        player.getEquipment().setBoots(this.getUpdated(player.getEquipment().getBoots(), player));
        for (int i = 0; i < 9; ++i) {
            player.getInventory().setItem(i, this.getUpdated(player.getInventory().getItem(i), player));
        }
        player.getEquipment().setItemInOffHand(this.getUpdated(player.getEquipment().getItemInOffHand(), player));
    }
    
    public ItemStack getUpdated(final ItemStack itemStack, final Player player) {
        return this.getUpdated(MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack), player);
    }
    
    public ItemStack getUpdated(final NBTItem nbtItem, final Player player) {
        final Type value = Type.get(nbtItem.getType());
        if (value == null) {
            return nbtItem.getItem();
        }
        final ItemStack build = MMOItems.plugin.getTemplates().getTemplate(value, nbtItem.getString("MMOITEMS_ITEM_ID")).newBuilder(PlayerData.get((OfflinePlayer)player).getRPG()).build().newBuilder().build();
        build.setAmount(nbtItem.getItem().getAmount());
        final ItemMeta itemMeta = build.getItemMeta();
        final NBTItem value2 = NBTItem.get(build);
        final LinkedList<Component> loreComponents = new LinkedList<Component>();
        itemMeta.getLore().forEach(s -> loreComponents.add(LegacyComponent.parse(s)));
        nbtItem.getItem().getItemMeta().getEnchants().forEach((enchantment, n) -> itemMeta.addEnchant(enchantment, (int)n, true));
        int n2 = 0;
        for (final Component component : value2.getLoreComponents()) {
            if (component.color() != NamedTextColor.GRAY) {
                break;
            }
            loreComponents.add(n2++, component);
        }
        ((Damageable)itemMeta).setDamage(((Damageable)nbtItem.getItem().getItemMeta()).getDamage());
        build.setItemMeta(itemMeta);
        value2.setDisplayNameComponent(LegacyComponent.parse(nbtItem.getItem().getItemMeta().getDisplayName()));
        value2.setLoreComponents((List)loreComponents);
        return value2.toItem();
    }
    
    public enum KeepOption
    {
        KEEP_LORE(new String[] { "Any lore line starting with '&7' will be", "kept when updating your item.", "", "This option is supposed to keep", "the item custom enchants.", ChatColor.RED + "May not support every enchant plugin." }), 
        KEEP_ENCHANTS(new String[] { "The item keeps its old enchantments." }), 
        KEEP_DURABILITY(new String[] { "The item keeps its durability.", "Don't use this option if you", "are using texture-by-durability!" }), 
        KEEP_NAME(new String[] { "The item keeps its display name." }), 
        KEEP_GEMS(new String[] { "The item keeps its empty gem", "sockets and applied gems." }), 
        KEEP_SOULBOUND(new String[] { "The item keeps its soulbound data." });
        
        private final List<String> lore;
        
        private KeepOption(final String[] a) {
            this.lore = Arrays.asList(a);
        }
        
        public List<String> getLore() {
            return this.lore;
        }
        
        public String getPath() {
            return this.name().toLowerCase().replace("_", "-").substring(5);
        }
        
        private static /* synthetic */ KeepOption[] $values() {
            return new KeepOption[] { KeepOption.KEEP_LORE, KeepOption.KEEP_ENCHANTS, KeepOption.KEEP_DURABILITY, KeepOption.KEEP_NAME, KeepOption.KEEP_GEMS, KeepOption.KEEP_SOULBOUND };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
