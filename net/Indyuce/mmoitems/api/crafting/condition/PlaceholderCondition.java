// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.crafting.condition;

import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmoitems.api.player.PlayerData;
import io.lumine.mythic.lib.api.MMOLineConfig;

public class PlaceholderCondition extends Condition
{
    private final String value;
    private final String placeholder;
    private final String comparator;
    private final String compareTo;
    
    public PlaceholderCondition(final MMOLineConfig mmoLineConfig) {
        super("placeholder");
        mmoLineConfig.validate(new String[] { "placeholder" });
        this.value = mmoLineConfig.getString("placeholder");
        final String[] split = this.value.split("~");
        this.placeholder = split[0];
        this.comparator = split[1];
        this.compareTo = split[2];
    }
    
    @Override
    public boolean isMet(final PlayerData playerData) {
        final String setPlaceholders = PlaceholderAPI.setPlaceholders(playerData.getPlayer(), this.placeholder);
        final String comparator = this.comparator;
        switch (comparator) {
            case "<": {
                return Double.valueOf(setPlaceholders) < Double.valueOf(this.compareTo);
            }
            case "<=": {
                return Double.valueOf(setPlaceholders) <= Double.valueOf(this.compareTo);
            }
            case ">": {
                return Double.valueOf(setPlaceholders) > Double.valueOf(this.compareTo);
            }
            case ">=": {
                return Double.valueOf(setPlaceholders) >= Double.valueOf(this.compareTo);
            }
            case "==":
            case "=": {
                return Double.valueOf(setPlaceholders) == Double.valueOf(this.compareTo);
            }
            case "!=": {
                return Double.valueOf(setPlaceholders) != Double.valueOf(this.compareTo);
            }
            case "equals": {
                return setPlaceholders.equals(this.compareTo);
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public String formatDisplay(final String s) {
        return s.replace("#placeholder#", "" + this.placeholder);
    }
    
    @Override
    public void whenCrafting(final PlayerData playerData) {
    }
}
