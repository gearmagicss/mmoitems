// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import java.util.List;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.utils.adventure.text.Component;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.stat.type.SelfConsumable;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.ConsumableConsumedEvent;
import net.Indyuce.mmoitems.ItemStats;
import java.util.Iterator;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.api.Type;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.Indyuce.mmoitems.comp.flags.FlagPlugin;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;

public class Consumable extends UseItem
{
    public Consumable(final Player player, final NBTItem nbtItem) {
        super(player, nbtItem);
    }
    
    @Override
    public boolean applyItemCosts() {
        return MMOItems.plugin.getFlags().isFlagAllowed(this.player, FlagPlugin.CustomFlag.MI_CONSUMABLES) && this.playerData.getRPG().canUse(this.getNBTItem(), true);
    }
    
    public boolean useOnItem(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final NBTItem nbtItem) {
        if (inventoryClickEvent.getClickedInventory() != inventoryClickEvent.getWhoClicked().getInventory()) {
            return false;
        }
        final Type value = Type.get(nbtItem.getType());
        final Iterator<ConsumableItemInteraction> iterator = MMOItems.plugin.getStats().getConsumableActions().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().handleConsumableEffect(inventoryClickEvent, this.playerData, this, nbtItem, value)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean useWithoutItem() {
        final NBTItem nbtItem = this.getNBTItem();
        if (nbtItem.getBoolean(ItemStats.INEDIBLE.getNBTPath())) {
            return false;
        }
        final ConsumableConsumedEvent consumableConsumedEvent = new ConsumableConsumedEvent(this.mmoitem, this.player, this);
        Bukkit.getPluginManager().callEvent((Event)consumableConsumedEvent);
        if (consumableConsumedEvent.isCancelled()) {
            return this.If(consumableConsumedEvent.isConsume());
        }
        boolean b = false;
        final Iterator<SelfConsumable> iterator = MMOItems.plugin.getStats().getSelfConsumables().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().onSelfConsume(this.mmoitem, this.player)) {
                b = true;
            }
        }
        int i = (int)nbtItem.getStat(ItemStats.MAX_CONSUME.getNBTPath());
        if (i > 1 && b) {
            final ItemStack clone = nbtItem.toItem().clone();
            final String colors = MythicLib.plugin.parseColors(MMOItems.plugin.getLanguage().getStatFormat("max-consume"));
            final String replace = colors.replace("#", Integer.toString(i));
            --i;
            nbtItem.addTag(new ItemTag[] { new ItemTag(ItemStats.MAX_CONSUME.getNBTPath(), (Object)i) });
            final List lore = nbtItem.toItem().clone().getItemMeta().getLore();
            for (int j = 0; j < lore.size(); ++j) {
                if (lore.get(j).equals(replace)) {
                    lore.set(j, colors.replace("#", Integer.toString(i)));
                    final ArrayList<Component> loreComponents = new ArrayList<Component>();
                    lore.forEach(s -> loreComponents.add(LegacyComponent.parse(s)));
                    nbtItem.setLoreComponents((List)loreComponents);
                    break;
                }
            }
            final ItemStack clone2 = nbtItem.toItem().clone();
            clone2.setAmount(1);
            if (this.player.getInventory().getItemInMainHand().equals((Object)clone)) {
                this.player.getInventory().setItemInMainHand(clone2);
            }
            else if (this.player.getInventory().getItemInOffHand().equals((Object)clone)) {
                this.player.getInventory().setItemInOffHand(clone2);
            }
            if (clone.getAmount() > 1) {
                clone.setAmount(clone.getAmount() - 1);
                new SmartGive(this.player).give(new ItemStack[] { clone });
            }
            return false;
        }
        return (!nbtItem.getBoolean(ItemStats.DISABLE_RIGHT_CLICK_CONSUME.getNBTPath()) && b) || this.If(consumableConsumedEvent.isConsume());
    }
    
    boolean If(@Nullable final Boolean b) {
        return b != null && b;
    }
    
    public boolean hasVanillaEating() {
        return (this.getItem().getType().isEdible() || this.getItem().getType() == Material.POTION || this.getItem().getType() == Material.MILK_BUCKET) && this.getNBTItem().hasTag("MMOITEMS_VANILLA_EATING");
    }
}
