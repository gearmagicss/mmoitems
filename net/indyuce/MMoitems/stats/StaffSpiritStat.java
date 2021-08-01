// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.SunfireSpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.ThunderSpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.XRaySpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.LightningSpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.ManaSpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.VoidSpirit;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.NetherSpirit;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.staff.StaffAttackHandler;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.MMOItems;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class StaffSpiritStat extends StringStat
{
    public StaffSpiritStat() {
        super("STAFF_SPIRIT", VersionMaterial.BONE_MEAL.toMaterial(), "Staff Spirit", new String[] { "Spirit changes the texture", "of the magic attack.", "&9Tip: /mi list spirit" }, new String[] { "staff", "wand" }, new Material[0]);
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        try {
            final StaffSpirit value = StaffSpirit.valueOf(s.toUpperCase().replace(" ", "_").replace("-", "_"));
            editionInventory.getEditedSection().set("staff-spirit", (Object)value.name());
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Staff Spirit successfully changed to " + value.getName() + ".");
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage() + " (See all Staff Spirits here: /mi list spirit).");
        }
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final StaffSpirit value = StaffSpirit.valueOf(statData.toString().toUpperCase().replace(" ", "_").replace("-", "_"));
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_STAFF_SPIRIT", (Object)value.name()));
        itemStackBuilder.getLore().insert("staff-spirit", value.getName());
    }
    
    public enum StaffSpirit
    {
        NETHER_SPIRIT("Nether Spirit", "Shoots fire beams.", (StaffAttackHandler)new NetherSpirit()), 
        VOID_SPIRIT("Void Spirit", "Shoots shulker missiles.", (StaffAttackHandler)new VoidSpirit()), 
        MANA_SPIRIT("Mana Spirit", "Summons mana bolts.", (StaffAttackHandler)new ManaSpirit()), 
        LIGHTNING_SPIRIT("Lightning Spirit", "Summons lightning bolts.", (StaffAttackHandler)new LightningSpirit()), 
        XRAY_SPIRIT("X-Ray Spirit", "Fires piercing & powerful X-rays.", (StaffAttackHandler)new XRaySpirit()), 
        THUNDER_SPIRIT("Thunder Spirit", "Fires AoE damaging thunder strikes.", (StaffAttackHandler)new ThunderSpirit()), 
        SUNFIRE_SPIRIT("Sunfire Spirit", "Fires AoE damaging fire comets.", (StaffAttackHandler)new SunfireSpirit());
        
        private final String lore;
        private final StaffAttackHandler handler;
        private final String name;
        
        private StaffSpirit(final String name2, final String lore, final StaffAttackHandler handler) {
            this.name = name2;
            this.lore = lore;
            this.handler = handler;
        }
        
        public static StaffSpirit get(final NBTItem nbtItem) {
            try {
                return valueOf(nbtItem.getString("MMOITEMS_STAFF_SPIRIT"));
            }
            catch (Exception ex) {
                return null;
            }
        }
        
        public String getDefaultName() {
            return this.name;
        }
        
        public String getName() {
            return MMOItems.plugin.getLanguage().getStaffSpiritName(this);
        }
        
        public boolean hasLore() {
            return this.lore != null;
        }
        
        public String getLore() {
            return this.lore;
        }
        
        public StaffAttackHandler getAttack() {
            return this.handler;
        }
        
        private static /* synthetic */ StaffSpirit[] $values() {
            return new StaffSpirit[] { StaffSpirit.NETHER_SPIRIT, StaffSpirit.VOID_SPIRIT, StaffSpirit.MANA_SPIRIT, StaffSpirit.LIGHTNING_SPIRIT, StaffSpirit.XRAY_SPIRIT, StaffSpirit.THUNDER_SPIRIT, StaffSpirit.SUNFIRE_SPIRIT };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
