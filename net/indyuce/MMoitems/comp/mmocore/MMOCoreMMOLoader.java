// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mmocore;

import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import io.lumine.mythic.lib.version.VersionMaterial;
import java.util.Optional;
import org.bukkit.block.Block;
import net.Indyuce.mmocore.api.block.BlockType;
import net.Indyuce.mmoitems.comp.mmocore.load.SmeltMMOItemExperienceSource;
import net.Indyuce.mmoitems.comp.mmocore.load.MineMIBlockExperienceSource;
import net.Indyuce.mmocore.api.experience.source.type.ExperienceSource;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmoitems.comp.mmocore.load.GetMMOItemObjective;
import net.Indyuce.mmocore.api.quest.objective.Objective;
import org.bukkit.configuration.ConfigurationSection;
import net.Indyuce.mmoitems.comp.mmocore.load.RandomItemDropItem;
import net.Indyuce.mmoitems.comp.mmocore.load.ItemTemplateDropItem;
import net.Indyuce.mmocore.api.droptable.dropitem.DropItem;
import net.Indyuce.mmoitems.comp.mmocore.load.MMOItemTrigger;
import net.Indyuce.mmoitems.api.crafting.trigger.Trigger;
import net.Indyuce.mmoitems.comp.mmocore.crafting.ExperienceCraftingTrigger;
import net.Indyuce.mmoitems.api.crafting.condition.Condition;
import io.lumine.mythic.lib.api.MMOLineConfig;
import net.Indyuce.mmoitems.api.crafting.ConditionalDisplay;
import net.Indyuce.mmoitems.comp.mmocore.crafting.ProfessionCondition;
import net.Indyuce.mmoitems.api.block.CustomBlock;
import java.util.function.Function;
import net.Indyuce.mmoitems.comp.mmocore.load.MMOItemsBlockType;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmocore.api.load.MMOLoader;

public class MMOCoreMMOLoader extends MMOLoader
{
    private static final ItemStat MANA_REGENERATION;
    private static final ItemStat MAX_STAMINA;
    private static final ItemStat STAMINA_REGENERATION;
    private static final ItemStat ADDITIONAL_EXPERIENCE;
    private static final ItemStat HEALTH_REGENERATION;
    
    public MMOCoreMMOLoader() {
        MMOCore.plugin.loadManager.registerLoader((MMOLoader)this);
        MMOCore.plugin.mineManager.registerBlockType(block -> MMOItems.plugin.getCustomBlocks().getFromBlock(block.getBlockData()).map((Function<? super CustomBlock, ?>)MMOItemsBlockType::new));
        MMOItems.plugin.getStats().register(MMOCoreMMOLoader.HEALTH_REGENERATION);
        MMOItems.plugin.getStats().register(MMOCoreMMOLoader.MANA_REGENERATION);
        MMOItems.plugin.getStats().register(MMOCoreMMOLoader.MAX_STAMINA);
        MMOItems.plugin.getStats().register(MMOCoreMMOLoader.STAMINA_REGENERATION);
        MMOItems.plugin.getStats().register(MMOCoreMMOLoader.ADDITIONAL_EXPERIENCE);
        MMOItems.plugin.getCrafting().registerCondition("profession", (Function<MMOLineConfig, Condition>)ProfessionCondition::new, new ConditionalDisplay("&a\u2714 Requires #level# in #profession#", "&c\u2716 Requires #level# in #profession#"));
        MMOItems.plugin.getCrafting().registerTrigger("exp", (Function<MMOLineConfig, Trigger>)ExperienceCraftingTrigger::new);
    }
    
    public net.Indyuce.mmocore.api.droptable.condition.Condition loadCondition(final MMOLineConfig mmoLineConfig) {
        return null;
    }
    
    public net.Indyuce.mmocore.api.quest.trigger.Trigger loadTrigger(final MMOLineConfig mmoLineConfig) {
        if (mmoLineConfig.getKey().equals("mmoitem")) {
            return new MMOItemTrigger(mmoLineConfig);
        }
        return null;
    }
    
    public DropItem loadDropItem(final MMOLineConfig mmoLineConfig) {
        if (mmoLineConfig.getKey().equals("mmoitem") || mmoLineConfig.getKey().equals("mmoitemtemplate")) {
            return new ItemTemplateDropItem(mmoLineConfig);
        }
        if (mmoLineConfig.getKey().equals("miloot")) {
            return new RandomItemDropItem(mmoLineConfig);
        }
        return null;
    }
    
    public Objective loadObjective(final MMOLineConfig mmoLineConfig, final ConfigurationSection configurationSection) {
        if (mmoLineConfig.getKey().equals("getmmoitem")) {
            return new GetMMOItemObjective(configurationSection, mmoLineConfig);
        }
        return null;
    }
    
    public ExperienceSource<?> loadExperienceSource(final MMOLineConfig mmoLineConfig, final Profession profession) {
        if (mmoLineConfig.getKey().equals("minemiblock")) {
            return (ExperienceSource<?>)new MineMIBlockExperienceSource(profession, mmoLineConfig);
        }
        if (mmoLineConfig.getKey().equalsIgnoreCase("smeltmmoitem")) {
            return (ExperienceSource<?>)new SmeltMMOItemExperienceSource(profession, mmoLineConfig);
        }
        return null;
    }
    
    public BlockType loadBlockType(final MMOLineConfig mmoLineConfig) {
        if (mmoLineConfig.getKey().equalsIgnoreCase("miblock") || mmoLineConfig.getKey().equals("mmoitemsblock") || mmoLineConfig.getKey().equals("mmoitem") || mmoLineConfig.getKey().equals("mmoitems")) {
            return (BlockType)new MMOItemsBlockType(mmoLineConfig);
        }
        return null;
    }
    
    static {
        MANA_REGENERATION = new DoubleStat("MANA_REGENERATION", VersionMaterial.LAPIS_LAZULI.toMaterial(), "Mana Regeneration", new String[] { "Increases mana regen." });
        MAX_STAMINA = new DoubleStat("MAX_STAMINA", VersionMaterial.LIGHT_BLUE_DYE.toMaterial(), "Max Stamina", new String[] { "Adds stamina to your max stamina bar." });
        STAMINA_REGENERATION = new DoubleStat("STAMINA_REGENERATION", VersionMaterial.LIGHT_BLUE_DYE.toMaterial(), "Stamina Regeneration", new String[] { "Increases stamina regen." });
        ADDITIONAL_EXPERIENCE = new DoubleStat("ADDITIONAL_EXPERIENCE", VersionMaterial.EXPERIENCE_BOTTLE.toMaterial(), "Additional Experience", new String[] { "Additional MMOCore main class experience in %." });
        HEALTH_REGENERATION = new DoubleStat("HEALTH_REGENERATION", Material.BREAD, "Health Regeneration", new String[] { "Increases MMOCore health regen.", "In %." });
    }
}
