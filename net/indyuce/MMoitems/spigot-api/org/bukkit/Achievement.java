// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

public enum Achievement
{
    OPEN_INVENTORY("OPEN_INVENTORY", 0), 
    MINE_WOOD("MINE_WOOD", 1, Achievement.OPEN_INVENTORY), 
    BUILD_WORKBENCH("BUILD_WORKBENCH", 2, Achievement.MINE_WOOD), 
    BUILD_PICKAXE("BUILD_PICKAXE", 3, Achievement.BUILD_WORKBENCH), 
    BUILD_FURNACE("BUILD_FURNACE", 4, Achievement.BUILD_PICKAXE), 
    ACQUIRE_IRON("ACQUIRE_IRON", 5, Achievement.BUILD_FURNACE), 
    BUILD_HOE("BUILD_HOE", 6, Achievement.BUILD_WORKBENCH), 
    MAKE_BREAD("MAKE_BREAD", 7, Achievement.BUILD_HOE), 
    BAKE_CAKE("BAKE_CAKE", 8, Achievement.BUILD_HOE), 
    BUILD_BETTER_PICKAXE("BUILD_BETTER_PICKAXE", 9, Achievement.BUILD_PICKAXE), 
    COOK_FISH("COOK_FISH", 10, Achievement.BUILD_FURNACE), 
    ON_A_RAIL("ON_A_RAIL", 11, Achievement.ACQUIRE_IRON), 
    BUILD_SWORD("BUILD_SWORD", 12, Achievement.BUILD_WORKBENCH), 
    KILL_ENEMY("KILL_ENEMY", 13, Achievement.BUILD_SWORD), 
    KILL_COW("KILL_COW", 14, Achievement.BUILD_SWORD), 
    FLY_PIG("FLY_PIG", 15, Achievement.KILL_COW), 
    SNIPE_SKELETON("SNIPE_SKELETON", 16, Achievement.KILL_ENEMY), 
    GET_DIAMONDS("GET_DIAMONDS", 17, Achievement.ACQUIRE_IRON), 
    NETHER_PORTAL("NETHER_PORTAL", 18, Achievement.GET_DIAMONDS), 
    GHAST_RETURN("GHAST_RETURN", 19, Achievement.NETHER_PORTAL), 
    GET_BLAZE_ROD("GET_BLAZE_ROD", 20, Achievement.NETHER_PORTAL), 
    BREW_POTION("BREW_POTION", 21, Achievement.GET_BLAZE_ROD), 
    END_PORTAL("END_PORTAL", 22, Achievement.GET_BLAZE_ROD), 
    THE_END("THE_END", 23, Achievement.END_PORTAL), 
    ENCHANTMENTS("ENCHANTMENTS", 24, Achievement.GET_DIAMONDS), 
    OVERKILL("OVERKILL", 25, Achievement.ENCHANTMENTS), 
    BOOKCASE("BOOKCASE", 26, Achievement.ENCHANTMENTS), 
    EXPLORE_ALL_BIOMES("EXPLORE_ALL_BIOMES", 27, Achievement.END_PORTAL), 
    SPAWN_WITHER("SPAWN_WITHER", 28, Achievement.THE_END), 
    KILL_WITHER("KILL_WITHER", 29, Achievement.SPAWN_WITHER), 
    FULL_BEACON("FULL_BEACON", 30, Achievement.KILL_WITHER), 
    BREED_COW("BREED_COW", 31, Achievement.KILL_COW), 
    DIAMONDS_TO_YOU("DIAMONDS_TO_YOU", 32, Achievement.GET_DIAMONDS), 
    OVERPOWERED("OVERPOWERED", 33, Achievement.BUILD_BETTER_PICKAXE);
    
    private final Achievement parent;
    
    private Achievement(final String name, final int ordinal) {
        this.parent = null;
    }
    
    private Achievement(final String name, final int ordinal, final Achievement parent) {
        this.parent = parent;
    }
    
    public boolean hasParent() {
        return this.parent != null;
    }
    
    public Achievement getParent() {
        return this.parent;
    }
}
