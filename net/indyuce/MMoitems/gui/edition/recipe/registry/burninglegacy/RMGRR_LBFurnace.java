// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.gui.edition.recipe.registry.burninglegacy;

import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.recipe.CraftingType;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RMGRR_LegacyBurning;

public class RMGRR_LBFurnace extends RMGRR_LegacyBurning
{
    @NotNull
    @Override
    public CraftingType getLegacyBurningType() {
        return CraftingType.FURNACE;
    }
}
