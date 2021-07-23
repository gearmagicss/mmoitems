// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.recipe;

import org.bukkit.enchantments.Enchantment;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import net.Indyuce.mmoitems.api.interaction.GemStone;
import net.Indyuce.mmoitems.ItemStats;
import net.Indyuce.mmoitems.stat.data.GemSocketsData;
import java.util.Collection;
import net.Indyuce.mmoitems.api.ItemTier;
import java.util.Iterator;
import org.bukkit.inventory.PlayerInventory;
import java.util.HashMap;
import org.bukkit.inventory.Inventory;
import io.lumine.mythic.lib.api.crafting.outputs.MRORecipe;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import io.lumine.mythic.lib.api.crafting.recipes.MythicCachedResult;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.util.Ref;
import org.bukkit.entity.Player;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicRecipeInventory;
import io.lumine.mythic.lib.api.crafting.recipes.vmp.VanillaInventoryMapping;
import org.bukkit.event.inventory.InventoryClickEvent;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicBlueprintInventory;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.jetbrains.annotations.Nullable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;

public class CustomSmithingRecipe extends MythicRecipeOutput
{
    @NotNull
    final MMOItemTemplate outputItem;
    final boolean dropGemstones;
    @NotNull
    final SmithingCombinationType enchantmentTreatment;
    @NotNull
    final SmithingCombinationType upgradeTreatment;
    
    public CustomSmithingRecipe(@NotNull final MMOItemTemplate outputItem, final boolean dropGemstones, @NotNull final SmithingCombinationType enchantmentTreatment, @NotNull final SmithingCombinationType upgradeTreatment) {
        this.outputItem = outputItem;
        this.dropGemstones = dropGemstones;
        this.enchantmentTreatment = enchantmentTreatment;
        this.upgradeTreatment = upgradeTreatment;
    }
    
    @NotNull
    public MMOItemTemplate getOutputItem() {
        return this.outputItem;
    }
    
    @NotNull
    public SmithingCombinationType getEnchantmentTreatment() {
        return this.enchantmentTreatment;
    }
    
    public boolean isDropGemstones() {
        return this.dropGemstones;
    }
    
    @NotNull
    public SmithingCombinationType getUpgradeTreatment() {
        return this.upgradeTreatment;
    }
    
    @Nullable
    MMOItem fromStack(@Nullable final ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        if (NBTItem.get(itemStack).hasType()) {
            return new LiveMMOItem(itemStack);
        }
        return null;
    }
    
    @Nullable
    ItemStack firstFromFirstSide(@NotNull final MythicBlueprintInventory mythicBlueprintInventory) {
        if (mythicBlueprintInventory.getSideInventoryNames().size() == 0) {
            return null;
        }
        return mythicBlueprintInventory.getSideInventory((String)mythicBlueprintInventory.getSideInventoryNames().get(0)).getFirst();
    }
    
    @NotNull
    public MythicRecipeInventory applyDisplay(@NotNull final MythicBlueprintInventory mythicBlueprintInventory, @NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final VanillaInventoryMapping vanillaInventoryMapping) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return mythicBlueprintInventory.getResultInventory();
        }
        final MythicBlueprintInventory from = vanillaInventoryMapping.extractFrom(inventoryClickEvent.getView().getTopInventory());
        final MMOItem fromCombinationWith = this.fromCombinationWith(this.fromStack(from.getMainInventory().getFirst()), this.fromStack(this.firstFromFirstSide(from)), (Player)inventoryClickEvent.getWhoClicked(), null);
        final MythicRecipeInventory clone = mythicBlueprintInventory.getResultInventory().clone();
        clone.setItemAt(vanillaInventoryMapping.getResultWidth(vanillaInventoryMapping.getResultInventoryStart()), vanillaInventoryMapping.getResultHeight(vanillaInventoryMapping.getResultInventoryStart()), fromCombinationWith.newBuilder().build());
        return clone;
    }
    
    public void applyResult(@NotNull final MythicRecipeInventory mythicRecipeInventory, @NotNull final MythicBlueprintInventory mythicBlueprintInventory, @NotNull final MythicCachedResult mythicCachedResult, @NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final VanillaInventoryMapping vanillaInventoryMapping, int n) {
        inventoryClickEvent.setCancelled(true);
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)inventoryClickEvent.getWhoClicked();
        final ItemStack first = mythicBlueprintInventory.getMainInventory().getFirst();
        final ItemStack firstFromFirstSide = this.firstFromFirstSide(mythicBlueprintInventory);
        final MMOItem fromStack = this.fromStack(first);
        final MMOItem fromStack2 = this.fromStack(firstFromFirstSide);
        final Ref ref = new Ref();
        final MMOItem fromCombinationWith = this.fromCombinationWith(fromStack, fromStack2, player, (Ref<ArrayList<ItemStack>>)ref);
        final MythicRecipeInventory clone = mythicBlueprintInventory.getResultInventory().clone();
        clone.setItemAt(vanillaInventoryMapping.getResultWidth(vanillaInventoryMapping.getResultInventoryStart()), vanillaInventoryMapping.getResultHeight(vanillaInventoryMapping.getResultInventoryStart()), fromCombinationWith.newBuilder().build());
        if (n == 1 && inventoryClickEvent.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            final ItemStack cursor = inventoryClickEvent.getCursor();
            this.processInventory(mythicRecipeInventory, clone, n);
            ItemStack item = mythicRecipeInventory.getItemAt(vanillaInventoryMapping.getResultWidth(inventoryClickEvent.getSlot()), vanillaInventoryMapping.getResultHeight(inventoryClickEvent.getSlot()));
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            final ItemStack clone2 = item.clone();
            if (!SilentNumbers.isAir(cursor)) {
                if (!cursor.isSimilar(clone2)) {
                    return;
                }
                final int amount = cursor.getAmount();
                final int amount2 = clone2.getAmount();
                if (amount + amount2 > clone2.getMaxStackSize()) {
                    return;
                }
                clone2.setAmount(amount + amount2);
            }
            item.setAmount(0);
            vanillaInventoryMapping.applyToResultInventory(inventoryClickEvent.getInventory(), mythicRecipeInventory, false);
            inventoryClickEvent.getView().setCursor(clone2);
        }
        else {
            final ArrayList itemsList = MRORecipe.toItemsList(clone);
            HashMap<Integer, ItemStack> hashMap = null;
            final PlayerInventory inventory = player.getInventory();
            int n2 = 0;
            for (int i = 1; i <= n; ++i) {
                final HashMap distributeInInventory = MRORecipe.distributeInInventory((Inventory)inventory, itemsList, (HashMap)hashMap);
                if (distributeInInventory == null) {
                    break;
                }
                hashMap = (HashMap<Integer, ItemStack>)distributeInInventory;
                n2 = i;
            }
            if (n2 == 0) {
                return;
            }
            n = n2;
            for (final Integer key : hashMap.keySet()) {
                ((Inventory)inventory).setItem((int)key, (ItemStack)hashMap.get(key));
            }
        }
        if (this.isDropGemstones() && ref.getValue() != null && player.getLocation().getWorld() != null) {
            for (final ItemStack itemStack : player.getInventory().addItem((ItemStack[])((ArrayList)ref.getValue()).toArray(new ItemStack[0])).values()) {
                if (SilentNumbers.isAir(itemStack)) {
                    continue;
                }
                player.getWorld().dropItem(player.getLocation(), itemStack);
            }
        }
        this.consumeIngredients(mythicBlueprintInventory, mythicCachedResult, inventoryClickEvent.getInventory(), vanillaInventoryMapping, n);
    }
    
    @NotNull
    MMOItem fromCombinationWith(@Nullable final MMOItem mmoItem, @Nullable final MMOItem mmoItem2, @NotNull final Player player, @Nullable final Ref<ArrayList<ItemStack>> ref) {
        MMOItem mmoItem3 = this.getOutputItem().newBuilder(0, null).build();
        final ArrayList<MMOItem> list = new ArrayList<MMOItem>();
        if (mmoItem != null) {
            list.addAll(mmoItem.extractGemstones());
        }
        if (mmoItem2 != null) {
            list.addAll(mmoItem2.extractGemstones());
        }
        final ArrayList<ItemStack> list2 = new ArrayList<ItemStack>();
        for (final MMOItem mmoItem4 : list) {
            final GemSocketsData gemSocketsData = (GemSocketsData)mmoItem3.getData(ItemStats.GEM_SOCKETS);
            if (gemSocketsData == null || gemSocketsData.getEmptySlots().size() == 0) {
                list2.add(mmoItem4.newBuilder().build());
            }
            else {
                final GemStone.ApplyResult applyOntoItem = new GemStone(player, mmoItem4.newBuilder().buildNBT()).applyOntoItem(mmoItem3, mmoItem3.getType(), "", false, true);
                if (applyOntoItem.getType().equals(GemStone.ResultType.SUCCESS) && applyOntoItem.getResultAsMMOItem() != null) {
                    mmoItem3 = applyOntoItem.getResultAsMMOItem();
                }
                else {
                    list2.add(mmoItem4.newBuilder().build());
                }
            }
        }
        Ref.setValue((Ref)ref, (Object)list2);
        if (!this.getEnchantmentTreatment().equals(SmithingCombinationType.NONE)) {
            EnchantListData enchantListData = (EnchantListData)mmoItem3.getData(ItemStats.ENCHANTS);
            if (enchantListData == null) {
                enchantListData = (EnchantListData)ItemStats.ENCHANTS.getClearStatData();
            }
            final EnchantListData enchantListData2 = (EnchantListData)((mmoItem != null) ? mmoItem.getData(ItemStats.ENCHANTS) : ((EnchantListData)ItemStats.ENCHANTS.getClearStatData()));
            final EnchantListData enchantListData3 = (EnchantListData)((mmoItem2 != null) ? mmoItem2.getData(ItemStats.ENCHANTS) : ((EnchantListData)ItemStats.ENCHANTS.getClearStatData()));
            for (final Enchantment enchantment : Enchantment.values()) {
                int level = enchantListData.getLevel(enchantment);
                final int level2 = enchantListData2.getLevel(enchantment);
                final int level3 = enchantListData3.getLevel(enchantment);
                int n = 0;
                switch (this.getEnchantmentTreatment()) {
                    case ADDITIVE: {
                        n = level2 + level3 + level;
                        break;
                    }
                    case MAXIMUM: {
                        if (level == 0) {
                            level = level2;
                        }
                        n = Math.max(level, Math.max(level2, level3));
                        break;
                    }
                    case MINIMUM: {
                        if (level == 0) {
                            level = level2;
                        }
                        n = Math.max(level, Math.min(level2, level3));
                        break;
                    }
                    default: {
                        if (level == 0) {
                            n = SilentNumbers.ceil((level2 + level3) / 2.0);
                            break;
                        }
                        n = SilentNumbers.ceil((level2 + level3 + level) / 3.0);
                        break;
                    }
                }
                enchantListData.addEnchant(enchantment, n);
            }
        }
        if (mmoItem3.hasUpgradeTemplate() && !this.getUpgradeTreatment().equals(SmithingCombinationType.NONE)) {
            int upgradeLevel = 0;
            if (mmoItem != null) {
                upgradeLevel = mmoItem.getUpgradeLevel();
            }
            int upgradeLevel2 = 0;
            if (mmoItem2 != null) {
                upgradeLevel2 = mmoItem2.getUpgradeLevel();
            }
            int a = 0;
            switch (this.getUpgradeTreatment()) {
                case ADDITIVE: {
                    a = upgradeLevel + upgradeLevel2;
                    break;
                }
                case MAXIMUM: {
                    a = Math.max(upgradeLevel, upgradeLevel2);
                    break;
                }
                case MINIMUM: {
                    a = Math.min(upgradeLevel, upgradeLevel2);
                    break;
                }
                default: {
                    a = SilentNumbers.ceil((upgradeLevel + upgradeLevel2) / 2.0);
                    break;
                }
            }
            mmoItem3.getUpgradeTemplate().upgradeTo(mmoItem3, Math.min(a, mmoItem3.getMaxUpgradeLevel()));
        }
        return mmoItem3;
    }
}
