// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import com.google.common.collect.Maps;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;
import org.bukkit.block.BlockFace;
import java.util.Map;

public enum Effect
{
    CLICK2("CLICK2", 0, 1000, Type.SOUND), 
    CLICK1("CLICK1", 1, 1001, Type.SOUND), 
    BOW_FIRE("BOW_FIRE", 2, 1002, Type.SOUND), 
    DOOR_TOGGLE("DOOR_TOGGLE", 3, 1006, Type.SOUND), 
    IRON_DOOR_TOGGLE("IRON_DOOR_TOGGLE", 4, 1005, Type.SOUND), 
    TRAPDOOR_TOGGLE("TRAPDOOR_TOGGLE", 5, 1007, Type.SOUND), 
    IRON_TRAPDOOR_TOGGLE("IRON_TRAPDOOR_TOGGLE", 6, 1037, Type.SOUND), 
    FENCE_GATE_TOGGLE("FENCE_GATE_TOGGLE", 7, 1008, Type.SOUND), 
    DOOR_CLOSE("DOOR_CLOSE", 8, 1012, Type.SOUND), 
    IRON_DOOR_CLOSE("IRON_DOOR_CLOSE", 9, 1011, Type.SOUND), 
    TRAPDOOR_CLOSE("TRAPDOOR_CLOSE", 10, 1013, Type.SOUND), 
    IRON_TRAPDOOR_CLOSE("IRON_TRAPDOOR_CLOSE", 11, 1036, Type.SOUND), 
    FENCE_GATE_CLOSE("FENCE_GATE_CLOSE", 12, 1014, Type.SOUND), 
    EXTINGUISH("EXTINGUISH", 13, 1009, Type.SOUND), 
    RECORD_PLAY(1010, Type.SOUND, (Class<?>)Material.class), 
    GHAST_SHRIEK("GHAST_SHRIEK", 15, 1015, Type.SOUND), 
    GHAST_SHOOT("GHAST_SHOOT", 16, 1016, Type.SOUND), 
    BLAZE_SHOOT("BLAZE_SHOOT", 17, 1018, Type.SOUND), 
    ZOMBIE_CHEW_WOODEN_DOOR("ZOMBIE_CHEW_WOODEN_DOOR", 18, 1019, Type.SOUND), 
    ZOMBIE_CHEW_IRON_DOOR("ZOMBIE_CHEW_IRON_DOOR", 19, 1020, Type.SOUND), 
    ZOMBIE_DESTROY_DOOR("ZOMBIE_DESTROY_DOOR", 20, 1021, Type.SOUND), 
    SMOKE(2000, Type.VISUAL, (Class<?>)BlockFace.class), 
    STEP_SOUND(2001, Type.SOUND, (Class<?>)Material.class), 
    POTION_BREAK(2002, Type.VISUAL, (Class<?>)Potion.class), 
    ENDER_SIGNAL("ENDER_SIGNAL", 24, 2003, Type.VISUAL), 
    MOBSPAWNER_FLAMES("MOBSPAWNER_FLAMES", 25, 2004, Type.VISUAL), 
    BREWING_STAND_BREW("BREWING_STAND_BREW", 26, 1035, Type.SOUND), 
    CHORUS_FLOWER_GROW("CHORUS_FLOWER_GROW", 27, 1033, Type.SOUND), 
    CHORUS_FLOWER_DEATH("CHORUS_FLOWER_DEATH", 28, 1034, Type.SOUND), 
    PORTAL_TRAVEL("PORTAL_TRAVEL", 29, 1032, Type.SOUND), 
    ENDEREYE_LAUNCH("ENDEREYE_LAUNCH", 30, 1003, Type.SOUND), 
    FIREWORK_SHOOT("FIREWORK_SHOOT", 31, 1004, Type.SOUND), 
    VILLAGER_PLANT_GROW(2005, Type.VISUAL, (Class<?>)Integer.class), 
    DRAGON_BREATH("DRAGON_BREATH", 33, 2006, Type.VISUAL), 
    ANVIL_BREAK("ANVIL_BREAK", 34, 1029, Type.SOUND), 
    ANVIL_USE("ANVIL_USE", 35, 1030, Type.SOUND), 
    ANVIL_LAND("ANVIL_LAND", 36, 1031, Type.SOUND), 
    ENDERDRAGON_SHOOT("ENDERDRAGON_SHOOT", 37, 1017, Type.SOUND), 
    WITHER_BREAK_BLOCK("WITHER_BREAK_BLOCK", 38, 1022, Type.SOUND), 
    WITHER_SHOOT("WITHER_SHOOT", 39, 1024, Type.SOUND), 
    ZOMBIE_INFECT("ZOMBIE_INFECT", 40, 1026, Type.SOUND), 
    ZOMBIE_CONVERTED_VILLAGER("ZOMBIE_CONVERTED_VILLAGER", 41, 1027, Type.SOUND), 
    BAT_TAKEOFF("BAT_TAKEOFF", 42, 1025, Type.SOUND), 
    END_GATEWAY_SPAWN("END_GATEWAY_SPAWN", 43, 3000, Type.VISUAL), 
    ENDERDRAGON_GROWL("ENDERDRAGON_GROWL", 44, 3001, Type.SOUND), 
    FIREWORKS_SPARK("FIREWORKS_SPARK", 45, "fireworksSpark", Type.PARTICLE), 
    CRIT("CRIT", 46, "crit", Type.PARTICLE), 
    MAGIC_CRIT("MAGIC_CRIT", 47, "magicCrit", Type.PARTICLE), 
    POTION_SWIRL("POTION_SWIRL", 48, "mobSpell", Type.PARTICLE), 
    POTION_SWIRL_TRANSPARENT("POTION_SWIRL_TRANSPARENT", 49, "mobSpellAmbient", Type.PARTICLE), 
    SPELL("SPELL", 50, "spell", Type.PARTICLE), 
    INSTANT_SPELL("INSTANT_SPELL", 51, "instantSpell", Type.PARTICLE), 
    WITCH_MAGIC("WITCH_MAGIC", 52, "witchMagic", Type.PARTICLE), 
    NOTE("NOTE", 53, "note", Type.PARTICLE), 
    PORTAL("PORTAL", 54, "portal", Type.PARTICLE), 
    FLYING_GLYPH("FLYING_GLYPH", 55, "enchantmenttable", Type.PARTICLE), 
    FLAME("FLAME", 56, "flame", Type.PARTICLE), 
    LAVA_POP("LAVA_POP", 57, "lava", Type.PARTICLE), 
    FOOTSTEP("FOOTSTEP", 58, "footstep", Type.PARTICLE), 
    SPLASH("SPLASH", 59, "splash", Type.PARTICLE), 
    PARTICLE_SMOKE("PARTICLE_SMOKE", 60, "smoke", Type.PARTICLE), 
    EXPLOSION_HUGE("EXPLOSION_HUGE", 61, "hugeexplosion", Type.PARTICLE), 
    EXPLOSION_LARGE("EXPLOSION_LARGE", 62, "largeexplode", Type.PARTICLE), 
    EXPLOSION("EXPLOSION", 63, "explode", Type.PARTICLE), 
    VOID_FOG("VOID_FOG", 64, "depthsuspend", Type.PARTICLE), 
    SMALL_SMOKE("SMALL_SMOKE", 65, "townaura", Type.PARTICLE), 
    CLOUD("CLOUD", 66, "cloud", Type.PARTICLE), 
    COLOURED_DUST("COLOURED_DUST", 67, "reddust", Type.PARTICLE), 
    SNOWBALL_BREAK("SNOWBALL_BREAK", 68, "snowballpoof", Type.PARTICLE), 
    WATERDRIP("WATERDRIP", 69, "dripWater", Type.PARTICLE), 
    LAVADRIP("LAVADRIP", 70, "dripLava", Type.PARTICLE), 
    SNOW_SHOVEL("SNOW_SHOVEL", 71, "snowshovel", Type.PARTICLE), 
    SLIME("SLIME", 72, "slime", Type.PARTICLE), 
    HEART("HEART", 73, "heart", Type.PARTICLE), 
    VILLAGER_THUNDERCLOUD("VILLAGER_THUNDERCLOUD", 74, "angryVillager", Type.PARTICLE), 
    HAPPY_VILLAGER("HAPPY_VILLAGER", 75, "happyVillager", Type.PARTICLE), 
    LARGE_SMOKE("LARGE_SMOKE", 76, "largesmoke", Type.PARTICLE), 
    ITEM_BREAK("iconcrack", Type.PARTICLE, (Class<?>)Material.class), 
    TILE_BREAK("blockcrack", Type.PARTICLE, (Class<?>)MaterialData.class), 
    TILE_DUST("blockdust", Type.PARTICLE, (Class<?>)MaterialData.class);
    
    private final int id;
    private final Type type;
    private final Class<?> data;
    private static final Map<Integer, Effect> BY_ID;
    private static final Map<String, Effect> BY_NAME;
    private final String particleName;
    
    static {
        BY_ID = Maps.newHashMap();
        BY_NAME = Maps.newHashMap();
        Effect[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Effect effect = values[i];
            if (effect.type != Type.PARTICLE) {
                Effect.BY_ID.put(effect.id, effect);
            }
        }
        Effect[] values2;
        for (int length2 = (values2 = values()).length, j = 0; j < length2; ++j) {
            final Effect effect = values2[j];
            if (effect.type == Type.PARTICLE) {
                Effect.BY_NAME.put(effect.particleName, effect);
            }
        }
    }
    
    private Effect(final String s, final int n, final int id, final Type type) {
        this(id, type, null);
    }
    
    private Effect(final int id, final Type type, final Class<?> data) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.particleName = null;
    }
    
    private Effect(final String particleName, final Type type, final Class<?> data) {
        this.particleName = particleName;
        this.type = type;
        this.id = 0;
        this.data = data;
    }
    
    private Effect(final String name, final int ordinal, final String particleName, final Type type) {
        this.particleName = particleName;
        this.type = type;
        this.id = 0;
        this.data = null;
    }
    
    @Deprecated
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.particleName;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public Class<?> getData() {
        return this.data;
    }
    
    @Deprecated
    public static Effect getById(final int id) {
        return Effect.BY_ID.get(id);
    }
    
    public static Effect getByName(final String name) {
        return Effect.BY_NAME.get(name);
    }
    
    public enum Type
    {
        SOUND("SOUND", 0), 
        VISUAL("VISUAL", 1), 
        PARTICLE("PARTICLE", 2);
        
        private Type(final String name, final int ordinal) {
        }
    }
}
