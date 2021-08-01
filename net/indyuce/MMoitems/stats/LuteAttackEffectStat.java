// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.stat;

import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.SlashLuteAttack;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.BruteLuteAttack;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.SimpleLuteAttack;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.CircularLuteAttack;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.WaveLuteAttack;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.Indyuce.mmoitems.api.interaction.weapon.untargeted.lute.LuteAttackHandler;
import io.lumine.mythic.lib.api.item.ItemTag;
import net.Indyuce.mmoitems.stat.data.type.StatData;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import net.Indyuce.mmoitems.api.edition.StatEdition;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import net.Indyuce.mmoitems.gui.edition.EditionInventory;
import org.bukkit.Material;
import io.lumine.mythic.lib.version.VersionMaterial;
import net.Indyuce.mmoitems.stat.type.StringStat;

public class LuteAttackEffectStat extends StringStat
{
    public LuteAttackEffectStat() {
        super("LUTE_ATTACK_EFFECT", VersionMaterial.DIAMOND_HORSE_ARMOR.toMaterial(), "Lute Attack Effect", new String[] { "Changes how your lute behaves", "when right clicked.", "&9Tip: /mi list lute" }, new String[] { "lute" }, new Material[0]);
    }
    
    @Override
    public void whenClicked(@NotNull final EditionInventory editionInventory, @NotNull final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getAction() == InventoryAction.PICKUP_HALF) {
            editionInventory.getEditedSection().set("lute-attack-effect", (Object)null);
            editionInventory.registerTemplateEdition();
            editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Successfully removed the lute attack effect.");
        }
        else {
            new StatEdition(editionInventory, this, new Object[0]).enable("Write in the chat the text you want.");
        }
    }
    
    @Override
    public void whenInput(@NotNull final EditionInventory editionInventory, @NotNull final String s, final Object... array) {
        final LuteAttackEffect value = LuteAttackEffect.valueOf(s.toUpperCase().replace(" ", "_").replace("-", "_"));
        editionInventory.getEditedSection().set("lute-attack-effect", (Object)value.name());
        editionInventory.registerTemplateEdition();
        editionInventory.getPlayer().sendMessage(MMOItems.plugin.getPrefix() + "Lute attack effect successfully changed to " + value.getDefaultName() + ".");
    }
    
    @Override
    public void whenApplied(@NotNull final ItemStackBuilder itemStackBuilder, @NotNull final StatData statData) {
        final LuteAttackEffect value = LuteAttackEffect.valueOf(statData.toString().toUpperCase().replace(" ", "_").replace("-", "_"));
        itemStackBuilder.addItemTag(new ItemTag("MMOITEMS_LUTE_ATTACK_EFFECT", (Object)value.name()));
        itemStackBuilder.getLore().insert("lute-attack-effect", value.getName());
    }
    
    public enum LuteAttackEffect
    {
        WAVE((LuteAttackHandler)new WaveLuteAttack()), 
        CIRCULAR((LuteAttackHandler)new CircularLuteAttack()), 
        SIMPLE((LuteAttackHandler)new SimpleLuteAttack()), 
        BRUTE((LuteAttackHandler)new BruteLuteAttack()), 
        SLASH((LuteAttackHandler)new SlashLuteAttack());
        
        private final LuteAttackHandler handler;
        
        private LuteAttackEffect(final LuteAttackHandler handler) {
            this.handler = handler;
        }
        
        public LuteAttackHandler getAttack() {
            return this.handler;
        }
        
        public String getDefaultName() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase();
        }
        
        public String getName() {
            return MMOItems.plugin.getLanguage().getLuteAttackEffectName(this);
        }
        
        public static LuteAttackEffect get(final NBTItem nbtItem) {
            try {
                return valueOf(nbtItem.getString("MMOITEMS_LUTE_ATTACK_EFFECT"));
            }
            catch (IllegalArgumentException ex) {
                return null;
            }
        }
        
        private static /* synthetic */ LuteAttackEffect[] $values() {
            return new LuteAttackEffect[] { LuteAttackEffect.WAVE, LuteAttackEffect.CIRCULAR, LuteAttackEffect.SIMPLE, LuteAttackEffect.BRUTE, LuteAttackEffect.SLASH };
        }
        
        static {
            $VALUES = $values();
        }
    }
}
