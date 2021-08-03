// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import java.lang.reflect.Field;
import org.bukkit.inventory.meta.ItemMeta;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import net.Indyuce.mmoitems.MMOUtils;
import org.bukkit.ChatColor;
import net.Indyuce.mmoitems.api.util.message.Message;
import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.lib.api.util.SmartGive;
import net.Indyuce.mmoitems.stat.data.SkullTextureData;
import io.lumine.mythic.lib.version.VersionMaterial;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Damageable;
import net.Indyuce.mmoitems.stat.data.ParticleData;
import net.Indyuce.mmoitems.ItemStats;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.Consumable;
import net.Indyuce.mmoitems.api.player.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.type.ConsumableItemInteraction;
import net.Indyuce.mmoitems.stat.type.BooleanStat;

public class CanDeskin extends BooleanStat implements ConsumableItemInteraction
{
    public CanDeskin() {
        super("CAN_DESKIN", Material.LEATHER, "Can Deskin?", new String[] { "Players can deskin their item", "and get their skin back", "from the item." }, new String[] { "consumable" }, new Material[0]);
    }
    
    @Override
    public boolean handleConsumableEffect(@NotNull final InventoryClickEvent inventoryClickEvent, @NotNull final PlayerData playerData, @NotNull final Consumable consumable, @NotNull final NBTItem nbtItem, final Type type) {
        final String string = nbtItem.getString("MMOITEMS_SKIN_ID");
        final Player player = playerData.getPlayer();
        if (consumable.getNBTItem().getBoolean("MMOITEMS_CAN_DESKIN") && !string.isEmpty()) {
            final String string2 = nbtItem.getString("MMOITEMS_ITEM_ID");
            nbtItem.removeTag(new String[] { "MMOITEMS_HAS_SKIN" });
            nbtItem.removeTag(new String[] { "MMOITEMS_SKIN_ID" });
            final MMOItemTemplate templateOrThrow = MMOItems.plugin.getTemplates().getTemplateOrThrow(type, string2);
            final MMOItem build = templateOrThrow.newBuilder(playerData.getRPG()).build();
            final ItemStack build2 = templateOrThrow.newBuilder(playerData.getRPG()).build().newBuilder().build();
            final int i = build2.getItemMeta().hasCustomModelData() ? build2.getItemMeta().getCustomModelData() : -1;
            if (i != -1) {
                nbtItem.addTag(new ItemTag[] { new ItemTag("CustomModelData", (Object)i) });
            }
            else {
                nbtItem.removeTag(new String[] { "CustomModelData" });
            }
            if (build.hasData(ItemStats.ITEM_PARTICLES)) {
                nbtItem.addTag(new ItemTag[] { new ItemTag("MMOITEMS_ITEM_PARTICLES", (Object)((ParticleData)build.getData(ItemStats.ITEM_PARTICLES)).toJson().toString()) });
            }
            else {
                nbtItem.removeTag(new String[] { "MMOITEMS_ITEM_PARTICLES" });
            }
            final ItemStack item = nbtItem.toItem();
            final ItemMeta itemMeta = item.getItemMeta();
            final ItemMeta itemMeta2 = build2.getItemMeta();
            if (itemMeta.isUnbreakable()) {
                itemMeta.setUnbreakable(itemMeta2.isUnbreakable());
                if (itemMeta instanceof Damageable && itemMeta2 instanceof Damageable) {
                    ((Damageable)itemMeta).setDamage(((Damageable)itemMeta2).getDamage());
                }
            }
            if (itemMeta instanceof LeatherArmorMeta && itemMeta2 instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta)itemMeta).setColor(((LeatherArmorMeta)itemMeta2).getColor());
            }
            if (nbtItem.hasTag("SkullOwner") && item.getType() == VersionMaterial.PLAYER_HEAD.toMaterial() && build2.getType() == VersionMaterial.PLAYER_HEAD.toMaterial()) {
                try {
                    final Field declaredField = ((LeatherArmorMeta)itemMeta).getClass().getDeclaredField("profile");
                    declaredField.setAccessible(true);
                    declaredField.set(itemMeta, ((SkullTextureData)build.getData(ItemStats.SKULL_TEXTURE)).getGameProfile());
                }
                catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                    MMOItems.plugin.getLogger().warning("Could not read skull texture");
                }
            }
            item.setItemMeta(itemMeta);
            item.setType(build2.getType());
            nbtItem.getItem().setAmount(0);
            new SmartGive(player).give(new ItemStack[] { item });
            new SmartGive(player).give(new ItemStack[] { MMOItems.plugin.getTemplates().getTemplateOrThrow(Type.SKIN, string).newBuilder(playerData.getRPG()).build().newBuilder().build() });
            Message.SKIN_REMOVED.format(ChatColor.YELLOW, "#item#", MMOUtils.getDisplayName(item)).send((CommandSender)player);
            return true;
        }
        return false;
    }
}
