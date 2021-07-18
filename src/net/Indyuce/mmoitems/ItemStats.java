package net.Indyuce.mmoitems;

import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.Abilities;
import net.Indyuce.mmoitems.stat.Amphibian;
import net.Indyuce.mmoitems.stat.Armor;
import net.Indyuce.mmoitems.stat.ArmorToughness;
import net.Indyuce.mmoitems.stat.ArrowParticles;
import net.Indyuce.mmoitems.stat.ArrowPotionEffects;
import net.Indyuce.mmoitems.stat.AttackDamage;
import net.Indyuce.mmoitems.stat.AttackSpeed;
import net.Indyuce.mmoitems.stat.CanDeconstruct;
import net.Indyuce.mmoitems.stat.CanDeskin;
import net.Indyuce.mmoitems.stat.CanIdentify;
import net.Indyuce.mmoitems.stat.Commands;
import net.Indyuce.mmoitems.stat.CompatibleIds;
import net.Indyuce.mmoitems.stat.CompatibleTypes;
import net.Indyuce.mmoitems.stat.Crafting;
import net.Indyuce.mmoitems.stat.CustomModelData;
import net.Indyuce.mmoitems.stat.CustomSounds;
import net.Indyuce.mmoitems.stat.DisableAdvancedEnchantments;
import net.Indyuce.mmoitems.stat.DisableDeathDrop;
import net.Indyuce.mmoitems.stat.DisplayName;
import net.Indyuce.mmoitems.stat.DurabilityBar;
import net.Indyuce.mmoitems.stat.DyeColor;
import net.Indyuce.mmoitems.stat.Effects;
import net.Indyuce.mmoitems.stat.Elements;
import net.Indyuce.mmoitems.stat.Enchants;
import net.Indyuce.mmoitems.stat.GemColor;
import net.Indyuce.mmoitems.stat.GemSockets;
import net.Indyuce.mmoitems.stat.GemUpgradeScaling;
import net.Indyuce.mmoitems.stat.GrantedPermissions;
import net.Indyuce.mmoitems.stat.HideDye;
import net.Indyuce.mmoitems.stat.HideEnchants;
import net.Indyuce.mmoitems.stat.HidePotionEffects;
import net.Indyuce.mmoitems.stat.InternalRevisionID;
import net.Indyuce.mmoitems.stat.ItemDamage;
import net.Indyuce.mmoitems.stat.ItemLevel;
import net.Indyuce.mmoitems.stat.ItemParticles;
import net.Indyuce.mmoitems.stat.ItemSetStat;
import net.Indyuce.mmoitems.stat.ItemTierStat;
import net.Indyuce.mmoitems.stat.ItemTypeRestriction;
import net.Indyuce.mmoitems.stat.KnockbackResistance;
import net.Indyuce.mmoitems.stat.Lore;
import net.Indyuce.mmoitems.stat.LoreFormat;
import net.Indyuce.mmoitems.stat.LostWhenBroken;
import net.Indyuce.mmoitems.stat.LuteAttackEffectStat;
import net.Indyuce.mmoitems.stat.LuteAttackSoundStat;
import net.Indyuce.mmoitems.stat.MaterialStat;
import net.Indyuce.mmoitems.stat.MaxHealth;
import net.Indyuce.mmoitems.stat.MaximumDurability;
import net.Indyuce.mmoitems.stat.MovementSpeed;
import net.Indyuce.mmoitems.stat.NBTTags;
import net.Indyuce.mmoitems.stat.PermanentEffects;
import net.Indyuce.mmoitems.stat.Permission;
import net.Indyuce.mmoitems.stat.PickaxePower;
import net.Indyuce.mmoitems.stat.PotionColor;
import net.Indyuce.mmoitems.stat.PotionEffects;
import net.Indyuce.mmoitems.stat.ProjectileParticles;
import net.Indyuce.mmoitems.stat.RandomUnsocket;
import net.Indyuce.mmoitems.stat.RepairPower;
import net.Indyuce.mmoitems.stat.RepairPowerPercent;
import net.Indyuce.mmoitems.stat.RepairType;
import net.Indyuce.mmoitems.stat.RequiredBiomes;
import net.Indyuce.mmoitems.stat.RequiredClass;
import net.Indyuce.mmoitems.stat.RequiredLevel;
import net.Indyuce.mmoitems.stat.RestoreFood;
import net.Indyuce.mmoitems.stat.RestoreHealth;
import net.Indyuce.mmoitems.stat.RestoreMana;
import net.Indyuce.mmoitems.stat.RestoreSaturation;
import net.Indyuce.mmoitems.stat.RestoreStamina;
import net.Indyuce.mmoitems.stat.RevisionID;
import net.Indyuce.mmoitems.stat.ShieldPatternStat;
import net.Indyuce.mmoitems.stat.SkullTextureStat;
import net.Indyuce.mmoitems.stat.SoulbindingBreakChance;
import net.Indyuce.mmoitems.stat.SoulbindingChance;
import net.Indyuce.mmoitems.stat.Soulbound;
import net.Indyuce.mmoitems.stat.SoulboundLevel;
import net.Indyuce.mmoitems.stat.StaffSpiritStat;
import net.Indyuce.mmoitems.stat.StoredTags;
import net.Indyuce.mmoitems.stat.SuccessRate;
import net.Indyuce.mmoitems.stat.Unbreakable;
import net.Indyuce.mmoitems.stat.Unstackable;
import net.Indyuce.mmoitems.stat.UpgradeStat;
import net.Indyuce.mmoitems.stat.VanillaEatingAnimation;
import net.Indyuce.mmoitems.stat.block.BlockID;
import net.Indyuce.mmoitems.stat.block.GenTemplate;
import net.Indyuce.mmoitems.stat.block.MaxXP;
import net.Indyuce.mmoitems.stat.block.MinXP;
import net.Indyuce.mmoitems.stat.block.RequiredPower;
import net.Indyuce.mmoitems.stat.type.BooleanStat;
import net.Indyuce.mmoitems.stat.type.DisableStat;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import org.bukkit.Material;

public class ItemStats {
  public static final ItemStat REVISION_ID = (ItemStat)new RevisionID();
  
  public static final ItemStat MATERIAL = (ItemStat)new MaterialStat();
  
  public static final ItemStat DURABILITY = (ItemStat)new ItemDamage();
  
  public static final ItemStat CUSTOM_MODEL_DATA = (ItemStat)new CustomModelData();
  
  public static final ItemStat MAX_DURABILITY = (ItemStat)new MaximumDurability();
  
  public static final ItemStat WILL_BREAK = (ItemStat)new LostWhenBroken();
  
  public static final ItemStat NAME = (ItemStat)new DisplayName();
  
  public static final ItemStat LORE = (ItemStat)new Lore();
  
  public static final ItemStat NBT_TAGS = (ItemStat)new NBTTags();
  
  public static final ItemStat LORE_FORMAT = (ItemStat)new LoreFormat();
  
  public static final ItemStat BLOCK_ID = (ItemStat)new BlockID();
  
  public static final ItemStat REQUIRED_POWER = (ItemStat)new RequiredPower();
  
  public static final ItemStat MIN_XP = (ItemStat)new MinXP();
  
  public static final ItemStat MAX_XP = (ItemStat)new MaxXP();
  
  public static final ItemStat GEN_TEMPLATE = (ItemStat)new GenTemplate();
  
  public static final ItemStat DISPLAYED_TYPE = (ItemStat)new StringStat("DISPLAYED_TYPE", VersionMaterial.OAK_SIGN.toMaterial(), "Displayed Type", new String[] { "This option will only affect the", "type displayed on the item lore." }, new String[] { "all" }, new Material[0]);
  
  public static final ItemStat ENCHANTS = (ItemStat)new Enchants();
  
  public static final ItemStat HIDE_ENCHANTS = (ItemStat)new HideEnchants();
  
  public static final ItemStat PERMISSION = (ItemStat)new Permission();
  
  public static final ItemStat ITEM_PARTICLES = (ItemStat)new ItemParticles();
  
  public static final ItemStat ARROW_PARTICLES = (ItemStat)new ArrowParticles();
  
  public static final ItemStat PROJECTILE_PARTICLES = (ItemStat)new ProjectileParticles();
  
  public static final ItemStat DISABLE_INTERACTION = (ItemStat)new DisableStat("INTERACTION", VersionMaterial.GRASS_BLOCK.toMaterial(), "Disable Interaction", new String[] { "!block", "all" }, new String[] { "Disable any unwanted interaction:", "block placement, item use..." });
  
  public static final ItemStat DISABLE_CRAFTING = (ItemStat)new DisableStat("CRAFTING", VersionMaterial.CRAFTING_TABLE.toMaterial(), "Disable Crafting", new String[] { "Players can't use this item while crafting." }), DISABLE_SMELTING = (ItemStat)new DisableStat("SMELTING", Material.FURNACE, "Disable Smelting", new String[] { "Players can't use this item in furnaces." });
  
  public static final ItemStat DISABLE_SMITHING = (ItemStat)new DisableStat("SMITHING", Material.DAMAGED_ANVIL, "Disable Smithing", new String[] { "Players can't smith this item in smithing tables." });
  
  public static final ItemStat DISABLE_ENCHANTING = (ItemStat)new DisableStat("ENCHANTING", VersionMaterial.ENCHANTING_TABLE.toMaterial(), "Disable Enchanting", new String[] { "!block", "all" }, new String[] { "Players can't enchant this item." });
  
  public static final ItemStat DISABLE_ADVANCED_ENCHANTS = (ItemStat)new DisableAdvancedEnchantments();
  
  public static final ItemStat DISABLE_REPAIRING = (ItemStat)new DisableStat("REPAIRING", Material.ANVIL, "Disable Repairing", new String[] { "!block", "all" }, new String[] { "Players can't use this item in anvils." });
  
  public static final ItemStat DISABLE_ARROW_SHOOTING = (ItemStat)new DisableStat("ARROW_SHOOTING", Material.ARROW, "Disable Arrow Shooting", new Material[] { Material.ARROW }, new String[] { "Players can't shoot this", "item using a bow." });
  
  public static final ItemStat DISABLE_ATTACK_PASSIVE = (ItemStat)new DisableStat("ATTACK_PASSIVE", Material.BARRIER, "Disable Attack Passive", new String[] { "piercing", "slashing", "blunt" }, new String[] { "Disables the blunt/slashing/piercing", "passive effects on attacks." });
  
  public static final ItemStat REQUIRED_LEVEL = (ItemStat)new RequiredLevel();
  
  public static final ItemStat REQUIRED_CLASS = (ItemStat)new RequiredClass();
  
  public static final ItemStat ATTACK_DAMAGE = (ItemStat)new AttackDamage();
  
  public static final ItemStat ATTACK_SPEED = (ItemStat)new AttackSpeed();
  
  public static final ItemStat CRITICAL_STRIKE_CHANCE = (ItemStat)new DoubleStat("CRITICAL_STRIKE_CHANCE", Material.NETHER_STAR, "Critical Strike Chance", new String[] { "Critical Strikes deal more damage.", "In % chance." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat CRITICAL_STRIKE_POWER = (ItemStat)new DoubleStat("CRITICAL_STRIKE_POWER", Material.NETHER_STAR, "Critical Strike Power", new String[] { "The extra damage weapon crits deals.", "(Stacks with default value)", "In %." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat BLOCK_POWER = (ItemStat)new DoubleStat("BLOCK_POWER", Material.IRON_HELMET, "Block Power", new String[] { "The % of the damage your", "armor/shield can block.", "Default: 25%" }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat BLOCK_RATING = (ItemStat)new DoubleStat("BLOCK_RATING", Material.IRON_HELMET, "Block Rating", new String[] { "The chance your piece of armor", "has to block any entity attack." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat BLOCK_COOLDOWN_REDUCTION = (ItemStat)new DoubleStat("BLOCK_COOLDOWN_REDUCTION", Material.IRON_HELMET, "Block Cooldown Reduction", new String[] { "Reduces the blocking cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat DODGE_RATING = (ItemStat)new DoubleStat("DODGE_RATING", Material.FEATHER, "Dodge Rating", new String[] { "The chance to dodge an attack.", "Dodging completely negates", "the attack damage." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat DODGE_COOLDOWN_REDUCTION = (ItemStat)new DoubleStat("DODGE_COOLDOWN_REDUCTION", Material.FEATHER, "Dodge Cooldown Reduction", new String[] { "Reduces the dodging cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat PARRY_RATING = (ItemStat)new DoubleStat("PARRY_RATING", Material.BUCKET, "Parry Rating", new String[] { "The chance to parry an attack.", "Parrying negates the damage", "and knocks the attacker back." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat PARRY_COOLDOWN_REDUCTION = (ItemStat)new DoubleStat("PARRY_COOLDOWN_REDUCTION", Material.BUCKET, "Parry Cooldown Reduction", new String[] { "Reduces the parrying cooldown (%)." }, new String[] { "!miscellaneous", "!block", "all" }, new Material[0]);
  
  public static final ItemStat COOLDOWN_REDUCTION = (ItemStat)new DoubleStat("COOLDOWN_REDUCTION", Material.BOOK, "Cooldown Reduction", new String[] { "Reduces cooldowns of item and player skills (%)." });
  
  public static final ItemStat RANGE = (ItemStat)new DoubleStat("RANGE", Material.STICK, "Range", new String[] { "The range of your item attacks." }, new String[] { "staff", "whip", "wand", "musket" }, new Material[0]);
  
  public static final ItemStat MANA_COST = (ItemStat)new DoubleStat("MANA_COST", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Mana Cost", new String[] { "Mana spent by your weapon to be used." }, new String[] { "piercing", "slashing", "blunt", "range" }, new Material[0]);
  
  public static final ItemStat STAMINA_COST = (ItemStat)new DoubleStat("STAMINA_COST", VersionMaterial.LIGHT_GRAY_DYE.toMaterial(), "Stamina Cost", new String[] { "Stamina spent by your weapon to be used." }, new String[] { "piercing", "slashing", "blunt", "range" }, new Material[0]);
  
  public static final ItemStat ARROW_VELOCITY = (ItemStat)new DoubleStat("ARROW_VELOCITY", Material.ARROW, "Arrow Velocity", new String[] { "Determins how far your", "weapon can shoot.", "Default: 1.0" }, new String[] { "bow", "crossbow" }, new Material[0]);
  
  public static final ItemStat ARROW_POTION_EFFECTS = (ItemStat)new ArrowPotionEffects();
  
  public static final ItemStat PVE_DAMAGE = (ItemStat)new DoubleStat("PVE_DAMAGE", VersionMaterial.PORKCHOP.toMaterial(), "PvE Damage", new String[] { "Additional damage against", "non human entities in %." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "gem_stone", "accessory" }, new Material[0]);
  
  public static final ItemStat PVP_DAMAGE = (ItemStat)new DoubleStat("PVP_DAMAGE", VersionMaterial.SKELETON_SKULL.toMaterial(), "PvP Damage", new String[] { "Additional damage", "against players in %." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool", "armor", "gem_stone", "accessory" }, new Material[0]);
  
  public static final ItemStat BLUNT_POWER = (ItemStat)new DoubleStat("BLUNT_POWER", Material.IRON_AXE, "Blunt Power", new String[] { "The radius of the AoE attack.", "If set to 2.0, enemies within 2 blocks", "around your target will take damage." }, new String[] { "blunt", "gem_stone" }, new Material[0]);
  
  public static final ItemStat BLUNT_RATING = (ItemStat)new DoubleStat("BLUNT_RATING", Material.BRICK, "Blunt Rating", new String[] { "The force of the blunt attack.", "If set to 50%, enemies hit by the attack", "will take 50% of the initial damage." }, new String[] { "blunt", "gem_stone" }, new Material[0]);
  
  public static final ItemStat WEAPON_DAMAGE = (ItemStat)new DoubleStat("WEAPON_DAMAGE", Material.IRON_SWORD, "Weapon Damage", new String[] { "Additional on-hit weapon damage in %." });
  
  public static final ItemStat SKILL_DAMAGE = (ItemStat)new DoubleStat("SKILL_DAMAGE", Material.BOOK, "Skill Damage", new String[] { "Additional ability damage in %." });
  
  public static final ItemStat PROJECTILE_DAMAGE = (ItemStat)new DoubleStat("PROJECTILE_DAMAGE", Material.ARROW, "Projectile Damage", new String[] { "Additional skill/weapon projectile damage." });
  
  public static final ItemStat MAGIC_DAMAGE = (ItemStat)new DoubleStat("MAGIC_DAMAGE", Material.MAGMA_CREAM, "Magic Damage", new String[] { "Additional magic skill damage in %." });
  
  public static final ItemStat PHYSICAL_DAMAGE = (ItemStat)new DoubleStat("PHYSICAL_DAMAGE", Material.IRON_AXE, "Physical Damage", new String[] { "Additional skill/weapon physical damage." });
  
  public static final ItemStat DEFENSE = (ItemStat)new DoubleStat("DEFENSE", Material.SHIELD, "Defense", new String[] { "Reduces damage from any source.", "Formula can be set in MMOLib Config." });
  
  public static final ItemStat DAMAGE_REDUCTION = (ItemStat)new DoubleStat("DAMAGE_REDUCTION", Material.IRON_CHESTPLATE, "Damage Reduction", new String[] { "Reduces damage from any source.", "In %." });
  
  public static final ItemStat FALL_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("FALL_DAMAGE_REDUCTION", Material.FEATHER, "Fall Damage Reduction", new String[] { "Reduces fall damage.", "In %." });
  
  public static final ItemStat PROJECTILE_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("PROJECTILE_DAMAGE_REDUCTION", VersionMaterial.SNOWBALL.toMaterial(), "Projectile Damage Reduction", new String[] { "Reduces projectile damage.", "In %." });
  
  public static final ItemStat PHYSICAL_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("PHYSICAL_DAMAGE_REDUCTION", Material.LEATHER_CHESTPLATE, "Physical Damage Reduction", new String[] { "Reduces physical damage.", "In %." });
  
  public static final ItemStat FIRE_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("FIRE_DAMAGE_REDUCTION", Material.BLAZE_POWDER, "Fire Damage Reduction", new String[] { "Reduces fire damage.", "In %." });
  
  public static final ItemStat MAGIC_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("MAGIC_DAMAGE_REDUCTION", Material.POTION, "Magic Damage Reduction", new String[] { "Reduce magic damage dealt by potions.", "In %." });
  
  public static final ItemStat PVE_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("PVE_DAMAGE_REDUCTION", VersionMaterial.PORKCHOP.toMaterial(), "PvE Damage Reduction", new String[] { "Reduces damage dealt by mobs.", "In %." });
  
  public static final ItemStat PVP_DAMAGE_REDUCTION = (ItemStat)new DoubleStat("PVP_DAMAGE_REDUCTION", VersionMaterial.SKELETON_SKULL.toMaterial(), "PvP Damage Reduction", new String[] { "Reduces damage dealt by players", "In %." });
  
  public static final ItemStat UNDEAD_DAMAGE = (ItemStat)new DoubleStat("UNDEAD_DAMAGE", VersionMaterial.SKELETON_SKULL.toMaterial(), "Undead Damage", new String[] { "Deals additional damage to undead.", "In %." });
  
  public static final ItemStat UNBREAKABLE = (ItemStat)new Unbreakable();
  
  public static final ItemStat TIER = (ItemStat)new ItemTierStat();
  
  public static final ItemStat SET = (ItemStat)new ItemSetStat();
  
  public static final ItemStat ARMOR = (ItemStat)new Armor();
  
  public static final ItemStat ARMOR_TOUGHNESS = (ItemStat)new ArmorToughness();
  
  public static final ItemStat MAX_HEALTH = (ItemStat)new MaxHealth();
  
  public static final ItemStat UNSTACKABLE = (ItemStat)new Unstackable();
  
  public static final ItemStat MAX_MANA = (ItemStat)new DoubleStat("MAX_MANA", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Max Mana", new String[] { "Adds mana to your max mana bar." });
  
  public static final ItemStat KNOCKBACK_RESISTANCE = (ItemStat)new KnockbackResistance();
  
  public static final ItemStat MOVEMENT_SPEED = (ItemStat)new MovementSpeed();
  
  public static final ItemStat TWO_HANDED = (ItemStat)new BooleanStat("TWO_HANDED", Material.IRON_INGOT, "Two Handed", new String[] { "If set to true, a player will be", "significantly slower if holding two", "items, one being Two Handed." }, new String[] { "piercing", "slashing", "blunt", "offhand", "range", "tool" }, new Material[0]);
  
  public static final ItemStat EQUIP_PRIORITY = (ItemStat)new DoubleStat("EQUIP_PRIORITY", VersionMaterial.DIAMOND_HORSE_ARMOR.toMaterial(), "Equip Priority", new String[] { "Sets the level of priority this item has for the", "right click to swap equipped armor feature." });
  
  public static final ItemStat REQUIRED_BIOMES = (ItemStat)new RequiredBiomes();
  
  public static final ItemStat DROP_ON_DEATH = (ItemStat)new DisableDeathDrop();
  
  public static final ItemStat DURABILITY_BAR = (ItemStat)new DurabilityBar();
  
  public static final ItemStat PERM_EFFECTS = (ItemStat)new PermanentEffects();
  
  public static final ItemStat GRANTED_PERMISSIONS = (ItemStat)new GrantedPermissions();
  
  public static final ItemStat RESTORE_HEALTH = (ItemStat)new RestoreHealth();
  
  public static final ItemStat RESTORE_FOOD = (ItemStat)new RestoreFood();
  
  public static final ItemStat RESTORE_SATURATION = (ItemStat)new RestoreSaturation();
  
  public static final ItemStat RESTORE_MANA = (ItemStat)new RestoreMana();
  
  public static final ItemStat RESTORE_STAMINA = (ItemStat)new RestoreStamina();
  
  public static final ItemStat CAN_IDENTIFY = (ItemStat)new CanIdentify();
  
  public static final ItemStat CAN_DECONSTRUCT = (ItemStat)new CanDeconstruct();
  
  public static final ItemStat CAN_DESKIN = (ItemStat)new CanDeskin();
  
  public static final ItemStat EFFECTS = (ItemStat)new Effects();
  
  public static final ItemStat SOULBINDING_CHANCE = (ItemStat)new SoulbindingChance();
  
  public static final ItemStat SOULBOUND_BREAK_CHANCE = (ItemStat)new SoulbindingBreakChance();
  
  public static final ItemStat SOULBOUND_LEVEL = (ItemStat)new SoulboundLevel();
  
  public static final ItemStat AUTO_SOULBIND = (ItemStat)new BooleanStat("AUTO_SOULBIND", VersionMaterial.ENDER_EYE.toMaterial(), "Auto-Soulbind", new String[] { "Automatically soulbinds this item to", "a player when he acquires it." }, new String[] { "!consumable", "all" }, new Material[0]);
  
  public static final ItemStat ITEM_COOLDOWN = (ItemStat)new DoubleStat("ITEM_COOLDOWN", Material.COOKED_CHICKEN, "Item Cooldown", new String[] { "This cooldown applies for consumables", "as well as for item commands." }, new String[] { "!armor", "!gem_stone", "!block", "all" }, new Material[0]);
  
  public static final ItemStat VANILLA_EATING_ANIMATION = (ItemStat)new VanillaEatingAnimation();
  
  public static final ItemStat GEM_COLOR = (ItemStat)new GemColor();
  
  public static final ItemStat GEM_UPGRADE_SCALING = (ItemStat)new GemUpgradeScaling();
  
  public static final ItemStat ITEM_TYPE_RESTRICTION = (ItemStat)new ItemTypeRestriction();
  
  public static final ItemStat MAX_CONSUME = (ItemStat)new DoubleStat("MAX_CONSUME", Material.BLAZE_POWDER, "Max Consume", new String[] { "Max amount of usage before", "item disappears." }, new String[] { "consumable" }, new Material[0]);
  
  public static final ItemStat SUCCESS_RATE = (ItemStat)new SuccessRate();
  
  public static final ItemStat CRAFTING = (ItemStat)new Crafting();
  
  public static final ItemStat CRAFT_PERMISSION = (ItemStat)new StringStat("CRAFT_PERMISSION", VersionMaterial.OAK_SIGN.toMaterial(), "Crafting Recipe Permission", new String[] { "The permission needed to craft this item.", "Changing this value requires &o/mi reload recipes&7." }, new String[] { "all" }, new Material[0]);
  
  public static final ItemStat CRAFT_AMOUNT = (ItemStat)new DoubleStat("CRAFTED_AMOUNT", Material.WOODEN_AXE, "Crafted Amount", new String[] { "The stack count for", "this item when crafted." }, new String[] { "all" }, new Material[0]);
  
  public static final ItemStat AUTOSMELT = (ItemStat)new BooleanStat("AUTOSMELT", Material.COAL, "Autosmelt", new String[] { "If set to true, your tool will", "automaticaly smelt mined ores." }, new String[] { "tool" }, new Material[0]);
  
  public static final ItemStat BOUNCING_CRACK = (ItemStat)new BooleanStat("BOUNCING_CRACK", VersionMaterial.COBBLESTONE_WALL.toMaterial(), "Bouncing Crack", new String[] { "If set to true, your tool will", "also break nearby blocks." }, new String[] { "tool" }, new Material[0]);
  
  public static final ItemStat PICKAXE_POWER = (ItemStat)new PickaxePower();
  
  public static final ItemStat CUSTOM_SOUNDS = (ItemStat)new CustomSounds();
  
  public static final ItemStat ELEMENTS = (ItemStat)new Elements();
  
  public static final ItemStat COMMANDS = (ItemStat)new Commands();
  
  public static final ItemStat STAFF_SPIRIT = (ItemStat)new StaffSpiritStat();
  
  public static final ItemStat LUTE_ATTACK_SOUND = (ItemStat)new LuteAttackSoundStat();
  
  public static final ItemStat LUTE_ATTACK_EFFECT = (ItemStat)new LuteAttackEffectStat();
  
  public static final ItemStat NOTE_WEIGHT = (ItemStat)new DoubleStat("NOTE_WEIGHT", VersionMaterial.MUSIC_DISC_MALL.toMaterial(), "Note Weight", new String[] { "Defines how the projectile cast", "by your lute tilts downwards." }, new String[] { "lute" }, new Material[0]);
  
  public static final ItemStat REMOVE_ON_CRAFT = (ItemStat)new BooleanStat("REMOVE_ON_CRAFT", Material.GLASS_BOTTLE, "Remove on Craft", new String[] { "If the item should be completely", "removed when used in a recipe,", "or if it should become an", "empty bottle or bucket." }, new String[] { "all" }, new Material[] { Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.MILK_BUCKET, Material.LAVA_BUCKET, Material.WATER_BUCKET });
  
  public static final ItemStat COMPATIBLE_TYPES = (ItemStat)new CompatibleTypes();
  
  public static final ItemStat COMPATIBLE_IDS = (ItemStat)new CompatibleIds();
  
  public static final ItemStat GEM_SOCKETS = (ItemStat)new GemSockets();
  
  public static final ItemStat RANDOM_UNSOCKET = (ItemStat)new RandomUnsocket();
  
  public static final ItemStat REPAIR = (ItemStat)new RepairPower();
  
  public static final ItemStat REPAIR_PERCENT = (ItemStat)new RepairPowerPercent();
  
  public static final ItemStat REPAIR_TYPE = (ItemStat)new RepairType();
  
  public static final ItemStat INEDIBLE = (ItemStat)new BooleanStat("INEDIBLE", Material.POISONOUS_POTATO, "Inedible", new String[] { "Players won't be able to right-click this consumable.", "", "No effects of it will take place." }, new String[] { "consumable" }, new Material[0]);
  
  public static final ItemStat DISABLE_RIGHT_CLICK_CONSUME = (ItemStat)new DisableStat("RIGHT_CLICK_CONSUME", Material.BAKED_POTATO, "Infinite Consume", new String[] { "consumable" }, new String[] { "Players will be able to right-click this consumable", "and benefit from its effects, but it won't be consumed." });
  
  public static final ItemStat KNOCKBACK = (ItemStat)new DoubleStat("KNOCKBACK", VersionMaterial.IRON_HORSE_ARMOR.toMaterial(), "Knockback", new String[] { "Using this musket will knock", "the user back if positive." }, new String[] { "musket" }, new Material[0]);
  
  public static final ItemStat RECOIL = (ItemStat)new DoubleStat("RECOIL", VersionMaterial.IRON_HORSE_ARMOR.toMaterial(), "Recoil", new String[] { "Corresponds to the shooting innacuracy." }, new String[] { "musket" }, new Material[0]);
  
  public static final ItemStat HANDWORN = (ItemStat)new BooleanStat("HANDWORN", Material.STRING, "Handworn", new String[] { "This item ignores two-handedness.", "", "Basically for a ring or a glove that you", " can wear and still have your hand free", " to carry a two-handed weapon." }, new String[] { "offhand" }, new Material[0]);
  
  public static final ItemStat AMPHIBIAN = (ItemStat)new Amphibian();
  
  public static final ItemStat ABILITIES = (ItemStat)new Abilities();
  
  public static final ItemStat UPGRADE = (ItemStat)new UpgradeStat();
  
  public static final ItemStat SKULL_TEXTURE = (ItemStat)new SkullTextureStat();
  
  public static final ItemStat DYE_COLOR = (ItemStat)new DyeColor();
  
  public static final ItemStat HIDE_DYE = (ItemStat)new HideDye();
  
  public static final ItemStat POTION_EFFECTS = (ItemStat)new PotionEffects();
  
  public static final ItemStat POTION_COLOR = (ItemStat)new PotionColor();
  
  public static final ItemStat SHIELD_PATTERN = (ItemStat)new ShieldPatternStat();
  
  public static final ItemStat HIDE_POTION_EFFECTS = (ItemStat)new HidePotionEffects();
  
  public static final ItemStat SOULBOUND = (ItemStat)new Soulbound();
  
  public static final ItemStat STORED_TAGS = (ItemStat)new StoredTags();
  
  public static final ItemStat ITEM_LEVEL = (ItemStat)new ItemLevel();
  
  public static final ItemStat INTERNAL_REVISION_ID = (ItemStat)new InternalRevisionID();
}
