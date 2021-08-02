// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.entity;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.permissions.Permissible;

public interface HumanEntity extends LivingEntity, AnimalTamer, Permissible, InventoryHolder
{
    String getName();
    
    PlayerInventory getInventory();
    
    Inventory getEnderChest();
    
    MainHand getMainHand();
    
    boolean setWindowProperty(final InventoryView.Property p0, final int p1);
    
    InventoryView getOpenInventory();
    
    InventoryView openInventory(final Inventory p0);
    
    InventoryView openWorkbench(final Location p0, final boolean p1);
    
    InventoryView openEnchanting(final Location p0, final boolean p1);
    
    void openInventory(final InventoryView p0);
    
    InventoryView openMerchant(final Villager p0, final boolean p1);
    
    void closeInventory();
    
    @Deprecated
    ItemStack getItemInHand();
    
    @Deprecated
    void setItemInHand(final ItemStack p0);
    
    ItemStack getItemOnCursor();
    
    void setItemOnCursor(final ItemStack p0);
    
    boolean isSleeping();
    
    int getSleepTicks();
    
    GameMode getGameMode();
    
    void setGameMode(final GameMode p0);
    
    boolean isBlocking();
    
    int getExpToLevel();
}
