// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import net.Indyuce.mmoitems.api.item.util.LoreUpdate;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.type.PlayerConsumable;
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
    public boolean checkItemRequirements() {
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
    
    @Deprecated
    public boolean useWithoutItem() {
        return this.useOnPlayer() == ConsumableConsumeResult.CONSUME;
    }
    
    public ConsumableConsumeResult useOnPlayer() {
        final NBTItem nbtItem = this.getNBTItem();
        if (nbtItem.getBoolean(ItemStats.INEDIBLE.getNBTPath())) {
            return ConsumableConsumeResult.CANCEL;
        }
        final ConsumableConsumedEvent consumableConsumedEvent = new ConsumableConsumedEvent(this.playerData, this.mmoitem, this);
        Bukkit.getPluginManager().callEvent((Event)consumableConsumedEvent);
        if (consumableConsumedEvent.isCancelled()) {
            return ConsumableConsumeResult.CANCEL;
        }
        final Iterator<PlayerConsumable> iterator = MMOItems.plugin.getStats().getPlayerConsumables().iterator();
        while (iterator.hasNext()) {
            iterator.next().onConsume(this.mmoitem, this.player);
        }
        int integer = nbtItem.getInteger(ItemStats.MAX_CONSUME.getNBTPath());
        if (integer > 1) {
            --integer;
            nbtItem.addTag(new ItemTag[] { new ItemTag(ItemStats.MAX_CONSUME.getNBTPath(), (Object)integer) });
            final String colors = MythicLib.inst().parseColors(MMOItems.plugin.getLanguage().getStatFormat("max-consume"));
            final ItemStack updateLore = new LoreUpdate(nbtItem.toItem(), colors.replace("#", "" + (integer + 1)), colors.replace("#", "" + integer)).updateLore();
            final ItemStack item = nbtItem.getItem();
            if (item.getAmount() > 1) {
                updateLore.setAmount(1);
                if (this.player.getInventory().getItemInMainHand().equals((Object)item)) {
                    this.player.getInventory().setItemInMainHand(updateLore);
                }
                else if (this.player.getInventory().getItemInOffHand().equals((Object)item)) {
                    this.player.getInventory().setItemInOffHand(updateLore);
                }
                item.setAmount(item.getAmount() - 1);
                new SmartGive(this.player).give(new ItemStack[] { item });
            }
            else if (this.player.getInventory().getItemInMainHand().equals((Object)item)) {
                this.player.getInventory().setItemInMainHand(updateLore);
            }
            else if (this.player.getInventory().getItemInOffHand().equals((Object)item)) {
                this.player.getInventory().setItemInOffHand(updateLore);
            }
            return ConsumableConsumeResult.NOT_CONSUME;
        }
        return (!consumableConsumedEvent.isConsumed() || nbtItem.getBoolean(ItemStats.DISABLE_RIGHT_CLICK_CONSUME.getNBTPath())) ? ConsumableConsumeResult.NOT_CONSUME : ConsumableConsumeResult.CONSUME;
    }
    
    public boolean hasVanillaEating() {
        return (this.getItem().getType().isEdible() || this.getItem().getType() == Material.POTION || this.getItem().getType() == Material.MILK_BUCKET) && this.getNBTItem().hasTag("MMOITEMS_VANILLA_EATING");
    }
    
    public enum ConsumableConsumeResult
    {
        CANCEL, 
        CONSUME, 
        NOT_CONSUME;
        
        private static /* synthetic */ ConsumableConsumeResult[] $values() {
            return new ConsumableConsumeResult[] { ConsumableConsumeResult.CANCEL, ConsumableConsumeResult.CONSUME, ConsumableConsumeResult.NOT_CONSUME };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
