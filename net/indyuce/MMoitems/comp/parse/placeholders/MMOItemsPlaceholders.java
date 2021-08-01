// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.parse.placeholders;

import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.math.EvaluatedFormula;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.MMOItems;
import java.text.DecimalFormat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class MMOItemsPlaceholders extends PlaceholderExpansion
{
    private final DecimalFormat oneDigit;
    private final DecimalFormat twoDigits;
    
    public MMOItemsPlaceholders() {
        this.oneDigit = new DecimalFormat("0.#");
        this.twoDigits = new DecimalFormat("0.##");
    }
    
    public String getAuthor() {
        return "Indyuce";
    }
    
    public String getIdentifier() {
        return "mmoitems";
    }
    
    public String getVersion() {
        return MMOItems.plugin.getDescription().getVersion();
    }
    
    public boolean persist() {
        return true;
    }
    
    public String onRequest(@Nullable final OfflinePlayer offlinePlayer, @NotNull final String s) {
        if (s.equals("stat_defense_percent")) {
            return this.twoDigits.format(100.0 - this.calculateDefense(MMOPlayerData.get(offlinePlayer))) + "%";
        }
        if (s.startsWith("stat_elements") && offlinePlayer.isOnline()) {
            final String[] split = s.split("_");
            if (split.length > 3) {
                final String string = "MMOITEMS_" + split[2].toUpperCase() + "_" + split[3].toUpperCase();
                double number = 0.0;
                for (final EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    if (this.hasItem((Player)offlinePlayer, equipmentSlot)) {
                        final NBTItem value = NBTItem.get(((Player)offlinePlayer).getInventory().getItem(equipmentSlot));
                        if (value.hasTag(string)) {
                            number += value.getDouble(string);
                        }
                    }
                }
                return this.twoDigits.format(number);
            }
        }
        else if (s.startsWith("stat_")) {
            final ItemStat value2 = MMOItems.plugin.getStats().get(s.substring(5).toUpperCase());
            if (value2 != null) {
                return this.twoDigits.format(PlayerData.get(offlinePlayer).getStats().getStat(value2));
            }
        }
        if (s.startsWith("ability_cd_")) {
            final PlayerData value3 = PlayerData.get(offlinePlayer);
            return value3.hasCooldownInfo(s.substring(11)) ? this.oneDigit.format(value3.getCooldownInfo(s.substring(11)).getRemaining()) : "0";
        }
        if (s.startsWith("type_")) {
            final String upperCase = s.substring(5, s.lastIndexOf("_")).toUpperCase();
            if (!MMOItems.plugin.getTypes().has(upperCase)) {
                return "Invalid type";
            }
            final Type value4 = Type.get(upperCase);
            if ("total".equals(s.substring(6 + upperCase.length()).toLowerCase())) {
                return "" + MMOItems.plugin.getTemplates().getTemplates(value4).size();
            }
            return value4.getName();
        }
        else if (s.startsWith("tier_")) {
            final String upperCase2 = s.substring(5).toUpperCase();
            if (!MMOItems.plugin.getTiers().has(upperCase2)) {
                return "Invalid tier";
            }
            return MMOItems.plugin.getTiers().get(upperCase2).getName();
        }
        else {
            if (!offlinePlayer.isOnline()) {
                return null;
            }
            if (s.equals("durability")) {
                return "" + (int)MythicLib.plugin.getVersion().getWrapper().getNBTItem(offlinePlayer.getPlayer().getInventory().getItemInMainHand()).getDouble("MMOITEMS_DURABILITY");
            }
            if (s.equals("durability_max")) {
                return "" + (int)MythicLib.plugin.getVersion().getWrapper().getNBTItem(offlinePlayer.getPlayer().getInventory().getItemInMainHand()).getDouble("MMOITEMS_MAX_DURABILITY");
            }
            if (s.equals("durability_ratio")) {
                final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(offlinePlayer.getPlayer().getInventory().getItemInMainHand());
                return this.oneDigit.format(nbtItem.getDouble("MMOITEMS_DURABILITY") / nbtItem.getDouble("MMOITEMS_MAX_DURABILITY") * 100.0);
            }
            if (s.equals("durability_bar_square")) {
                return this.getCurrentDurabilityBar(offlinePlayer.getPlayer().getInventory().getItemInMainHand(), "\u2588", 10);
            }
            if (s.equals("durability_bar_diamond")) {
                return this.getCurrentDurabilityBar(offlinePlayer.getPlayer().getInventory().getItemInMainHand(), "\u25c6", 15);
            }
            if (s.equals("durability_bar_thin")) {
                return this.getCurrentDurabilityBar(offlinePlayer.getPlayer().getInventory().getItemInMainHand(), "|", 20);
            }
            return null;
        }
    }
    
    private double calculateDefense(final MMOPlayerData mmoPlayerData) {
        return Math.max(0.0, new EvaluatedFormula(MythicLib.plugin.getConfig().getString("defense-application", "#damage# * (1 - (#defense# / (#defense# + 100)))").replace("#defense#", String.valueOf(mmoPlayerData.getStatMap().getStat("DEFENSE"))).replace("#damage#", String.valueOf(100))).evaluate());
    }
    
    private String getCurrentDurabilityBar(final ItemStack itemStack, final String str, final int n) {
        final NBTItem nbtItem = MythicLib.plugin.getVersion().getWrapper().getNBTItem(itemStack);
        final long round = Math.round(nbtItem.getDouble("MMOITEMS_DURABILITY") / nbtItem.getDouble("MMOITEMS_MAX_DURABILITY") * n);
        final StringBuilder sb = new StringBuilder("" + ChatColor.GREEN);
        for (int i = 0; i < n; ++i) {
            sb.append((i == round) ? ChatColor.WHITE : "").append(str);
        }
        return sb.toString();
    }
    
    private boolean hasItem(final Player player, final EquipmentSlot equipmentSlot) {
        return player.getInventory().getItem(equipmentSlot) != null && player.getInventory().getItem(equipmentSlot).getType() != Material.AIR;
    }
}
