// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.interaction.util;

import net.Indyuce.mmoitems.api.item.util.DynamicLore;
import io.lumine.mythic.lib.api.item.ItemTag;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.Sound;
import net.Indyuce.mmoitems.api.event.item.CustomDurabilityDamage;
import org.apache.commons.lang.Validate;
import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import net.Indyuce.mmoitems.api.event.item.CustomDurabilityRepair;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import java.util.Random;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.item.NBTItem;

public class DurabilityItem
{
    private final NBTItem nbtItem;
    private final Player player;
    private final int maxDurability;
    private final int unbreakingLevel;
    private final boolean barHidden;
    private final boolean unbreakable;
    private int durability;
    private static final Random RANDOM;
    
    public DurabilityItem(final Player player, final ItemStack itemStack) {
        this(player, NBTItem.get(itemStack));
    }
    
    public DurabilityItem(final Player player, final NBTItem nbtItem) {
        this.player = player;
        this.nbtItem = nbtItem;
        this.unbreakable = this.nbtItem.getBoolean("Unbreakable");
        this.durability = this.nbtItem.getInteger("MMOITEMS_DURABILITY");
        this.maxDurability = this.nbtItem.getInteger("MMOITEMS_MAX_DURABILITY");
        this.barHidden = this.nbtItem.getBoolean("MMOITEMS_DURABILITY_BAR");
        this.unbreakingLevel = ((this.nbtItem.getItem().getItemMeta() != null && this.nbtItem.getItem().getItemMeta().hasEnchant(Enchantment.DURABILITY)) ? this.nbtItem.getItem().getItemMeta().getEnchantLevel(Enchantment.DURABILITY) : 0);
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public int getMaxDurability() {
        return this.maxDurability;
    }
    
    public int getDurability() {
        return this.durability;
    }
    
    public boolean isBarHidden() {
        return this.barHidden;
    }
    
    public boolean isUnbreakable() {
        return this.unbreakable;
    }
    
    public int getUnbreakingLevel() {
        return this.unbreakingLevel;
    }
    
    public NBTItem getNBTItem() {
        return this.nbtItem;
    }
    
    public boolean isBroken() {
        return this.nbtItem.hasTag("MMOITEMS_DURABILITY") && this.durability <= 0;
    }
    
    public boolean isLostWhenBroken() {
        return this.nbtItem.getBoolean("MMOITEMS_WILL_BREAK");
    }
    
    public boolean isValid() {
        return this.player.getGameMode() != GameMode.CREATIVE && this.nbtItem.hasTag("MMOITEMS_DURABILITY");
    }
    
    public DurabilityItem addDurability(final int n) {
        final CustomDurabilityRepair customDurabilityRepair = new CustomDurabilityRepair(this, n);
        Bukkit.getPluginManager().callEvent((Event)customDurabilityRepair);
        if (customDurabilityRepair.isCancelled()) {
            return this;
        }
        Validate.isTrue(n > 0, "Durability gain must be greater than 0");
        this.durability = Math.max(0, Math.min(this.durability + n, this.maxDurability));
        return this;
    }
    
    public DurabilityItem decreaseDurability(final int n) {
        final CustomDurabilityDamage customDurabilityDamage = new CustomDurabilityDamage(this, n);
        Bukkit.getPluginManager().callEvent((Event)customDurabilityDamage);
        if (customDurabilityDamage.isCancelled()) {
            return this;
        }
        if (!this.unbreakable) {
            if (this.getUnbreakingLevel() > 0 && DurabilityItem.RANDOM.nextInt(this.getUnbreakingLevel()) > 0) {
                return this;
            }
            this.durability = Math.max(0, Math.min(this.durability - n, this.maxDurability));
            if (this.durability <= 0) {
                this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                PlayerData.get((OfflinePlayer)this.player).getInventory().scheduleUpdate();
            }
        }
        return this;
    }
    
    public ItemStack toItem() {
        if (!this.unbreakable) {
            if (!this.barHidden) {
                this.nbtItem.addTag(new ItemTag[] { new ItemTag("Damage", (Object)((this.durability == this.maxDurability) ? 0 : Math.max(1, (int)((1.0 - this.durability / (double)this.maxDurability) * this.nbtItem.getItem().getType().getMaxDurability())))) });
            }
            this.nbtItem.addTag(new ItemTag[] { new ItemTag("MMOITEMS_DURABILITY", (Object)this.durability) });
        }
        return new DynamicLore(this.nbtItem).build();
    }
    
    static {
        RANDOM = new Random();
    }
}
