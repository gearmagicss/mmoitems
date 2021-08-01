// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.item.build;

import java.util.UUID;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.stat.type.StatHistory;
import java.util.Iterator;
import io.lumine.mythic.lib.api.util.LegacyComponent;
import org.bukkit.attribute.Attribute;
import com.google.gson.JsonArray;
import io.lumine.mythicenchants.enchants.MythicEnchant;
import org.bukkit.enchantments.Enchantment;
import net.Indyuce.mmoitems.stat.data.EnchantListData;
import net.Indyuce.mmoitems.stat.data.StringListData;
import net.Indyuce.mmoitems.api.Type;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import java.util.logging.Level;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.stat.type.Previewable;
import net.Indyuce.mmoitems.stat.DisplayName;
import net.Indyuce.mmoitems.stat.Enchants;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.Arrays;
import org.bukkit.inventory.ItemFlag;
import java.util.Collection;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Material;
import net.Indyuce.mmoitems.stat.data.MaterialData;
import net.Indyuce.mmoitems.ItemStats;
import java.util.ArrayList;
import org.bukkit.attribute.AttributeModifier;
import io.lumine.mythic.lib.api.item.ItemTag;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;

public class ItemStackBuilder
{
    @NotNull
    private final MMOItem mmoitem;
    private final ItemStack item;
    private final ItemMeta meta;
    private final LoreBuilder lore;
    private final List<ItemTag> tags;
    private static final AttributeModifier fakeModifier;
    public static final String history_keyword = "HSTRY_";
    
    public ItemStackBuilder(@NotNull final MMOItem mmoitem) {
        this.tags = new ArrayList<ItemTag>();
        this.mmoitem = mmoitem;
        this.item = new ItemStack(mmoitem.hasData(ItemStats.MATERIAL) ? ((MaterialData)mmoitem.getData(ItemStats.MATERIAL)).getMaterial() : Material.DIAMOND_SWORD);
        this.lore = new LoreBuilder(mmoitem.hasData(ItemStats.LORE_FORMAT) ? MMOItems.plugin.getFormats().getFormat(mmoitem.getData(ItemStats.LORE_FORMAT).toString()) : MMOItems.plugin.getLanguage().getDefaultLoreFormat());
        (this.meta = this.item.getItemMeta()).addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        this.tags.add(new ItemTag("MMOITEMS_ITEM_TYPE", (Object)mmoitem.getType().getId()));
        this.tags.add(new ItemTag("MMOITEMS_ITEM_ID", (Object)mmoitem.getId()));
    }
    
    public LoreBuilder getLore() {
        return this.lore;
    }
    
    @NotNull
    public MMOItem getMMOItem() {
        return this.mmoitem;
    }
    
    public ItemStack getItemStack() {
        return this.item;
    }
    
    public ItemMeta getMeta() {
        return this.meta;
    }
    
    public void addItemTag(final List<ItemTag> list) {
        this.tags.addAll(list);
    }
    
    public void addItemTag(final ItemTag... a) {
        this.tags.addAll(Arrays.asList(a));
    }
    
    public NBTItem buildNBT() {
        return this.buildNBT(false);
    }
    
    public NBTItem buildNBT(final boolean b) {
        final MMOItem clone = this.mmoitem.clone();
        if (!clone.hasData(ItemStats.ENCHANTS)) {
            clone.setData(ItemStats.ENCHANTS, ItemStats.ENCHANTS.getClearStatData());
        }
        for (final ItemStat itemStat : clone.getStats()) {
            try {
                final StatHistory statHistory = clone.getStatHistory(itemStat);
                final int upgradeLevel = this.mmoitem.getUpgradeLevel();
                if (statHistory != null) {
                    clone.setData(itemStat, statHistory.recalculate(upgradeLevel));
                    if (!statHistory.isClear() || itemStat instanceof Enchants || itemStat instanceof DisplayName) {
                        this.addItemTag(new ItemTag("HSTRY_" + itemStat.getId(), (Object)statHistory.toNBTString()));
                    }
                }
                if (b && itemStat instanceof Previewable) {
                    final MMOItemTemplate template = MMOItems.plugin.getTemplates().getTemplate(clone.getType(), clone.getId());
                    if (template == null) {
                        throw new IllegalArgumentException("MMOItem $r" + clone.getType().getId() + " " + clone.getId() + "$b doesn't exist.");
                    }
                    ((Previewable)itemStat).whenPreviewed(this, clone.getData(itemStat), template.getBaseItemData().get(itemStat));
                }
                else {
                    itemStat.whenApplied(this, clone.getData(itemStat));
                }
            }
            catch (IllegalArgumentException | NullPointerException ex) {
                final Throwable t;
                MMOItems.plugin.getLogger().log(Level.WARNING, FriendlyFeedbackProvider.quickForConsole((FriendlyFeedbackPalette)FFPMMOItems.get(), "An error occurred while trying to generate item '$f{0}$b' with stat '$f{1}$b': {2}", new String[] { clone.getId(), itemStat.getId(), t.getMessage() }));
            }
        }
        if (clone.getType() == Type.GEM_STONE) {
            this.lore.insert("gem-stone-lore", ItemStat.translate("gem-stone-lore"));
        }
        this.lore.insert("item-type", ItemStat.translate("item-type").replace("#", clone.getStats().contains(ItemStats.DISPLAYED_TYPE) ? clone.getData(ItemStats.DISPLAYED_TYPE).toString() : clone.getType().getName()));
        if (clone.hasData(ItemStats.LORE)) {
            final ArrayList<String> list = new ArrayList<String>();
            ((StringListData)clone.getData(ItemStats.LORE)).getList().forEach(s2 -> list.add(this.lore.applySpecialPlaceholders(s2)));
            this.lore.insert("lore", list);
        }
        List<String> list2 = this.lore.build();
        if (MMOItems.plugin.getMythicEnchantsSupport() != null && this.mmoitem.hasData(ItemStats.ENCHANTS)) {
            final ItemStack clone2 = this.item.clone();
            final ItemMeta itemMeta = clone2.getItemMeta();
            itemMeta.setLore((List)list2);
            clone2.setItemMeta(itemMeta);
            final EnchantListData enchantListData = (EnchantListData)this.mmoitem.getData(ItemStats.ENCHANTS);
            for (final Enchantment enchantment : enchantListData.getEnchants()) {
                final int level = enchantListData.getLevel(enchantment);
                if (level != 0 && enchantment instanceof MythicEnchant) {
                    MMOItems.plugin.getMythicEnchantsSupport().handleEnchant(clone2, enchantment, level);
                }
            }
            list2 = (List<String>)clone2.getItemMeta().getLore();
        }
        this.meta.setLore((List)list2);
        final JsonArray jsonArray = new JsonArray();
        list2.forEach(s -> jsonArray.add(s));
        if (jsonArray.size() != 0) {
            this.tags.add(new ItemTag("MMOITEMS_DYNAMIC_LORE", (Object)jsonArray.toString()));
        }
        this.meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, ItemStackBuilder.fakeModifier);
        this.item.setItemMeta(this.meta);
        final NBTItem value = NBTItem.get(this.item);
        if (this.mmoitem.hasData(ItemStats.NAME) && this.meta.hasDisplayName()) {
            value.setDisplayNameComponent(LegacyComponent.parse(this.meta.getDisplayName()));
        }
        return value.addTag((List)this.tags);
    }
    
    public ItemStack build() {
        return this.buildNBT().toItem();
    }
    
    public ItemStack build(final boolean b) {
        return this.buildNBT(b).toItem();
    }
    
    static {
        fakeModifier = new AttributeModifier(UUID.fromString("87851e28-af12-43f6-898e-c62bde6bd0ec"), "mmoitemsDecoy", 0.0, AttributeModifier.Operation.ADD_NUMBER);
    }
}
