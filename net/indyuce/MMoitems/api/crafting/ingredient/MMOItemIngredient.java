// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient;

import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;
import net.Indyuce.mmoitems.stat.DisplayName;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.stat.data.MaterialData;
import net.Indyuce.mmoitems.stat.data.random.RandomStatData;
import net.Indyuce.mmoitems.ItemStats;
import org.bukkit.inventory.meta.ItemMeta;
import io.lumine.mythic.lib.MythicLib;
import org.bukkit.inventory.ItemStack;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import io.lumine.mythic.lib.api.util.ui.SilentNumbers;
import net.Indyuce.mmoitems.api.crafting.ConfigMMOItem;
import net.Indyuce.mmoitems.MMOItems;
import io.lumine.mythic.lib.api.MMOLineConfig;
import org.jetbrains.annotations.NotNull;
import io.lumine.mythic.lib.api.util.ui.QuickNumberRange;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.MMOItemPlayerIngredient;

public class MMOItemIngredient extends Ingredient<MMOItemPlayerIngredient>
{
    private final MMOItemTemplate template;
    @NotNull
    private final QuickNumberRange level;
    private final String display;
    
    public MMOItemIngredient(final MMOLineConfig mmoLineConfig) {
        super("mmoitem", mmoLineConfig);
        mmoLineConfig.validate(new String[] { "type", "id" });
        this.template = MMOItems.plugin.getTemplates().getTemplateOrThrow(MMOItems.plugin.getTypes().getOrThrow(mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_")), mmoLineConfig.getString("id"));
        final QuickNumberRange fromString = QuickNumberRange.getFromString(mmoLineConfig.getString("level", ".."));
        if (fromString != null) {
            this.level = fromString;
        }
        else {
            this.level = new QuickNumberRange((Double)null, (Double)null);
        }
        this.display = (mmoLineConfig.contains("display") ? mmoLineConfig.getString("display") : this.findName());
    }
    
    public MMOItemIngredient(final ConfigMMOItem configMMOItem) {
        super("mmoitem", configMMOItem.getAmount());
        this.template = configMMOItem.getTemplate();
        this.level = new QuickNumberRange((Double)null, (Double)null);
        this.display = this.findName();
    }
    
    public MMOItemTemplate getTemplate() {
        return this.template;
    }
    
    @Override
    public String getKey() {
        return "mmoitem:" + this.template.getType().getId().toLowerCase() + ((this.level.hasMax() || this.level.hasMax()) ? ("-" + this.level.toString()) : "") + "_" + this.template.getId().toLowerCase();
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#item#", this.display).replace("#level#", (this.level.hasMax() || this.level.hasMax()) ? ("lvl." + this.level.toString() + " ") : "").replace("#amount#", String.valueOf(this.getAmount()));
    }
    
    @Override
    public boolean matches(final MMOItemPlayerIngredient mmoItemPlayerIngredient) {
        return mmoItemPlayerIngredient.getType().equals(this.template.getType().getId()) && mmoItemPlayerIngredient.getId().equals(this.template.getId()) && (SilentNumbers.floor(this.level.getAsDouble(0.0)) == 0 || this.level.inRange((double)mmoItemPlayerIngredient.getUpgradeLevel()));
    }
    
    @NotNull
    @Override
    public ItemStack generateItemStack(@NotNull final RPGPlayer rpgPlayer) {
        final ItemStack build = this.template.newBuilder(rpgPlayer).build().newBuilder().build(true);
        if (SilentNumbers.floor(this.level.getAsDouble(0.0)) != 0 && build.getItemMeta() != null) {
            final ItemMeta itemMeta = build.getItemMeta();
            itemMeta.setDisplayName(MythicLib.plugin.parseColors(this.findName()));
            build.setItemMeta(itemMeta);
        }
        build.setAmount(this.getAmount());
        return build;
    }
    
    @Override
    public String toString() {
        return this.getKey();
    }
    
    private String findName() {
        String s;
        if (this.template.getBaseItemData().containsKey(ItemStats.NAME)) {
            s = this.template.getBaseItemData().get(ItemStats.NAME).toString().replace("<tier-color>", "").replace("<tier-name>", "").replace("<tier-color-cleaned>", "");
        }
        else if (this.template.getBaseItemData().containsKey(ItemStats.MATERIAL)) {
            s = MMOUtils.caseOnWords(this.template.getBaseItemData().get(ItemStats.MATERIAL).getMaterial().name().toLowerCase().replace("_", " "));
        }
        else {
            s = "Unrecognized Item";
        }
        if (SilentNumbers.floor(this.level.getAsDouble(0.0)) != 0) {
            return DisplayName.appendUpgradeLevel(s, SilentNumbers.floor(this.level.getAsDouble(0.0)));
        }
        return s;
    }
}
