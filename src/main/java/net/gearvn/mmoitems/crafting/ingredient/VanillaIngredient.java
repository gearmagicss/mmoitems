// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.ingredient;

import io.lumine.mythic.lib.api.util.LegacyComponent;
import io.lumine.mythic.lib.api.item.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.MMOUtils;
import io.lumine.mythic.lib.api.MMOLineConfig;
import org.bukkit.Material;

public class VanillaIngredient extends Ingredient
{
    private final Material material;
    private final String displayName;
    private final String display;
    
    public VanillaIngredient(final MMOLineConfig mmoLineConfig) {
        super("vanilla", mmoLineConfig);
        mmoLineConfig.validate(new String[] { "type" });
        this.material = Material.valueOf(mmoLineConfig.getString("type").toUpperCase().replace("-", "_").replace(" ", "_"));
        this.displayName = (mmoLineConfig.contains("name") ? mmoLineConfig.getString("name") : null);
        this.display = (mmoLineConfig.contains("display") ? mmoLineConfig.getString("display") : MMOUtils.caseOnWords(this.material.name().toLowerCase().replace("_", " ")));
    }
    
    @Override
    public String getKey() {
        return "vanilla:" + this.material.name().toLowerCase() + "_" + this.displayName;
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#item#", this.display).replace("#amount#", "" + this.getAmount());
    }
    
    @NotNull
    @Override
    public ItemStack generateItemStack(@NotNull final RPGPlayer rpgPlayer) {
        final NBTItem value = NBTItem.get(new ItemStack(this.material, this.getAmount()));
        if (this.displayName != null) {
            value.setDisplayNameComponent(LegacyComponent.parse(this.displayName));
        }
        return value.toItem();
    }
}
