// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems;

import java.util.function.Consumer;
import net.Indyuce.mmoitems.api.util.MMOItemReforger;
import net.Indyuce.mmoitems.api.util.NumericStatFormula;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ReloadCommandTreeNode extends CommandTreeNode
{
    public ReloadCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "reload");
        this.addChild((CommandTreeNode)new SubReloadCommandTreeNode("recipes", this, this::reloadRecipes));
        this.addChild((CommandTreeNode)new SubReloadCommandTreeNode("stations", this, this::reloadStations));
        this.addChild((CommandTreeNode)new SubReloadCommandTreeNode("all", this, commandSender -> {
            this.reloadMain(commandSender);
            this.reloadRecipes(commandSender);
            this.reloadStations(commandSender);
        }));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        this.reloadMain(commandSender);
        return CommandTreeNode.CommandResult.SUCCESS;
    }
    
    public void reloadMain(final CommandSender commandSender) {
        MMOItems.plugin.getEquipListener().reload();
        MMOItems.plugin.getLanguage().reload();
        MMOItems.plugin.getDropTables().reload();
        MMOItems.plugin.getTypes().reload();
        MMOItems.plugin.getTiers().reload();
        MMOItems.plugin.getSets().reload();
        MMOItems.plugin.getUpgrades().reload();
        MMOItems.plugin.getWorldGen().reload();
        MMOItems.plugin.getCustomBlocks().reload();
        MMOItems.plugin.getLayouts().reload();
        MMOItems.plugin.getFormats().reload();
        MMOItems.plugin.getTemplates().reload();
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + MMOItems.plugin.getName() + " " + MMOItems.plugin.getDescription().getVersion() + " reloaded.");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getTypes().getAll().size() + ChatColor.GRAY + " Item Types");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getTiers().getAll().size() + ChatColor.GRAY + " Item Tiers");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getSets().getAll().size() + ChatColor.GRAY + " Item Sets");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getUpgrades().getAll().size() + ChatColor.GRAY + " Upgrade Templates");
        NumericStatFormula.reload();
        MMOItemReforger.reload();
    }
    
    public void reloadRecipes(final CommandSender commandSender) {
        MMOItems.plugin.getRecipes().reload();
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reloaded recipes.");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + (MMOItems.plugin.getRecipes().getLoadedLegacyRecipes().size() + MMOItems.plugin.getRecipes().getLegacyCustomRecipes().size() + MMOItems.plugin.getRecipes().getCustomRecipes().size()) + ChatColor.GRAY + " Recipes");
    }
    
    public void reloadStations(final CommandSender commandSender) {
        MMOItems.plugin.getLayouts().reload();
        MMOItems.plugin.getCrafting().reload();
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "Successfully reloaded the crafting stations..");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getCrafting().getAll().size() + ChatColor.GRAY + " Crafting Stations");
        commandSender.sendMessage(MMOItems.plugin.getPrefix() + "- " + ChatColor.RED + MMOItems.plugin.getCrafting().countRecipes() + ChatColor.GRAY + " Recipes");
    }
    
    public static class SubReloadCommandTreeNode extends CommandTreeNode
    {
        private final Consumer<CommandSender> action;
        
        public SubReloadCommandTreeNode(final String s, final CommandTreeNode commandTreeNode, final Consumer<CommandSender> action) {
            super(commandTreeNode, s);
            this.action = action;
        }
        
        public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
            this.action.accept(commandSender);
            return CommandTreeNode.CommandResult.SUCCESS;
        }
    }
}
