// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.event.inventory;

public enum InventoryType
{
    CHEST("CHEST", 0, 27, "Chest"), 
    DISPENSER("DISPENSER", 1, 9, "Dispenser"), 
    DROPPER("DROPPER", 2, 9, "Dropper"), 
    FURNACE("FURNACE", 3, 3, "Furnace"), 
    WORKBENCH("WORKBENCH", 4, 10, "Crafting"), 
    CRAFTING("CRAFTING", 5, 5, "Crafting"), 
    ENCHANTING("ENCHANTING", 6, 2, "Enchanting"), 
    BREWING("BREWING", 7, 5, "Brewing"), 
    PLAYER("PLAYER", 8, 41, "Player"), 
    CREATIVE("CREATIVE", 9, 9, "Creative"), 
    MERCHANT("MERCHANT", 10, 3, "Villager"), 
    ENDER_CHEST("ENDER_CHEST", 11, 27, "Ender Chest"), 
    ANVIL("ANVIL", 12, 3, "Repairing"), 
    BEACON("BEACON", 13, 1, "container.beacon"), 
    HOPPER("HOPPER", 14, 5, "Item Hopper");
    
    private final int size;
    private final String title;
    
    private InventoryType(final String name, final int ordinal, final int defaultSize, final String defaultTitle) {
        this.size = defaultSize;
        this.title = defaultTitle;
    }
    
    public int getDefaultSize() {
        return this.size;
    }
    
    public String getDefaultTitle() {
        return this.title;
    }
    
    public enum SlotType
    {
        RESULT("RESULT", 0), 
        CRAFTING("CRAFTING", 1), 
        ARMOR("ARMOR", 2), 
        CONTAINER("CONTAINER", 3), 
        QUICKBAR("QUICKBAR", 4), 
        OUTSIDE("OUTSIDE", 5), 
        FUEL("FUEL", 6);
        
        private SlotType(final String name, final int ordinal) {
        }
    }
}
