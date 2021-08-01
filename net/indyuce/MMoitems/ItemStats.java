// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems;

import net.Indyuce.mmoitems.stat.InternalRevisionID;
import net.Indyuce.mmoitems.stat.ItemLevel;
import net.Indyuce.mmoitems.stat.StoredTags;
import net.Indyuce.mmoitems.stat.CustomDurability;
import net.Indyuce.mmoitems.stat.Soulbound;
import net.Indyuce.mmoitems.stat.HidePotionEffects;
import net.Indyuce.mmoitems.stat.ShieldPatternStat;
import net.Indyuce.mmoitems.stat.PotionColor;
import net.Indyuce.mmoitems.stat.PotionEffects;
import net.Indyuce.mmoitems.stat.HideDye;
import net.Indyuce.mmoitems.stat.DyeColor;
import net.Indyuce.mmoitems.stat.SkullTextureStat;
import net.Indyuce.mmoitems.stat.UpgradeStat;
import net.Indyuce.mmoitems.stat.Abilities;
import net.Indyuce.mmoitems.stat.Amphibian;
import net.Indyuce.mmoitems.stat.RepairType;
import net.Indyuce.mmoitems.stat.RepairPowerPercent;
import net.Indyuce.mmoitems.stat.RepairPower;
import net.Indyuce.mmoitems.stat.RandomUnsocket;
import net.Indyuce.mmoitems.stat.GemSockets;
import net.Indyuce.mmoitems.stat.CompatibleIds;
import net.Indyuce.mmoitems.stat.CompatibleTypes;
import net.Indyuce.mmoitems.stat.LuteAttackEffectStat;
import net.Indyuce.mmoitems.stat.LuteAttackSoundStat;
import net.Indyuce.mmoitems.stat.StaffSpiritStat;
import net.Indyuce.mmoitems.stat.Commands;
import net.Indyuce.mmoitems.stat.Elements;
import net.Indyuce.mmoitems.stat.CustomSounds;
import net.Indyuce.mmoitems.stat.PickaxePower;
import net.Indyuce.mmoitems.stat.Crafting;
import net.Indyuce.mmoitems.stat.SuccessRate;
import net.Indyuce.mmoitems.stat.MaxConsume;
import net.Indyuce.mmoitems.stat.ItemTypeRestriction;
import net.Indyuce.mmoitems.stat.GemUpgradeScaling;
import net.Indyuce.mmoitems.stat.GemColor;
import net.Indyuce.mmoitems.stat.VanillaEatingAnimation;
import net.Indyuce.mmoitems.stat.SoulboundLevel;
import net.Indyuce.mmoitems.stat.SoulbindingBreakChance;
import net.Indyuce.mmoitems.stat.SoulbindingChance;
import net.Indyuce.mmoitems.stat.Effects;
import net.Indyuce.mmoitems.stat.CanDeskin;
import net.Indyuce.mmoitems.stat.CanDeconstruct;
import net.Indyuce.mmoitems.stat.CanIdentify;
import net.Indyuce.mmoitems.stat.RestoreStamina;
import net.Indyuce.mmoitems.stat.RestoreMana;
import net.Indyuce.mmoitems.stat.RestoreSaturation;
import net.Indyuce.mmoitems.stat.RestoreFood;
import net.Indyuce.mmoitems.stat.RestoreHealth;
import net.Indyuce.mmoitems.stat.GrantedPermissions;
import net.Indyuce.mmoitems.stat.PermanentEffects;
import net.Indyuce.mmoitems.stat.DurabilityBar;
import net.Indyuce.mmoitems.stat.DisableDeathDrop;
import net.Indyuce.mmoitems.stat.RequiredBiomes;
import net.Indyuce.mmoitems.stat.type.BooleanStat;
import net.Indyuce.mmoitems.stat.MovementSpeed;
import net.Indyuce.mmoitems.stat.KnockbackResistance;
import net.Indyuce.mmoitems.stat.Unstackable;
import net.Indyuce.mmoitems.stat.MaxHealth;
import net.Indyuce.mmoitems.stat.ArmorToughness;
import net.Indyuce.mmoitems.stat.Armor;
import net.Indyuce.mmoitems.stat.ItemSetStat;
import net.Indyuce.mmoitems.stat.ItemTierStat;
import net.Indyuce.mmoitems.stat.Unbreakable;
import net.Indyuce.mmoitems.stat.ArrowPotionEffects;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.AttackSpeed;
import net.Indyuce.mmoitems.stat.AttackDamage;
import net.Indyuce.mmoitems.stat.RequiredClass;
import net.Indyuce.mmoitems.stat.RequiredLevel;
import net.Indyuce.mmoitems.stat.DisableAdvancedEnchantments;
import net.Indyuce.mmoitems.stat.type.DisableStat;
import net.Indyuce.mmoitems.stat.ProjectileParticles;
import net.Indyuce.mmoitems.stat.ArrowParticles;
import net.Indyuce.mmoitems.stat.ItemParticles;
import net.Indyuce.mmoitems.stat.Permission;
import net.Indyuce.mmoitems.stat.HideEnchants;
import net.Indyuce.mmoitems.stat.Enchants;
import net.Indyuce.mmoitems.stat.type.StringStat;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.block.GenTemplate;
import net.Indyuce.mmoitems.stat.block.MaxXP;
import net.Indyuce.mmoitems.stat.block.MinXP;
import net.Indyuce.mmoitems.stat.block.RequirePowerToBreak;
import net.Indyuce.mmoitems.stat.block.RequiredPower;
import net.Indyuce.mmoitems.stat.block.BlockID;
import net.Indyuce.mmoitems.stat.LoreFormat;
import net.Indyuce.mmoitems.stat.NBTTags;
import net.Indyuce.mmoitems.stat.Lore;
import net.Indyuce.mmoitems.stat.DisplayName;
import net.Indyuce.mmoitems.stat.LostWhenBroken;
import net.Indyuce.mmoitems.stat.MaximumDurability;
import net.Indyuce.mmoitems.stat.CustomModelData;
import net.Indyuce.mmoitems.stat.ItemDamage;
import net.Indyuce.mmoitems.stat.MaterialStat;
import net.Indyuce.mmoitems.stat.RevisionID;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class ItemStats
{
    public static final ItemStat REVISION_ID;
    public static final ItemStat MATERIAL;
    public static final ItemStat ITEM_DAMAGE;
    public static final ItemStat CUSTOM_MODEL_DATA;
    public static final ItemStat MAX_DURABILITY;
    public static final ItemStat WILL_BREAK;
    public static final ItemStat NAME;
    public static final ItemStat LORE;
    public static final ItemStat NBT_TAGS;
    public static final ItemStat LORE_FORMAT;
    public static final ItemStat BLOCK_ID;
    public static final ItemStat REQUIRED_POWER;
    public static final ItemStat REQUIRE_POWER_TO_BREAK;
    public static final ItemStat MIN_XP;
    public static final ItemStat MAX_XP;
    public static final ItemStat GEN_TEMPLATE;
    public static final ItemStat DISPLAYED_TYPE;
    public static final ItemStat ENCHANTS;
    public static final ItemStat HIDE_ENCHANTS;
    public static final ItemStat PERMISSION;
    public static final ItemStat ITEM_PARTICLES;
    public static final ItemStat ARROW_PARTICLES;
    public static final ItemStat PROJECTILE_PARTICLES;
    public static final ItemStat DISABLE_INTERACTION;
    public static final ItemStat DISABLE_CRAFTING;
    public static final ItemStat DISABLE_SMELTING;
    public static final ItemStat DISABLE_SMITHING;
    public static final ItemStat DISABLE_ENCHANTING;
    public static final ItemStat DISABLE_ADVANCED_ENCHANTS;
    public static final ItemStat DISABLE_REPAIRING;
    public static final ItemStat DISABLE_ARROW_SHOOTING;
    public static final ItemStat DISABLE_ATTACK_PASSIVE;
    public static final ItemStat REQUIRED_LEVEL;
    public static final ItemStat REQUIRED_CLASS;
    public static final ItemStat ATTACK_DAMAGE;
    public static final ItemStat ATTACK_SPEED;
    public static final ItemStat CRITICAL_STRIKE_CHANCE;
    public static final ItemStat CRITICAL_STRIKE_POWER;
    public static final ItemStat BLOCK_POWER;
    public static final ItemStat BLOCK_RATING;
    public static final ItemStat BLOCK_COOLDOWN_REDUCTION;
    public static final ItemStat DODGE_RATING;
    public static final ItemStat DODGE_COOLDOWN_REDUCTION;
    public static final ItemStat PARRY_RATING;
    public static final ItemStat PARRY_COOLDOWN_REDUCTION;
    public static final ItemStat COOLDOWN_REDUCTION;
    public static final ItemStat RANGE;
    public static final ItemStat MANA_COST;
    public static final ItemStat STAMINA_COST;
    public static final ItemStat ARROW_VELOCITY;
    public static final ItemStat ARROW_POTION_EFFECTS;
    public static final ItemStat PVE_DAMAGE;
    public static final ItemStat PVP_DAMAGE;
    public static final ItemStat BLUNT_POWER;
    public static final ItemStat BLUNT_RATING;
    public static final ItemStat WEAPON_DAMAGE;
    public static final ItemStat SKILL_DAMAGE;
    public static final ItemStat PROJECTILE_DAMAGE;
    public static final ItemStat MAGIC_DAMAGE;
    public static final ItemStat PHYSICAL_DAMAGE;
    public static final ItemStat DEFENSE;
    public static final ItemStat DAMAGE_REDUCTION;
    public static final ItemStat FALL_DAMAGE_REDUCTION;
    public static final ItemStat PROJECTILE_DAMAGE_REDUCTION;
    public static final ItemStat PHYSICAL_DAMAGE_REDUCTION;
    public static final ItemStat FIRE_DAMAGE_REDUCTION;
    public static final ItemStat MAGIC_DAMAGE_REDUCTION;
    public static final ItemStat PVE_DAMAGE_REDUCTION;
    public static final ItemStat PVP_DAMAGE_REDUCTION;
    public static final ItemStat UNDEAD_DAMAGE;
    public static final ItemStat UNBREAKABLE;
    public static final ItemStat TIER;
    public static final ItemStat SET;
    public static final ItemStat ARMOR;
    public static final ItemStat ARMOR_TOUGHNESS;
    public static final ItemStat MAX_HEALTH;
    public static final ItemStat UNSTACKABLE;
    public static final ItemStat MAX_MANA;
    public static final ItemStat KNOCKBACK_RESISTANCE;
    public static final ItemStat MOVEMENT_SPEED;
    public static final ItemStat TWO_HANDED;
    public static final ItemStat EQUIP_PRIORITY;
    public static final ItemStat REQUIRED_BIOMES;
    public static final ItemStat DROP_ON_DEATH;
    public static final ItemStat DURABILITY_BAR;
    public static final ItemStat PERM_EFFECTS;
    public static final ItemStat GRANTED_PERMISSIONS;
    public static final ItemStat RESTORE_HEALTH;
    public static final ItemStat RESTORE_FOOD;
    public static final ItemStat RESTORE_SATURATION;
    public static final ItemStat RESTORE_MANA;
    public static final ItemStat RESTORE_STAMINA;
    public static final ItemStat CAN_IDENTIFY;
    public static final ItemStat CAN_DECONSTRUCT;
    public static final ItemStat CAN_DESKIN;
    public static final ItemStat EFFECTS;
    public static final ItemStat SOULBINDING_CHANCE;
    public static final ItemStat SOULBOUND_BREAK_CHANCE;
    public static final ItemStat SOULBOUND_LEVEL;
    public static final ItemStat AUTO_SOULBIND;
    public static final ItemStat ITEM_COOLDOWN;
    public static final ItemStat VANILLA_EATING_ANIMATION;
    public static final ItemStat GEM_COLOR;
    public static final ItemStat GEM_UPGRADE_SCALING;
    public static final ItemStat ITEM_TYPE_RESTRICTION;
    public static final ItemStat MAX_CONSUME;
    public static final ItemStat SUCCESS_RATE;
    public static final ItemStat CRAFTING;
    public static final ItemStat CRAFT_PERMISSION;
    public static final ItemStat AUTOSMELT;
    public static final ItemStat BOUNCING_CRACK;
    public static final ItemStat PICKAXE_POWER;
    public static final ItemStat CUSTOM_SOUNDS;
    public static final ItemStat ELEMENTS;
    public static final ItemStat COMMANDS;
    public static final ItemStat STAFF_SPIRIT;
    public static final ItemStat LUTE_ATTACK_SOUND;
    public static final ItemStat LUTE_ATTACK_EFFECT;
    public static final ItemStat NOTE_WEIGHT;
    public static final ItemStat REMOVE_ON_CRAFT;
    public static final ItemStat COMPATIBLE_TYPES;
    public static final ItemStat COMPATIBLE_IDS;
    public static final ItemStat GEM_SOCKETS;
    public static final ItemStat RANDOM_UNSOCKET;
    public static final ItemStat REPAIR;
    public static final ItemStat REPAIR_PERCENT;
    public static final ItemStat REPAIR_TYPE;
    public static final ItemStat INEDIBLE;
    public static final ItemStat DISABLE_RIGHT_CLICK_CONSUME;
    public static final ItemStat KNOCKBACK;
    public static final ItemStat RECOIL;
    public static final ItemStat HANDWORN;
    public static final ItemStat AMPHIBIAN;
    public static final ItemStat ABILITIES;
    public static final ItemStat UPGRADE;
    public static final ItemStat SKULL_TEXTURE;
    public static final ItemStat DYE_COLOR;
    public static final ItemStat HIDE_DYE;
    public static final ItemStat POTION_EFFECTS;
    public static final ItemStat POTION_COLOR;
    public static final ItemStat SHIELD_PATTERN;
    public static final ItemStat HIDE_POTION_EFFECTS;
    public static final ItemStat SOULBOUND;
    public static final ItemStat CUSTOM_DURABILITY;
    public static final ItemStat STORED_TAGS;
    public static final ItemStat ITEM_LEVEL;
    public static final ItemStat INTERNAL_REVISION_ID;
    @Deprecated
    public static final ItemStat DURABILITY;
    
    static {
        REVISION_ID = new RevisionID();
        MATERIAL = new MaterialStat();
        ITEM_DAMAGE = new ItemDamage();
        CUSTOM_MODEL_DATA = new CustomModelData();
        MAX_DURABILITY = new MaximumDurability();
        WILL_BREAK = new LostWhenBroken();
        NAME = new DisplayName();
        LORE = new Lore();
        NBT_TAGS = new NBTTags();
        LORE_FORMAT = new LoreFormat();
        BLOCK_ID = new BlockID();
        REQUIRED_POWER = new RequiredPower();
        REQUIRE_POWER_TO_BREAK = new RequirePowerToBreak();
        MIN_XP = new MinXP();
        MAX_XP = new MaxXP();
        GEN_TEMPLATE = new GenTemplate();
        DISPLAYED_TYPE = new StringStat("DISPLAYED_TYPE", VersionMaterial.OAK_SIGN.toMaterial(), "Displayed Type", new String[] { "This option will only affect the", "type displayed on the item lore." }, new String[] { "all" }, new Material[0]);
        ENCHANTS = new Enchants();
        HIDE_ENCHANTS = new HideEnchants();
        PERMISSION = new Permission();
        ITEM_PARTICLES = new ItemParticles();
        ARROW_PARTICLES = new ArrowParticles();
        PROJECTILE_PARTICLES = new ProjectileParticles();
        DISABLE_INTERACTION = new DisableStat("INTERACTION", VersionMaterial.GRASS_BLOCK.toMaterial(), "Disable Interaction", new String[] { "!block", "all" }, new String[] { "Disable any unwanted interaction:", "block placement, item use..." });
        DISABLE_CRAFTING = new DisableStat("CRAFTING", VersionMaterial.CRAFTING_TABLE.toMaterial(), "Disable Crafting", new String[] { "Players can't use this item while crafting." });
        DISABLE_SMELTING = new DisableStat("SMELTING", Material.FURNACE, "Disable Smelting", new String[] { "Players can't use this item in furnaces." });
        DISABLE_SMITHING = new DisableStat("SMITHING", Material.DAMAGED_ANVIL, "Disable Smithing", new String[] { "Players can't smith this item in smithing tables." });
        DISABLE_ENCHANTING = new DisableStat("ENCHANTING", VersionMaterial.ENCHANTING_TABLE.toMaterial(), "Disable Enchanting", new String[] { "!block", "all" }, new String[] { "Players can't enchant this item." });
        DISABLE_ADVANCED_ENCHANTS = new DisableAdvancedEnchantments();
        DISABLE_REPAIRING = new DisableStat("REPAIRING", Material.ANVIL, "Disable Repairing", new String[] { "!block", "all" }, new String[] { "Players can't use this item in anvils." });
        DISABLE_ARROW_SHOOTING = new DisableStat("ARROW_SHOOTING", Material.ARROW, "Disable Arrow Shooting", new Material[] { Material.ARROW }, new String[] { "Players can't shoot this", "item using a bow." });
        DISABLE_ATTACK_PASSIVE = new DisableStat("ATTACK_PASSIVE", Material.BARRIER, "Disable Attack Passive", new String[] { "piercing", "slashing", "blunt" }, new String[] { "Disables the blunt/slashing/piercing", "passive effects on attacks." });
        REQUIRED_LEVEL = new RequiredLevel();
        REQUIRED_CLASS = new RequiredClass();
        ATTACK_DAMAGE = new AttackDamage();
        ATTACK_SPEED = new AttackSpeed();
        CRITICAL_STRIKE_CHANCE = new DoubleStat("CRITICAL_STRIKE_CHANCE", Material.NETHER_STAR, "Critical Strike Chance", new String[] { "Critical Strikes deal more damage.", "In % chance." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        CRITICAL_STRIKE_POWER = new DoubleStat("CRITICAL_STRIKE_POWER", Material.NETHER_STAR, "Critical Strike Power", new String[] { "The extra damage weapon crits deals.", "(Stacks with default value)", "In %." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        BLOCK_POWER = new DoubleStat("BLOCK_POWER", Material.IRON_HELMET, "Block Power", new String[] { "The % of the damage your", "armor/shield can block.", "Default: 25%" }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        BLOCK_RATING = new DoubleStat("BLOCK_RATING", Material.IRON_HELMET, "Block Rating", new String[] { "The chance your piece of armor", "has to block any entity attack." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        BLOCK_COOLDOWN_REDUCTION = new DoubleStat("BLOCK_COOLDOWN_REDUCTION", Material.IRON_HELMET, "Block Cooldown Reduction", new String[] { "Reduces the blocking cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        DODGE_RATING = new DoubleStat("DODGE_RATING", Material.FEATHER, "Dodge Rating", new String[] { "The chance to dodge an attack.", "Dodging completely negates", "the attack damage." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        DODGE_COOLDOWN_REDUCTION = new DoubleStat("DODGE_COOLDOWN_REDUCTION", Material.FEATHER, "Dodge Cooldown Reduction", new String[] { "Reduces the dodging cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        PARRY_RATING = new DoubleStat("PARRY_RATING", Material.BUCKET, "Parry Rating", new String[] { "The chance to parry an attack.", "Parrying negates the damage", "and knocks the attacker back." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        PARRY_COOLDOWN_REDUCTION = new DoubleStat("PARRY_COOLDOWN_REDUCTION", Material.BUCKET, "Parry Cooldown Reduction", new String[] { "Reduces the parrying cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
        COOLDOWN_REDUCTION = new DoubleStat("COOLDOWN_REDUCTION", Material.BOOK, "Cooldown Reduction", new String[] { "Reduces cooldowns of item and player skills (%)." });
        RANGE = new DoubleStat("RANGE", Material.STICK, "Range", new String[] { "The range of your item attacks." }, new String[] { "staff", "whip", "wand", "musket" }, new Material[0]);
        MANA_COST = new DoubleStat("MANA_COST", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Mana Cost", new String[] { "Mana spent by your weapon to be used." }, new String[] { "piercing", "slashing", "blunt", "range" }, new Material[0]);
        STAMINA_COST = new DoubleStat("STAMINA_COST", VersionMaterial.LIGHT_GRAY_DYE.toMaterial(), "Stamina Cost", new String[] { "Stamina spent by your weapon to be used." }, new String[] { "piercing", "slashing", "blunt", "range" }, new Material[0]);
        ARROW_VELOCITY = new DoubleStat("ARROW_VELOCITY", Material.ARROW, "Arrow Velocity", new String[] { "Determines how far your", "weapon can shoot.", "Default: 1.0" }, new String[] { "bow", "crossbow" }, new Material[0]);
        ARROW_POTION_EFFECTS = new ArrowPotionEffects();
        PVE_DAMAGE = new DoubleStat("PVE_DAMAGE", VersionMaterial.PORKCHOP.toMaterial(), "PvE Damage", new String[] { "Additional damage against", "non human entities in %." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "gem_stone", "accessory" }, new Material[0]);
        PVP_DAMAGE = new DoubleStat("PVP_DAMAGE", VersionMaterial.SKELETON_SKULL.toMaterial(), "PvP Damage", new String[] { "Additional damage", "against players in %." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "gem_stone", "accessory" }, new Material[0]);
        BLUNT_POWER = new DoubleStat("BLUNT_POWER", Material.IRON_AXE, "Blunt Power", new String[] { "The radius of the AoE attack.", "If set to 2.0, enemies within 2 blocks", "around your target will take damage." }, new String[] { "blunt", "gem_stone" }, new Material[0]);
        BLUNT_RATING = new DoubleStat("BLUNT_RATING", Material.BRICK, "Blunt Rating", new String[] { "The force of the blunt attack.", "If set to 50%, enemies hit by the attack", "will take 50% of the initial damage." }, new String[] { "blunt", "gem_stone" }, new Material[0]);
        WEAPON_DAMAGE = new DoubleStat("WEAPON_DAMAGE", Material.IRON_SWORD, "Weapon Damage", new String[] { "Additional on-hit weapon damage in %." });
        SKILL_DAMAGE = new DoubleStat("SKILL_DAMAGE", Material.BOOK, "Skill Damage", new String[] { "Additional ability damage in %." });
        PROJECTILE_DAMAGE = new DoubleStat("PROJECTILE_DAMAGE", Material.ARROW, "Projectile Damage", new String[] { "Additional skill/weapon projectile damage." });
        MAGIC_DAMAGE = new DoubleStat("MAGIC_DAMAGE", Material.MAGMA_CREAM, "Magic Damage", new String[] { "Additional magic skill damage in %." });
        PHYSICAL_DAMAGE = new DoubleStat("PHYSICAL_DAMAGE", Material.IRON_AXE, "Physical Damage", new String[] { "Additional skill/weapon physical damage." });
        DEFENSE = new DoubleStat("DEFENSE", Material.SHIELD, "Defense", new String[] { "Reduces damage from any source.", "Formula can be set in MMOLib Config." });
        DAMAGE_REDUCTION = new DoubleStat("DAMAGE_REDUCTION", Material.IRON_CHESTPLATE, "Damage Reduction", new String[] { "Reduces damage from any source.", "In %." });
        FALL_DAMAGE_REDUCTION = new DoubleStat("FALL_DAMAGE_REDUCTION", Material.FEATHER, "Fall Damage Reduction", new String[] { "Reduces fall damage.", "In %." });
        PROJECTILE_DAMAGE_REDUCTION = new DoubleStat("PROJECTILE_DAMAGE_REDUCTION", VersionMaterial.SNOWBALL.toMaterial(), "Projectile Damage Reduction", new String[] { "Reduces projectile damage.", "In %." });
        PHYSICAL_DAMAGE_REDUCTION = new DoubleStat("PHYSICAL_DAMAGE_REDUCTION", Material.LEATHER_CHESTPLATE, "Physical Damage Reduction", new String[] { "Reduces physical damage.", "In %." });
        FIRE_DAMAGE_REDUCTION = new DoubleStat("FIRE_DAMAGE_REDUCTION", Material.BLAZE_POWDER, "Fire Damage Reduction", new String[] { "Reduces fire damage.", "In %." });
        MAGIC_DAMAGE_REDUCTION = new DoubleStat("MAGIC_DAMAGE_REDUCTION", Material.POTION, "Magic Damage Reduction", new String[] { "Reduce magic damage dealt by potions.", "In %." });
        PVE_DAMAGE_REDUCTION = new DoubleStat("PVE_DAMAGE_REDUCTION", VersionMaterial.PORKCHOP.toMaterial(), "PvE Damage Reduction", new String[] { "Reduces damage dealt by mobs.", "In %." });
        PVP_DAMAGE_REDUCTION = new DoubleStat("PVP_DAMAGE_REDUCTION", VersionMaterial.SKELETON_SKULL.toMaterial(), "PvP Damage Reduction", new String[] { "Reduces damage dealt by players", "In %." });
        UNDEAD_DAMAGE = new DoubleStat("UNDEAD_DAMAGE", VersionMaterial.SKELETON_SKULL.toMaterial(), "Undead Damage", new String[] { "Deals additional damage to undead.", "In %." });
        UNBREAKABLE = new Unbreakable();
        TIER = new ItemTierStat();
        SET = new ItemSetStat();
        ARMOR = new Armor();
        ARMOR_TOUGHNESS = new ArmorToughness();
        MAX_HEALTH = new MaxHealth();
        UNSTACKABLE = new Unstackable();
        MAX_MANA = new DoubleStat("MAX_MANA", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Max Mana", new String[] { "Adds mana to your max mana bar." });
        KNOCKBACK_RESISTANCE = new KnockbackResistance();
        MOVEMENT_SPEED = new MovementSpeed();
        TWO_HANDED = new BooleanStat("TWO_HANDED", Material.IRON_INGOT, "Two Handed", new String[] { "If set to true, a player will be", "significantly slower if holding two", "items, one being Two Handed." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool" }, new Material[0]);
        EQUIP_PRIORITY = new DoubleStat("EQUIP_PRIORITY", VersionMaterial.DIAMOND_HORSE_ARMOR.toMaterial(), "Equip Priority", new String[] { "Sets the level of priority this item has for the", "right click to swap equipped armor feature." });
        REQUIRED_BIOMES = new RequiredBiomes();
        DROP_ON_DEATH = new DisableDeathDrop();
        DURABILITY_BAR = new DurabilityBar();
        PERM_EFFECTS = new PermanentEffects();
        GRANTED_PERMISSIONS = new GrantedPermissions();
        RESTORE_HEALTH = new RestoreHealth();
        RESTORE_FOOD = new RestoreFood();
        RESTORE_SATURATION = new RestoreSaturation();
        RESTORE_MANA = new RestoreMana();
        RESTORE_STAMINA = new RestoreStamina();
        CAN_IDENTIFY = new CanIdentify();
        CAN_DECONSTRUCT = new CanDeconstruct();
        CAN_DESKIN = new CanDeskin();
        EFFECTS = new Effects();
        SOULBINDING_CHANCE = new SoulbindingChance();
        SOULBOUND_BREAK_CHANCE = new SoulbindingBreakChance();
        SOULBOUND_LEVEL = new SoulboundLevel();
        AUTO_SOULBIND = new BooleanStat("AUTO_SOULBIND", VersionMaterial.ENDER_EYE.toMaterial(), "Auto-Soulbind", new String[] { "Automatically soulbinds this item to", "a player when he acquires it." }, new String[] { "!consumable", "all" }, new Material[0]);
        ITEM_COOLDOWN = new DoubleStat("ITEM_COOLDOWN", Material.COOKED_CHICKEN, "Item Cooldown", new String[] { "This cooldown applies for consumables", "as well as for item commands." }, new String[] { "!armor", "!gem_stone", "!block", "all" }, new Material[0]);
        VANILLA_EATING_ANIMATION = new VanillaEatingAnimation();
        GEM_COLOR = new GemColor();
        GEM_UPGRADE_SCALING = new GemUpgradeScaling();
        ITEM_TYPE_RESTRICTION = new ItemTypeRestriction();
        MAX_CONSUME = new MaxConsume();
        SUCCESS_RATE = new SuccessRate();
        CRAFTING = new Crafting();
        CRAFT_PERMISSION = new StringStat("CRAFT_PERMISSION", VersionMaterial.OAK_SIGN.toMaterial(), "Crafting Recipe Permission", new String[] { "The permission needed to craft this item.", "Changing this value requires &o/mi reload recipes&7." }, new String[] { "all" }, new Material[0]);
        AUTOSMELT = new BooleanStat("AUTOSMELT", Material.COAL, "Autosmelt", new String[] { "If set to true, your tool will", "automaticaly smelt mined ores." }, new String[] { "tool" }, new Material[0]);
        BOUNCING_CRACK = new BooleanStat("BOUNCING_CRACK", VersionMaterial.COBBLESTONE_WALL.toMaterial(), "Bouncing Crack", new String[] { "If set to true, your tool will", "also break nearby blocks." }, new String[] { "tool" }, new Material[0]);
        PICKAXE_POWER = new PickaxePower();
        CUSTOM_SOUNDS = new CustomSounds();
        ELEMENTS = new Elements();
        COMMANDS = new Commands();
        STAFF_SPIRIT = new StaffSpiritStat();
        LUTE_ATTACK_SOUND = new LuteAttackSoundStat();
        LUTE_ATTACK_EFFECT = new LuteAttackEffectStat();
        NOTE_WEIGHT = new DoubleStat("NOTE_WEIGHT", VersionMaterial.MUSIC_DISC_MALL.toMaterial(), "Note Weight", new String[] { "Defines how the projectile cast", "by your lute tilts downwards." }, new String[] { "lute" }, new Material[0]);
        REMOVE_ON_CRAFT = new BooleanStat("REMOVE_ON_CRAFT", Material.GLASS_BOTTLE, "Remove on Craft", new String[] { "If the item should be completely", "removed when used in a recipe,", "or if it should become an", "empty bottle or bucket." }, new String[] { "all" }, new Material[] { Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.MILK_BUCKET, Material.LAVA_BUCKET, Material.WATER_BUCKET });
        COMPATIBLE_TYPES = new CompatibleTypes();
        COMPATIBLE_IDS = new CompatibleIds();
        GEM_SOCKETS = new GemSockets();
        RANDOM_UNSOCKET = new RandomUnsocket();
        REPAIR = new RepairPower();
        REPAIR_PERCENT = new RepairPowerPercent();
        REPAIR_TYPE = new RepairType();
        INEDIBLE = new BooleanStat("INEDIBLE", Material.POISONOUS_POTATO, "Inedible", new String[] { "Players won't be able to right-click this consumable.", "", "No effects of it will take place." }, new String[] { "consumable" }, new Material[0]);
        DISABLE_RIGHT_CLICK_CONSUME = new DisableStat("RIGHT_CLICK_CONSUME", Material.BAKED_POTATO, "Infinite Consume", new String[] { "consumable" }, new String[] { "Players will be able to right-click this consumable", "and benefit from its effects, but it won't be consumed." });
        KNOCKBACK = new DoubleStat("KNOCKBACK", VersionMaterial.IRON_HORSE_ARMOR.toMaterial(), "Knockback", new String[] { "Using this musket will knock", "the user back if positive." }, new String[] { "musket" }, new Material[0]);
        RECOIL = new DoubleStat("RECOIL", VersionMaterial.IRON_HORSE_ARMOR.toMaterial(), "Recoil", new String[] { "Corresponds to the shooting innacuracy." }, new String[] { "musket" }, new Material[0]);
        HANDWORN = new BooleanStat("HANDWORN", Material.STRING, "Handworn", new String[] { "This item ignores two-handedness.", "", "Basically for a ring or a glove that you", " can wear and still have your hand free", " to carry a two-handed weapon." }, new String[] { "offhand" }, new Material[0]);
        AMPHIBIAN = new Amphibian();
        ABILITIES = new Abilities();
        UPGRADE = new UpgradeStat();
        SKULL_TEXTURE = new SkullTextureStat();
        DYE_COLOR = new DyeColor();
        HIDE_DYE = new HideDye();
        POTION_EFFECTS = new PotionEffects();
        POTION_COLOR = new PotionColor();
        SHIELD_PATTERN = new ShieldPatternStat();
        HIDE_POTION_EFFECTS = new HidePotionEffects();
        SOULBOUND = new Soulbound();
        CUSTOM_DURABILITY = new CustomDurability();
        STORED_TAGS = new StoredTags();
        ITEM_LEVEL = new ItemLevel();
        INTERNAL_REVISION_ID = new InternalRevisionID();
        DURABILITY = ItemStats.ITEM_DAMAGE;
    }
}
