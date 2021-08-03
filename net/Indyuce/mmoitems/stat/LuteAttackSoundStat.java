// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class LuteAttackSoundStat extends StringStat
{
    public LuteAttackSoundStat() {
        super("LUTE_ATTACK_SOUND", VersionMaterial.GOLDEN_HORSE_ARMOR.toMaterial(), "Lute Attack Sound", new String[] { "The sound played when", "basic attacking with this lute." }, new String[] { "lute" }, new Material[0]);
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_LUTE_ATTACK_SOUND", (Object)statData.toString().toUpperCase().replace("-", "_").replace(" ", "_")));
    }
}
