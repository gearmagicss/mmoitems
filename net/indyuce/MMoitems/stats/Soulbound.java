// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Sound;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import java.util.UUID;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem;
import java.util.ArrayList;
import java.util.regex.Pattern;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.SoulboundData;
import net.Indyuce.mmoitems.api.util.message.Message;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import java.util.Optional;
import java.util.List;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.jetbrains.annotations.Nullable;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.InternalStat;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class Soulbound extends ItemStat implements InternalStat, ItemRestriction
{
    public Soulbound() {
        super("SOULBOUND", VersionMaterial.ENDER_EYE.toMaterial(), "Soulbound", new String[0], new String[] { "all" }, new Material[0]);
    }
    
    @Nullable
    @Override
    public RandomStatData whenInitialized(final Object o) {
        return null;
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
    }
    
    @Override
    public void whenDisplayed(final List<String> list, final Optional<RandomStatData> optional) {
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(this.getAppliedNBT(statData));
        itemStackBuilder.getLore().insert("soulbound", Message.SOULBOUND_ITEM_LORE.getUpdated().replace("#player#", ((SoulboundData)statData).getName()).replace("#level#", MMOUtils.intToRoman(((SoulboundData)statData).getLevel())).split(Pattern.quote("//")));
    }
    
    @NotNull
    @Override
    public ArrayList<ItemTag> getAppliedNBT(@NotNull final StatData statData) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        list.add(new ItemTag(this.getNBTPath(), (Object)((SoulboundData)statData).toJson().toString()));
        return list;
    }
    
    @Override
    public void whenLoaded(@NotNull final ReadMMOItem readMMOItem) {
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (readMMOItem.getNBT().hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), readMMOItem.getNBT(), SupportedNBTTagValues.STRING));
        }
        final StatData loadedNBT = this.getLoadedNBT(list);
        if (loadedNBT != null) {
            readMMOItem.setData(this, loadedNBT);
        }
    }
    
    @Nullable
    @Override
    public StatData getLoadedNBT(@NotNull final ArrayList<ItemTag> list) {
        final ItemTag tagAtPath = ItemTag.getTagAtPath(this.getNBTPath(), (ArrayList)list);
        if (tagAtPath != null) {
            try {
                return new SoulboundData(new JsonParser().parse((String)tagAtPath.getValue()).getAsJsonObject());
            }
            catch (JsonSyntaxException ex) {}
            catch (IllegalStateException ex2) {}
        }
        return null;
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new SoulboundData(UUID.fromString("df930b7b-a84d-4f76-90ac-33be6a5b6c88"), "gunging", 0);
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (nbtItem.hasTag(ItemStats.SOULBOUND.getNBTPath()) && !nbtItem.getString(ItemStats.SOULBOUND.getNBTPath()).contains(rpgPlayer.getPlayer().getUniqueId().toString()) && !rpgPlayer.getPlayer().hasPermission("mmoitems.bypass.soulbound")) {
            if (b) {
                final int asInt = new JsonParser().parse(nbtItem.getString(ItemStats.SOULBOUND.getNBTPath())).getAsJsonObject().get("Level").getAsInt();
                Message.SOULBOUND_RESTRICTION.format(ChatColor.RED, new String[0]).send(rpgPlayer.getPlayer(), "cant-use-item");
                rpgPlayer.getPlayer().playSound(rpgPlayer.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.5f);
                rpgPlayer.getPlayer().damage(MMOItems.plugin.getLanguage().soulboundBaseDamage + asInt * MMOItems.plugin.getLanguage().soulboundPerLvlDamage);
            }
            return false;
        }
        return true;
    }
}
