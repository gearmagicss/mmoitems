// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.command.mmoitems.list;

import io.lumine.mythic.lib.MythicLib;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import io.lumine.mythic.lib.mmolibcommands.api.CommandTreeNode;

public class ListCommandTreeNode extends CommandTreeNode
{
    public ListCommandTreeNode(final CommandTreeNode commandTreeNode) {
        super(commandTreeNode, "list");
        this.addChild((CommandTreeNode)new AbilityCommandTreeNode(this));
        this.addChild((CommandTreeNode)new MIAbilityCommandTreeNode(this));
        this.addChild((CommandTreeNode)new MMAbilityCommandTreeNode(this));
        this.addChild((CommandTreeNode)new TPAbilityCommandTreeNode(this));
        this.addChild((CommandTreeNode)new LuteAttackCommandTreeNode(this));
        this.addChild((CommandTreeNode)new StaffSpiritCommandTreeNode(this));
        this.addChild((CommandTreeNode)new TypeCommandTreeNode(this));
    }
    
    public CommandTreeNode.CommandResult execute(final CommandSender commandSender, final String[] array) {
        commandSender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------[" + ChatColor.LIGHT_PURPLE + " MMOItems: lists " + ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "]-----------------");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list type " + ChatColor.WHITE + "shows all item types (sword, axe...)");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list spirit " + ChatColor.WHITE + "shows all available staff spirits");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list lute " + ChatColor.WHITE + "shows all available lute attack effects");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list ability " + ChatColor.WHITE + "shows all available abilities");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list miability " + ChatColor.WHITE + "shows all available MMOItems abilities");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list mmability " + ChatColor.WHITE + "shows all available MythicMobs abilities");
        commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "/mi list tpability " + ChatColor.WHITE + "shows all available Third Party abilities");
        if (commandSender instanceof Player) {
            commandSender.sendMessage("");
            commandSender.sendMessage("Spigot Javadoc Links:");
            MythicLib.plugin.getVersion().getWrapper().sendJson((Player)commandSender, "[{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Materials/Blocks\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}},{\"text\":\" " + ChatColor.LIGHT_PURPLE + "- \"},{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Potion Effects\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}},{\"text\":\" " + ChatColor.LIGHT_PURPLE + "- \"},{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Sounds\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}}]");
            MythicLib.plugin.getVersion().getWrapper().sendJson((Player)commandSender, "[{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Entities/Mobs\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}},{\"text\":\" " + ChatColor.LIGHT_PURPLE + "- \"},{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Enchantments\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}},{\"text\":\" " + ChatColor.LIGHT_PURPLE + "- \"},{\"text\":\"" + ChatColor.UNDERLINE + ChatColor.GREEN + "Particles\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particles.html\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"" + ChatColor.GREEN + "Click to open webpage.\"}]}}}]");
        }
        return CommandTreeNode.CommandResult.SUCCESS;
    }
}
