// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util.message;

import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.MMOItems;

public enum Message
{
    RECEIVED_ITEM("You received &6#item#&e#amount#."), 
    HANDS_TOO_CHARGED("You can't do anything, your hands are too charged."), 
    SPELL_ON_COOLDOWN("#progress# &eYou must wait #left# second#s# before casting this spell."), 
    ITEM_ON_COOLDOWN("This item is on cooldown! Please wait #left#s."), 
    NOT_ENOUGH_PERMS_COMMAND("You don't have enough permissions."), 
    ATTACK_BLOCKED("You just blocked #percent#% of the attack damage!"), 
    ATTACK_DODGED("You just dodged an attack!"), 
    ATTACK_PARRIED("You just parried an attack!"), 
    NOT_ENOUGH_LEVELS("You don't have enough levels to use this item!"), 
    SOULBOUND_RESTRICTION("This item is linked to another player, you can't use it!"), 
    NOT_ENOUGH_PERMS("You don't have enough permissions to use this."), 
    WRONG_CLASS("You don't have the right class!"), 
    NOT_ENOUGH_MANA("You don't have enough mana!"), 
    NOT_ENOUGH_STAMINA("You don't have enough stamina!"), 
    NOT_ENOUGH_ATTRIBUTE("You don't have enough #attribute#!"), 
    NOT_ENOUGH_PROFESSION("You aren't high enough #profession# level!"), 
    UNIDENTIFIED_ITEM("You can't use an unidentified item!"), 
    ZERO_DURABILITY("This item is broken, you first need to repair it."), 
    CANNOT_IDENTIFY_STACKED_ITEMS("You may only identify one item at a time."), 
    SUCCESSFULLY_IDENTIFIED("You successfully identified &6#item#&e."), 
    SUCCESSFULLY_DECONSTRUCTED("You successfully deconstructed &6#item#&e."), 
    GEM_STONE_APPLIED("You successfully applied &6#gem#&e onto your &6#item#&e."), 
    GEM_STONE_BROKE("Your gem stone &6#gem#&c broke while trying to apply it onto &6#item#&c."), 
    REPAIRED_ITEM("You successfully repaired &6#item#&e for &6#amount# &euses."), 
    SKIN_APPLIED("You successfully applied the skin onto your &6#item#&e!"), 
    SKIN_REMOVED("You successfully removed the skin from your &6#item#&e!"), 
    SKIN_BROKE("Your skin broke while trying to apply it onto your &6#item#&c."), 
    SKIN_REJECTED("A skin has already been applied onto your &6#item#&c!"), 
    SKIN_INCOMPATIBLE("This skin is not compatible with your &6#item#&c!"), 
    RANDOM_UNSOCKET_GEM_TOO_OLD("The gems have bonded strongly with your item. Cannot remove."), 
    RANDOM_UNSOCKET_SUCCESS("&aYou removed &3#gem# &afrom your &6#item#&a!"), 
    CANT_BIND_ITEM("This item is currently linked to #player# by a Lvl #level# soulbound. You will have to break this soulbound first."), 
    NO_SOULBOUND("This item is not bound to anyone."), 
    CANT_BIND_STACKED("You can't bind stacked items."), 
    UNSUCCESSFUL_SOULBOUND("Your soulbound failed."), 
    UNSUCCESSFUL_SOULBOUND_BREAK("You couldn't break the soulbound."), 
    LOW_SOULBOUND_LEVEL("This item soulbound is Lvl #level#. You will need a higher soulbound level on your consumable to break this soulbound."), 
    SUCCESSFULLY_BIND_ITEM("You successfully applied a Lvl &6#level# &esoulbound to your &6#item#&e."), 
    SUCCESSFULLY_BREAK_BIND("You successfully broke the Lvl &6#level# &eitem soulbound!"), 
    SOULBOUND_ITEM_LORE("&4Linked to #player#//&4Lvl #level# Soulbound"), 
    CANT_UPGRADED_STACK("You can't upgrade stacked items."), 
    MAX_UPGRADES_HIT("This item cannot be upgraded anymore."), 
    UPGRADE_FAIL("Your upgrade failed and you lost your consumable."), 
    UPGRADE_FAIL_STATION("Your upgrade failed and you lost your materials."), 
    WRONG_UPGRADE_REFERENCE("You cannot upgrade this item with this consumable."), 
    UPGRADE_SUCCESS("You successfully upgraded your &6#item#&e!"), 
    NOT_HAVE_ITEM_UPGRADE("You don't have the item to upgrade!"), 
    UPGRADE_REQUIREMENT_SAFE_CHECK("You would not meet the upgraded item requirements."), 
    NOT_ENOUGH_MATERIALS("You do not have enough materials to craft this item."), 
    CONDITIONS_NOT_MET("You cannot craft this item."), 
    CRAFTING_CANCELED("You cancelled a crafting recipe."), 
    CRAFTING_QUEUE_FULL("The crafting queue is currently full."), 
    STATION_BIG_STACK("Amount Crafted: #size#"), 
    RECIPE_PREVIEW("Recipe Preview"), 
    UNABLE_TO_REPAIR("This item can't be repaired by this consumable!");
    
    private final String defaultMessage;
    private final String path;
    
    private Message(final String defaultMessage) {
        this.defaultMessage = defaultMessage;
        this.path = this.name().toLowerCase().replace("_", "-");
    }
    
    public String getDefault() {
        return this.defaultMessage;
    }
    
    public String getUpdated() {
        return MMOItems.plugin.getLanguage().getMessage(this.path);
    }
    
    public String formatRaw(final ChatColor chatColor, final String... array) {
        return this.format(chatColor, array).toString();
    }
    
    public PlayerMessage format(final ChatColor chatColor, final String... array) {
        return new PlayerMessage(this.getUpdated()).format(chatColor, array);
    }
}
