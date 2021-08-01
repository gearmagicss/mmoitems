// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.comp.mythicmobs.crafting;

import net.Indyuce.mmoitems.api.crafting.ingredient.inventory.PlayerIngredient;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import java.util.Optional;
import org.apache.commons.lang.Validate;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.mythic.lib.api.MMOLineConfig;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import net.Indyuce.mmoitems.api.crafting.ingredient.Ingredient;

@Deprecated
public class MythicItemIngredient extends Ingredient<MythicItemPlayerIngredient>
{
    private final MythicItem mythicitem;
    private final String display;
    
    public MythicItemIngredient(final MMOLineConfig mmoLineConfig) {
        super("mythicitem", mmoLineConfig);
        mmoLineConfig.validate(new String[] { "item" });
        final Optional item = MythicMobs.inst().getItemManager().getItem(mmoLineConfig.getString("item"));
        Validate.isTrue(item.isPresent(), "Could not find MM Item with ID '" + mmoLineConfig.getString("item") + "'");
        this.display = (mmoLineConfig.contains("display") ? mmoLineConfig.getString("display") : item.get().getDisplayName());
        this.mythicitem = item.get();
    }
    
    @Override
    public String getKey() {
        return "mythicitem:" + this.mythicitem.getInternalName().toLowerCase();
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#item#", this.display).replace("#amount#", "" + this.getAmount());
    }
    
    @Override
    public boolean matches(final MythicItemPlayerIngredient mythicItemPlayerIngredient) {
        return false;
    }
    
    @NotNull
    @Override
    public ItemStack generateItemStack(@NotNull final RPGPlayer rpgPlayer) {
        return BukkitAdapter.adapt(this.mythicitem.generateItemStack(this.getAmount()));
    }
    
    @Override
    public String toString() {
        return this.getKey();
    }
}
