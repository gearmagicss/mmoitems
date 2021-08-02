// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.util.Java15Compat;
import com.google.common.collect.Maps;
import org.bukkit.material.SpawnEgg;
import org.bukkit.material.Dye;
import org.bukkit.material.Coal;
import org.bukkit.material.Banner;
import org.bukkit.material.Hopper;
import org.bukkit.material.Comparator;
import org.bukkit.material.Skull;
import org.bukkit.material.FlowerPot;
import org.bukkit.material.Command;
import org.bukkit.material.Tripwire;
import org.bukkit.material.TripwireHook;
import org.bukkit.material.EnderChest;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.WoodenStep;
import org.bukkit.material.Cauldron;
import org.bukkit.material.NetherWarts;
import org.bukkit.material.Gate;
import org.bukkit.material.Vine;
import org.bukkit.material.Mushroom;
import org.bukkit.material.SmoothBrick;
import org.bukkit.material.MonsterEggs;
import org.bukkit.material.TrapDoor;
import org.bukkit.material.Diode;
import org.bukkit.material.Cake;
import org.bukkit.material.Pumpkin;
import org.bukkit.material.Button;
import org.bukkit.material.RedstoneTorch;
import org.bukkit.material.PressurePlate;
import org.bukkit.material.Lever;
import org.bukkit.material.Rails;
import org.bukkit.material.Ladder;
import org.bukkit.material.Door;
import org.bukkit.material.Sign;
import org.bukkit.material.Furnace;
import org.bukkit.material.Crops;
import org.bukkit.material.RedstoneWire;
import org.bukkit.material.Chest;
import org.bukkit.material.Stairs;
import org.bukkit.material.Torch;
import org.bukkit.material.Step;
import org.bukkit.material.Wool;
import org.bukkit.material.PistonExtensionMaterial;
import org.bukkit.material.LongGrass;
import org.bukkit.material.PistonBaseMaterial;
import org.bukkit.material.DetectorRail;
import org.bukkit.material.PoweredRail;
import org.bukkit.material.Bed;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Leaves;
import org.bukkit.material.Tree;
import org.bukkit.material.Sapling;
import org.bukkit.material.Wood;
import java.util.Map;
import org.bukkit.material.MaterialData;
import java.lang.reflect.Constructor;

public enum Material
{
    AIR("AIR", 0, 0, 0), 
    STONE("STONE", 1, 1), 
    GRASS("GRASS", 2, 2), 
    DIRT("DIRT", 3, 3), 
    COBBLESTONE("COBBLESTONE", 4, 4), 
    WOOD(5, (Class<? extends MaterialData>)Wood.class), 
    SAPLING(6, (Class<? extends MaterialData>)Sapling.class), 
    BEDROCK("BEDROCK", 7, 7), 
    WATER(8, (Class<? extends MaterialData>)MaterialData.class), 
    STATIONARY_WATER(9, (Class<? extends MaterialData>)MaterialData.class), 
    LAVA(10, (Class<? extends MaterialData>)MaterialData.class), 
    STATIONARY_LAVA(11, (Class<? extends MaterialData>)MaterialData.class), 
    SAND("SAND", 12, 12), 
    GRAVEL("GRAVEL", 13, 13), 
    GOLD_ORE("GOLD_ORE", 14, 14), 
    IRON_ORE("IRON_ORE", 15, 15), 
    COAL_ORE("COAL_ORE", 16, 16), 
    LOG(17, (Class<? extends MaterialData>)Tree.class), 
    LEAVES(18, (Class<? extends MaterialData>)Leaves.class), 
    SPONGE("SPONGE", 19, 19), 
    GLASS("GLASS", 20, 20), 
    LAPIS_ORE("LAPIS_ORE", 21, 21), 
    LAPIS_BLOCK("LAPIS_BLOCK", 22, 22), 
    DISPENSER(23, (Class<? extends MaterialData>)Dispenser.class), 
    SANDSTONE(24, (Class<? extends MaterialData>)Sandstone.class), 
    NOTE_BLOCK("NOTE_BLOCK", 25, 25), 
    BED_BLOCK(26, (Class<? extends MaterialData>)Bed.class), 
    POWERED_RAIL(27, (Class<? extends MaterialData>)PoweredRail.class), 
    DETECTOR_RAIL(28, (Class<? extends MaterialData>)DetectorRail.class), 
    PISTON_STICKY_BASE(29, (Class<? extends MaterialData>)PistonBaseMaterial.class), 
    WEB("WEB", 30, 30), 
    LONG_GRASS(31, (Class<? extends MaterialData>)LongGrass.class), 
    DEAD_BUSH("DEAD_BUSH", 32, 32), 
    PISTON_BASE(33, (Class<? extends MaterialData>)PistonBaseMaterial.class), 
    PISTON_EXTENSION(34, (Class<? extends MaterialData>)PistonExtensionMaterial.class), 
    WOOL(35, (Class<? extends MaterialData>)Wool.class), 
    PISTON_MOVING_PIECE("PISTON_MOVING_PIECE", 36, 36), 
    YELLOW_FLOWER("YELLOW_FLOWER", 37, 37), 
    RED_ROSE("RED_ROSE", 38, 38), 
    BROWN_MUSHROOM("BROWN_MUSHROOM", 39, 39), 
    RED_MUSHROOM("RED_MUSHROOM", 40, 40), 
    GOLD_BLOCK("GOLD_BLOCK", 41, 41), 
    IRON_BLOCK("IRON_BLOCK", 42, 42), 
    DOUBLE_STEP(43, (Class<? extends MaterialData>)Step.class), 
    STEP(44, (Class<? extends MaterialData>)Step.class), 
    BRICK("BRICK", 45, 45), 
    TNT("TNT", 46, 46), 
    BOOKSHELF("BOOKSHELF", 47, 47), 
    MOSSY_COBBLESTONE("MOSSY_COBBLESTONE", 48, 48), 
    OBSIDIAN("OBSIDIAN", 49, 49), 
    TORCH(50, (Class<? extends MaterialData>)Torch.class), 
    FIRE("FIRE", 51, 51), 
    MOB_SPAWNER("MOB_SPAWNER", 52, 52), 
    WOOD_STAIRS(53, (Class<? extends MaterialData>)Stairs.class), 
    CHEST(54, (Class<? extends MaterialData>)Chest.class), 
    REDSTONE_WIRE(55, (Class<? extends MaterialData>)RedstoneWire.class), 
    DIAMOND_ORE("DIAMOND_ORE", 56, 56), 
    DIAMOND_BLOCK("DIAMOND_BLOCK", 57, 57), 
    WORKBENCH("WORKBENCH", 58, 58), 
    CROPS(59, (Class<? extends MaterialData>)Crops.class), 
    SOIL(60, (Class<? extends MaterialData>)MaterialData.class), 
    FURNACE(61, (Class<? extends MaterialData>)Furnace.class), 
    BURNING_FURNACE(62, (Class<? extends MaterialData>)Furnace.class), 
    SIGN_POST(63, 64, (Class<? extends MaterialData>)Sign.class), 
    WOODEN_DOOR(64, (Class<? extends MaterialData>)Door.class), 
    LADDER(65, (Class<? extends MaterialData>)Ladder.class), 
    RAILS(66, (Class<? extends MaterialData>)Rails.class), 
    COBBLESTONE_STAIRS(67, (Class<? extends MaterialData>)Stairs.class), 
    WALL_SIGN(68, 64, (Class<? extends MaterialData>)Sign.class), 
    LEVER(69, (Class<? extends MaterialData>)Lever.class), 
    STONE_PLATE(70, (Class<? extends MaterialData>)PressurePlate.class), 
    IRON_DOOR_BLOCK(71, (Class<? extends MaterialData>)Door.class), 
    WOOD_PLATE(72, (Class<? extends MaterialData>)PressurePlate.class), 
    REDSTONE_ORE("REDSTONE_ORE", 73, 73), 
    GLOWING_REDSTONE_ORE("GLOWING_REDSTONE_ORE", 74, 74), 
    REDSTONE_TORCH_OFF(75, (Class<? extends MaterialData>)RedstoneTorch.class), 
    REDSTONE_TORCH_ON(76, (Class<? extends MaterialData>)RedstoneTorch.class), 
    STONE_BUTTON(77, (Class<? extends MaterialData>)Button.class), 
    SNOW("SNOW", 78, 78), 
    ICE("ICE", 79, 79), 
    SNOW_BLOCK("SNOW_BLOCK", 80, 80), 
    CACTUS(81, (Class<? extends MaterialData>)MaterialData.class), 
    CLAY("CLAY", 82, 82), 
    SUGAR_CANE_BLOCK(83, (Class<? extends MaterialData>)MaterialData.class), 
    JUKEBOX("JUKEBOX", 84, 84), 
    FENCE("FENCE", 85, 85), 
    PUMPKIN(86, (Class<? extends MaterialData>)Pumpkin.class), 
    NETHERRACK("NETHERRACK", 87, 87), 
    SOUL_SAND("SOUL_SAND", 88, 88), 
    GLOWSTONE("GLOWSTONE", 89, 89), 
    PORTAL("PORTAL", 90, 90), 
    JACK_O_LANTERN(91, (Class<? extends MaterialData>)Pumpkin.class), 
    CAKE_BLOCK(92, 64, (Class<? extends MaterialData>)Cake.class), 
    DIODE_BLOCK_OFF(93, (Class<? extends MaterialData>)Diode.class), 
    DIODE_BLOCK_ON(94, (Class<? extends MaterialData>)Diode.class), 
    STAINED_GLASS("STAINED_GLASS", 95, 95), 
    TRAP_DOOR(96, (Class<? extends MaterialData>)TrapDoor.class), 
    MONSTER_EGGS(97, (Class<? extends MaterialData>)MonsterEggs.class), 
    SMOOTH_BRICK(98, (Class<? extends MaterialData>)SmoothBrick.class), 
    HUGE_MUSHROOM_1(99, (Class<? extends MaterialData>)Mushroom.class), 
    HUGE_MUSHROOM_2(100, (Class<? extends MaterialData>)Mushroom.class), 
    IRON_FENCE("IRON_FENCE", 101, 101), 
    THIN_GLASS("THIN_GLASS", 102, 102), 
    MELON_BLOCK("MELON_BLOCK", 103, 103), 
    PUMPKIN_STEM(104, (Class<? extends MaterialData>)MaterialData.class), 
    MELON_STEM(105, (Class<? extends MaterialData>)MaterialData.class), 
    VINE(106, (Class<? extends MaterialData>)Vine.class), 
    FENCE_GATE(107, (Class<? extends MaterialData>)Gate.class), 
    BRICK_STAIRS(108, (Class<? extends MaterialData>)Stairs.class), 
    SMOOTH_STAIRS(109, (Class<? extends MaterialData>)Stairs.class), 
    MYCEL("MYCEL", 110, 110), 
    WATER_LILY("WATER_LILY", 111, 111), 
    NETHER_BRICK("NETHER_BRICK", 112, 112), 
    NETHER_FENCE("NETHER_FENCE", 113, 113), 
    NETHER_BRICK_STAIRS(114, (Class<? extends MaterialData>)Stairs.class), 
    NETHER_WARTS(115, (Class<? extends MaterialData>)NetherWarts.class), 
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE", 116, 116), 
    BREWING_STAND(117, (Class<? extends MaterialData>)MaterialData.class), 
    CAULDRON(118, (Class<? extends MaterialData>)Cauldron.class), 
    ENDER_PORTAL("ENDER_PORTAL", 119, 119), 
    ENDER_PORTAL_FRAME("ENDER_PORTAL_FRAME", 120, 120), 
    ENDER_STONE("ENDER_STONE", 121, 121), 
    DRAGON_EGG("DRAGON_EGG", 122, 122), 
    REDSTONE_LAMP_OFF("REDSTONE_LAMP_OFF", 123, 123), 
    REDSTONE_LAMP_ON("REDSTONE_LAMP_ON", 124, 124), 
    WOOD_DOUBLE_STEP(125, (Class<? extends MaterialData>)Wood.class), 
    WOOD_STEP(126, (Class<? extends MaterialData>)WoodenStep.class), 
    COCOA(127, (Class<? extends MaterialData>)CocoaPlant.class), 
    SANDSTONE_STAIRS(128, (Class<? extends MaterialData>)Stairs.class), 
    EMERALD_ORE("EMERALD_ORE", 129, 129), 
    ENDER_CHEST(130, (Class<? extends MaterialData>)EnderChest.class), 
    TRIPWIRE_HOOK(131, (Class<? extends MaterialData>)TripwireHook.class), 
    TRIPWIRE(132, (Class<? extends MaterialData>)Tripwire.class), 
    EMERALD_BLOCK("EMERALD_BLOCK", 133, 133), 
    SPRUCE_WOOD_STAIRS(134, (Class<? extends MaterialData>)Stairs.class), 
    BIRCH_WOOD_STAIRS(135, (Class<? extends MaterialData>)Stairs.class), 
    JUNGLE_WOOD_STAIRS(136, (Class<? extends MaterialData>)Stairs.class), 
    COMMAND(137, (Class<? extends MaterialData>)Command.class), 
    BEACON("BEACON", 138, 138), 
    COBBLE_WALL("COBBLE_WALL", 139, 139), 
    FLOWER_POT(140, (Class<? extends MaterialData>)FlowerPot.class), 
    CARROT(141, (Class<? extends MaterialData>)Crops.class), 
    POTATO(142, (Class<? extends MaterialData>)Crops.class), 
    WOOD_BUTTON(143, (Class<? extends MaterialData>)Button.class), 
    SKULL(144, (Class<? extends MaterialData>)Skull.class), 
    ANVIL("ANVIL", 145, 145), 
    TRAPPED_CHEST(146, (Class<? extends MaterialData>)Chest.class), 
    GOLD_PLATE("GOLD_PLATE", 147, 147), 
    IRON_PLATE("IRON_PLATE", 148, 148), 
    REDSTONE_COMPARATOR_OFF(149, (Class<? extends MaterialData>)Comparator.class), 
    REDSTONE_COMPARATOR_ON(150, (Class<? extends MaterialData>)Comparator.class), 
    DAYLIGHT_DETECTOR("DAYLIGHT_DETECTOR", 151, 151), 
    REDSTONE_BLOCK("REDSTONE_BLOCK", 152, 152), 
    QUARTZ_ORE("QUARTZ_ORE", 153, 153), 
    HOPPER(154, (Class<? extends MaterialData>)Hopper.class), 
    QUARTZ_BLOCK("QUARTZ_BLOCK", 155, 155), 
    QUARTZ_STAIRS(156, (Class<? extends MaterialData>)Stairs.class), 
    ACTIVATOR_RAIL(157, (Class<? extends MaterialData>)PoweredRail.class), 
    DROPPER(158, (Class<? extends MaterialData>)Dispenser.class), 
    STAINED_CLAY("STAINED_CLAY", 159, 159), 
    STAINED_GLASS_PANE("STAINED_GLASS_PANE", 160, 160), 
    LEAVES_2(161, (Class<? extends MaterialData>)Leaves.class), 
    LOG_2(162, (Class<? extends MaterialData>)Tree.class), 
    ACACIA_STAIRS(163, (Class<? extends MaterialData>)Stairs.class), 
    DARK_OAK_STAIRS(164, (Class<? extends MaterialData>)Stairs.class), 
    SLIME_BLOCK("SLIME_BLOCK", 165, 165), 
    BARRIER("BARRIER", 166, 166), 
    IRON_TRAPDOOR(167, (Class<? extends MaterialData>)TrapDoor.class), 
    PRISMARINE("PRISMARINE", 168, 168), 
    SEA_LANTERN("SEA_LANTERN", 169, 169), 
    HAY_BLOCK("HAY_BLOCK", 170, 170), 
    CARPET("CARPET", 171, 171), 
    HARD_CLAY("HARD_CLAY", 172, 172), 
    COAL_BLOCK("COAL_BLOCK", 173, 173), 
    PACKED_ICE("PACKED_ICE", 174, 174), 
    DOUBLE_PLANT("DOUBLE_PLANT", 175, 175), 
    STANDING_BANNER(176, (Class<? extends MaterialData>)Banner.class), 
    WALL_BANNER(177, (Class<? extends MaterialData>)Banner.class), 
    DAYLIGHT_DETECTOR_INVERTED("DAYLIGHT_DETECTOR_INVERTED", 178, 178), 
    RED_SANDSTONE("RED_SANDSTONE", 179, 179), 
    RED_SANDSTONE_STAIRS(180, (Class<? extends MaterialData>)Stairs.class), 
    DOUBLE_STONE_SLAB2("DOUBLE_STONE_SLAB2", 181, 181), 
    STONE_SLAB2("STONE_SLAB2", 182, 182), 
    SPRUCE_FENCE_GATE(183, (Class<? extends MaterialData>)Gate.class), 
    BIRCH_FENCE_GATE(184, (Class<? extends MaterialData>)Gate.class), 
    JUNGLE_FENCE_GATE(185, (Class<? extends MaterialData>)Gate.class), 
    DARK_OAK_FENCE_GATE(186, (Class<? extends MaterialData>)Gate.class), 
    ACACIA_FENCE_GATE(187, (Class<? extends MaterialData>)Gate.class), 
    SPRUCE_FENCE("SPRUCE_FENCE", 188, 188), 
    BIRCH_FENCE("BIRCH_FENCE", 189, 189), 
    JUNGLE_FENCE("JUNGLE_FENCE", 190, 190), 
    DARK_OAK_FENCE("DARK_OAK_FENCE", 191, 191), 
    ACACIA_FENCE("ACACIA_FENCE", 192, 192), 
    SPRUCE_DOOR(193, (Class<? extends MaterialData>)Door.class), 
    BIRCH_DOOR(194, (Class<? extends MaterialData>)Door.class), 
    JUNGLE_DOOR(195, (Class<? extends MaterialData>)Door.class), 
    ACACIA_DOOR(196, (Class<? extends MaterialData>)Door.class), 
    DARK_OAK_DOOR(197, (Class<? extends MaterialData>)Door.class), 
    END_ROD("END_ROD", 198, 198), 
    CHORUS_PLANT("CHORUS_PLANT", 199, 199), 
    CHORUS_FLOWER("CHORUS_FLOWER", 200, 200), 
    PURPUR_BLOCK("PURPUR_BLOCK", 201, 201), 
    PURPUR_PILLAR("PURPUR_PILLAR", 202, 202), 
    PURPUR_STAIRS(203, (Class<? extends MaterialData>)Stairs.class), 
    PURPUR_DOUBLE_SLAB("PURPUR_DOUBLE_SLAB", 204, 204), 
    PURPUR_SLAB("PURPUR_SLAB", 205, 205), 
    END_BRICKS("END_BRICKS", 206, 206), 
    BEETROOT_BLOCK(207, (Class<? extends MaterialData>)Crops.class), 
    GRASS_PATH("GRASS_PATH", 208, 208), 
    END_GATEWAY("END_GATEWAY", 209, 209), 
    COMMAND_REPEATING(210, (Class<? extends MaterialData>)Command.class), 
    COMMAND_CHAIN(211, (Class<? extends MaterialData>)Command.class), 
    FROSTED_ICE("FROSTED_ICE", 212, 212), 
    MAGMA("MAGMA", 213, 213), 
    NETHER_WART_BLOCK("NETHER_WART_BLOCK", 214, 214), 
    RED_NETHER_BRICK("RED_NETHER_BRICK", 215, 215), 
    BONE_BLOCK("BONE_BLOCK", 216, 216), 
    STRUCTURE_VOID("STRUCTURE_VOID", 217, 217), 
    STRUCTURE_BLOCK("STRUCTURE_BLOCK", 218, 255), 
    IRON_SPADE("IRON_SPADE", 219, 256, 1, 250), 
    IRON_PICKAXE("IRON_PICKAXE", 220, 257, 1, 250), 
    IRON_AXE("IRON_AXE", 221, 258, 1, 250), 
    FLINT_AND_STEEL("FLINT_AND_STEEL", 222, 259, 1, 64), 
    APPLE("APPLE", 223, 260), 
    BOW("BOW", 224, 261, 1, 384), 
    ARROW("ARROW", 225, 262), 
    COAL(263, (Class<? extends MaterialData>)Coal.class), 
    DIAMOND("DIAMOND", 227, 264), 
    IRON_INGOT("IRON_INGOT", 228, 265), 
    GOLD_INGOT("GOLD_INGOT", 229, 266), 
    IRON_SWORD("IRON_SWORD", 230, 267, 1, 250), 
    WOOD_SWORD("WOOD_SWORD", 231, 268, 1, 59), 
    WOOD_SPADE("WOOD_SPADE", 232, 269, 1, 59), 
    WOOD_PICKAXE("WOOD_PICKAXE", 233, 270, 1, 59), 
    WOOD_AXE("WOOD_AXE", 234, 271, 1, 59), 
    STONE_SWORD("STONE_SWORD", 235, 272, 1, 131), 
    STONE_SPADE("STONE_SPADE", 236, 273, 1, 131), 
    STONE_PICKAXE("STONE_PICKAXE", 237, 274, 1, 131), 
    STONE_AXE("STONE_AXE", 238, 275, 1, 131), 
    DIAMOND_SWORD("DIAMOND_SWORD", 239, 276, 1, 1561), 
    DIAMOND_SPADE("DIAMOND_SPADE", 240, 277, 1, 1561), 
    DIAMOND_PICKAXE("DIAMOND_PICKAXE", 241, 278, 1, 1561), 
    DIAMOND_AXE("DIAMOND_AXE", 242, 279, 1, 1561), 
    STICK("STICK", 243, 280), 
    BOWL("BOWL", 244, 281), 
    MUSHROOM_SOUP("MUSHROOM_SOUP", 245, 282, 1), 
    GOLD_SWORD("GOLD_SWORD", 246, 283, 1, 32), 
    GOLD_SPADE("GOLD_SPADE", 247, 284, 1, 32), 
    GOLD_PICKAXE("GOLD_PICKAXE", 248, 285, 1, 32), 
    GOLD_AXE("GOLD_AXE", 249, 286, 1, 32), 
    STRING("STRING", 250, 287), 
    FEATHER("FEATHER", 251, 288), 
    SULPHUR("SULPHUR", 252, 289), 
    WOOD_HOE("WOOD_HOE", 253, 290, 1, 59), 
    STONE_HOE("STONE_HOE", 254, 291, 1, 131), 
    IRON_HOE("IRON_HOE", 255, 292, 1, 250), 
    DIAMOND_HOE("DIAMOND_HOE", 256, 293, 1, 1561), 
    GOLD_HOE("GOLD_HOE", 257, 294, 1, 32), 
    SEEDS("SEEDS", 258, 295), 
    WHEAT("WHEAT", 259, 296), 
    BREAD("BREAD", 260, 297), 
    LEATHER_HELMET("LEATHER_HELMET", 261, 298, 1, 55), 
    LEATHER_CHESTPLATE("LEATHER_CHESTPLATE", 262, 299, 1, 80), 
    LEATHER_LEGGINGS("LEATHER_LEGGINGS", 263, 300, 1, 75), 
    LEATHER_BOOTS("LEATHER_BOOTS", 264, 301, 1, 65), 
    CHAINMAIL_HELMET("CHAINMAIL_HELMET", 265, 302, 1, 165), 
    CHAINMAIL_CHESTPLATE("CHAINMAIL_CHESTPLATE", 266, 303, 1, 240), 
    CHAINMAIL_LEGGINGS("CHAINMAIL_LEGGINGS", 267, 304, 1, 225), 
    CHAINMAIL_BOOTS("CHAINMAIL_BOOTS", 268, 305, 1, 195), 
    IRON_HELMET("IRON_HELMET", 269, 306, 1, 165), 
    IRON_CHESTPLATE("IRON_CHESTPLATE", 270, 307, 1, 240), 
    IRON_LEGGINGS("IRON_LEGGINGS", 271, 308, 1, 225), 
    IRON_BOOTS("IRON_BOOTS", 272, 309, 1, 195), 
    DIAMOND_HELMET("DIAMOND_HELMET", 273, 310, 1, 363), 
    DIAMOND_CHESTPLATE("DIAMOND_CHESTPLATE", 274, 311, 1, 528), 
    DIAMOND_LEGGINGS("DIAMOND_LEGGINGS", 275, 312, 1, 495), 
    DIAMOND_BOOTS("DIAMOND_BOOTS", 276, 313, 1, 429), 
    GOLD_HELMET("GOLD_HELMET", 277, 314, 1, 77), 
    GOLD_CHESTPLATE("GOLD_CHESTPLATE", 278, 315, 1, 112), 
    GOLD_LEGGINGS("GOLD_LEGGINGS", 279, 316, 1, 105), 
    GOLD_BOOTS("GOLD_BOOTS", 280, 317, 1, 91), 
    FLINT("FLINT", 281, 318), 
    PORK("PORK", 282, 319), 
    GRILLED_PORK("GRILLED_PORK", 283, 320), 
    PAINTING("PAINTING", 284, 321), 
    GOLDEN_APPLE("GOLDEN_APPLE", 285, 322), 
    SIGN("SIGN", 286, 323, 16), 
    WOOD_DOOR("WOOD_DOOR", 287, 324, 64), 
    BUCKET("BUCKET", 288, 325, 16), 
    WATER_BUCKET("WATER_BUCKET", 289, 326, 1), 
    LAVA_BUCKET("LAVA_BUCKET", 290, 327, 1), 
    MINECART("MINECART", 291, 328, 1), 
    SADDLE("SADDLE", 292, 329, 1), 
    IRON_DOOR("IRON_DOOR", 293, 330, 64), 
    REDSTONE("REDSTONE", 294, 331), 
    SNOW_BALL("SNOW_BALL", 295, 332, 16), 
    BOAT("BOAT", 296, 333, 1), 
    LEATHER("LEATHER", 297, 334), 
    MILK_BUCKET("MILK_BUCKET", 298, 335, 1), 
    CLAY_BRICK("CLAY_BRICK", 299, 336), 
    CLAY_BALL("CLAY_BALL", 300, 337), 
    SUGAR_CANE("SUGAR_CANE", 301, 338), 
    PAPER("PAPER", 302, 339), 
    BOOK("BOOK", 303, 340), 
    SLIME_BALL("SLIME_BALL", 304, 341), 
    STORAGE_MINECART("STORAGE_MINECART", 305, 342, 1), 
    POWERED_MINECART("POWERED_MINECART", 306, 343, 1), 
    EGG("EGG", 307, 344, 16), 
    COMPASS("COMPASS", 308, 345), 
    FISHING_ROD("FISHING_ROD", 309, 346, 1, 64), 
    WATCH("WATCH", 310, 347), 
    GLOWSTONE_DUST("GLOWSTONE_DUST", 311, 348), 
    RAW_FISH("RAW_FISH", 312, 349), 
    COOKED_FISH("COOKED_FISH", 313, 350), 
    INK_SACK(351, (Class<? extends MaterialData>)Dye.class), 
    BONE("BONE", 315, 352), 
    SUGAR("SUGAR", 316, 353), 
    CAKE("CAKE", 317, 354, 1), 
    BED("BED", 318, 355, 1), 
    DIODE("DIODE", 319, 356), 
    COOKIE("COOKIE", 320, 357), 
    MAP(358, (Class<? extends MaterialData>)MaterialData.class), 
    SHEARS("SHEARS", 322, 359, 1, 238), 
    MELON("MELON", 323, 360), 
    PUMPKIN_SEEDS("PUMPKIN_SEEDS", 324, 361), 
    MELON_SEEDS("MELON_SEEDS", 325, 362), 
    RAW_BEEF("RAW_BEEF", 326, 363), 
    COOKED_BEEF("COOKED_BEEF", 327, 364), 
    RAW_CHICKEN("RAW_CHICKEN", 328, 365), 
    COOKED_CHICKEN("COOKED_CHICKEN", 329, 366), 
    ROTTEN_FLESH("ROTTEN_FLESH", 330, 367), 
    ENDER_PEARL("ENDER_PEARL", 331, 368, 16), 
    BLAZE_ROD("BLAZE_ROD", 332, 369), 
    GHAST_TEAR("GHAST_TEAR", 333, 370), 
    GOLD_NUGGET("GOLD_NUGGET", 334, 371), 
    NETHER_STALK("NETHER_STALK", 335, 372), 
    POTION(373, 1, (Class<? extends MaterialData>)MaterialData.class), 
    GLASS_BOTTLE("GLASS_BOTTLE", 337, 374), 
    SPIDER_EYE("SPIDER_EYE", 338, 375), 
    FERMENTED_SPIDER_EYE("FERMENTED_SPIDER_EYE", 339, 376), 
    BLAZE_POWDER("BLAZE_POWDER", 340, 377), 
    MAGMA_CREAM("MAGMA_CREAM", 341, 378), 
    BREWING_STAND_ITEM("BREWING_STAND_ITEM", 342, 379), 
    CAULDRON_ITEM("CAULDRON_ITEM", 343, 380), 
    EYE_OF_ENDER("EYE_OF_ENDER", 344, 381), 
    SPECKLED_MELON("SPECKLED_MELON", 345, 382), 
    MONSTER_EGG(383, 64, (Class<? extends MaterialData>)SpawnEgg.class), 
    EXP_BOTTLE("EXP_BOTTLE", 347, 384, 64), 
    FIREBALL("FIREBALL", 348, 385, 64), 
    BOOK_AND_QUILL("BOOK_AND_QUILL", 349, 386, 1), 
    WRITTEN_BOOK("WRITTEN_BOOK", 350, 387, 16), 
    EMERALD("EMERALD", 351, 388, 64), 
    ITEM_FRAME("ITEM_FRAME", 352, 389), 
    FLOWER_POT_ITEM("FLOWER_POT_ITEM", 353, 390), 
    CARROT_ITEM("CARROT_ITEM", 354, 391), 
    POTATO_ITEM("POTATO_ITEM", 355, 392), 
    BAKED_POTATO("BAKED_POTATO", 356, 393), 
    POISONOUS_POTATO("POISONOUS_POTATO", 357, 394), 
    EMPTY_MAP("EMPTY_MAP", 358, 395), 
    GOLDEN_CARROT("GOLDEN_CARROT", 359, 396), 
    SKULL_ITEM("SKULL_ITEM", 360, 397), 
    CARROT_STICK("CARROT_STICK", 361, 398, 1, 25), 
    NETHER_STAR("NETHER_STAR", 362, 399), 
    PUMPKIN_PIE("PUMPKIN_PIE", 363, 400), 
    FIREWORK("FIREWORK", 364, 401), 
    FIREWORK_CHARGE("FIREWORK_CHARGE", 365, 402), 
    ENCHANTED_BOOK("ENCHANTED_BOOK", 366, 403, 1), 
    REDSTONE_COMPARATOR("REDSTONE_COMPARATOR", 367, 404), 
    NETHER_BRICK_ITEM("NETHER_BRICK_ITEM", 368, 405), 
    QUARTZ("QUARTZ", 369, 406), 
    EXPLOSIVE_MINECART("EXPLOSIVE_MINECART", 370, 407, 1), 
    HOPPER_MINECART("HOPPER_MINECART", 371, 408, 1), 
    PRISMARINE_SHARD("PRISMARINE_SHARD", 372, 409), 
    PRISMARINE_CRYSTALS("PRISMARINE_CRYSTALS", 373, 410), 
    RABBIT("RABBIT", 374, 411), 
    COOKED_RABBIT("COOKED_RABBIT", 375, 412), 
    RABBIT_STEW("RABBIT_STEW", 376, 413, 1), 
    RABBIT_FOOT("RABBIT_FOOT", 377, 414), 
    RABBIT_HIDE("RABBIT_HIDE", 378, 415), 
    ARMOR_STAND("ARMOR_STAND", 379, 416, 16), 
    IRON_BARDING("IRON_BARDING", 380, 417, 1), 
    GOLD_BARDING("GOLD_BARDING", 381, 418, 1), 
    DIAMOND_BARDING("DIAMOND_BARDING", 382, 419, 1), 
    LEASH("LEASH", 383, 420), 
    NAME_TAG("NAME_TAG", 384, 421), 
    COMMAND_MINECART("COMMAND_MINECART", 385, 422, 1), 
    MUTTON("MUTTON", 386, 423), 
    COOKED_MUTTON("COOKED_MUTTON", 387, 424), 
    BANNER("BANNER", 388, 425, 16), 
    END_CRYSTAL("END_CRYSTAL", 389, 426), 
    SPRUCE_DOOR_ITEM("SPRUCE_DOOR_ITEM", 390, 427), 
    BIRCH_DOOR_ITEM("BIRCH_DOOR_ITEM", 391, 428), 
    JUNGLE_DOOR_ITEM("JUNGLE_DOOR_ITEM", 392, 429), 
    ACACIA_DOOR_ITEM("ACACIA_DOOR_ITEM", 393, 430), 
    DARK_OAK_DOOR_ITEM("DARK_OAK_DOOR_ITEM", 394, 431), 
    CHORUS_FRUIT("CHORUS_FRUIT", 395, 432), 
    CHORUS_FRUIT_POPPED("CHORUS_FRUIT_POPPED", 396, 433), 
    BEETROOT("BEETROOT", 397, 434), 
    BEETROOT_SEEDS("BEETROOT_SEEDS", 398, 435), 
    BEETROOT_SOUP("BEETROOT_SOUP", 399, 436, 1), 
    DRAGONS_BREATH("DRAGONS_BREATH", 400, 437), 
    SPLASH_POTION("SPLASH_POTION", 401, 438, 1), 
    SPECTRAL_ARROW("SPECTRAL_ARROW", 402, 439), 
    TIPPED_ARROW("TIPPED_ARROW", 403, 440), 
    LINGERING_POTION("LINGERING_POTION", 404, 441, 1), 
    SHIELD("SHIELD", 405, 442, 1, 336), 
    ELYTRA("ELYTRA", 406, 443, 1, 431), 
    BOAT_SPRUCE("BOAT_SPRUCE", 407, 444, 1), 
    BOAT_BIRCH("BOAT_BIRCH", 408, 445, 1), 
    BOAT_JUNGLE("BOAT_JUNGLE", 409, 446, 1), 
    BOAT_ACACIA("BOAT_ACACIA", 410, 447, 1), 
    BOAT_DARK_OAK("BOAT_DARK_OAK", 411, 448, 1), 
    GOLD_RECORD("GOLD_RECORD", 412, 2256, 1), 
    GREEN_RECORD("GREEN_RECORD", 413, 2257, 1), 
    RECORD_3("RECORD_3", 414, 2258, 1), 
    RECORD_4("RECORD_4", 415, 2259, 1), 
    RECORD_5("RECORD_5", 416, 2260, 1), 
    RECORD_6("RECORD_6", 417, 2261, 1), 
    RECORD_7("RECORD_7", 418, 2262, 1), 
    RECORD_8("RECORD_8", 419, 2263, 1), 
    RECORD_9("RECORD_9", 420, 2264, 1), 
    RECORD_10("RECORD_10", 421, 2265, 1), 
    RECORD_11("RECORD_11", 422, 2266, 1), 
    RECORD_12("RECORD_12", 423, 2267, 1);
    
    private final int id;
    private final Constructor<? extends MaterialData> ctor;
    private static Material[] byId;
    private static final Map<String, Material> BY_NAME;
    private final int maxStack;
    private final short durability;
    
    static {
        Material.byId = new Material[383];
        BY_NAME = Maps.newHashMap();
        Material[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final Material material = values[i];
            if (Material.byId.length > material.id) {
                Material.byId[material.id] = material;
            }
            else {
                (Material.byId = Java15Compat.Arrays_copyOfRange(Material.byId, 0, material.id + 2))[material.id] = material;
            }
            Material.BY_NAME.put(material.name(), material);
        }
    }
    
    private Material(final String s, final int n, final int id) {
        this(s, n, id, 64);
    }
    
    private Material(final String s, final int n, final int id, final int stack) {
        this(id, stack, MaterialData.class);
    }
    
    private Material(final String s, final int n, final int id, final int stack, final int durability) {
        this(id, stack, durability, MaterialData.class);
    }
    
    private Material(final int id, final Class<? extends MaterialData> data) {
        this(id, 64, data);
    }
    
    private Material(final int id, final int stack, final Class<? extends MaterialData> data) {
        this(id, stack, 0, data);
    }
    
    private Material(final int id, final int stack, final int durability, final Class<? extends MaterialData> data) {
        this.id = id;
        this.durability = (short)durability;
        this.maxStack = stack;
        try {
            this.ctor = data.getConstructor(Integer.TYPE, Byte.TYPE);
        }
        catch (NoSuchMethodException ex) {
            throw new AssertionError((Object)ex);
        }
        catch (SecurityException ex2) {
            throw new AssertionError((Object)ex2);
        }
    }
    
    @Deprecated
    public int getId() {
        return this.id;
    }
    
    public int getMaxStackSize() {
        return this.maxStack;
    }
    
    public short getMaxDurability() {
        return this.durability;
    }
    
    public Class<? extends MaterialData> getData() {
        return this.ctor.getDeclaringClass();
    }
    
    @Deprecated
    public MaterialData getNewData(final byte raw) {
        try {
            return (MaterialData)this.ctor.newInstance(this.id, raw);
        }
        catch (InstantiationException ex) {
            final Throwable t = ex.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException)t;
            }
            if (t instanceof Error) {
                throw (Error)t;
            }
            throw new AssertionError((Object)t);
        }
        catch (Throwable t2) {
            throw new AssertionError((Object)t2);
        }
    }
    
    public boolean isBlock() {
        return this.id < 256;
    }
    
    public boolean isEdible() {
        switch (this) {
            case APPLE:
            case MUSHROOM_SOUP:
            case BREAD:
            case PORK:
            case GRILLED_PORK:
            case GOLDEN_APPLE:
            case RAW_FISH:
            case COOKED_FISH:
            case COOKIE:
            case MELON:
            case RAW_BEEF:
            case COOKED_BEEF:
            case RAW_CHICKEN:
            case COOKED_CHICKEN:
            case ROTTEN_FLESH:
            case SPIDER_EYE:
            case CARROT_ITEM:
            case POTATO_ITEM:
            case BAKED_POTATO:
            case POISONOUS_POTATO:
            case GOLDEN_CARROT:
            case PUMPKIN_PIE:
            case RABBIT:
            case COOKED_RABBIT:
            case RABBIT_STEW:
            case MUTTON:
            case COOKED_MUTTON:
            case CHORUS_FRUIT:
            case BEETROOT:
            case BEETROOT_SOUP: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    @Deprecated
    public static Material getMaterial(final int id) {
        if (Material.byId.length > id && id >= 0) {
            return Material.byId[id];
        }
        return null;
    }
    
    public static Material getMaterial(final String name) {
        return Material.BY_NAME.get(name);
    }
    
    public static Material matchMaterial(final String name) {
        Validate.notNull(name, "Name cannot be null");
        Material result = null;
        try {
            result = getMaterial(Integer.parseInt(name));
        }
        catch (NumberFormatException ex) {}
        if (result == null) {
            String filtered = name.toUpperCase();
            filtered = filtered.replaceAll("\\s+", "_").replaceAll("\\W", "");
            result = Material.BY_NAME.get(filtered);
        }
        return result;
    }
    
    public boolean isRecord() {
        return this.id >= Material.GOLD_RECORD.id && this.id <= Material.RECORD_12.id;
    }
    
    public boolean isSolid() {
        if (!this.isBlock() || this.id == 0) {
            return false;
        }
        switch (this) {
            case STONE:
            case GRASS:
            case DIRT:
            case COBBLESTONE:
            case WOOD:
            case BEDROCK:
            case SAND:
            case GRAVEL:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case LOG:
            case LEAVES:
            case SPONGE:
            case GLASS:
            case LAPIS_ORE:
            case LAPIS_BLOCK:
            case DISPENSER:
            case SANDSTONE:
            case NOTE_BLOCK:
            case BED_BLOCK:
            case PISTON_STICKY_BASE:
            case PISTON_BASE:
            case PISTON_EXTENSION:
            case WOOL:
            case PISTON_MOVING_PIECE:
            case GOLD_BLOCK:
            case IRON_BLOCK:
            case DOUBLE_STEP:
            case STEP:
            case BRICK:
            case TNT:
            case BOOKSHELF:
            case MOSSY_COBBLESTONE:
            case OBSIDIAN:
            case MOB_SPAWNER:
            case WOOD_STAIRS:
            case CHEST:
            case DIAMOND_ORE:
            case DIAMOND_BLOCK:
            case WORKBENCH:
            case SOIL:
            case FURNACE:
            case BURNING_FURNACE:
            case SIGN_POST:
            case WOODEN_DOOR:
            case COBBLESTONE_STAIRS:
            case WALL_SIGN:
            case STONE_PLATE:
            case IRON_DOOR_BLOCK:
            case WOOD_PLATE:
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
            case ICE:
            case SNOW_BLOCK:
            case CACTUS:
            case CLAY:
            case JUKEBOX:
            case FENCE:
            case PUMPKIN:
            case NETHERRACK:
            case SOUL_SAND:
            case GLOWSTONE:
            case JACK_O_LANTERN:
            case CAKE_BLOCK:
            case STAINED_GLASS:
            case TRAP_DOOR:
            case MONSTER_EGGS:
            case SMOOTH_BRICK:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case IRON_FENCE:
            case THIN_GLASS:
            case MELON_BLOCK:
            case FENCE_GATE:
            case BRICK_STAIRS:
            case SMOOTH_STAIRS:
            case MYCEL:
            case NETHER_BRICK:
            case NETHER_FENCE:
            case NETHER_BRICK_STAIRS:
            case ENCHANTMENT_TABLE:
            case BREWING_STAND:
            case CAULDRON:
            case ENDER_PORTAL_FRAME:
            case ENDER_STONE:
            case DRAGON_EGG:
            case REDSTONE_LAMP_OFF:
            case REDSTONE_LAMP_ON:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SANDSTONE_STAIRS:
            case EMERALD_ORE:
            case ENDER_CHEST:
            case EMERALD_BLOCK:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case COMMAND:
            case BEACON:
            case COBBLE_WALL:
            case ANVIL:
            case TRAPPED_CHEST:
            case GOLD_PLATE:
            case IRON_PLATE:
            case DAYLIGHT_DETECTOR:
            case REDSTONE_BLOCK:
            case QUARTZ_ORE:
            case HOPPER:
            case QUARTZ_BLOCK:
            case QUARTZ_STAIRS:
            case DROPPER:
            case STAINED_CLAY:
            case STAINED_GLASS_PANE:
            case LEAVES_2:
            case LOG_2:
            case ACACIA_STAIRS:
            case DARK_OAK_STAIRS:
            case SLIME_BLOCK:
            case BARRIER:
            case IRON_TRAPDOOR:
            case PRISMARINE:
            case SEA_LANTERN:
            case HAY_BLOCK:
            case HARD_CLAY:
            case COAL_BLOCK:
            case PACKED_ICE:
            case STANDING_BANNER:
            case WALL_BANNER:
            case DAYLIGHT_DETECTOR_INVERTED:
            case RED_SANDSTONE:
            case RED_SANDSTONE_STAIRS:
            case DOUBLE_STONE_SLAB2:
            case STONE_SLAB2:
            case SPRUCE_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case SPRUCE_FENCE:
            case BIRCH_FENCE:
            case JUNGLE_FENCE:
            case DARK_OAK_FENCE:
            case ACACIA_FENCE:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
            case PURPUR_BLOCK:
            case PURPUR_PILLAR:
            case PURPUR_STAIRS:
            case PURPUR_DOUBLE_SLAB:
            case PURPUR_SLAB:
            case END_BRICKS:
            case GRASS_PATH:
            case COMMAND_REPEATING:
            case COMMAND_CHAIN:
            case FROSTED_ICE:
            case MAGMA:
            case NETHER_WART_BLOCK:
            case RED_NETHER_BRICK:
            case BONE_BLOCK:
            case STRUCTURE_BLOCK: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isTransparent() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case AIR:
            case SAPLING:
            case POWERED_RAIL:
            case DETECTOR_RAIL:
            case LONG_GRASS:
            case DEAD_BUSH:
            case YELLOW_FLOWER:
            case RED_ROSE:
            case BROWN_MUSHROOM:
            case RED_MUSHROOM:
            case TORCH:
            case FIRE:
            case REDSTONE_WIRE:
            case CROPS:
            case LADDER:
            case RAILS:
            case LEVER:
            case REDSTONE_TORCH_OFF:
            case REDSTONE_TORCH_ON:
            case STONE_BUTTON:
            case SNOW:
            case SUGAR_CANE_BLOCK:
            case PORTAL:
            case DIODE_BLOCK_OFF:
            case DIODE_BLOCK_ON:
            case PUMPKIN_STEM:
            case MELON_STEM:
            case VINE:
            case WATER_LILY:
            case NETHER_WARTS:
            case ENDER_PORTAL:
            case COCOA:
            case TRIPWIRE_HOOK:
            case TRIPWIRE:
            case FLOWER_POT:
            case CARROT:
            case POTATO:
            case WOOD_BUTTON:
            case SKULL:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case ACTIVATOR_RAIL:
            case CARPET:
            case DOUBLE_PLANT:
            case END_ROD:
            case CHORUS_PLANT:
            case CHORUS_FLOWER:
            case BEETROOT_BLOCK:
            case END_GATEWAY:
            case STRUCTURE_VOID: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isFlammable() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case WOOD:
            case LOG:
            case LEAVES:
            case NOTE_BLOCK:
            case BED_BLOCK:
            case LONG_GRASS:
            case DEAD_BUSH:
            case WOOL:
            case TNT:
            case BOOKSHELF:
            case WOOD_STAIRS:
            case CHEST:
            case WORKBENCH:
            case SIGN_POST:
            case WOODEN_DOOR:
            case WALL_SIGN:
            case WOOD_PLATE:
            case JUKEBOX:
            case FENCE:
            case TRAP_DOOR:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case VINE:
            case FENCE_GATE:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case TRAPPED_CHEST:
            case DAYLIGHT_DETECTOR:
            case LEAVES_2:
            case LOG_2:
            case ACACIA_STAIRS:
            case DARK_OAK_STAIRS:
            case CARPET:
            case DOUBLE_PLANT:
            case STANDING_BANNER:
            case WALL_BANNER:
            case DAYLIGHT_DETECTOR_INVERTED:
            case SPRUCE_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case SPRUCE_FENCE:
            case BIRCH_FENCE:
            case JUNGLE_FENCE:
            case DARK_OAK_FENCE:
            case ACACIA_FENCE:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isBurnable() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case WOOD:
            case LOG:
            case LEAVES:
            case LONG_GRASS:
            case DEAD_BUSH:
            case WOOL:
            case YELLOW_FLOWER:
            case RED_ROSE:
            case TNT:
            case BOOKSHELF:
            case WOOD_STAIRS:
            case FENCE:
            case VINE:
            case FENCE_GATE:
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
            case SPRUCE_WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case LEAVES_2:
            case LOG_2:
            case ACACIA_STAIRS:
            case DARK_OAK_STAIRS:
            case HAY_BLOCK:
            case CARPET:
            case COAL_BLOCK:
            case DOUBLE_PLANT:
            case SPRUCE_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case SPRUCE_FENCE:
            case BIRCH_FENCE:
            case JUNGLE_FENCE:
            case DARK_OAK_FENCE:
            case ACACIA_FENCE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isOccluding() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case STONE:
            case GRASS:
            case DIRT:
            case COBBLESTONE:
            case WOOD:
            case BEDROCK:
            case SAND:
            case GRAVEL:
            case GOLD_ORE:
            case IRON_ORE:
            case COAL_ORE:
            case LOG:
            case SPONGE:
            case LAPIS_ORE:
            case LAPIS_BLOCK:
            case DISPENSER:
            case SANDSTONE:
            case NOTE_BLOCK:
            case WOOL:
            case GOLD_BLOCK:
            case IRON_BLOCK:
            case DOUBLE_STEP:
            case BRICK:
            case BOOKSHELF:
            case MOSSY_COBBLESTONE:
            case OBSIDIAN:
            case MOB_SPAWNER:
            case DIAMOND_ORE:
            case DIAMOND_BLOCK:
            case WORKBENCH:
            case FURNACE:
            case BURNING_FURNACE:
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
            case SNOW_BLOCK:
            case CLAY:
            case JUKEBOX:
            case PUMPKIN:
            case NETHERRACK:
            case SOUL_SAND:
            case JACK_O_LANTERN:
            case MONSTER_EGGS:
            case SMOOTH_BRICK:
            case HUGE_MUSHROOM_1:
            case HUGE_MUSHROOM_2:
            case MELON_BLOCK:
            case MYCEL:
            case NETHER_BRICK:
            case ENDER_STONE:
            case REDSTONE_LAMP_OFF:
            case REDSTONE_LAMP_ON:
            case WOOD_DOUBLE_STEP:
            case EMERALD_ORE:
            case EMERALD_BLOCK:
            case COMMAND:
            case QUARTZ_ORE:
            case QUARTZ_BLOCK:
            case DROPPER:
            case STAINED_CLAY:
            case LOG_2:
            case SLIME_BLOCK:
            case BARRIER:
            case PRISMARINE:
            case HAY_BLOCK:
            case HARD_CLAY:
            case COAL_BLOCK:
            case PACKED_ICE:
            case RED_SANDSTONE:
            case DOUBLE_STONE_SLAB2:
            case PURPUR_BLOCK:
            case PURPUR_PILLAR:
            case PURPUR_DOUBLE_SLAB:
            case END_BRICKS:
            case COMMAND_REPEATING:
            case COMMAND_CHAIN:
            case MAGMA:
            case NETHER_WART_BLOCK:
            case RED_NETHER_BRICK:
            case BONE_BLOCK:
            case STRUCTURE_BLOCK: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean hasGravity() {
        if (!this.isBlock()) {
            return false;
        }
        switch (this) {
            case SAND:
            case GRAVEL:
            case ANVIL: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
}
