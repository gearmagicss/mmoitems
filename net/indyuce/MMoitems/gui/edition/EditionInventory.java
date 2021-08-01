// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.OfflinePlayer;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.ItemReference;
import java.util.Map;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import java.util.Optional;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.item.template.TemplateModifier;
import net.Indyuce.mmoitems.api.ConfigFile;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.gui.PluginInventory;

public abstract class EditionInventory extends PluginInventory
{
    protected MMOItemTemplate template;
    private final ConfigFile configFile;
    private TemplateModifier editedModifier;
    private ItemStack cachedItem;
    int previousPage;
    @NotNull
    final FriendlyFeedbackProvider ffp;
    
    public EditionInventory(@NotNull final Player player, @NotNull final MMOItemTemplate template) {
        super(player);
        (this.ffp = new FriendlyFeedbackProvider((FriendlyFeedbackPalette)FFPMMOItems.get())).activatePrefix(true, "Edition");
        this.template = template;
        this.configFile = template.getType().getConfigFile();
        player.getOpenInventory();
        if (player.getOpenInventory().getTopInventory().getHolder() instanceof EditionInventory) {
            this.cachedItem = ((EditionInventory)player.getOpenInventory().getTopInventory().getHolder()).cachedItem;
        }
    }
    
    public MMOItemTemplate getEdited() {
        return this.template;
    }
    
    public ConfigurationSection getEditedSection() {
        final ConfigurationSection configurationSection = this.configFile.getConfig().getConfigurationSection(this.template.getId());
        Validate.notNull((Object)configurationSection, "Could not find config section associated to the template '" + this.template.getType().getId() + "." + this.template.getId() + "': make sure the config section name is in capital letters");
        return configurationSection.getConfigurationSection((this.editedModifier == null) ? ".base" : (".modifiers." + this.editedModifier.getId() + ".stats"));
    }
    
    public Optional<RandomStatData> getEventualStatData(final ItemStat itemStat) {
        final Map<ItemStat, RandomStatData> map = (this.editedModifier != null) ? this.editedModifier.getItemData() : this.template.getBaseItemData();
        return map.containsKey(itemStat) ? Optional.of(map.get(itemStat)) : Optional.empty();
    }
    
    public void registerTemplateEdition() {
        this.configFile.registerTemplateEdition(this.template);
        this.template = MMOItems.plugin.getTemplates().getTemplate(this.template.getType(), this.template.getId());
        this.editedModifier = ((this.editedModifier != null) ? this.template.getModifier(this.editedModifier.getId()) : null);
        this.updateCachedItem();
        this.open();
    }
    
    public void updateCachedItem() {
        this.cachedItem = this.template.newBuilder(PlayerData.get((OfflinePlayer)this.getPlayer()).getRPG()).build().newBuilder().build();
    }
    
    public ItemStack getCachedItem() {
        if (this.cachedItem != null) {
            return this.cachedItem;
        }
        this.updateCachedItem();
        return this.cachedItem;
    }
    
    public void addEditionInventoryItems(final Inventory inventory, final boolean b) {
        final ItemStack itemStack = new ItemStack(Material.CHEST);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.setDisplayName(ChatColor.GREEN + "\u2724" + " Get the Item! " + "\u2724");
        final ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.GRAY + "You may also use /mi " + this.template.getType().getId() + " " + this.template.getId());
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.YELLOW + "\u25b8" + " Left click to get the item.");
        lore.add(ChatColor.YELLOW + "\u25b8" + " Right click to get it & reroll its stats.");
        itemMeta.setLore((List)lore);
        itemStack.setItemMeta(itemMeta);
        if (b) {
            final ItemStack itemStack2 = new ItemStack(Material.BARRIER);
            final ItemMeta itemMeta2 = itemStack2.getItemMeta();
            itemMeta2.setDisplayName(ChatColor.GREEN + "\u21e8" + " Back");
            itemStack2.setItemMeta(itemMeta2);
            inventory.setItem(6, itemStack2);
        }
        inventory.setItem(2, itemStack);
        inventory.setItem(4, this.getCachedItem());
    }
    
    public void open(final int previousPage) {
        this.previousPage = previousPage;
        this.open();
    }
    
    public int getPreviousPage() {
        return this.previousPage;
    }
    
    @NotNull
    public FriendlyFeedbackProvider getFFP() {
        return this.ffp;
    }
}
