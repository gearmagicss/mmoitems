// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

public enum Statistic
{
    DAMAGE_DEALT("DAMAGE_DEALT", 0), 
    DAMAGE_TAKEN("DAMAGE_TAKEN", 1), 
    DEATHS("DEATHS", 2), 
    MOB_KILLS("MOB_KILLS", 3), 
    PLAYER_KILLS("PLAYER_KILLS", 4), 
    FISH_CAUGHT("FISH_CAUGHT", 5), 
    ANIMALS_BRED("ANIMALS_BRED", 6), 
    TREASURE_FISHED("TREASURE_FISHED", 7), 
    JUNK_FISHED("JUNK_FISHED", 8), 
    LEAVE_GAME("LEAVE_GAME", 9), 
    JUMP("JUMP", 10), 
    DROP("DROP", 11, Type.ITEM), 
    PICKUP("PICKUP", 12, Type.ITEM), 
    PLAY_ONE_TICK("PLAY_ONE_TICK", 13), 
    WALK_ONE_CM("WALK_ONE_CM", 14), 
    SWIM_ONE_CM("SWIM_ONE_CM", 15), 
    FALL_ONE_CM("FALL_ONE_CM", 16), 
    SNEAK_TIME("SNEAK_TIME", 17), 
    CLIMB_ONE_CM("CLIMB_ONE_CM", 18), 
    FLY_ONE_CM("FLY_ONE_CM", 19), 
    DIVE_ONE_CM("DIVE_ONE_CM", 20), 
    MINECART_ONE_CM("MINECART_ONE_CM", 21), 
    BOAT_ONE_CM("BOAT_ONE_CM", 22), 
    PIG_ONE_CM("PIG_ONE_CM", 23), 
    HORSE_ONE_CM("HORSE_ONE_CM", 24), 
    SPRINT_ONE_CM("SPRINT_ONE_CM", 25), 
    CROUCH_ONE_CM("CROUCH_ONE_CM", 26), 
    AVIATE_ONE_CM("AVIATE_ONE_CM", 27), 
    MINE_BLOCK("MINE_BLOCK", 28, Type.BLOCK), 
    USE_ITEM("USE_ITEM", 29, Type.ITEM), 
    BREAK_ITEM("BREAK_ITEM", 30, Type.ITEM), 
    CRAFT_ITEM("CRAFT_ITEM", 31, Type.ITEM), 
    KILL_ENTITY("KILL_ENTITY", 32, Type.ENTITY), 
    ENTITY_KILLED_BY("ENTITY_KILLED_BY", 33, Type.ENTITY), 
    TIME_SINCE_DEATH("TIME_SINCE_DEATH", 34), 
    TALKED_TO_VILLAGER("TALKED_TO_VILLAGER", 35), 
    TRADED_WITH_VILLAGER("TRADED_WITH_VILLAGER", 36), 
    CAKE_SLICES_EATEN("CAKE_SLICES_EATEN", 37), 
    CAULDRON_FILLED("CAULDRON_FILLED", 38), 
    CAULDRON_USED("CAULDRON_USED", 39), 
    ARMOR_CLEANED("ARMOR_CLEANED", 40), 
    BANNER_CLEANED("BANNER_CLEANED", 41), 
    BREWINGSTAND_INTERACTION("BREWINGSTAND_INTERACTION", 42), 
    BEACON_INTERACTION("BEACON_INTERACTION", 43), 
    DROPPER_INSPECTED("DROPPER_INSPECTED", 44), 
    HOPPER_INSPECTED("HOPPER_INSPECTED", 45), 
    DISPENSER_INSPECTED("DISPENSER_INSPECTED", 46), 
    NOTEBLOCK_PLAYED("NOTEBLOCK_PLAYED", 47), 
    NOTEBLOCK_TUNED("NOTEBLOCK_TUNED", 48), 
    FLOWER_POTTED("FLOWER_POTTED", 49), 
    TRAPPED_CHEST_TRIGGERED("TRAPPED_CHEST_TRIGGERED", 50), 
    ENDERCHEST_OPENED("ENDERCHEST_OPENED", 51), 
    ITEM_ENCHANTED("ITEM_ENCHANTED", 52), 
    RECORD_PLAYED("RECORD_PLAYED", 53), 
    FURNACE_INTERACTION("FURNACE_INTERACTION", 54), 
    CRAFTING_TABLE_INTERACTION("CRAFTING_TABLE_INTERACTION", 55), 
    CHEST_OPENED("CHEST_OPENED", 56), 
    SLEEP_IN_BED("SLEEP_IN_BED", 57);
    
    private final Type type;
    
    private Statistic(final String s, final int n) {
        this(s, n, Type.UNTYPED);
    }
    
    private Statistic(final String name, final int ordinal, final Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public boolean isSubstatistic() {
        return this.type != Type.UNTYPED;
    }
    
    public boolean isBlock() {
        return this.type == Type.BLOCK;
    }
    
    public enum Type
    {
        UNTYPED("UNTYPED", 0), 
        ITEM("ITEM", 1), 
        BLOCK("BLOCK", 2), 
        ENTITY("ENTITY", 3);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
