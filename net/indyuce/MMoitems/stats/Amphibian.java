// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import org.bukkit.util.BoundingBox;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.block.Block;
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.ArrayList;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.stat.data.StringData;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.GemStoneStat;
import net.Indyuce.mmoitems.stat.type.ItemRestriction;
import net.Indyuce.mmoitems.stat.type.ChooseStat;

public class Amphibian extends ChooseStat implements ItemRestriction, GemStoneStat
{
    public static final String NORMAL = "UNRESTRICTED";
    public static final String DRY = "DRY";
    public static final String WET = "WET";
    public static final String DAMP = "DAMP";
    public static final String LAVA = "LAVA";
    public static final String MOLTEN = "MOLTEN";
    public static final String LIQUID = "LIQUID";
    public static final String SUBMERGED = "SUBMERGED";
    
    public Amphibian() {
        super("AMPHIBIAN", Material.WATER_BUCKET, "Amphibian", new String[] { "May this item only be used in specific", "environments regarding liquids?" }, new String[] { "!block", "all" }, new Material[0]);
        this.addChoices("UNRESTRICTED", "DRY", "WET", "DAMP", "LAVA", "MOLTEN", "LIQUID", "SUBMERGED");
        this.setHint("UNRESTRICTED", "No liquids dependency");
        this.setHint("DRY", "The item does not work if the player is touching a liquid block.");
        this.setHint("WET", "The only works if the player is touching water (rain does not count).");
        this.setHint("DAMP", "The only works if the player is completely submerged in water.");
        this.setHint("LAVA", "The only works if the player is touching lava.");
        this.setHint("MOLTEN", "The only works if the player is completely submerged in lava.");
        this.setHint("LIQUID", "The only works if the player is touching any liquid.");
        this.setHint("SUBMERGED", "The only works if the player is completely submerged in any liquid.");
    }
    
    @NotNull
    @Override
    public StatData getClearStatData() {
        return new StringData("UNRESTRICTED");
    }
    
    @Override
    public boolean canUse(final RPGPlayer rpgPlayer, final NBTItem nbtItem, final boolean b) {
        if (!nbtItem.hasTag(this.getNBTPath())) {
            return true;
        }
        final ArrayList<ItemTag> list = new ArrayList<ItemTag>();
        if (nbtItem.hasTag(this.getNBTPath())) {
            list.add(ItemTag.getTagAtPath(this.getNBTPath(), nbtItem, SupportedNBTTagValues.STRING));
        }
        final StringData stringData = (StringData)this.getLoadedNBT(list);
        if (stringData == null) {
            return true;
        }
        final String string = stringData.toString();
        switch (string) {
            default: {
                return true;
            }
            case "DRY": {
                final Iterator<Block> iterator = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().isLiquid()) {
                        return false;
                    }
                }
                return true;
            }
            case "WET": {
                final Iterator<Block> iterator2 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().getType().equals((Object)Material.WATER)) {
                        return true;
                    }
                }
                return false;
            }
            case "DAMP": {
                final Iterator<Block> iterator3 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator3.hasNext()) {
                    if (!iterator3.next().getType().equals((Object)Material.WATER)) {
                        return false;
                    }
                }
                return true;
            }
            case "LAVA": {
                final Iterator<Block> iterator4 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator4.hasNext()) {
                    if (iterator4.next().getType().equals((Object)Material.LAVA)) {
                        return true;
                    }
                }
                return false;
            }
            case "MOLTEN": {
                final Iterator<Block> iterator5 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator5.hasNext()) {
                    if (!iterator5.next().getType().equals((Object)Material.LAVA)) {
                        return false;
                    }
                }
                return true;
            }
            case "LIQUID": {
                final Iterator<Block> iterator6 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator6.hasNext()) {
                    if (iterator6.next().isLiquid()) {
                        return true;
                    }
                }
                return false;
            }
            case "SUBMERGED": {
                final Iterator<Block> iterator7 = this.blocksTouchedByPlayer(rpgPlayer.getPlayer()).iterator();
                while (iterator7.hasNext()) {
                    if (!iterator7.next().isLiquid()) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    
    @Override
    public boolean isDynamic() {
        return true;
    }
    
    ArrayList<Block> blocksTouchedByPlayer(@NotNull final Player player) {
        final BoundingBox boundingBox = player.getBoundingBox();
        final ArrayList<Block> list = new ArrayList<Block>();
        for (double minX = boundingBox.getMinX(); minX <= boundingBox.getMaxX(); minX += Math.min(1.0, Math.max(boundingBox.getWidthX() - (minX - boundingBox.getMinX()), 0.001))) {
            for (double minY = boundingBox.getMinY(); minY <= boundingBox.getMaxY(); minY += Math.min(1.0, Math.max(boundingBox.getHeight() - (minY - boundingBox.getMinY()), 0.001))) {
                for (double minZ = boundingBox.getMinZ(); minZ <= boundingBox.getMaxZ(); minZ += Math.min(1.0, Math.max(boundingBox.getWidthZ() - (minZ - boundingBox.getMinZ()), 0.001))) {
                    final Block block = player.getLocation().getWorld().getBlockAt(SilentNumbers.floor(minX), SilentNumbers.floor(minY), SilentNumbers.floor(minZ));
                    if (!list.contains(block)) {
                        list.add(block);
                    }
                }
            }
        }
        return list;
    }
}
